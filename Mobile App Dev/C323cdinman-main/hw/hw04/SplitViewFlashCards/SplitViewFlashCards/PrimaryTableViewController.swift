//
//  PrimaryTableViewController.swift
//  SplitViewFlashCards
//
//  Created by Inman, Conner David on 2/19/26.
//
import UIKit
import Foundation

class PrimaryTableViewController: UITableViewController {
    var model: FlashCardModel?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if model == nil {
            if let appDelegate = UIApplication.shared.delegate as? AppDelegate {
                model = appDelegate.myFlashCardModel
            }
        }
        print("Table cards:", model?.getNumberOfCards() ?? -1)
        print("Model injected?", model != nil)
        print("Card count:", model?.cards.count ?? -1)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        tableView.reloadData()
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return model?.cards.count ?? 0
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        
        cell.textLabel?.text = model?.getQuestion(at: indexPath.row)
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        guard
            let splitVC = self.splitViewController,
            let tabBarVC = splitVC.viewControllers.last as? UITabBarController,
            let navVC = tabBarVC.viewControllers?.first as? UINavigationController,
            let flashVC = navVC.topViewController as? FlashCardViewController,
            let editNav = tabBarVC.viewControllers?[1] as? UINavigationController,
            let editVC = editNav.topViewController as? EditCardsViewController,
            let card = model?.getCard(at: indexPath.row)
        else {
            print("Could not reach FlashCardViewController")
            return
        }
        
        flashVC.currentIndex = indexPath.row
        flashVC.showQuestion()
    }
}
