import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem9Test {

    @Test
    void testProblem9() {
        assertAll(
                () -> assertEquals(100, Problem9.strSumNums("hello50how20are30you?")),
                () -> assertEquals(10, Problem9.strSumNums("t1h1i1s1i1s1e1a1s1y1!")),
                () -> assertEquals(0, Problem9.strSumNums("there are no numbers :(")),
                () -> assertEquals(0, Problem9.strSumNums("still 0 just 0 zero0!")),
                () -> assertEquals(500000, Problem9.strSumNums("500000")),
                () -> assertEquals(33, Problem9.strSumNums("1A23B4C5")),
                () -> assertEquals(0, Problem9.strSumNums("abcxyz")),
                () -> assertEquals(15, Problem9.strSumNums("1 2 3 4 5")),
                () -> assertEquals(0, Problem9.strSumNums("")),
                () -> assertEquals(357, Problem9.strSumNums("a357z")),

                () -> assertEquals(1, Problem8.compareFiles("File002.txt", "File1.txt")),  // Leading zeros
                () -> assertEquals(-1, Problem8.compareFiles("File2.txt", "File10.txt")),  // Single vs double digits
                () -> assertEquals(0, Problem8.compareFiles("File001.txt", "File1.txt")),  // Leading zeros treated as same number
                () -> assertEquals(1, Problem8.compareFiles("File9.txt", "File007.txt")),  // Leading zeros with higher number
                () -> assertEquals(-1, Problem8.compareFiles("Document1.pdf", "Document2.pdf")),  // Numeric comparison with different numbers
                () -> assertEquals(1, Problem8.compareFiles("Document10.pdf", "Document2.pdf")),  // Larger numeric part
                () -> assertEquals(-1, Problem8.compareFiles("File1.txt", "File1.doc")),  // Same numeric part, different extension
                () -> assertEquals(1, Problem8.compareFiles("Report10.txt", "Report2.txt"))  // Different numeric parts, same extension
        );


    }
}
