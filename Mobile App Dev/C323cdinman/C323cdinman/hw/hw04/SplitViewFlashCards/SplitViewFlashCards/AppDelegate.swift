//
//  AppDelegate.swift
//  SplitViewFlashCards
//
//  Created by Inman, Conner David on 2/19/26.
//

import UIKit

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    //hw04 - create an instance of the main model class
    //let myLearnerCardModel: LearnerCardModel = LearnerCardModel()
    //this doesnt work it was never defined big man
    
    var myFlashCardModel = FlashCardModel()
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool{
        return true
    }


}

