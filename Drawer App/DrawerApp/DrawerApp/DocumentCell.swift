// Drawer
// Conner Inman (cdinman@iu.edu) & Caden Batts (cabatts@iu.edu)
// C323: Mobile App Development

import UIKit

// MARK: - DocumentCell

/// A UITableViewCell displaying a document's thumbnail, title, and date.
/// Matches the DocumentCell (UITableViewCell) class in the UML class diagram.
class DocumentCell: UITableViewCell {

    // MARK: - Reuse Identifier

    static let reuseID = "DocumentCell"

    // MARK: - UI Elements

    /// Thumbnail image preview of the document
    let thumbnail = UIImageView()

    /// Document title label
    let titleLabel = UILabel()

    /// Document creation date label
    let dateLabel = UILabel()

    /// Document type badge (Sketch / PDF)
    private let typeBadge = UILabel()

    // MARK: - Initializers

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        setupViews()
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    // MARK: - Setup

    private func setupViews() {
        // Thumbnail
        thumbnail.translatesAutoresizingMaskIntoConstraints = false
        thumbnail.contentMode = .scaleAspectFill
        thumbnail.clipsToBounds = true
        thumbnail.layer.cornerRadius = 6
        thumbnail.backgroundColor = .systemGray5
        thumbnail.image = UIImage(systemName: "doc.richtext")
        thumbnail.tintColor = .systemGray3
        contentView.addSubview(thumbnail)

        // Title label
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.font = .systemFont(ofSize: 16, weight: .medium)
        titleLabel.textColor = .label
        contentView.addSubview(titleLabel)

        // Date label
        dateLabel.translatesAutoresizingMaskIntoConstraints = false
        dateLabel.font = .systemFont(ofSize: 12, weight: .regular)
        dateLabel.textColor = .secondaryLabel
        contentView.addSubview(dateLabel)

        // Type badge
        typeBadge.translatesAutoresizingMaskIntoConstraints = false
        typeBadge.font = .systemFont(ofSize: 10, weight: .semibold)
        typeBadge.textColor = .white
        typeBadge.layer.cornerRadius = 4
        typeBadge.clipsToBounds = true
        typeBadge.textAlignment = .center
        contentView.addSubview(typeBadge)

        NSLayoutConstraint.activate([
            thumbnail.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 16),
            thumbnail.centerYAnchor.constraint(equalTo: contentView.centerYAnchor),
            thumbnail.widthAnchor.constraint(equalToConstant: 48),
            thumbnail.heightAnchor.constraint(equalToConstant: 48),

            titleLabel.leadingAnchor.constraint(equalTo: thumbnail.trailingAnchor, constant: 12),
            titleLabel.topAnchor.constraint(equalTo: contentView.topAnchor, constant: 14),
            titleLabel.trailingAnchor.constraint(equalTo: typeBadge.leadingAnchor, constant: -8),

            dateLabel.leadingAnchor.constraint(equalTo: thumbnail.trailingAnchor, constant: 12),
            dateLabel.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 4),

            typeBadge.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -16),
            typeBadge.centerYAnchor.constraint(equalTo: contentView.centerYAnchor),
            typeBadge.widthAnchor.constraint(equalToConstant: 44),
            typeBadge.heightAnchor.constraint(equalToConstant: 20)
        ])
    }

    // MARK: - Configuration

    /// Configures the cell with a Document model object.
    /// Matches configure(with:) in the UML class diagram.
    func configure(with document: Document) {
        titleLabel.text = document.title

        // Format the date
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        formatter.timeStyle = .none
        dateLabel.text = formatter.string(from: document.createdAt)

        // Set thumbnail if available
        if let data = document.thumbnailData, let image = UIImage(data: data) {
            thumbnail.image = image
            thumbnail.contentMode = .scaleAspectFill
        } else {
            // Default icon based on document type
            let iconName = document.type == .sketch ? "pencil.and.scribble" : "doc.richtext"
            thumbnail.image = UIImage(systemName: iconName)
            thumbnail.contentMode = .center
            thumbnail.tintColor = .systemGray3
        }

        // Type badge styling
        switch document.type {
        case .sketch:
            typeBadge.text = "Sketch"
            typeBadge.backgroundColor = .systemBlue
        case .annotatedPDF:
            typeBadge.text = "PDF"
            typeBadge.backgroundColor = .systemOrange
        case .imported:
            typeBadge.text = "PDF"
            typeBadge.backgroundColor = .systemGray
        }
    }
}
