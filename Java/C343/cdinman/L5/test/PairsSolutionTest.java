import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.jetbrains.annotations.NotNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the PairsSolution methods using a couple of example arrays.
 * It demonstrates how each method (HashSet, Two-Pointer, Brute Force) finds
 * pairs that sum to a specific target. It also highlights time complexity differences.
 */
public class PairsSolutionTest {

    /**
     * Utility method to compare all 3 algorithms given:
     *
     * @param arr the array
     * @param target the target
     * @param expected the expected Set of all possible pairs found
     * @param solver the solver that holds all 3 algorithms
     */
    private void assertAllAlgorithmsEqual(
            int[] arr,
            int target,
            Set<String> expected,
            PairsSolution solver) {
        assertEquals(expected, solver.findPairsHashSet(arr.clone(), target));
        assertEquals(expected, solver.findPairsTwoPointer(arr.clone(), target));
        assertEquals(expected, solver.findPairsBruteForce(arr.clone(), target));
    }

    /**
     * Utility method to print the contents of an int array.
     *
     * @param arr the array to print
     */
    private static void printArray(int[] arr) {
        System.out.print("[ ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println("]");
    }


    // used to time a runnable
    private long timeIt(@NotNull Runnable r) {
        long startTime = System.currentTimeMillis();
        r.run();
        return System.currentTimeMillis() - startTime;
    }

    // runnable for findPairsHashSet
    private @NotNull Runnable runHashSet(
            int[] arr,
            int target,
            @NotNull Set<String> expected,
            @NotNull PairsSolution solver) {
        return () -> {
            solver.findPairsHashSet(arr, target);
        };
    }

    // runnable for findPairsTwoPointer
    private @NotNull Runnable runTwoPointer(
            int[] arr,
            int target,
            @NotNull Set<String> expected,
            @NotNull PairsSolution solver) {
        return () -> {
            solver.findPairsTwoPointer(arr, target);
        };
    }

    // runnable for findPairsBruteForce
    private @NotNull Runnable runBruteForce(
            int[] arr,
            int target,
            @NotNull Set<String> expected,
            @NotNull PairsSolution solver) {
        return () -> {
            solver.findPairsBruteForce(arr, target);
        };
    }





    @Test
    void test0() {
        PairsSolution solver = new PairsSolution();
        int[] arr = {2, 4, 3, 5, 7, 8, 9};
        int target = 9;
        Set<String> expected = Set.of("(2, 7)", "(4, 5)");
        assertAllAlgorithmsEqual(arr, target, expected, solver);
    }

    @Test
    void test1() {
        PairsSolution solver = new PairsSolution();
        int[] arr = {1, 1, 2, 3, 4, 5};
        int target = 6;
        Set<String> expected = Set.of("(1, 5)", "(2, 4)");
        assertAllAlgorithmsEqual(arr, target, expected, solver);
    }

    @Test
    void testNoValidPairs() {
        PairsSolution solver = new PairsSolution();
        int[] arr = {1, 2, 2, 3, 4, 5};
        int target = 2;
        Set<String> expected = Set.of();
        assertAllAlgorithmsEqual(arr, target, expected, solver);
    }

    @Test
    void testWithNegatives() {
        PairsSolution solver = new PairsSolution();
        int[] arr = {-5, -4, -3, -2, -2, -1};
        int target = -6;
        Set<String> expected = Set.of("(-5, -1)", "(-4, -2)");
        assertAllAlgorithmsEqual(arr, target, expected, solver);
    }

    @Test
    void testLargeNumbers() {
        PairsSolution solver = new PairsSolution();
        int[] arr = {1000, 2000, 3000, 4000, 5000, 6000};
        int target = 6000;
        Set<String> expected = Set.of("(1000, 5000)", "(2000, 4000)");
        assertAllAlgorithmsEqual(arr, target, expected, solver);
    }

    @Test
    void testVeryLongArray() {
        // create a test where the array is extremely long
        // so that you can feel the BruteForce algorithm running much slower
        // TODO
    }

    @Test
    void compare() {
        // use timeIt and 3 runnable, provide proper input (arr, target)
        // compare the time complexity of 3 algorithms
        // hint: array needs to be long enough
        PairsSolution solver = new PairsSolution();
        // Forgot how to use random util had to look this up too
        int[] arr = new java.util.Random().ints(10_000, -1000, 1000).toArray();
        int target = 50;

        //   \/ holy had to look up how to do this \/
        long hashTime = timeIt(runHashSet(arr.clone(), target, Set.of(), solver));
        long twoPointerTime = timeIt(runTwoPointer(arr.clone(), target, Set.of(), solver));
        long bruteForceTime = timeIt(runBruteForce(arr.clone(), target, Set.of(), solver));

        System.out.println("\n--- Performance Comparison ---");
        System.out.println("HashSet (O(n))         : " + hashTime + " ms");
        System.out.println("TwoPointer (O(n log n)): " + twoPointerTime + " ms");
        System.out.println("BruteForce (O(n²))     : " + bruteForceTime + " ms");
    }


}
