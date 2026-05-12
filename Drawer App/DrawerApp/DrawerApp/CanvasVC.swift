// Drawer
// Conner Inman (cdinman@iu.edu) & Caden Batts (cabatts@iu.edu)
// C323: Mobile App Development

import UIKit
import PencilKit
import CoreData

// MARK: - CanvasVC

/// UIViewController presenting a PencilKit canvas for freehand drawing.
/// This is the app's primary working functionality for Assignment 03.
/// Matches the CanvasVC class in the UML class diagram.
class CanvasVC: UIViewController {

    // MARK: - Properties

    /// The document being edited. Nil means a new blank sketch.
    var document: Document?

    /// The PencilKit canvas view — the core drawing surface
    private let canvasView = PKCanvasView()

    /// The PencilKit tool picker (pen, eraser, ruler, etc.)
    private let toolPicker = PKToolPicker()

    /// Convenience accessor for Core Data context
    private var context: NSManagedObjectContext {
        return (UIApplication.shared.delegate as! AppDelegate).context
    }

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupNavigationBar()
        setupCanvasView()
        loadExistingDrawing()
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        // Show the PencilKit tool picker when the canvas appears
        toolPicker.setVisible(true, forFirstResponder: canvasView)
        toolPicker.addObserver(canvasView)
        canvasView.becomeFirstResponder()
        applyUserPreferences()
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        toolPicker.setVisible(false, forFirstResponder: canvasView)
    }

    // MARK: - Setup

    private func setupNavigationBar() {
        title = document?.title ?? "Canvas"

        let exportButton = UIBarButtonItem(
            title: "Export",
            style: .plain,
            target: self,
            action: #selector(exportTapped)
        )

        let saveButton = UIBarButtonItem(
            title: "Save",
            style: .done,
            target: self,
            action: #selector(saveTapped)
        )

        let sigButton = UIBarButtonItem(
            image: UIImage(systemName: "signature"),
            style: .plain,
            target: self,
            action: #selector(insertSignatureTapped)
        )

        let toolToggleButton = UIBarButtonItem(
            image: UIImage(systemName: "pencil.slash"),
            style: .plain,
            target: self,
            action: #selector(toggleToolPickerTapped)
        )
        navigationItem.leftBarButtonItem = toolToggleButton
        navigationItem.rightBarButtonItems = [saveButton, exportButton, sigButton]
    }

    private func setupCanvasView() {
        view.backgroundColor = .systemBackground

        canvasView.translatesAutoresizingMaskIntoConstraints = false
        canvasView.backgroundColor = .white
        canvasView.drawingPolicy = .anyInput
        canvasView.delegate = self

        view.addSubview(canvasView)

        NSLayoutConstraint.activate([
            canvasView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            canvasView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            canvasView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            canvasView.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
    }

    /// Loads an existing PKDrawing from the document's saved data, if available.
    private func loadExistingDrawing() {
        guard let data = document?.drawingData else { return }
        do {
            let drawing = try PKDrawing(data: data)
            canvasView.drawing = drawing
        } catch {
            print("Failed to load drawing: \(error)")
        }
    }

    // MARK: - User Preferences

    /// Reads saved UserDefaults preferences and applies them to the canvas.
    /// Called each time the view appears so changes made in Settings take effect.
    private func applyUserPreferences() {
        // Apply canvas background color
        if let hex = UserDefaults.standard.string(forKey: "canvasBackground"),
           let color = UIColor(hex: hex) {
            canvasView.backgroundColor = color
        }

        // Apply default pen color to the active ink tool
        if let hex = UserDefaults.standard.string(forKey: "defaultPenColor"),
           let color = UIColor(hex: hex) {
            if let inkTool = canvasView.tool as? PKInkingTool {
                // Preserve the current ink type and width, just swap the color
                canvasView.tool = PKInkingTool(inkTool.inkType, color: color, width: inkTool.width)
            } else {
                // No ink tool active yet — set a default pen with the saved color
                canvasView.tool = PKInkingTool(.pen, color: color, width: 3)
            }
        }
    }

    // MARK: - Core Data Operations

    /// Saves the current PKDrawing to Core Data.
    /// Creates a new Document record if none exists, or updates the existing one.
    func saveDrawing() {
        let drawingData = canvasView.drawing.dataRepresentation()

        if let doc = document {
            doc.drawingData = drawingData
        } else {
            let newDoc = Document.insert(into: context, title: "Untitled Sketch", type: .sketch)
            newDoc.drawingData = drawingData
            document = newDoc
        }

        do {
            try context.save()
            showSaveConfirmation()
        } catch {
            print("Failed to save drawing: \(error)")
        }
    }

    /// Exports the current canvas as a PDF and presents a share sheet.
    func exportAsPDF() {
        let drawing = canvasView.drawing
        let bounds = drawing.bounds.isEmpty
            ? CGRect(origin: .zero, size: canvasView.bounds.size)
            : drawing.bounds.insetBy(dx: -20, dy: -20)

        let pdfRenderer = UIGraphicsPDFRenderer(bounds: bounds)
        let pdfData = pdfRenderer.pdfData { ctx in
            ctx.beginPage()
            UIColor.white.setFill()
            ctx.fill(bounds)
            let image = drawing.image(from: bounds, scale: UIScreen.main.scale)
            image.draw(in: bounds)
        }

        let fileName = (document?.title ?? "Sketch")
            .replacingOccurrences(of: " ", with: "_")
        let tempURL = FileManager.default.temporaryDirectory
            .appendingPathComponent("\(fileName).pdf")
        do {
            try pdfData.write(to: tempURL)
        } catch {
            showErrorAlert(message: "Could not write PDF: \(error.localizedDescription)")
            return
        }

        let activityVC = UIActivityViewController(activityItems: [tempURL], applicationActivities: nil)
        activityVC.popoverPresentationController?.barButtonItem = navigationItem.rightBarButtonItems?.first
        present(activityVC, animated: true)
    }

    /// Presents a picker of saved signatures and stamps the chosen one onto the canvas.
    func insertSignature() {
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
            x: canvasView.contentOffset.x + canvasView.bounds.midX,
            y: canvasView.contentOffset.y + canvasView.bounds.midY
        )
        let stampRect = CGRect(
            x: visibleCenter.x - stampSize.width / 2,
            y: visibleCenter.y - stampSize.height / 2,
            width: stampSize.width,
            height: stampSize.height
        )

        let renderer = UIGraphicsImageRenderer(size: stampSize)
        let renderedImage = renderer.image { _ in
            sigImage.draw(in: CGRect(origin: .zero, size: stampSize))
        }

        let imageView = UIImageView(image: renderedImage)
        imageView.frame = stampRect
        imageView.contentMode = .scaleAspectFit
        imageView.isUserInteractionEnabled = false
        imageView.tag = 999
        canvasView.addSubview(imageView)

        let fullBounds = canvasView.bounds
        let compositeRenderer = UIGraphicsImageRenderer(bounds: fullBounds)
        let composite = compositeRenderer.image { _ in
            UIColor.white.setFill()
            UIRectFill(fullBounds)
            canvasView.drawing.image(from: fullBounds, scale: UIScreen.main.scale).draw(in: fullBounds)
            renderedImage.draw(in: stampRect)
        }
        document?.thumbnailData = composite.pngData()
    }

    // MARK: - Actions

    @objc private func saveTapped() { saveDrawing() }
    @objc private func exportTapped() { exportAsPDF() }
    @objc private func insertSignatureTapped() { insertSignature() }

    @objc private func toggleToolPickerTapped() {
        let isVisible = toolPicker.isVisible
        toolPicker.setVisible(!isVisible, forFirstResponder: canvasView)
        if !isVisible { canvasView.becomeFirstResponder() }
        let iconName = isVisible ? "pencil.tip.crop.circle" : "pencil.slash"
        navigationItem.leftBarButtonItem?.image = UIImage(systemName: iconName)
    }

    // MARK: - Helpers

    private func showSaveConfirmation() {
        let banner = UILabel()
        banner.text = "  Saved  "
        banner.backgroundColor = UIColor.systemGreen.withAlphaComponent(0.85)
        banner.textColor = .white
        banner.font = .systemFont(ofSize: 14, weight: .semibold)
        banner.layer.cornerRadius = 10
        banner.clipsToBounds = true
        banner.translatesAutoresizingMaskIntoConstraints = false
        banner.alpha = 0
        view.addSubview(banner)
        NSLayoutConstraint.activate([
            banner.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            banner.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 12)
        ])
        UIView.animate(withDuration: 0.3, animations: { banner.alpha = 1 }) { _ in
            UIView.animate(withDuration: 0.4, delay: 1.2, animations: { banner.alpha = 0 }) { _ in
                banner.removeFromSuperview()
            }
        }
    }

    private func showErrorAlert(message: String) {
        let alert = UIAlertController(title: "Error", message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        present(alert, animated: true)
    }
}

// MARK: - PKCanvasViewDelegate

extension CanvasVC: PKCanvasViewDelegate {
    func canvasViewDrawingDidChange(_ canvasView: PKCanvasView) {
        // Drawing changed — could auto-save here in a future version
    }
}
