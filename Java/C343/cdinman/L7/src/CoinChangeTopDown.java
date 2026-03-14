import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;

public class CoinChangeTopDown {
    private static HashMap<Integer, Integer> memo = new HashMap<>();

    public static int minChangeMemo(int amount, int[] denominations) {
        // Hint: Use recursion and store results in a HashMap to avoid redundant calculation
        if (amount==0) return 0;
        if (amount<0) return -1;
        if (memo.containsKey(amount)) return memo.get(amount);

        int minCoin=Integer.MAX_VALUE;

        for(int coin: denominations){
            int res = minChangeMemo(amount-coin,denominations);
            if(res>=0 && res<minCoin){
                minCoin=res +1;
            }
        }
        int result = (minCoin == Integer.MAX_VALUE) ? -1 : minCoin;
        memo.put(amount,result);
        return result;
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
