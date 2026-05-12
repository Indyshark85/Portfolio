// Drawer
// Conner Inman (cdinman@iu.edu) & Caden Batts (cabatts@iu.edu)
// C323: Mobile App Development

import UIKit
import CoreData
import PencilKit

// MARK: - SettingsVC

/// UIViewController displaying user preferences and saved signatures.
/// Matches the SettingsVC class in the UML class diagram
/// and the "Settings" wireframe screen.
class SettingsVC: UIViewController {

    // MARK: - Properties

    /// Saved signatures loaded from Core Data
    private var signatures: [Signature] = []

    /// Convenience accessor for Core Data context
    private var context: NSManagedObjectContext {
        return (UIApplication.shared.delegate as! AppDelegate).context
    }

    // MARK: - UI Elements

    private let scrollView = UIScrollView()
    private let contentStack = UIStackView()

    // Saved Signatures section
    private let signaturesHeaderLabel = UILabel()
    private let signaturesTablePlaceholder = UILabel()
    private let addSignatureButton = UIButton(type: .system)

    // Preferences section
    private let preferencesHeaderLabel = UILabel()
    private let penColorButton = UIButton(type: .system)
    private let canvasBackgroundButton = UIButton(type: .system)

    // Storage section
    private let storageHeaderLabel = UILabel()
    private let documentsCountLabel = UILabel()

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Settings"
        view.backgroundColor = .systemGroupedBackground
        setupLayout()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        loadSignatures()
        updateDocumentCount()
    }

    // MARK: - Layout Setup

    private func setupLayout() {
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        contentStack.translatesAutoresizingMaskIntoConstraints = false
        contentStack.axis = .vertical
        contentStack.spacing = 16
        contentStack.layoutMargins = UIEdgeInsets(top: 24, left: 20, bottom: 24, right: 20)
        contentStack.isLayoutMarginsRelativeArrangement = true

        view.addSubview(scrollView)
        scrollView.addSubview(contentStack)

        NSLayoutConstraint.activate([
            scrollView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            scrollView.bottomAnchor.constraint(equalTo: view.bottomAnchor),

            contentStack.topAnchor.constraint(equalTo: scrollView.topAnchor),
            contentStack.leadingAnchor.constraint(equalTo: scrollView.leadingAnchor),
            contentStack.trailingAnchor.constraint(equalTo: scrollView.trailingAnchor),
            contentStack.bottomAnchor.constraint(equalTo: scrollView.bottomAnchor),
            contentStack.widthAnchor.constraint(equalTo: scrollView.widthAnchor)
        ])

        buildSignaturesSection()
        buildPreferencesSection()
        buildStorageSection()
    }

    private func buildSignaturesSection() {
        signaturesHeaderLabel.text = "Saved Signatures"
        signaturesHeaderLabel.font = .systemFont(ofSize: 13, weight: .semibold)
        signaturesHeaderLabel.textColor = .secondaryLabel

        let card = makeCard()

        signaturesTablePlaceholder.text = "No signatures saved yet."
        signaturesTablePlaceholder.textColor = .secondaryLabel
        signaturesTablePlaceholder.font = .systemFont(ofSize: 15)
        signaturesTablePlaceholder.numberOfLines = 0

        addSignatureButton.setTitle("+ Add New Signature", for: .normal)
        addSignatureButton.addTarget(self, action: #selector(addSignatureTapped), for: .touchUpInside)

        let cardStack = UIStackView(arrangedSubviews: [signaturesTablePlaceholder, addSignatureButton])
        cardStack.axis = .vertical
        cardStack.spacing = 12
        cardStack.translatesAutoresizingMaskIntoConstraints = false
        card.addSubview(cardStack)
        NSLayoutConstraint.activate([
            cardStack.topAnchor.constraint(equalTo: card.topAnchor, constant: 12),
            cardStack.leadingAnchor.constraint(equalTo: card.leadingAnchor, constant: 16),
            cardStack.trailingAnchor.constraint(equalTo: card.trailingAnchor, constant: -16),
            cardStack.bottomAnchor.constraint(equalTo: card.bottomAnchor, constant: -12)
        ])

        contentStack.addArrangedSubview(signaturesHeaderLabel)
        contentStack.addArrangedSubview(card)
    }

    private func buildPreferencesSection() {
        preferencesHeaderLabel.text = "Preferences"
        preferencesHeaderLabel.font = .systemFont(ofSize: 13, weight: .semibold)
        preferencesHeaderLabel.textColor = .secondaryLabel

        let card = makeCard()

        penColorButton.setTitle("Default Pen Color", for: .normal)
        penColorButton.contentHorizontalAlignment = .left
        penColorButton.addTarget(self, action: #selector(penColorTapped), for: .touchUpInside)

        let divider = makeDivider()

        canvasBackgroundButton.setTitle("Canvas Background", for: .normal)
        canvasBackgroundButton.contentHorizontalAlignment = .left
        canvasBackgroundButton.addTarget(self, action: #selector(canvasBackgroundTapped), for: .touchUpInside)

        let cardStack = UIStackView(arrangedSubviews: [penColorButton, divider, canvasBackgroundButton])
        cardStack.axis = .vertical
        cardStack.spacing = 8
        cardStack.translatesAutoresizingMaskIntoConstraints = false
        card.addSubview(cardStack)
        NSLayoutConstraint.activate([
            cardStack.topAnchor.constraint(equalTo: card.topAnchor, constant: 12),
            cardStack.leadingAnchor.constraint(equalTo: card.leadingAnchor, constant: 16),
            cardStack.trailingAnchor.constraint(equalTo: card.trailingAnchor, constant: -16),
            cardStack.bottomAnchor.constraint(equalTo: card.bottomAnchor, constant: -12)
        ])

        contentStack.addArrangedSubview(preferencesHeaderLabel)
        contentStack.addArrangedSubview(card)
    }

    private func buildStorageSection() {
        storageHeaderLabel.text = "Storage"
        storageHeaderLabel.font = .systemFont(ofSize: 13, weight: .semibold)
        storageHeaderLabel.textColor = .secondaryLabel

        let card = makeCard()
        documentsCountLabel.font = .systemFont(ofSize: 15)
        documentsCountLabel.translatesAutoresizingMaskIntoConstraints = false
        card.addSubview(documentsCountLabel)
        NSLayoutConstraint.activate([
            documentsCountLabel.topAnchor.constraint(equalTo: card.topAnchor, constant: 12),
            documentsCountLabel.leadingAnchor.constraint(equalTo: card.leadingAnchor, constant: 16),
            documentsCountLabel.trailingAnchor.constraint(equalTo: card.trailingAnchor, constant: -16),
            documentsCountLabel.bottomAnchor.constraint(equalTo: card.bottomAnchor, constant: -12)
        ])

        contentStack.addArrangedSubview(storageHeaderLabel)
        contentStack.addArrangedSubview(card)
    }

    // MARK: - Core Data Operations

    /// Loads all saved Signature records from Core Data.
    /// Matches loadSignatures() in the UML class diagram.
    func loadSignatures() {
        let request = NSFetchRequest<Signature>(entityName: "Signature")
        request.sortDescriptors = [NSSortDescriptor(key: "createdAt", ascending: false)]
        do {
            signatures = try context.fetch(request)
            let hasSignatures = !signatures.isEmpty
            signaturesTablePlaceholder.text = hasSignatures
                ? signatures.map { "• \($0.name)" }.joined(separator: "\n")
                : "No signatures saved yet."
        } catch {
            print("Failed to load signatures: \(error)")
        }
    }

    /// Saves a new signature (name + PNG imageData) to Core Data.
    /// Called after the user finishes drawing in the signature canvas modal.
    /// Matches saveSignature() in the UML class diagram.
    func saveSignature(name: String, imageData: Data) {
        _ = Signature.insert(into: context, name: name, imageData: imageData)
        do {
            try context.save()
            loadSignatures()
        } catch {
            print("Failed to save signature: \(error)")
        }
    }

    /// Deletes a signature from Core Data.
    /// Matches deleteSignature() in the UML class diagram.
    func deleteSignature(_ signature: Signature) {
        context.delete(signature)
        do {
            try context.save()
            loadSignatures()
        } catch {
            print("Failed to delete signature: \(error)")
        }
    }

    private func updateDocumentCount() {
        let request = NSFetchRequest<Document>(entityName: "Document")
        let count = (try? context.count(for: request)) ?? 0
        documentsCountLabel.text = "Documents Stored: \(count)"
    }

    // MARK: - Actions

    /// Presents a modal PKCanvasView for the user to draw a new signature,
    /// then saves the result with a name entered via an alert.
    @objc private func addSignatureTapped() {
        let sigVC = SignatureCaptureVC()
        sigVC.onSave = { [weak self] name, imageData in
            self?.saveSignature(name: name, imageData: imageData)
        }
        let nav = UINavigationController(rootViewController: sigVC)
        nav.modalPresentationStyle = .formSheet
        present(nav, animated: true)
    }

    /// Presents a UIColorPickerViewController (iOS 14+) so the user can choose
    /// a default pen color, which is stored in UserDefaults.
    @objc private func penColorTapped() {
        let picker = UIColorPickerViewController()
        picker.title = "Default Pen Color"
        picker.supportsAlpha = false
        // Restore previously saved color
        if let hex = UserDefaults.standard.string(forKey: "defaultPenColor"),
           let color = UIColor(hex: hex) {
            picker.selectedColor = color
        }
        picker.delegate = self
        present(picker, animated: true)
        
    }

    /// Presents an action sheet so the user can choose a canvas background color.
    /// The selection is stored in UserDefaults and read by CanvasVC on launch.
    @objc private func canvasBackgroundTapped() {
        let sheet = UIAlertController(
            title: "Canvas Background",
            message: "Choose a background for new sketches.",
            preferredStyle: .actionSheet
        )

        let options: [(String, String)] = [
            ("White", "#FFFFFF"),
            ("Light Gray", "#F2F2F7"),
            ("Cream / Paper", "#FFFDF0"),
            ("Dark (Dark Mode)", "#1C1C1E")
        ]

        for (name, hex) in options {
            sheet.addAction(UIAlertAction(title: name, style: .default) { _ in
                UserDefaults.standard.set(hex, forKey: "canvasBackground")
                self.showConfirmationBanner(message: "Background set to \(name)")
            })
        }
        sheet.addAction(UIAlertAction(title: "Cancel", style: .cancel))
        sheet.popoverPresentationController?.sourceView = canvasBackgroundButton
        present(sheet, animated: true)
    }

    // MARK: - Helpers

    private func makeCard() -> UIView {
        let card = UIView()
        card.backgroundColor = .secondarySystemGroupedBackground
        card.layer.cornerRadius = 12
        card.clipsToBounds = true
        return card
    }

    private func makeDivider() -> UIView {
        let divider = UIView()
        divider.backgroundColor = .separator
        divider.heightAnchor.constraint(equalToConstant: 0.5).isActive = true
        return divider
    }

    private func showConfirmationBanner(message: String) {
        let banner = UILabel()
        banner.text = "  \(message)  "
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
            UIView.animate(withDuration: 0.4, delay: 1.5, animations: { banner.alpha = 0 }) { _ in
                banner.removeFromSuperview()
            }
        }
    }
}

// MARK: - UIColorPickerViewControllerDelegate (iOS 14+)

@available(iOS 14.0, *)
extension SettingsVC: UIColorPickerViewControllerDelegate {
    func colorPickerViewControllerDidFinish(_ viewController: UIColorPickerViewController) {
        let selected = viewController.selectedColor
        UserDefaults.standard.set(selected.hexString, forKey: "defaultPenColor")
        showConfirmationBanner(message: "Pen color saved")
    }
}

// MARK: - SignatureCaptureVC

/// A modal view controller presenting a PKCanvasView for drawing a signature.
/// After tapping Save, it prompts for a name and calls `onSave` with the
/// PNG-encoded drawing.
class SignatureCaptureVC: UIViewController {

    // MARK: - Callback

    /// Called with (name, pngData) when the user confirms their signature.
    var onSave: ((String, Data) -> Void)?

    // MARK: - UI

    private let canvasView = PKCanvasView()
    private let toolPicker = PKToolPicker()
    private let instructionLabel = UILabel()
    private let clearButton = UIButton(type: .system)

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Draw Signature"
        view.backgroundColor = .systemBackground
        setupNavigationBar()
        setupCanvas()
        setupInstruction()
        setupClearButton()
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        toolPicker.setVisible(true, forFirstResponder: canvasView)
        toolPicker.addObserver(canvasView)
        canvasView.becomeFirstResponder()
    }

    // MARK: - Setup

    private func setupNavigationBar() {
        navigationItem.leftBarButtonItem = UIBarButtonItem(
            title: "Cancel", style: .plain, target: self, action: #selector(cancelTapped)
        )
        navigationItem.rightBarButtonItem = UIBarButtonItem(
            title: "Save", style: .done, target: self, action: #selector(saveTapped)
        )
    }

    private func setupCanvas() {
        canvasView.translatesAutoresizingMaskIntoConstraints = false
        canvasView.backgroundColor = .white
        canvasView.drawingPolicy = .anyInput
        canvasView.layer.borderColor = UIColor.separator.cgColor
        canvasView.layer.borderWidth = 1
        canvasView.layer.cornerRadius = 8
        view.addSubview(canvasView)

        NSLayoutConstraint.activate([
            canvasView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 60),
            canvasView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 24),
            canvasView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -24),
            canvasView.heightAnchor.constraint(equalToConstant: 200)
        ])
    }

    private func setupInstruction() {
        instructionLabel.translatesAutoresizingMaskIntoConstraints = false
        instructionLabel.text = "Sign above using your finger or Apple Pencil"
        instructionLabel.font = .systemFont(ofSize: 14)
        instructionLabel.textColor = .secondaryLabel
        instructionLabel.textAlignment = .center
        view.addSubview(instructionLabel)

        NSLayoutConstraint.activate([
            instructionLabel.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 24),
            instructionLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 24),
            instructionLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -24)
        ])
    }

    private func setupClearButton() {
        clearButton.translatesAutoresizingMaskIntoConstraints = false
        clearButton.setTitle("Clear", for: .normal)
        clearButton.addTarget(self, action: #selector(clearTapped), for: .touchUpInside)
        view.addSubview(clearButton)

        NSLayoutConstraint.activate([
            clearButton.topAnchor.constraint(equalTo: canvasView.bottomAnchor, constant: 12),
            clearButton.trailingAnchor.constraint(equalTo: canvasView.trailingAnchor)
        ])
    }

    // MARK: - Actions

    @objc private func cancelTapped() {
        dismiss(animated: true)
    }

    @objc private func clearTapped() {
        canvasView.drawing = PKDrawing()
    }

    /// Validates the drawing is non-empty, then prompts for a name before saving.
    @objc private func saveTapped() {
        guard !canvasView.drawing.strokes.isEmpty else {
            let alert = UIAlertController(
                title: "Empty Signature",
                message: "Please draw your signature before saving.",
                preferredStyle: .alert
            )
            alert.addAction(UIAlertAction(title: "OK", style: .default))
            present(alert, animated: true)
            return
        }

        // Render the drawing to PNG within its tight bounding box
        let drawingBounds = canvasView.drawing.bounds.insetBy(dx: -8, dy: -8)
        let image = canvasView.drawing.image(from: drawingBounds, scale: UIScreen.main.scale)
        guard let pngData = image.pngData() else {
            let alert = UIAlertController(title: "Error", message: "Could not encode signature.", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default))
            present(alert, animated: true)
            return
        }

        // Prompt the user to name the signature
        let nameAlert = UIAlertController(
            title: "Name Your Signature",
            message: "Enter a label so you can identify it later.",
            preferredStyle: .alert
        )
        nameAlert.addTextField { tf in
            tf.placeholder = "e.g. My Signature, Initials"
            tf.autocapitalizationType = .words
        }
        nameAlert.addAction(UIAlertAction(title: "Cancel", style: .cancel))
        nameAlert.addAction(UIAlertAction(title: "Save", style: .default) { [weak self, weak nameAlert] _ in
            let name = nameAlert?.textFields?.first?.text?.trimmingCharacters(in: .whitespaces)
            let finalName = (name?.isEmpty == false) ? name! : "My Signature"
            self?.onSave?(finalName, pngData)
            self?.dismiss(animated: true)
        })
        present(nameAlert, animated: true)
    }
}
