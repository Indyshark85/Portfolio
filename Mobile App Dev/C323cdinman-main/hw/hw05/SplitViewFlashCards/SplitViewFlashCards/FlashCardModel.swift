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
        save()
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
        save()
    }
    func getFileURL() -> URL {
        let fileURL = FileManager.default.urls(
            for: .documentDirectory,
            in: .userDomainMask
        )[0].appendingPathComponent("FlashCardModel.plist")
        return fileURL
    }
    func save() {
        let encoder = PropertyListEncoder()
        let url = getFileURL()

        do {
            let data = try encoder.encode(self)

            try data.write(to: url, options: .atomic)

            print("Saved flashcards at:", url.path)

        } catch {
            print("Save failed:", error)
        }
    }
    
    static func load() -> FlashCardModel? {

        let url = FileManager.default.urls(
            for: .documentDirectory,
            in: .userDomainMask
        )[0].appendingPathComponent("FlashCardModel.plist")

        print("Looking for file at:", url.path)

        guard FileManager.default.fileExists(atPath: url.path) else {
            print("No saved flashcards yet")
            return nil
        }

        do {
            let data = try Data(contentsOf: url)
            let decoder = PropertyListDecoder()
            return try decoder.decode(FlashCardModel.self, from: data)
        } catch {
            print("load failed:", error)
            return nil
        }
    }
    
    
}
