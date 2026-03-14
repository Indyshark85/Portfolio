//
//  EditCardsViewController.swift
//  TabbedFlashCards
//
//  Created by Inman, Conner David on 2/12/26.
//

import UIKit

class EditCardsViewController: UIViewController {
    var appDeligate: AppDelegate?
    var myFlashCardModel: FlashCardModel?
    
    @IBOutlet weak var questionTextField: UITextField!
    @IBOutlet weak var answerTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.appDeligate = UIApplication.shared.delegate as? AppDelegate
        self.myFlashCardModel = self.appDeligate?.myFlashCardModel
        
        if let model = self.myFlashCardModel {
            questionTextField.text = model.getTheAnswer()
            answerTextField.text = model.getTheAnswer()
        }
        // Do any additional setup after loading the view.
    }
    
    
    @IBAction func butonOKAction(_ sender: AnyObject) {
        self.appDeligate = UIApplication.shared.delegate as? AppDelegate
        self.myFlashCardModel = self.appDeligate?.myFlashCardModel
        
        if let model = self.myFlashCardModel {
            let newQuestion = questionTextField.text!
            let newAnswer = answerTextField.text!
            
            model.setCurrentQuestion(pString: newQuestion)
            model.setCurrentAnswer(pString: newAnswer)
            
            print("Updated Question: \(newQuestion)")
            print("Updated Answer: \(newAnswer)")
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

}
