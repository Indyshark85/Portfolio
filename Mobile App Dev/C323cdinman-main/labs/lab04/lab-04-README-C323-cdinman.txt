1. in the bk2ch10p526textFieldDelegate example, which class
implements the UITextFieldDelegate protocol?


ViewController.swift


2. in the class implementing the UITextFieldDelegate protocol, what is
the role of the textFieldDidBeginEditing(_:) method, and what
functionality does that method implement?

This is called in order to designate that the field has become the first responder


3. try running the bk2ch10p526textFieldDelegate textbook example
in the iOS simulator, compiling and running the example in Xcode
26.2, and observe:
what is the outcome that you can observe when interacting with the
app in the iOS simulator, specifically when entering text in the
UITextField?

The text box seems to allow certain letters to remain black and after that all others will be red and underlined


4. Which methods (in the example's source code) cause the behavior
that you observe?

func beRed(_ textField : UITextField)