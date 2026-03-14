//
//  ContentView.swift
//  Guess3Hierarchical
//
//  Created by Inman, Conner David on 3/12/26.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            HeaderView()
            GuessInputView()
            SubmitButtonView()
        }
        .padding()
    }
}

struct HeaderView: View {
    var body: some View {
        Text("Guess the Number")
            .font(.largeTitle)
            .padding()
    }
}

struct GuessInputView: View {
    @State var guess: String = ""
    var body: some View {
        HStack{
            Text("Your Guess: ")
            TextField("Enter guess", text: $guess)
                .textFieldStyle(.roundedBorder)
        }
    }
}

struct SubmitButtonView: View {
    var body: some View {
        Button("Submit Guess"){
            print("Guess submitted")
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
