import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    @Test
    void test(){
        ChoiceQuestion q1=new ChoiceQuestion("What is the capital of Indiana?","Indianapolis");
        q1.addChoice("Indianapolis",true);
        q1.addChoice("Bloomington",false);
        q1.addChoice("Carmel",false);
        q1.addChoice("Bargersville",false);
        TrueFalseQuestion q2 =new TrueFalseQuestion("The Sky is blue",true);
        FillInBlankQuestion q3 = new FillInBlankQuestion("5+5=______?");
        q3.addChoice("10",true);
        q3.addChoice("11",false);
        q3.addChoice("3+4",false);
        q3.addChoice("9+10",false);
        assertAll(
                () -> assertEquals(
                        ("What is the capital of Indiana?" +
                                "\nCarmel"+
                                "\nBloomington"+
                                "\nBargersville"+
                                "\nIndianapolis")
                        ,q1.toString()),
                () -> assertEquals(false,q1.isCorrect("Bloomington")),
                () -> assertEquals(true,q1.isCorrect("Indianapolis")),
                () -> assertEquals("The Sky is blue" +"\ntrue"+"\nfalse",q2.toString()),
                () -> assertEquals(true,q2.isCorrect("true")),
                () -> assertEquals(false,q2.isCorrect("false")),
                () -> assertEquals(true,q3.isCorrect("10")),
                () -> assertEquals(false,q3.isCorrect("6")),
                () -> assertEquals(false,q3.isCorrect("7")),
                () -> assertEquals(false,q3.isCorrect("9+10")),
                () -> assertEquals("5+5=______?" +"\n11"+"\n9+10"+"\n3+4"+"\n10",q3.toString())
        );
    }

}
