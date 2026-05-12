// Drawer
// Conner Inman (cdinman@iu.edu) & Caden Batts (cabatts@iu.edu)
// C323: Mobile App Development


import Foundation
import CoreData

// MARK: - Signature (Core Data Entity)

/// Core Data managed object representing a saved signature.
/// Matches the Signature entity defined in the UML class diagram.
@objc(Signature)
class Signature: NSManagedObject {

    // MARK: - Attributes

    /// Unique identifier for the signature
    @NSManaged var id: UUID

    /// Display name for this signature (e.g. "My Signature", "Initials")
    @NSManaged var name: String

    /// PNG image data representing the signature drawing
    @NSManaged var imageData: Data

    /// Date the signature was saved
    @NSManaged var createdAt: Date

    // MARK: - Core Data Helpers

    /// Inserts and returns a new Signature into the given context
    static func insert(
        into context: NSManagedObjectContext,
        name: String,
        imageData: Data
    ) -> Signature {
        let sig = Signature(context: context)
        sig.id = UUID()
        sig.name = name
        sig.imageData = imageData
        sig.createdAt = Date()
        return sig
    }
}
