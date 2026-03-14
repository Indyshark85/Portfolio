import java.util.Arrays;
import java.util.Scanner;

public class CoinChange {

    public static int minChange(int amount, int[] denominations) {
        // TODO: Implement the Bottom-Up Dynamic Programming approach
        // Hint: Use an array dp[] where dp[i] represents the minimum number of coins needed for amount i

        return 0; // Placeholder return
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // TODO: Allow students to input custom denominations
        System.out.println("Enter coin denominations separated by spaces:");
        String[] input = scanner.nextLine().split(" ");
        int[] denominations = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();

        for (int i = 0; i <= 10; i++) {
            System.out.println("Minimum coins for " + i + " cents: " + minChange(i, denominations));
        }

        scanner.close();
    }
}

// TODO: Write a short description comparing the Bottom-Up and Top-Down approaches.
