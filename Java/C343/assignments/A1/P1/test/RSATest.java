import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {

    /**
     * Here we have some very simple tests that all work. The first
     * test is just to show that we can convert a string to a BigInteger
     * and back.
     * <p>
     * The second test is a simple encryption and decryption with small keys
     * so that can see the logic.
     * <p>
     * The third test is a more realistic test with a 1024-bit key.
     * <p> 
     * Your job is to write more tests. You should be able to break
     * the code by providing bad input. You should also test the
     * performance of the code.
     */

    @Test
    void bigInt () {
        String chars = "abc";

        BigInteger a = RSA.str2int(chars);
        String s = RSA.int2str(a);

        System.out.println("a: " + a);
        assertEquals(chars, s);
    }

    @Test
    void rsa0 () {
        RSA rsa = new RSA(5,17);

        String message = "A";
        BigInteger encrypted = rsa.encrypt(message);
        String decrypted = rsa.decrypt(encrypted);

        System.out.println("Encrypted message: " + encrypted);
        assertEquals(message, decrypted);
    }

    @Test
    void rsa1 () {
        RSA rsa = new RSA(1024);

        String message = "Hello, world!";
        BigInteger encrypted = rsa.encrypt(message);
        String decrypted = rsa.decrypt(encrypted);

        System.out.println("Encrypted message: " + encrypted);
        assertEquals(message, decrypted);
    }
}
