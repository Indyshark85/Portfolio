import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Assumptions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RecursionExamplesTest {

    // -------------------- diskUsage tests --------------------

    @Test
    void diskUsage_nonexistent_returnsZero() {
        RecursionExamples rec = new RecursionExamples(Collections.emptySet());
        int size = rec.diskUsage("this-path-should-not-exist-123456789");
        assertEquals(0, size);
    }

    @Test
    void diskUsage_singleFile_returnsExactBytes(@TempDir Path tmp) throws IOException {
        // Create a single file of known size
        byte[] data = new byte[1234];
        Arrays.fill(data, (byte) 1);

        Path f = tmp.resolve("alone.bin");
        Files.write(f, data);

        RecursionExamples rec = new RecursionExamples(Collections.emptySet());
        int size = rec.diskUsage(f.toString());
        assertEquals(1234, size);
    }

    @Test
    void diskUsage_nestedDirectories_sumsAllFiles(@TempDir Path tmp) throws IOException {
        // Create a small tree:
        // tmp/
        //   a.txt (200)
        //   sub/
        //     b.bin (500)
        //     deep/
        //       c.dat (800)
        byte[] A = new byte[200];
        byte[] B = new byte[500];
        byte[] C = new byte[800];
        Arrays.fill(A, (byte) 7);
        Arrays.fill(B, (byte) 8);
        Arrays.fill(C, (byte) 9);

        Path a = tmp.resolve("a.txt");
        Files.write(a, A);

        Path sub = tmp.resolve("sub");
        Files.createDirectories(sub);

        Path b = sub.resolve("b.bin");
        Files.write(b, B);

        Path deep = sub.resolve("deep");
        Files.createDirectories(deep);

        Path c = deep.resolve("c.dat");
        Files.write(c, C);

        RecursionExamples rec = new RecursionExamples(Collections.emptySet());
        int size = rec.diskUsage(tmp.toString());
        assertEquals(200 + 500 + 800, size);
    }

    // -------------------- isShrinkable tests (with injected dictionary) --------------------

    @Test
    void isShrinkable_true_forClassicChain_startling() {
        // A classic shrinkable chain:
        // startling → starting → staring → string → sting → sing → sin → in → i
        Set<String> dict = new HashSet<>(Arrays.asList(
                "startling", "starting", "staring", "string", "sting", "sing", "sin", "in", "i"
        ));
        RecursionExamples rec = new RecursionExamples(dict);

        assertTrue(rec.isShrinkable("startling"));
        // spot-check a mid chain element also returns true
        assertTrue(rec.isShrinkable("string"));
        // smallest base case
        assertTrue(rec.isShrinkable("i"));
    }

    @Test
    void isShrinkable_false_whenIntermediateMissing() {
        // Similar to a chain but with one necessary step missing
        // stone → tone → one → on → o
        // Remove "tone" from dict; shrink should fail.
        Set<String> dict = new HashSet<>(Arrays.asList(
                "stone", /* "tone" missing */ "one", "on", "o"
        ));
        RecursionExamples rec = new RecursionExamples(dict);

        assertFalse(rec.isShrinkable("stone"));
        // but if we start at "one" that is a valid chain one→on→o
        assertTrue(rec.isShrinkable("one"));
    }

    @Test
    void isShrinkable_caseInsensitive_andRejectsNullOrEmpty() {
        Set<String> dict = new HashSet<>(Arrays.asList("a", "at", "eat"));
        RecursionExamples rec = new RecursionExamples(dict);

        // Case-insensitive: "EAT" is in dict as "eat"
        assertTrue(rec.isShrinkable("EAT"));

        // Null is never shrinkable
        assertFalse(rec.isShrinkable(null));

        // Empty string only shrinkable if dictionary contains "", which it does not here
        assertFalse(rec.isShrinkable(""));
    }

    @Test
    void isShrinkable_singleLetterDependsOnDict() {
        Set<String> dict = new HashSet<>(Arrays.asList("a"));
        RecursionExamples rec = new RecursionExamples(dict);
        assertTrue(rec.isShrinkable("a"));

        RecursionExamples recNoSingle = new RecursionExamples(Collections.emptySet());
        assertFalse(recNoSingle.isShrinkable("a"));
    }

    // -------------------- More tests using given "words" file --------------------

    @Test
    void isShrinkable_defaultDictionary_ifWordsAvailable() {
        // Only run if "words" is present (classpath or working directory)
        boolean hasClasspathWords;
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("words")) {
            hasClasspathWords = (in != null);
        } catch (IOException e) {
            hasClasspathWords = false;
        }
        boolean hasWorkingDirWords = new File("words").exists();

        Assumptions.assumeTrue(hasClasspathWords || hasWorkingDirWords,
                "Skipping: 'words' resource not found on classpath or working directory");

        // With the large dictionary, "startling" is a common shrinkable example
        RecursionExamples rec = new RecursionExamples();
        assertTrue(rec.isShrinkable("startling"));
    }

    // -------------------- More comprehensive tests using the provided words file --------------------

    // you might put your own tests (if any) here

    // -------------------- Private helpers for the new tests --------------------

    private static Optional<Set<String>> loadWordsIfAvailable() {
        // Try classpath first
        try (InputStream in = RecursionExamplesTest.class
                .getClassLoader()
                .getResourceAsStream("words")) {
            if (in != null) {
                return Optional.of(readLowercasedSet(in));
            }
        } catch (IOException ignored) {}

        // Fallback to working directory
        File f = new File("words");
        if (f.exists()) {
            try (InputStream in = new java.io.FileInputStream(f)) {
                return Optional.of(readLowercasedSet(in));
            } catch (IOException ignored) {}
        }
        return Optional.empty();
    }

    private static Set<String> readLowercasedSet(InputStream in) throws IOException {
        Set<String> set = new HashSet<>();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(in))) {
            String line;
            while ((line = br.readLine()) != null) {
                String w = line.trim().toLowerCase(Locale.ROOT);
                if (!w.isEmpty()) set.add(w);
            }
        }
        return set;
    }

    /** Find a chain [w3, w2, w1] with lengths 3->2->1, each deletion removing one char. */
    private static Optional<List<String>> findShrinkChainLength3(Set<String> dict) {
        Map<Integer, List<String>> byLen = new HashMap<>();
        for (String w : dict) {
            byLen.computeIfAbsent(w.length(), k -> new ArrayList<>()).add(w);
        }
        if (!byLen.containsKey(3) || !byLen.containsKey(2) || !byLen.containsKey(1)) {
            return Optional.empty();
        }
        Set<String> L1 = new HashSet<>(byLen.get(1));
        Set<String> L2 = new HashSet<>(byLen.get(2));

        Set<String> shrinkable2 = new HashSet<>();
        for (String w2 : L2) {
            if (hasDeletionTo(w2, L1)) {
                shrinkable2.add(w2);
            }
        }
        if (shrinkable2.isEmpty()) return Optional.empty();

        for (String w3 : byLen.get(3)) {
            for (int i = 0; i < w3.length(); i++) {
                String w2 = w3.substring(0, i) + w3.substring(i + 1);
                if (shrinkable2.contains(w2)) {
                    for (int j = 0; j < w2.length(); j++) {
                        String w1 = w2.substring(0, j) + w2.substring(j + 1);
                        if (L1.contains(w1)) {
                            return Optional.of(List.of(w3, w2, w1));
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    private static boolean hasDeletionTo(String w, Set<String> targetSet) {
        for (int i = 0; i < w.length(); i++) {
            String sub = w.substring(0, i) + w.substring(i + 1);
            if (targetSet.contains(sub)) return true;
        }
        return false;
    }

    private static Optional<String> findNonShrinkableDictionaryWord(Set<String> dict, RecursionExamples rx) {
        List<String> all = new ArrayList<>(dict);
        // Search longer words first (more likely to fail)
        all.sort(Comparator.comparingInt(String::length).reversed());
        for (String w : all) {
            if (w.length() >= 2 && !rx.isShrinkable(w)) {
                return Optional.of(w);
            }
        }
        return Optional.empty();
    }

    private static List<String> pickSomeWords(Set<String> dict, int n) {
        List<String> list = new ArrayList<>(dict);
        Collections.shuffle(list, new Random(42));
        list.sort(Comparator.comparingInt(String::length));
        List<String> out = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size && out.size() < n; i += Math.max(1, size / Math.max(3, n))) {
            out.add(list.get(i));
        }
        for (int i = 0; i < size && out.size() < n; i++) {
            if (!out.contains(list.get(i))) out.add(list.get(i));
        }
        return out;
    }

    private static String mixCase(String s) {
        StringBuilder b = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            b.append((i % 2 == 0) ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }
        return b.toString();
    }
}
