import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem8Test {

    @Test
    void testProblem8() {
        assertAll(
                () -> assertEquals(1, Problem8.compareFiles("File12.txt", "File1.txt")),
                () -> assertEquals(-1, Problem8.compareFiles("File10.txt", "File11.txt")),
                () -> assertEquals(-1, Problem8.compareFiles("File1.txt", "File12.txt")),
                () -> assertEquals(0, Problem8.compareFiles("File1.txt", "File1.txt")),
                () -> assertEquals(1, Problem8.compareFiles("Document20.pdf", "Document3.pdf")),
                () -> assertEquals(-1, Problem8.compareFiles("Data2.csv", "Data10.csv")),
                () -> assertEquals(-1, Problem8.compareFiles("Report5.doc", "Report6.doc")),
                () -> assertEquals(1, Problem8.compareFiles("Image7.png", "Image3.png"))




        );
    }
}
