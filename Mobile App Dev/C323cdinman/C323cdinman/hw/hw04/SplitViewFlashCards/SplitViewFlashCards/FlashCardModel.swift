//
//  TheTabbedFlashCardsModel.swift
//  TheTabbedFlashCards
//
//  Created by Inman, Conner David on 2/5/26.
//

import Foundation

struct FlashCard: Codable {
    var question: String
    var answer: String
}



class FlashCardModel: Codable{
    var cards:[FlashCard] = []
    
    init(){
        cards.append(FlashCard(question: "What is the capital of Florida?", answer: "Tallahassee"))
        cards.append(FlashCard(question: "What is the Capital of France", answer: "Paris"))
    }
    
    func updateCard(at index: Int, with newQuestion: String, and newAnswer: String){
        cards[index].question = newQuestion
        cards[index].answer = newAnswer
    }
    func getNumberOfCards() -> Int{
        return cards.count
    }
    func getQuestion(at index: Int) -> String{
        return cards[index].question
    }
    
    func getCard(at index: Int) -> FlashCard{
        return cards[index]
    }
    func addCard(_ card: FlashCard){
        cards.append(card)
    }
    
    
}
