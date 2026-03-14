//
//  PrimaryViewController.swift
//  TabbedFlashCards
//
//  Created by Inman, Conner David on 1/29/26.
//

import UIKit

class FlashCardViewController: UIViewController {
    @IBOutlet weak var questionLabel: UILabel!
    @IBOutlet weak var answerLabel: UILabel!

    var model: FlashCardModel?
    var currentIndex = 0
    var showingAnswer = false

    override func viewDidLoad() {
        super.viewDidLoad()

        // Grab shared model
        if let appDelegate = UIApplication.shared.delegate as? AppDelegate {
            model = appDelegate.myFlashCardModel
        }
    }

        // MARK: - Buttons

    var buttonpress = false
    @IBAction func nextCard(_ sender: Any) {
        guard let model = model else { return }
        
        currentIndex += 1
        if currentIndex >= model.getNumberOfCards() {
            currentIndex = 0
        }
        buttonpress = true
        showQuestion()
    }

    @IBAction func showAnswer(_ sender: Any) {
        guard let model = model else { return }
        if buttonpress == true{
            let card = model.getCard(at: currentIndex)
            answerLabel.text = card.answer
            showingAnswer = true
        }else{
            answerLabel.text = "Please Press Question Box"
        }
    }

    // MARK: - Helpers

    public func showQuestion() {
        guard let model = model,
                model.getNumberOfCards() > 0 else {
            questionLabel.text = "No cards"
            answerLabel.text = ""
            return
        }
        
        let card = model.getCard(at: currentIndex)
        questionLabel.text = card.question
        answerLabel.text = "(tap Show Answer)"
        showingAnswer = false
    }
}

