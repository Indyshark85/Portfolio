// Drawer
// Conner Inman (cdinman@iu.edu) & Caden Batts (cabatts@iu.edu)
// C323: Mobile App Development

import UIKit
import CoreData

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    // MARK: - Core Data Stack

    /// The persistent container for Core Data storage
    lazy var persistentContainer: NSPersistentContainer = {
        let container = NSPersistentContainer(name: "DrawerApp")
        container.loadPersistentStores { _, error in
            if let error = error {
                // In production, handle this gracefully rather than crashing
                fatalError("Core Data failed to load: \(error.localizedDescription)")
            }
        }
        return container
    }()

    /// Convenience accessor for the main managed object context
    var context: NSManagedObjectContext {
        return persistentContainer.viewContext
    }

    // MARK: - UIApplicationDelegate

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        return true
    }

    // MARK: - UISceneSession Lifecycle

    func application(
        _ application: UIApplication,
        configurationForConnecting connectingSceneSession: UISceneSession,
        options: UIScene.ConnectionOptions
    ) -> UISceneConfiguration {
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    // MARK: - Core Data Saving

    /// Save Core Data context if there are unsaved changes
    func saveContext() {
        let context = persistentContainer.viewContext
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                let nsError = error as NSError
                print("Core Data save error: \(nsError), \(nsError.userInfo)")
            }
        }
    }
}
