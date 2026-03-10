import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CapitalizeTest {
    private void createTestFile(String filename, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(content);
        writer.close();
    }

    private String readOutputFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

    @Test
    void testCapitalize() throws IOException{
        String[] inputs = {
                "hello world. this is a test.",
                "  this is sentence one.,  here is sentence two. ",
                "hi there. how are you.\nthis is a test.\nnew line here.",
                "",
                "this is. test five."
        };


        String[] expected = {
                "Hello world. This is a test.",
                "This is sentence one., Here is sentence two.",
                "Hi there. How are you. This is a test. New line here.",
                "",
                "This is. Test five."
        };

        // Filenames for input and output
        String[] filenames = { "a.in", "b.in", "c.in", "d.in", "e.in" };

        // Loop over each test case, create the input file, run the capitalize method, and check the output
        for (int i = 0; i < filenames.length; i++) {
            createTestFile(filenames[i], inputs[i]);  // Create the test file
            Capitalize.capitalize(filenames[i]);      // Run the capitalize method
        }

        // Use assertions to verify the output files contain the expected capitalized text
        assertAll(
                () -> assertEquals(expected[0], readOutputFile("a.out")),
                () -> assertEquals(expected[1], readOutputFile("b.out")),
                () -> assertEquals(expected[2], readOutputFile("c.out")),
                () -> assertEquals(expected[3], readOutputFile("d.out")),
                () -> assertEquals(expected[4], readOutputFile("e.out"))
        );

        // Clean up the created files (input and output files)
        for (String f : filenames) {
            Files.deleteIfExists(Paths.get(f));                      // Delete the input files
            Files.deleteIfExists(Paths.get(f.replace(".in", ".out")));  // Delete the output files
        }
    }
}
