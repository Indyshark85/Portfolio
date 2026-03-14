//
//  TheTabbedFlashCardsModel.swift
//  TheTabbedFlashCards
//
//  Created by Inman, Conner David on 2/5/26.
//

import Foundation

class FlashCardModel{
    private var question: [String]
    private var answers: [String]
    private var CurIdx: Int = 0
    
    
    init(){
        self.question = ["How much is 7+7 ?",
                        "In what country is Timbuktu ?",
                        "What rotates when you ride a bike ?"]
        self.answers = ["14", "Mali", "Wheels"]
        self.CurIdx = 0
        
    }
    
    func getNextQuestion() -> String{
        self.CurIdx += 1
        
        if self.CurIdx >= self.question.count{
            self.CurIdx = 0
        }
        return self.question[self.CurIdx]
    }
    func getcurrentQuestionIndex() -> String{
        return self.question[self.CurIdx]
    }
    
    func getTheAnswer() -> String{
        return self.answers[self.CurIdx]
    }
    
    func setCurrentQuestion(pString: String){
        self.question[self.CurIdx] = pString
    }
    func setCurrentAnswer(pString: String){
        self.answers[self.CurIdx] = pString
    }
    
}
