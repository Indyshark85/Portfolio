//
//  Model.swift
//  lab-08-secondTask
//
//  Created by Inman, Conner David on 3/5/26.
//

import Foundation

class Model {
    let maxNumber = 12
    
    func multiply(section: Int, row: Int) -> Int {
        return (section + 1) * (row + 1)
    }
}

let model = Model()
