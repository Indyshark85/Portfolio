class FillInBlankQuestion extends ChoiceQuestion {
    /**
     * Constructor
     * @param prompt String
     */
    FillInBlankQuestion(String prompt) {
        super(prompt,null);
    }

    /**
     * Adds a choice
     * @param ans String
     */
    void addChoice(String ans) {
        super.addChoice(ans, true);
    }

    /**
     *
     * @param ans - answer TO the question itself.
     * @return
     */
    @Override
    boolean isCorrect(String ans) {
        if (super.getChoices().contains(ans))
            return true;
        return false;
    }


    @Override
    void setAnswer(String ans) {
        throw new UnsupportedOperationException("FillInBlankQuestion: cannot directly set answer.");
    }

    
    @Override
    String getAnswer() {
        throw new UnsupportedOperationException("FillInBlankQuestion: cannot directly get answer.");
    }
}
