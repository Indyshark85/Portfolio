import java.util.Arrays;
import java.util.Scanner;

public class CoinChange {

    public static int minChange(int amount, int[] denominations) {
        // TODO: Implement the Bottom-Up Dynamic Programming approach
        // Hint: Use an array dp[] where dp[i] represents the minimum number of coins needed for amount i
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : denominations) {
                if (i - coin >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];

        //old code didnt understand until later what was being asked
        /**
        int change=0;
        int quarter=25,dime=10,nickel=5,penny=1;
        while(change!=amount){
            if ((change+quarter)<amount){
                change+=quarter;
                dp.add(25);
            }else if ((change+dime)<amount) {
                change +=dime;
                dp.add(10);
            }else if ((change+nickel)<amount){
                change +=nickel;
                dp.add(5);
            }else{
                change += penny;
                dp.add(1);
            }
        }
        return 0; // Placeholder return
         **/
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

/**
 * So both approaches make it so the code will break the problem into subproblems however bottom-up avoids
 * bulky recursive statements that are super computationally heavy. And while topdown does use recursion it
 * will store the data to avoid recomputation
 */

