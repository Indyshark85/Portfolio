import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SpellCheckerTest {
    private void createFile(String filename, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(content);
        writer.close();
    }

    private String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename))).trim();
    }
    @Test
    void testSpellChecker() throws IOException{
        // Create dictionary
        String dict = String.join("\n",
                "hi", "how", "are", "you", "doing", "i", "am", "just", "fine", "if",
                "say", "so", "myself", "but", "will", "also", "that", "throughly",
                "missing", "punctuation"
        );
        createFile("dictionary.txt", dict);

        // Test inputs and expected outputs
        String[] inputs = {
                "Hi hwo are you donig I am dioing jsut fine if I say so mysefl but I\nwill aslo sya that I am throughlyy misssing puncutiation",
                "Hi how are you I am just fine",
                "qwerty asdfg zxcvb",
                "Hi How ARE YoU",
                ""
        };

        String[] expecteds = {
                "Hi [hwo] are you [donig] I am [dioing] [jsut] fine if I say so [mysefl] but I will [aslo] [sya] that I am [throughlyy] [misssing] [puncutiation]",
                "Hi how are you I am just fine",
                "[qwerty] [asdfg] [zxcvb]",
                "Hi How ARE YoU",
                ""
        };

        // Run spellCheck and assertions
        for (int i = 0; i < inputs.length; i++) {
            String inputFile = "file" + i + ".in";
            String outputFile = "file" + i + ".out";
            createFile(inputFile, inputs[i]);
            SpellChecker.spellCheck("dictionary.txt", inputFile);
        }

        assertAll(
                () -> assertEquals(expecteds[0], readFile("file0.out")),
                () -> assertEquals(expecteds[1], readFile("file1.out")),
                () -> assertEquals(expecteds[2], readFile("file2.out")),
                () -> assertEquals(expecteds[3], readFile("file3.out")),
                () -> assertEquals(expecteds[4], readFile("file4.out"))
        );

        // Cleanup
        for (int i = 0; i < 5; i++) {
            Files.deleteIfExists(Paths.get("file" + i + ".in"));
            Files.deleteIfExists(Paths.get("file" + i + ".out"));
        }
        Files.deleteIfExists(Paths.get("dictionary.txt"));
    }
}
