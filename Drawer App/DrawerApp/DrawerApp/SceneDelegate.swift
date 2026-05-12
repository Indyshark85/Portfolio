// Drawer
// Conner Inman (cdinman@iu.edu) & Caden Batts (cabatts@iu.edu)
// C323: Mobile App Development

import UIKit

class SceneDelegate: UIResponder, UIWindowSceneDelegate {

    var window: UIWindow?

    // MARK: - Scene Lifecycle

    func scene(
        _ scene: UIScene,
        willConnectTo session: UISceneSession,
        options connectionOptions: UIScene.ConnectionOptions
    ) {
        guard let windowScene = scene as? UIWindowScene else { return }

        // Build the UITabBarController as the root container view controller
        let tabBarController = buildRootTabBarController()

        window = UIWindow(windowScene: windowScene)
        window?.rootViewController = tabBarController
        window?.makeKeyAndVisible()
    }

    // MARK: - Root Tab Bar Setup

    /// Constructs and returns the app's root UITabBarController with three tabs:
    /// Library, New (canvas), and Settings — matching the wireframe design.
    private func buildRootTabBarController() -> UITabBarController {
        let tabBar = UITabBarController()

        // Tab 1: Document Library
        let libraryVC = DocumentLibraryVC()
        let libraryNav = UINavigationController(rootViewController: libraryVC)
        libraryNav.tabBarItem = UITabBarItem(
            title: "Library",
            image: UIImage(systemName: "folder"),
            selectedImage: UIImage(systemName: "folder.fill")
        )

        // Tab 2: New Canvas (creates a blank sketch)
        let canvasVC = CanvasVC()
        let canvasNav = UINavigationController(rootViewController: canvasVC)
        canvasNav.tabBarItem = UITabBarItem(
            title: "New",
            image: UIImage(systemName: "pencil.circle"),
            selectedImage: UIImage(systemName: "pencil.circle.fill")
        )

        // Tab 3: Settings
        let settingsVC = SettingsVC()
        let settingsNav = UINavigationController(rootViewController: settingsVC)
        settingsNav.tabBarItem = UITabBarItem(
            title: "Settings",
            image: UIImage(systemName: "gearshape"),
            selectedImage: UIImage(systemName: "gearshape.fill")
        )

        tabBar.viewControllers = [libraryNav, canvasNav, settingsNav]
        tabBar.selectedIndex = 0

        // Style the tab bar
        let appearance = UITabBarAppearance()
        appearance.configureWithOpaqueBackground()
        tabBar.tabBar.standardAppearance = appearance
        tabBar.tabBar.scrollEdgeAppearance = appearance

        return tabBar
    }

    // MARK: - Core Data Save on Background

    func sceneDidEnterBackground(_ scene: UIScene) {
        // Flush Core Data when the app backgrounds, as shown in the state diagram
        (UIApplication.shared.delegate as? AppDelegate)?.saveContext()
    }
}
