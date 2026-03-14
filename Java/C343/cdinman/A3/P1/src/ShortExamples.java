import java.util.*;

/**
 * Write recursive solutions for the following methods.
 * <p>
 * For each method, there is one test case provided in <code>MainTest.java</code>.
 * Consult the test case to understand the expected behavior. Either before
 * or after writing the recursive method, add more test cases of your own.
 */

public class ShortExamples {

    /**
     * Compute the set of all subsequences of a given string. A subsequence
     * of a string is any string that can be obtained by deleting zero or more
     * characters from the original string while maintaining the order of the
     * remaining characters.
     */
    public static Set<String> allSubsequences(String str) {
        Set<String> result = new HashSet<>();
        if (str.isEmpty()) result.add("");
        else {
            Set<String> substrings = allSubsequences(str.substring(1));
            for (String sub : substrings) {
                result.add(sub);
                result.add(str.charAt(0) + sub);
            }
        }
        return result;

    }

    /**
     * Compute the minimum edit distance between two strings. The minimum edit
     * distance is the minimum number of insertions, deletions, and replacements
     * required to transform one string into another. Each insertion, deletion,
     * or replacement has a cost of 1.
     */
    public static int minEditDistance(String s1, String s2) {
        if (s1.isEmpty()) return s2.length();
        if (s2.isEmpty()) return s1.length();
        if (s1.charAt(0) == s2.charAt(0)) return minEditDistance(s1.substring(1), s2.substring(1));
        int insert = 1 + minEditDistance(s1, s2.substring(1));
        int delete = 1 + minEditDistance(s1.substring(1), s2);
        int replace = 1 + minEditDistance(s1.substring(1), s2.substring(1));
        return Math.min(insert, Math.min(delete, replace));
    }

    /**
     * Check if a given string is a subsequence of another.
     */
    public static boolean isSubsequence(String s1, String s2) {
        if (s1.isEmpty()) return true;
        if (s2.isEmpty()) return false;
        if (s1.charAt(0) == s2.charAt(0)) return isSubsequence(s1.substring(1), s2.substring(1));
        return isSubsequence(s1, s2.substring(1));
    }

    /**
     * Compute the longest common subsequence of two strings.
     */
    public static String longestCommonSubsequence(String s1, String s2) {
        if (s1.isEmpty() || s2.isEmpty()) return "";
        if (s1.charAt(0) == s2.charAt(0))
            return s1.charAt(0) + longestCommonSubsequence(s1.substring(1), s2.substring(1));
        String lcs1 = longestCommonSubsequence(s1.substring(1), s2);
        String lcs2 = longestCommonSubsequence(s1, s2.substring(1));
        return lcs1.length() > lcs2.length() ? lcs1 : lcs2;
    }

    /**
     * Merge two sorted strings in lexicographical order.
     */
    public static String mergeStrings(String s1, String s2) {
        if (s1.isEmpty()) return s2;
        if (s2.isEmpty()) return s1;
        if (s1.charAt(0) < s2.charAt(0)) return s1.charAt(0) + mergeStrings(s1.substring(1), s2);
        return s2.charAt(0) + mergeStrings(s1, s2.substring(1));
    }

    /**
     * Sort the characters in a string using merge sort.
     */
    public static String mergeSort (String s) {
        if (s.length() <= 1) return s;
        int mid = s.length() / 2;
        String left = mergeSort(s.substring(0, mid));
        String right = mergeSort(s.substring(mid));
        return mergeStrings(left, right);
    }

    /**
     * Compute the set of all permutations of a string.
     */
    public static Set<String> allPermutations(String str) {
        Set<String> result = new HashSet<>();
        if (str.isEmpty()) result.add("");
        else {
            char c = str.charAt(0);
            String rest = str.substring(1);
            Set<String> perms = allPermutations(rest);
            for (String perm : perms) {
                for (int i = 0; i <= perm.length(); i++) {
                    result.add(perm.substring(0, i) + c + perm.substring(i));
                }
            }
        }
        return result;
    }

    // given a list of numbers and a target sum and we want
    // to check if any subset of the list can sum up to the target sum
    // ex:
    // list = [1, 2, 3, 4, 5] target = 7 => true; target = 100 => false
    // list = [1, 10000, 1, 10000, 1, 10000, 1, 10000, 1, 10000], target 5 => true

    public static boolean subsetSum(int[] list, int target) {
        return subsetSum(list, target, 0);
    }

    private static boolean subsetSum(int[] list, int target, int index) {
        if (target == 0) return true;
        if (index == list.length) return false;
        return subsetSum(list, target-list[index], index+1) ||
                // make a recursive call with [10000, ...] target is 4
                subsetSum(list, target, index+1);
        // skip the first element try to reach target with remaining elements
    }
}
