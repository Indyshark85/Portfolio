class TrueFalseQuestion extends ChoiceQuestion{
    /**
     * Constructor
     * @param prompt String
     * @param isCorrect Boolean
     */
    TrueFalseQuestion(String prompt,boolean isCorrect) {
        super(prompt,isCorrect? "true":"false");
        super.addChoice("true", isCorrect);
        super.addChoice("false", !isCorrect);

    }

    /**
     * prints TrueFalse as a string
     * @return String
     */
    @Override
    public String toString(){
        return super.toString();
    }

}
