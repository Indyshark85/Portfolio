//
//  EditCardsViewController.swift
//  TabbedFlashCards
//
//  Created by Inman, Conner David on 2/12/26.
//

import UIKit

class EditCardsViewController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var questionTextField: UITextField!
    @IBOutlet weak var answerTextField: UITextField!
    
    var model: FlashCardModel?
    var currentIndex = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()

        if let appDelegate = UIApplication.shared.delegate as? AppDelegate {
            model = appDelegate.myFlashCardModel
        }
        
        questionTextField.text = ""
        answerTextField.text = ""
    }
    
    @IBAction func NewQuestionButton(_ sender: Any) {
        guard
            let q = questionTextField.text,
            let a = answerTextField.text,
            !q.isEmpty,
            !a.isEmpty
        else { return }

        let newCard = FlashCard(question: q, answer: a)
        model?.addCard(newCard)

        if let count = model?.getNumberOfCards() {
            currentIndex = count - 1
        }
        
        displayCard(at: currentIndex)
        refreshPrimaryTable()

        questionTextField.text = ""
        answerTextField.text = ""
    }
    
    @IBAction func nextCard(_ sender: Any) {
        guard let model = model, model.getNumberOfCards() > 0 else { return }

        currentIndex += 1
        if currentIndex >= model.getNumberOfCards() {
            currentIndex = 0
        }

        displayCard(at: currentIndex)
      }
    
    func displayCard(at index: Int) {
        view.endEditing(true)

        guard let model = model, model.getNumberOfCards() > 0 else {
            questionTextField.text = ""
            answerTextField.text = ""
            return
        }

        let card = model.getCard(at: index)
        questionTextField.text = card.question
        answerTextField.text = card.answer
    }
    
    
    
    @IBAction func EditButton(_ sender: Any) {
        guard
            let model = model,
            model.getNumberOfCards() > 0,
            let q = questionTextField.text, !q.isEmpty,
            let a = answerTextField.text, !a.isEmpty
        else { return }

        model.updateCard(at: currentIndex, with: q, and: a)
        refreshPrimaryTable()
        displayCard(at: currentIndex)
    }
    
    
    func refreshPrimaryTable() {
        guard
                let split = self.splitViewController,
                let primaryNav = split.viewControllers.first as? UINavigationController,
                let tableVC = primaryNav.topViewController as? PrimaryTableViewController
            else {
                print("Could not reach PrimaryTableViewController")
                return
            }

            tableVC.tableView.reloadData()
    }
    
}

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
