//  C323 Mobile App Development - Mitja Hmeljak Spring 2026
//
//  starter code for Homework 06 
//

import SwiftUI


struct ContentView: View {

    // TODO: instantiate model object:
    //
    @State var model = Guess3Model()
    
    @State var resultFirstLetter = "?"
    @State var resultSecondLetter = "?"
    @State var resultThirdLetter = "?"
    
    @State var guessFirstLetterVariable = "a"
    @State var guessSecondLetterVariable = "a"
    @State var guessThirdLetterVariable = "a"
    
    @State var attempts = 0
    @State var correctLetters = 0
    @State var lastAttempt = ""
    @State var fastestGame = "-"
    
    
    var body: some View {

        // TODO: Contain all the view elements within a stack
        //       (and stacks may be nested). This VStack is the
        //       top-level, and will contain all UI elements for this
        //       "screen/window" view.
        VStack {
            // TODO: Display game title
            Text("Guess 3 game")
                .font(.largeTitle)
                .padding()
            // TODO: Display Result Letters.
            //       Add view modifiers for appropriate style, positioning, etc.
            HStack(spacing: 30){
                if (resultFirstLetter == guessFirstLetterVariable){
                    Text(resultFirstLetter)
                        .font(.largeTitle)
                        .padding()
                        .background(.green)
                }else{
                    Text(resultFirstLetter)
                        .font(.largeTitle)
                        .padding()
                        .background(.gray)
                }
                if (resultSecondLetter == guessSecondLetterVariable){
                    Text(resultSecondLetter)
                        .font(.largeTitle)
                        .padding()
                        .background(.green)
                }else{
                    Text(resultSecondLetter)
                        .font(.largeTitle)
                        .padding()
                        .background(.gray)
                }
                if (resultThirdLetter == guessThirdLetterVariable){
                        Text(resultThirdLetter)
                            .font(.largeTitle)
                            .padding()
                            .background(.green)
                }else{
                        Text(resultThirdLetter)
                            .font(.largeTitle)
                            .padding()
                            .background(.gray)
                }
            }
            // TODO: Display fastest game
            HStack{
                Text("Fastest Game:")
                    .padding()
                    .background(Color.gray.opacity(0.2))
                Text(fastestGame)
                    .padding()
                    .background(Color.gray.opacity(0.2))
            }
            // TODO: Create Start Game button.
            //       Buttons will need an action closure, and a label closure,
            //       as shown in the outline here:
            Button(action: {
                resultFirstLetter = "?"
                resultSecondLetter = "?"
                resultThirdLetter = "?"
                
                guessFirstLetterVariable = "a"
                guessSecondLetterVariable = "a"
                guessThirdLetterVariable = "a"
                
                model.gameStart()
                /// TODO: set color for all three result letters:

                /// TODO: initialize text in three result letter variables:

                /// TODO: initialize text in three guess letter variables:

                /// TODO: ask model to start a new game:

            }, label: {
                Text("Start New Game")
            })
            .padding()
            .background(Color.yellow.opacity(0.2))

            // TODO: Display Number of Attempts
            Text("Number of Attempts: \(attempts)")
                .padding()
            // TODO: Display correct letters in last attempt
            Text("Correct Letters: \(correctLetters)")
                .padding()
            // TODO: Display last attempt
            Text("Last Attempt: \(lastAttempt)")
            // TODO: Display Guess letters, with respective "+" and "-"
            //       buttons for changing the letters
            HStack{
                VStack{
                    Button("+"){
                        if guessFirstLetterVariable < "z" {
                            var codeUnits: [Unicode.UTF8.CodeUnit] = Array(guessFirstLetterVariable.utf8)
                            codeUnits[0] += 1
                            guessFirstLetterVariable = String(decoding: codeUnits, as: Unicode.UTF8.self)
                        }
                    }
                    Text(guessFirstLetterVariable)
                        .font(.title)
                    Button("-"){
                        if guessFirstLetterVariable > "a" {
                            var codeUnits: [Unicode.UTF8.CodeUnit] = Array(guessFirstLetterVariable.utf8)
                            codeUnits[0] -= 1
                            guessFirstLetterVariable = String(decoding: codeUnits, as: UTF8.self)
                        }
                    }
                }
                VStack{
                    Button("+"){
                        if guessSecondLetterVariable < "z" {
                            var codeUnits: [Unicode.UTF8.CodeUnit] = Array(guessSecondLetterVariable.utf8)
                            codeUnits[0] += 1
                            guessSecondLetterVariable = String(decoding: codeUnits, as: Unicode.UTF8.self)
                        }
                    }
                    Text(guessSecondLetterVariable)
                        .font(.title)
                    Button("-"){
                        if guessSecondLetterVariable > "a" {
                            var codeUnits: [Unicode.UTF8.CodeUnit] = Array(guessSecondLetterVariable.utf8)
                            codeUnits[0] -= 1
                            guessSecondLetterVariable = String(decoding: codeUnits, as: UTF8.self)
                        }
                    }
                }
                
                VStack{
                    Button("+"){
                        if guessThirdLetterVariable < "z" {
                            var codeUnits: [Unicode.UTF8.CodeUnit] = Array(guessThirdLetterVariable.utf8)
                            codeUnits[0] += 1
                            guessThirdLetterVariable = String(decoding: codeUnits, as: Unicode.UTF8.self)
                        }
                    }
                    Text(guessThirdLetterVariable)
                        .font(.title)
                    Button("-"){
                        if guessThirdLetterVariable > "a" {
                            var codeUnits: [Unicode.UTF8.CodeUnit] = Array(guessThirdLetterVariable.utf8)
                            codeUnits[0] -= 1
                            guessThirdLetterVariable = String(decoding: codeUnits, as: UTF8.self)
                        }
                    }
                }
            }
            .padding()
            // Here is an example for the first "+" button:
            // TODO:
            //   adjustments are needed for the "-" button,
            //   then add the other "+" and "-" buttons,
            //   similarly to the first "+" and "-" buttons.



            // TODO: Create a button to guess
            Button(action: {

                // TODO: obtain text from each one of the three guess letter variables,
                //       then concatenate into a string.
                
                let guess = guessFirstLetterVariable + guessSecondLetterVariable + guessThirdLetterVariable
                
                let results = model.guessProcess(pGuess: guess)

                attempts = model.guesses
                correctLetters = model.correctLetters
                lastAttempt = model.lastGuess

                if results[0] { resultFirstLetter = guessFirstLetterVariable }
                if results[1] { resultSecondLetter = guessSecondLetterVariable }
                if results[2] { resultThirdLetter = guessThirdLetterVariable }

                if !model.currentlyPlaying {
                    fastestGame = String(model.bestGame)
                }

                // text concatenated using nil-coalescing operator:

                // TODO: ask model to process the input:

                // TODO: update all information in corresponding labels,
                //       as from results in the model,
                //       e.g. number of attempts so far, etc.:

                // TODO: check results for each letter in string,
                //       and update text for each correctly guessed letter variable


                // TODO: check if current guess is fully correct, stop
                //       the game if so, and update variable showing
                //       fastest game so far so far:

            }, label: {
                Text("Guess")
            })


            // Push the top-level VStack to the top of the screen,
            // rather than the default center:
            Spacer()
        }
    }
} // end of ContentView

#Preview {
    ContentView()
}
