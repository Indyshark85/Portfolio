import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains three methods to find all pairs in an integer array
 * that sum up to a given target value. Each method has a different
 * time complexity (O(n), O(n log n), and O(n^2)).
 */
public class PairsSolution {

    /**
     * O(n) Solution using a HashSet.
     *
     * Explanation:
     * 1. Initialize a HashSet 'seen' to keep track of values we've already encountered.
     * 2. For each number 'num' in the array:
     *    - Compute the 'complement' = (target - num).
     *    - Check if 'complement' is already in the HashSet.
     *      If it is, we've found a pair (complement, num) that sums to 'target'.
     *    - Regardless, insert the current number 'num' into the HashSet for future checks.
     * 3. The average time complexity is O(n), assuming HashSet operations (contains/add) are O(1) on average.
     *
     * @param arr    the input array of integers
     * @param target the sum we're looking to achieve with two numbers
     */
    public Set<String> findPairsHashSet(int[] arr, int target) {
        System.out.println("\n-- O(n) HashSet Solution --");
        // A HashSet to store numbers we've seen while traversing the array
        HashSet<Integer> seen = new HashSet<>();
        // Another HashSet to store the result pairs
        HashSet<String> result = new HashSet<>();
        // Iterate through the array
        for (int num : arr) {
            int complement = target - num;
            // If we have already seen the complement, then (complement, num) is a valid pair
            if (seen.contains(complement)) {
                String pr = "(" + complement + ", " + num + ")";
                result.add(pr);
                System.out.println("Pair: " + pr);
            }
            // Add the current number to the HashSet before moving on
            seen.add(num);
        }
        return result;
    }

    /**
     * O(n log n) Solution using Sorting + a Two-Pointer approach.
     *
     * Explanation:
     * 1. Sort the array in ascending order. Sorting takes O(n log n) time.
     * 2. Maintain two pointers:
     *    - 'left' starts at the beginning of the array (index 0).
     *    - 'right' starts at the end of the array (index arr.length - 1).
     * 3. Calculate the sum = arr[left] + arr[right].
     *    - If sum == target, we've found a pair. Move both pointers (left++ and right--) to look for more.
     *    - If sum < target, move left++ to increase the sum (because the array is sorted).
     *    - If sum > target, move right-- to decrease the sum.
     * 4. The while loop runs in O(n) after the sort, for a total of O(n log n).
     *
     * @param arr    the input array of integers
     * @param target the sum we're looking to achieve with two numbers
     */
    public Set<String> findPairsTwoPointer(int[] arr, int target) {
        System.out.println("\n-- O(n log n) Two-Pointer Solution --");

        HashSet<String> result = new HashSet<>();
        // Sort the array in-place; sorting costs O(n log n)
        Arrays.sort(arr);

        int left = 0;
        int right = arr.length - 1;

        // While the two pointers don't cross
        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) {
                String pr = "(" + arr[left] + ", " + arr[right] + ")";
                result.add(pr);
                System.out.println("Pair: " + pr);
                left++;
                right--;
            } else if (sum < target) {
                // Sum too small; move 'left' to the right to increase sum
                left++;
            } else {
                // Sum too large; move 'right' to the left to decrease sum
                right--;
            }
        }
        return result;
    }

    /**
     * O(n^2) Brute Force Solution.
     *
     * Explanation:
     * 1. Use two nested loops to consider every pair (arr[i], arr[j]) where j > i.
     * 2. If arr[i] + arr[j] == target, print the pair.
     * 3. Because of the nested loops, the time complexity is O(n^2).
     *
     * @param arr    the input array of integers
     * @param target the sum we're looking to achieve with two numbers
     */
    public Set<String> findPairsBruteForce(int[] arr, int target) {
        System.out.println("\n-- O(n^2) Brute Force Solution --");
        HashSet<String> result = new HashSet<>();
        // Outer loop picks the first element
        for (int i = 0; i < arr.length; i++) {
            // Inner loop picks the second element
            for (int j = i + 1; j < arr.length; j++) {
                // Check if the pair sums to the target
                if (arr[i] + arr[j] == target) {
                    String pr = "(" + arr[i] + ", " + arr[j] + ")";
                    result.add(pr);
                    System.out.println("Pair: " + pr);
                }
            }
        }
        return result;
    }
}