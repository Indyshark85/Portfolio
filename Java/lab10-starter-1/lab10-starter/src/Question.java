class Question {

    private final String PROMPT;
    private String answer;

    /**
     * Constructor
     * @param prompt String
     * @param answer String
     */
    public Question(String prompt, String answer) {
        this.PROMPT = prompt;
        this.answer = answer;
    }

    /**
     * Constuctor 2 (to address the case there is no ans)
     * @param prompt
     */
    public Question(String prompt) {
        this(prompt,null);
    }

    @Override
    public String toString() {
        return this.PROMPT;
    }

    /**
     * Determines whether a given answer is the correct answer.
     *
     * @param ans - answer TO the question itself.
     * @return true if ans is correct, false otherwise.
     */
    boolean isCorrect(String ans) {
        return this.answer.equals(ans);
    }

    String getPrompt() {
        return this.PROMPT;
    }

    void setAnswer(String ans) {
        this.answer = ans;
    }

    String getAnswer() {
        return this.answer;
    }
}