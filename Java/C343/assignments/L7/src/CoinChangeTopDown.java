import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;

public class CoinChangeTopDown {
    private static HashMap<Integer, Integer> memo = new HashMap<>();

    public static int minChangeMemo(int amount, int[] denominations) {
        // TODO: Implement the Top-Down approach with Memoization
        // Hint: Use recursion and store results in a HashMap to avoid redundant calculations

        return 0; // Placeholder return
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // TODO: Allow students to input custom denominations
        System.out.println("Enter coin denominations separated by spaces:");
        String[] input = scanner.nextLine().split(" ");
        int[] denominations = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();

        for (int i = 23; i < 70; i += 23) {
            long start = System.currentTimeMillis();
            System.out.println("Min coins for " + i + " cents: " + minChangeMemo(i, denominations));
            long end = System.currentTimeMillis();
            System.out.println("Time taken WITH memoization: " + (end - start) + "ms");
        }

        scanner.close();
    }
}
