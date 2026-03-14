//
//  TheFlashCardsWithModelModel.swift
//  TheFlashCardsWithModel
//
//  Created by Inman, Conner David on 2/5/26.
//

import Foundation

class TheFlashCardModel: Codable{
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
    func saveModel(_ model: TheFlashCardModel){
        let encoder = PropertyListEncoder()
        
        if let data = try? encoder.encode(model){
            let url = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)[0].appendingPathComponent("TheFlashCardModel.plist")
            try? data.write(to: url)
        }
    }
    
    func loadModel() -> TheFlashCardModel?{
        let url = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)[0].appendingPathComponent("TheFlashCardModel.plist")
        
        if let data = try? Data(contentsOf: url){
            
            let decoder = PropertyListDecoder()
            
            return try? decoder.decode(TheFlashCardModel.self, from: data)
        }
        return nil
    }
}
