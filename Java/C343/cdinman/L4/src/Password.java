import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A class that stores the hash of a password and can check if another password matches it.
 */

public class Password {
    private final MessageDigest md;
    private final byte[] hashBytes;

    // The Password object securely stores the hash of the given password in the hashBytes field.
    public Password(String password) throws NoSuchAlgorithmException {
        this.md = MessageDigest.getInstance("SHA-256");
        this.hashBytes = md.digest(password.getBytes());
    }

    // Verifies if a guessed password matches the hashed one.
    /**
     Using MessageDigest.isEqual protects against timing attacks,
     which exploit the time it takes to compare two byte arrays.
     */
    public boolean checkPassword(String password2) {
        byte[] hashBytes2 = md.digest(password2.getBytes());
        return MessageDigest.isEqual(hashBytes, hashBytes2);
    }
}