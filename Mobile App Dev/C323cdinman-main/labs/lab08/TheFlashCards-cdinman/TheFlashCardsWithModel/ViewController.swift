//
//  ViewController.swift
//  TheFlashCardsWithModelWithModel
//
//  Created by Inman, Conner David on 1/29/26.
//

import UIKit

class ViewController: UIViewController {
    //Two new properties for the ViewController Class         
    @IBOutlet weak var answerLabel: UILabel!
    @IBOutlet weak var questionLabel: UILabel!
    
    let myFlashCardModel = TheFlashCardModel()
    
    var buttonPress = false
    
    @IBAction func showQuestion(_ sender: Any) {
        let lQuestion = self.myFlashCardModel.getNextQuestion()
        self.questionLabel.text = lQuestion
        self .answerLabel.text = "?"
        buttonPress = true
        
    }
    @IBAction func showAnswer(_ sender: Any) {
        let lAnswer = self.myFlashCardModel.getTheAnswer()
        if buttonPress == true {
            self.answerLabel.text = lAnswer
        }else{
            self.answerLabel.text = "Tap me again!"
        }
        
    }
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
}

