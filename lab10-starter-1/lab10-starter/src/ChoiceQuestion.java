import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class ChoiceQuestion extends Question{
    private final Map<String,Boolean> QC;

    /**
     * Constructor
     * @param prompt String
     * @param answer String
     */
    ChoiceQuestion(String prompt, String answer) {
        super(prompt, answer);
        this.QC = new LinkedHashMap<>();
    }
    ChoiceQuestion(String prompt) {
        this(prompt, null);
    }


    /**
     * Adds a choice
     * @param Choice
     * @param isCorrect
     */
    void addChoice(String Choice, boolean isCorrect){
        if (isCorrect) {
            super.setAnswer(Choice);
        }
        QC.put(Choice,isCorrect);
    }

    /**
     * overides the toString to ouput all options
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();
        print.append(super.getPrompt());
        for (String choice : QC.keySet()) {
            print.append("\n").append(choice);
        }
        return print.toString();
    }

    public List<String> getChoices(){
        List<String> answers = new ArrayList<>();
        for (String choice: QC.keySet()){
            answers.add(choice);
        }
        return answers;
    }
}
