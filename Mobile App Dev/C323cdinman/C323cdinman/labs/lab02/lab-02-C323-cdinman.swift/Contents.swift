import Cocoa
let animal = "dog"
var guessC = 0
var guessCorrect = false

while !guessCorrect {
    print("Guess an Animal: ")
    
    if let userGuess = readLine(){
        guessC += 1
        if userGuess.lowercased() == animal {
            guessCorrect = true
            print("Correct! It took you \(guessC) guesses.")
        }else{
            print("Im not thinking of a \(userGuess).")
            print("Try again: ")
        }
    }else{
        print("No input recieved. Good bye")
        break
    }
    
}
