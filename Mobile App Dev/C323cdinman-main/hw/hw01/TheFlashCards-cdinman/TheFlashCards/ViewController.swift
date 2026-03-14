//
//  ViewController.swift
//  TheFlashCards
//
//  Created by Inman, Conner David on 1/29/26.
//

import UIKit

class ViewController: UIViewController {
    //Two new properties for the ViewController Class
    @IBOutlet weak var answerLabel: UILabel!
    @IBOutlet weak var questionLabel: UILabel!
    
    var buttonPress = false
    
    @IBAction func showQuestion(_ sender: Any) {
        self.questionLabel.text = "How old are you?"
        self.answerLabel.text = "(...try guessing...)"
        buttonPress=true
        
    }
    @IBAction func showAnswer(_ sender: Any) {
        if buttonPress == true {
            self.answerLabel.text = "I'm 26 years old!"
        }else{
            self.answerLabel.text = "Press the 'Show Question' button first."
        }
        
    }
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }


}

