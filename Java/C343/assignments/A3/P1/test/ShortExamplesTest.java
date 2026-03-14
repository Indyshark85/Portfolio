import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ShortExamplesTest {

    @Test
    void testAllSubsequences () {
        Set<String> result = ShortExamples.allSubsequences("abc");
        assertEquals(8, result.size());
        assertTrue(result.contains(""));
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
        assertTrue(result.contains("c"));
        assertTrue(result.contains("ab"));
        assertTrue(result.contains("bc"));
        assertTrue(result.contains("ac"));
        assertTrue(result.contains("abc"));
    }

    @Test
    void testMinDistance () {
        assertEquals(3, ShortExamples.minEditDistance("abc", "def"));
        assertEquals(1, ShortExamples.minEditDistance("abc", "dbc"));
        assertEquals(1, ShortExamples.minEditDistance("abc", "ab"));
        assertEquals(1, ShortExamples.minEditDistance("abc", "abcd"));
        assertEquals(0, ShortExamples.minEditDistance("abc", "abc"));
        System.out.println(ShortExamples.minEditDistance("abcdefg", "bcdefg"));
        /*
           compare first char in "abcdefg" and "bcdefg"
           not equal
             cost1 compare "bcdefg" with "bcdefg" by deleting the first "a" in the first str
             cost2 compare "abcdefg" with "cdefg" by deleting the first "b" in the second str
             cost3 compare "bcdefg" with "cdefg" by deleting the first "a" in the first str and the first "b" i the second str
         */
        System.out.println(ShortExamples.minEditDistance("multiplication", "multiplacatiion"));
        System.out.println(ShortExamples.minEditDistance("multiplicity", "multiplacatiion"));
    }

    @Test
    void testIsSubsequence() {
        assertTrue(ShortExamples.isSubsequence("abc", "abc"));
        assertFalse(ShortExamples.isSubsequence("abc", "a"));
        assertTrue(ShortExamples.isSubsequence("abc", "xaxbxxcxxdxxx"));
    }

    @Test
    void testLongestCommonSubsequence () {
        assertEquals("", ShortExamples.longestCommonSubsequence("abc", "def"));
        assertEquals("bc", ShortExamples.longestCommonSubsequence("abc", "dbc"));
        assertEquals("ab", ShortExamples.longestCommonSubsequence("abc", "ab"));
        assertEquals("abc", ShortExamples.longestCommonSubsequence("abc", "abcd"));
        assertEquals("abc", ShortExamples.longestCommonSubsequence("abc", "abc"));
        assertEquals("abc", ShortExamples.longestCommonSubsequence("abc", "axbxxcxxdxxx"));
        assertEquals("abc", ShortExamples.longestCommonSubsequence("yyyayyybyyyyyyc", "axbxxcxxdxxx"));
    }

    @Test
    void testMergeStrings () {
        assertEquals("abc", ShortExamples.mergeStrings("a", "bc"));
        assertEquals("abc", ShortExamples.mergeStrings("ab", "c"));
        assertEquals("abc", ShortExamples.mergeStrings("ac", "b"));
        assertEquals("abc", ShortExamples.mergeStrings("b", "ac"));
        assertEquals("abc", ShortExamples.mergeStrings("bc", "a"));
        assertEquals("abc", ShortExamples.mergeStrings("c", "ab"));
    }

    @Test
    void testMergeSort () {
        assertEquals("abc", ShortExamples.mergeSort("abc"));
        assertEquals("abc", ShortExamples.mergeSort("bac"));
        assertEquals("abc", ShortExamples.mergeSort("bca"));
        assertEquals("abc", ShortExamples.mergeSort("cab"));
        assertEquals("abc", ShortExamples.mergeSort("cba"));
    }

    @Test
    void testPermutations () {
        Set<String> result = ShortExamples.allPermutations("abc");
        // compute all permutations on "bc"
        // result is "bc" and "cb"
        // permutations for "xabc"
        assertEquals(6, result.size());
        assertTrue(result.contains("abc"));
        assertTrue(result.contains("acb"));
        assertTrue(result.contains("bac"));
        assertTrue(result.contains("bca"));
        assertTrue(result.contains("cab"));
        assertTrue(result.contains("cba"));
    }

    @Test
    void testSum () {
        int[] list = {1, 2, 3, 4, 5};
        assertTrue(ShortExamples.subsetSum(list, 7));
        assertFalse(ShortExamples.subsetSum(list, 100));
        int[] list2 = {1, 10000, 1, 10000, 1, 10000, 1, 10000, 1, 10000};
        assertTrue(ShortExamples.subsetSum(list2, 5));
    }
}