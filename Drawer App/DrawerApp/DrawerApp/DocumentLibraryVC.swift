// Drawer
// Conner Inman (cdinman@iu.edu) & Caden Batts (cabatts@iu.edu)
// C323: Mobile App Development

import UIKit
import CoreData
import UniformTypeIdentifiers

// MARK: - DocumentLibraryVC

/// UITableViewController that displays the user's document library,
/// organized into Sketches and PDFs sections. Matches the "My Documents"
/// wireframe and the DocumentLibraryVC class in the UML class diagram.
class DocumentLibraryVC: UITableViewController {

    // MARK: - Properties

    /// All documents loaded from Core Data
    private var documents: [Document] = []

    /// Convenience accessor for the app's managed object context
    private var context: NSManagedObjectContext {
        return (UIApplication.shared.delegate as! AppDelegate).context
    }

    /// Filtered sketches for Section 0
    private var sketches: [Document] {
        documents.filter { $0.type == .sketch }
    }

    /// Filtered PDFs (annotated + imported) for Section 1
    private var pdfs: [Document] {
        documents.filter { $0.type == .annotatedPDF || $0.type == .imported }
    }

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupNavigationBar()
        setupTableView()
        setupSearchController()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        loadDocuments()
    }

    // MARK: - Setup

    private func setupNavigationBar() {
        title = "My Documents"
        navigationItem.rightBarButtonItem = editButtonItem

        // Import PDF button
        let importButton = UIBarButtonItem(
            image: UIImage(systemName: "square.and.arrow.down"),
            style: .plain,
            target: self,
            action: #selector(importPDFTapped)
        )
        navigationItem.leftBarButtonItem = importButton
    }

    private func setupTableView() {
        tableView.register(DocumentCell.self, forCellReuseIdentifier: DocumentCell.reuseID)
        tableView.rowHeight = 72
    }

    private func setupSearchController() {
        let search = UISearchController(searchResultsController: nil)
        search.searchResultsUpdater = self
        search.obscuresBackgroundDuringPresentation = false
        search.searchBar.placeholder = "Search..."
        navigationItem.searchController = search
        definesPresentationContext = true
    }

    // MARK: - Core Data Operations

    /// Loads all Document records from Core Data, sorted by creation date descending.
    /// Matches loadDocuments() in the UML class diagram.
    func loadDocuments() {
        let request = NSFetchRequest<Document>(entityName: "Document")
        request.sortDescriptors = [NSSortDescriptor(key: "createdAt", ascending: false)]
        do {
            documents = try context.fetch(request)
            tableView.reloadData()
        } catch {
            print("Failed to fetch documents: \(error)")
        }
    }

    /// Deletes a Document from Core Data and removes it from the table view.
    /// Matches deleteDocument() in the UML class diagram.
    func deleteDocument(_ document: Document) {
        context.delete(document)
        do {
            try context.save()
            loadDocuments()
        } catch {
            print("Failed to delete document: \(error)")
        }
    }

    /// Presents a UIDocumentPickerViewController so the user can select a PDF
    /// from Files. The selected file is read as Data, saved to Core Data as an
    /// imported Document, and the library is reloaded.
    /// Matches importPDF() in the UML class diagram.
    func importPDF() {
        let picker: UIDocumentPickerViewController
        if #available(iOS 14.0, *) {
            picker = UIDocumentPickerViewController(forOpeningContentTypes: [UTType.pdf])
        } else {
            picker = UIDocumentPickerViewController(documentTypes: ["com.adobe.pdf"], in: .import)
        }
        picker.delegate = self
        picker.allowsMultipleSelection = false
        present(picker, animated: true)
    }

    /// Reads the PDF at `url`, creates a Document record, and saves it to Core Data.
    private func savePDF(from url: URL) {
        // Security-scope the URL so we can read sandbox-restricted files from Files app
        let accessing = url.startAccessingSecurityScopedResource()
        defer {
            if accessing { url.stopAccessingSecurityScopedResource() }
        }

        guard let pdfData = try? Data(contentsOf: url) else {
            showErrorAlert(message: "Could not read the selected PDF file.")
            return
        }

        // Derive a title from the file name, stripping the .pdf extension
        let rawName = url.deletingPathExtension().lastPathComponent
        let title = rawName.isEmpty ? "Imported PDF" : rawName

        let doc = Document.insert(into: context, title: title, type: .imported)
        doc.pdfData = pdfData

        do {
            try context.save()
            loadDocuments()
        } catch {
            showErrorAlert(message: "Could not save the PDF: \(error.localizedDescription)")
        }
    }

    // MARK: - Actions

    @objc private func importPDFTapped() {
        importPDF()
    }

    // MARK: - UITableViewDataSource

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }

    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return section == 0 ? "Sketches" : "PDFs"
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return section == 0 ? sketches.count : pdfs.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(
            withIdentifier: DocumentCell.reuseID,
            for: indexPath
        ) as! DocumentCell
        let doc = indexPath.section == 0 ? sketches[indexPath.row] : pdfs[indexPath.row]
        cell.configure(with: doc)
        return cell
    }

    override func tableView(
        _ tableView: UITableView,
        commit editingStyle: UITableViewCell.EditingStyle,
        forRowAt indexPath: IndexPath
    ) {
        if editingStyle == .delete {
            let doc = indexPath.section == 0 ? sketches[indexPath.row] : pdfs[indexPath.row]
            deleteDocument(doc)
        }
    }

    // MARK: - UITableViewDelegate

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let doc = indexPath.section == 0 ? sketches[indexPath.row] : pdfs[indexPath.row]

        if doc.type == .sketch {
            // Open in canvas for editing
            let canvasVC = CanvasVC()
            canvasVC.document = doc
            navigationController?.pushViewController(canvasVC, animated: true)
        } else {
            // Open in PDF annotation view
            let pdfVC = PDFAnnotationVC()
            pdfVC.document = doc
            navigationController?.pushViewController(pdfVC, animated: true)
        }
    }

    // MARK: - Helpers

    private func showErrorAlert(message: String) {
        let alert = UIAlertController(title: "Error", message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        present(alert, animated: true)
    }
}

// MARK: - UIDocumentPickerDelegate

extension DocumentLibraryVC: UIDocumentPickerDelegate {
    func documentPicker(_ controller: UIDocumentPickerViewController, didPickDocumentsAt urls: [URL]) {
        guard let url = urls.first else { return }
        savePDF(from: url)
    }

    func documentPickerWasCancelled(_ controller: UIDocumentPickerViewController) {
        // Nothing to do — user dismissed the picker
    }
}

// MARK: - UISearchResultsUpdating

extension DocumentLibraryVC: UISearchResultsUpdating {
    func updateSearchResults(for searchController: UISearchController) {
        guard let query = searchController.searchBar.text, !query.isEmpty else {
            loadDocuments()
            return
        }
        // Filter documents by title
        documents = documents.filter {
            $0.title.lowercased().contains(query.lowercased())
        }
        tableView.reloadData()
    }
}
