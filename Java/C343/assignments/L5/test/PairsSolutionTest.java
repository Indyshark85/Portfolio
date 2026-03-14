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
        // create a test where no valid pair found given (arr, target)
        // TODO
    }

    @Test
    void testWithNegatives() {
        // create a test where there are negative integers involved
        // TODO
    }

    @Test
    void testLargeNumbers() {
        // create a test where several large numbers are involved
        // TODO
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

        // TODO

    }


}
