public class Factorial {

    public static int factorial(int n) {
        if (n == 0) return 1;
        return n * factorial(n - 1);
    }

    public static int factorial_acc(int n, int acc) {
        if (n == 0) return acc;
        return factorial_acc(n - 1, acc * n);
    }

    public static int factorial_iter(int n, int acc) {
        while (n > 1) {
            acc *= n;
            n = n - 1;
        }
        return acc;
    }
}
