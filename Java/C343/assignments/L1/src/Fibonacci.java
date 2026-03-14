class TODO extends RuntimeException {} // TODO: remove this line when you're done

public class Fibonacci {

    public static int fibonacci(int n) {
        throw new TODO();
    }

    public static int fibonacci_iter(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        int prev = 0;   // F(0)
        int curr = 1;   // F(1)

        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }

        return curr;
    }
}
