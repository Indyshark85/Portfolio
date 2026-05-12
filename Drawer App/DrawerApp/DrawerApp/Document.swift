// Drawer
// Conner Inman (cdinman@iu.edu) & Caden Batts (cabatts@iu.edu)
// C323: Mobile App Development

import Foundation
import CoreData

// MARK: - DocType Enum

/// Represents the type of document stored in the library, matching the UML class diagram.
enum DocType: String {
    case sketch          // A freehand sketch created on a blank canvas
    case annotatedPDF    // A PDF with PencilKit annotations overlaid
    case imported        // A raw imported PDF with no annotations yet
}

// MARK: - Document (Core Data Entity)

/// Core Data managed object representing a document in the user's library.
/// Matches the Document entity defined in the UML class diagram.
@objc(Document)
class Document: NSManagedObject {

    // MARK: - Attributes

    /// Unique identifier for the document
    @NSManaged var id: UUID

    /// Display title of the document
    @NSManaged var title: String

    /// Date the document was created
    @NSManaged var createdAt: Date

    /// Raw string representing DocType (sketch / annotatedPDF / imported)
    @NSManaged var typeRaw: String

    /// Optional thumbnail image data for library preview
    @NSManaged var thumbnailData: Data?

    /// Optional drawing data (PKDrawing encoded as Data)
    @NSManaged var drawingData: Data?

    /// Optional PDF file data for imported/annotated PDFs
    @NSManaged var pdfData: Data?

    // MARK: - Computed Properties

    /// Typed accessor for the document's DocType
    var type: DocType {
        get { DocType(rawValue: typeRaw) ?? .sketch }
        set { typeRaw = newValue.rawValue }
    }

    // MARK: - Core Data Helpers

    /// Inserts and returns a new Document into the given context
    static func insert(
        into context: NSManagedObjectContext,
        title: String,
        type: DocType
    ) -> Document {
        let doc = Document(context: context)
        doc.id = UUID()
        doc.title = title
        doc.createdAt = Date()
        doc.type = type
        return doc
    }
}
