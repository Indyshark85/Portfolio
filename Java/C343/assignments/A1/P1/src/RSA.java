import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Random;

/**
 * A very simple RSA implementation.
 */

public class RSA {
    // The public key is (n, e) and the private key is (n, d)
    // d must be kept secret
    // n is the product of two primes, p and q
    // if one succeeds in factoring n, one can easily compute d
    private final @NotNull BigInteger n, e, d;

    public RSA (int p, int q) {
        this(BigInteger.valueOf(p), BigInteger.valueOf(q));
    }

    public RSA (int length) {
        this(BigInteger.probablePrime(length, new Random()), BigInteger.probablePrime(length, new Random()));
    }

    public RSA (@NotNull BigInteger p, @NotNull BigInteger q) {
        this.n = p.multiply(q);
        BigInteger phi = sub1(p).multiply(sub1(q));
        BigInteger tempe = new BigInteger("3");
        while (phi.gcd(tempe).intValue() > 1) { tempe = add1(tempe); }
        this.e = tempe;
        this.d = e.modInverse(phi);
    }

    /**
     * The next four methods are simple utility methods to do arithmetic
     * on BigIntegers and convert between Strings and BigIntegers.
     * All the methods could be private but the conversions are public
     * because they are useful for testing.
     */

    private static @NotNull BigInteger sub1 (@NotNull BigInteger a) {
        return a.subtract(BigInteger.ONE);
    }

    private static @NotNull BigInteger add1 (@NotNull BigInteger a) {
        return a.add(BigInteger.ONE);
    }

    public static @NotNull BigInteger str2int(@NotNull String chars) {
        return new BigInteger(chars.getBytes());
    }

    public static @NotNull String int2str(@NotNull BigInteger a) {
        return new String(a.toByteArray());
    }

    /**
     * The core of RSA: encryption and decryption. The process
     * is the same for both, except for the exponent used. Encryption
     * uses the public key exponent e, while decryption uses the private
     * key exponent d. So anyone can encrypt a message using the public
     * key, but only the holder of the private key can decrypt it.
     */

    public @NotNull BigInteger encrypt (@NotNull String message) {
        return str2int(message).modPow(e, n);
    }

     public @NotNull String decrypt (@NotNull BigInteger encrypted) {
        return int2str(encrypted.modPow(d,n));
    }
}
