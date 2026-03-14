//
//  TheFlashCardsWithModelModel.swift
//  TheFlashCardsWithModel
//
//  Created by Inman, Conner David on 2/5/26.
//

import Foundation

class TheFlashCardModel{
    var theCurrentQuestionIndex = 0
    var theQuestion = ["How much is 7+7 ?",
                       "In what country is Timbuktu ?",
                       "What rotates when you ride a bike ?"]
    var  theAnswers = ["14", "Mali", "Wheels"]
    
    init(){
        
    }
    
    func getNextQuestion() -> String{
        self.theCurrentQuestionIndex = self.theCurrentQuestionIndex + 1
        
        if self.theCurrentQuestionIndex == self.theQuestion.count{
            self.theCurrentQuestionIndex = 0
        }
        return self.theQuestion[self.theCurrentQuestionIndex]
    }
    
    func getTheAnswer() -> String{
        return self.theAnswers[self.theCurrentQuestionIndex]
    }
}
