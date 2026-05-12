// Drawer
// Conner Inman (cdinman@iu.edu) & Caden Batts (cabatts@iu.edu)
// C323: Mobile App Development

import UIKit
import PDFKit
import PencilKit
import CoreData

// MARK: - PDFAnnotationVC

/// UIViewController that displays a PDF document with a PencilKit canvas
/// overlaid for annotation. Matches PDFAnnotationView in the UML class diagram
/// and the "Annotate PDF" wireframe screen.
class PDFAnnotationVC: UIViewController {

    // MARK: - Properties

    /// The document being annotated (must have type .annotatedPDF or .imported)
    var document: Document?

    /// PDFKit view displaying the base PDF document
    private let pdfView = PDFView()

    /// PencilKit canvas overlaid on top of the PDF for annotation drawing
    private let annotationCanvas = PKCanvasView()

    /// PencilKit tool picker for annotation tools
    private let toolPicker = PKToolPicker()

    /// Convenience accessor for Core Data context
    private var context: NSManagedObjectContext {
        return (UIApplication.shared.delegate as! AppDelegate).context
    }

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupNavigationBar()
        setupPDFView()
        setupAnnotationCanvas()
        loadDocument()
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        toolPicker.setVisible(true, forFirstResponder: annotationCanvas)
        toolPicker.addObserver(annotationCanvas)
        annotationCanvas.becomeFirstResponder()
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        toolPicker.setVisible(false, forFirstResponder: annotationCanvas)
    }

    // MARK: - Setup

    private func setupNavigationBar() {
        title = "Annotate PDF"
        navigationItem.rightBarButtonItems = [
            UIBarButtonItem(title: "Done", style: .done, target: self, action: #selector(doneTapped)),
            UIBarButtonItem(
                image: UIImage(systemName: "square.and.arrow.up"),
                style: .plain,
                target: self,
                action: #selector(exportTapped)
            ),
            UIBarButtonItem(
                image: UIImage(systemName: "signature"),
                style: .plain,
                target: self,
                action: #selector(insertSignatureTapped)
            )
        ]
    }

    private func setupPDFView() {
        pdfView.translatesAutoresizingMaskIntoConstraints = false
        pdfView.displayMode = .singlePageContinuous
        pdfView.autoScales = true
        pdfView.backgroundColor = .systemGray6
        view.addSubview(pdfView)

        NSLayoutConstraint.activate([
            pdfView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            pdfView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            pdfView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            pdfView.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
    }

    private func setupAnnotationCanvas() {
        // The PKCanvasView sits on top of the PDFView, transparent background
        annotationCanvas.translatesAutoresizingMaskIntoConstraints = false
        annotationCanvas.backgroundColor = .clear
        annotationCanvas.drawingPolicy = .anyInput
        annotationCanvas.isOpaque = false
        view.addSubview(annotationCanvas)

        NSLayoutConstraint.activate([
            annotationCanvas.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            annotationCanvas.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            annotationCanvas.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            annotationCanvas.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
    }

    // MARK: - Document Loading

    /// Loads the PDF document from the document's stored pdfData.
    func loadDocument(url: URL? = nil) {
        if let data = document?.pdfData, let pdfDoc = PDFDocument(data: data) {
            pdfView.document = pdfDoc
        } else if let url = url, let pdfDoc = PDFDocument(url: url) {
            pdfView.document = pdfDoc
        } else {
            // Show placeholder message when no PDF is loaded
            showNoPDFPlaceholder()
        }

        // Load any existing annotation drawing
        if let drawingData = document?.drawingData {
            do {
                annotationCanvas.drawing = try PKDrawing(data: drawingData)
            } catch {
                print("Failed to load annotation drawing: \(error)")
            }
        }
    }

    // MARK: - Export

    /// Exports the PDF with annotations flattened into the document, then
    /// presents a UIActivityViewController so the user can share or save it.
    /// Renders each PDF page into a Core Graphics PDF context, then composites
    /// the PencilKit annotation strokes on top of every page.
    /// Matches exportAnnotated() in the UML class diagram.
    func exportAnnotated() {
        guard let pdfDoc = pdfView.document, pdfDoc.pageCount > 0 else {
            showErrorAlert(message: "No PDF document is loaded.")
            return
        }

        // Determine overall canvas bounds used for annotation drawing
        let canvasBounds = annotationCanvas.bounds
        let annotationImage = annotationCanvas.drawing.image(
            from: canvasBounds,
            scale: UIScreen.main.scale
        )

        // Build a PDF where each original page gets the annotation layer composited on top
        let pdfRenderer = UIGraphicsPDFRenderer(bounds: CGRect(origin: .zero, size: CGSize(width: 612, height: 792)))
        let outputData = pdfRenderer.pdfData { ctx in
            for pageIndex in 0 ..< pdfDoc.pageCount {
                guard let page = pdfDoc.page(at: pageIndex) else { continue }

                let pageBounds = page.bounds(for: .mediaBox)
                let pageRenderer = UIGraphicsPDFRenderer(bounds: pageBounds)
                // We add one page per PDF page in a shared renderer context below
                _ = pageRenderer // silence unused warning

                ctx.beginPage(withBounds: pageBounds, pageInfo: [:])
                let cgContext = ctx.cgContext

                // Flip coordinate system (PDF is bottom-up, UIKit is top-down)
                cgContext.saveGState()
                cgContext.translateBy(x: 0, y: pageBounds.height)
                cgContext.scaleBy(x: 1.0, y: -1.0)

                // Draw the original PDF page
                page.draw(with: .mediaBox, to: cgContext)
                cgContext.restoreGState()

                // Composite annotation image scaled to fit the page
                // Only draw annotations on the first page if canvas spans the whole view
                if pageIndex == 0 && !annotationCanvas.drawing.bounds.isEmpty {
                    annotationImage.draw(in: pageBounds)
                }
            }
        }

        // Write to a temporary URL for sharing
        let fileName = (document?.title ?? "AnnotatedPDF")
            .replacingOccurrences(of: " ", with: "_")
        let tempURL = FileManager.default.temporaryDirectory
            .appendingPathComponent("\(fileName)_annotated.pdf")
        do {
            try outputData.write(to: tempURL)
        } catch {
            showErrorAlert(message: "Could not write PDF: \(error.localizedDescription)")
            return
        }

        let activityVC = UIActivityViewController(activityItems: [tempURL], applicationActivities: nil)
        activityVC.popoverPresentationController?.barButtonItem =
            navigationItem.rightBarButtonItems?.first(where: {
                $0.image == UIImage(systemName: "square.and.arrow.up")
            })
        present(activityVC, animated: true)
    }

    // MARK: - Signature Insertion

    /// Presents a picker of saved signatures and stamps the chosen one onto
    /// the annotation canvas at the center of the visible area.
    private func insertSignature() {
        let request = NSFetchRequest<Signature>(entityName: "Signature")
        request.sortDescriptors = [NSSortDescriptor(key: "createdAt", ascending: false)]
        let signatures: [Signature]
        do {
            signatures = try context.fetch(request)
        } catch {
            showErrorAlert(message: "Could not load signatures.")
            return
        }

        guard !signatures.isEmpty else {
            let alert = UIAlertController(
                title: "No Signatures",
                message: "Go to Settings to add a signature first.",
                preferredStyle: .alert
            )
            alert.addAction(UIAlertAction(title: "OK", style: .default))
            present(alert, animated: true)
            return
        }

        let sheet = UIAlertController(title: "Insert Signature", message: nil, preferredStyle: .actionSheet)
        for sig in signatures {
            sheet.addAction(UIAlertAction(title: sig.name, style: .default) { [weak self] _ in
                self?.stampSignature(sig)
            })
        }
        sheet.addAction(UIAlertAction(title: "Cancel", style: .cancel))
        sheet.popoverPresentationController?.barButtonItem = navigationItem.rightBarButtonItems?.last
        present(sheet, animated: true)
    }

    /// Stamps a signature image onto the annotation canvas as a UIImageView overlay.
    private func stampSignature(_ signature: Signature) {
        guard let sigImage = UIImage(data: signature.imageData) else {
            showErrorAlert(message: "Could not read signature image.")
            return
        }

        let maxWidth: CGFloat = 200
        let scale = min(maxWidth / sigImage.size.width, 1.0)
        let stampSize = CGSize(
            width: sigImage.size.width * scale,
            height: sigImage.size.height * scale
        )

        let visibleCenter = CGPoint(
            x: annotationCanvas.contentOffset.x + annotationCanvas.bounds.midX,
            y: annotationCanvas.contentOffset.y + annotationCanvas.bounds.midY
        )
        let stampRect = CGRect(
            x: visibleCenter.x - stampSize.width / 2,
            y: visibleCenter.y - stampSize.height / 2,
            width: stampSize.width,
            height: stampSize.height
        )

        let imageView = UIImageView(image: sigImage)
        imageView.frame = stampRect
        imageView.contentMode = .scaleAspectFit
        imageView.isUserInteractionEnabled = false
        annotationCanvas.addSubview(imageView)
    }

    // MARK: - Actions

    @objc private func doneTapped() {
        saveAnnotations()
        navigationController?.popViewController(animated: true)
    }

    @objc private func exportTapped() {
        exportAnnotated()
    }

    @objc private func insertSignatureTapped() {
        insertSignature()
    }

    // MARK: - Save

    /// Saves the current PencilKit annotation drawing back to the document in Core Data.
    private func saveAnnotations() {
        document?.drawingData = annotationCanvas.drawing.dataRepresentation()
        document?.type = .annotatedPDF
        do {
            try context.save()
        } catch {
            print("Failed to save annotations: \(error)")
        }
    }

    // MARK: - Helpers

    private func showNoPDFPlaceholder() {
        let label = UILabel()
        label.text = "No PDF loaded.\nImport a PDF from the Library."
        label.numberOfLines = 0
        label.textAlignment = .center
        label.textColor = .secondaryLabel
        label.font = .systemFont(ofSize: 17)
        label.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(label)
        NSLayoutConstraint.activate([
            label.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            label.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            label.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 32),
            label.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -32)
        ])
    }

    private func showErrorAlert(message: String) {
        let alert = UIAlertController(title: "Error", message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        present(alert, animated: true)
    }
}
