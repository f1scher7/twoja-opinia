package twojaOpinia.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

    public static String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return ENCODER.encodeToString(salt);
    }
}