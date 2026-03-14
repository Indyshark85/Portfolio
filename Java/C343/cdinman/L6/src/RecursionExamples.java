import java.io.*;
import java.util.*;

/**
 * Starter Code, Lab 6: recursion over filesystem trees and over strings.
 * .
 * Problem 1: int diskUsage(String path)
 *   - Recursively sums the sizes (in bytes) of a file/directory subtree.
 *   - Uses java.io.File: f.length(), f.isDirectory(), and f.list().
 *   - Directories contribute only the sum of their children (not f.length()).
 * .
 * Problem 2: boolean isShrinkable(String str)
 *   - Returns true iff str can be reduced to a one-letter dictionary word
 *     by repeatedly removing one character and remaining a valid word
 *     at every intermediate step.
 *   - Loads a dictionary from a file named "words" (classpath resource).
 *   - Matching is case-insensitive.
 */
public class RecursionExamples {

    // -------- Problem 2 support: dictionary --------

    private final Set<String> dictionary;

    /** Use default dictionary loader (file named "words" on the classpath). */
    public RecursionExamples() {
        this(loadDefaultDictionary());
    }

    /** Allow injection of a dictionary (useful for testing). */
    public RecursionExamples(Set<String> dictionary) {
        this.dictionary = (dictionary == null) ? Collections.emptySet() : dictionary;
    }

    // ------------------- Problem 1: disk usage ---------------------

    /**
     * Recursively compute total disk usage, in bytes, for the file/directory at {@code path}.
     * If {@code path} does not exist or cannot be listed, returns 0.
     * .
     * Directories: only sum the sizes of their contents (do not add the directory's own length).
     * .
     * TODO: Implement the recursive traversal. Hints:
     *  - If path is null or does not exist, return 0.
     *  - Base case: for a regular file, return f.length(), clamped to int (use Integer.MAX_VALUE if the
     *    value exceeds int range).
     *  - Recursive case: for a directory, compute the total by summing the disk usage of each child entry.
     *  - Use f.list() to obtain child names; if it returns null (e.g., due to permissions), treat the
     *    directory as having no children.
     *  - Accumulate in a long to avoid overflow, then clamp to int on return.
     *
     * @param path absolute or relative filesystem path
     * @return total number of bytes used by the entry and all nested contents, clamped to int range
     */
    public int diskUsage(String path) {
        if (path==null){return 0;}
        File F=new File(path);
        if (!F.exists()){return 0;}
        if (F.isFile()) {
            long size = F.length();
            return (size > Integer.MAX_VALUE) ? Integer.MAX_VALUE :(int) size;
            //if size is greater than Integer.MAX_VALUE, then return Integer.MAX_VALUE, otherwise, return size cast to an int
        }
        String[] children=F.list();
        if (children==null){return 0;}

        long total=0;
        for (String child:children){
            File childFile=new File(F,child);
            total+= diskUsage(childFile.getPath());
        }
        return (total>Integer.MAX_VALUE) ? Integer.MAX_VALUE :(int) total;
        //if total is greater than Integer.MAX_VALUE, then return Integer.MAX_VALUE; otherwise, return total cast to an int

    }

    // ---------------- Problem 2: shrinkable words ------------------

    /**
     * Return true iff {@code str} is shrinkable:
     *  - {@code str} must be a dictionary word, and
     *  - removing one character at a time can eventually reach a one-letter dictionary word,
     *    with every intermediate string also a dictionary word.
     * .
     * Matching is case-insensitive (dictionary and inputs are normalized to lowercase).
     * .
     * TODO:
     *  - Normalize to lowercase.
     *  - Delegate to a private recursive helper that:
     *      * handles base cases (length <= 1) and membership test,
     *      * tries removing each character and short-circuits if any branch succeeds
     *
     * @param str candidate word
     * @return true if shrinkable, false otherwise
     */
    public boolean isShrinkable(String str) {
        if (str==null){return false;}
        return (isShrinkableRec(str.toLowerCase(Locale.ROOT)));
    }

    /** Recursive helper
     * TODO:
     *  - Base cases:
     *      * length <= 1: return dictionary.contains(s)
     *  - If not a dictionary word: return false.
     *  - For each index i, form s' by removing character i and recurse.
     *
     * @param s candidate word
     * @return true if shrinkable, false otherwise
     */
    private boolean isShrinkableRec(String s) {
        if (s.length() <= 1) return dictionary.contains(s);
        if (!dictionary.contains(s)) return false;

        for (int x =0 ; x<s.length();x++){
            String next=s.substring(0,x)+s.substring(x+1);
            if (isShrinkableRec(next)) return true;
        }
        return false;
    }

    // ---------------------- Dictionary loading ---------------------

    /**
     * Attempt to load a dictionary from a classpath resource named {@code "words"}.
     * Lines are trimmed and lowercased; empty lines are ignored.
     */
    private static Set<String> loadDefaultDictionary() {
        Set<String> set = new HashSet<>();
        InputStream in = RecursionExamples.class.getClassLoader().getResourceAsStream("words");
        if (in == null) return set;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = br.readLine()) != null) {
                String w = line.trim().toLowerCase(Locale.ROOT);
                if (!w.isEmpty()) set.add(w);
            }
        } catch (IOException ignored) { }
        return set;
    }
}
