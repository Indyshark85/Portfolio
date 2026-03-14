// C323 Mobile App Development - Mitja Hmeljak Spring 2026
//
//  starter code for Homework 06 
//

import Foundation

class Guess3Model {

    // provided 3-letter English words,
    //    starting with 'a' to 'm':
    let words = ["abs","ace","act","add","ads","age","ago","aid","all","and",
                 "app","arc","ate","awe","ban","bar","bat","bay","bee","bet",
                 "bid","big","bit","boa","bog","boo","bop","bot","bow","box",
                 "boy","bro","bud","bum","bun","bus","but","buy","bye","cab",
                 "can","cap","car","cat","dad","did","dig","dip","doe","dot",
                 "dub","dud","dye","ear","eat","eel","egg","ego","emu","end",
                 "err","eve","eye","fat","fax","fee","fit","fix","foe","fog",
                 "fox","fur","gag","gap","gas","gem","gig","gin","git","goo",
                 "gum","gut","ham","hay","hem","hen","her","him","his","hot",
                 "how","hue","hug","hum","hut","ion","irk","ivy","jab","jag",
                 "jam","jar","jaw","jet","jig","jog","kid","kin","kip","kit",
                 "koi","lab","lad","lax","lay","leg","let","lid","lip","lot",
                 "low","lur","map","mar","mat","maw","max","may","met","mix",
                 "mob","mom","mop","mud","mug","mum",

                 //extra words n-z
                 "nap","net","new","nil","nod","nor","not",
                 "oak","oar","odd","off","oil","old","one","orb",
                 "pad","pan","pat","paw","pay","pen","pet","pie",
                 "rag","ram","ran","rat","raw","red","rib","rid",
                 "sad","sap","sat","saw","say","sea","see","set",
                 "tan","tap","tar","tax","tea","ten","tin","tip",
                 "urn","use",
                 "van","vet","via","vow",
                 "war","wax","way","web","wet","who","why","win",
                 "yak","yam","yap","yaw","yes","yet",
                 "zap","zip","zoo"]

    //state variables,
    //     e.g. to keep track of the number of attempts in the best game so far,
    //     the number of guesses in the current game,
    //     the current word to be guessed, etc.
    var currentlyPlaying = false
    var bestGame = Int.min
    var guesses = 0
    var currentWord = ""
    var lastGuess = ""
    var correctLetters = 0

    
    func gameStart() {
        let lCurrentWordIndex = Int.random(in: 0..<self.words.count)
        currentWord = words[lCurrentWordIndex]
        
        guesses = 0
        lastGuess = ""
        correctLetters = 0
        
        currentlyPlaying = true
        
    }

    //gameStop() method:
    func gameStop() {
        self.currentlyPlaying = false
    }

    // TODO: implement guessProcess() method:
    func guessProcess(pGuess: String ) -> [Bool] {

        var lWhichLettersGuessed: [Bool] = [false,false,false]
        
        guard pGuess.count == 3 else {
            print("How did you manage this??? Invalid guess Length!")
            return lWhichLettersGuessed
        }
        
        guesses += 1
        lastGuess = pGuess
        correctLetters = 0
        
        for i in 0..<3 {
            let wordIndex = currentWord.index(currentWord.startIndex, offsetBy: i)
            let guessIndex = pGuess.index(pGuess.startIndex, offsetBy: i)
            
            if currentWord[wordIndex] == pGuess[guessIndex] {
                lWhichLettersGuessed[i] = true
                correctLetters += 1
            }
        }
        
        if pGuess == currentWord {
            currentlyPlaying = false
            
            if guesses < bestGame {
                bestGame = guesses
            }
        }

        print("in Model.guessProcess(), lWhichLettersGuessed is \(lWhichLettersGuessed)")
        return lWhichLettersGuessed

    } // end of guessProcess() method


} // end of class Guess3Model
