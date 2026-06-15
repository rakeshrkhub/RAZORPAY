package com.rakesh.razorpay.common.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomizerUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        // Each byte will later be filled with random data.
        byte[] buf = new byte[length];
        // Fill the byte array with cryptographically secure random bytes.
        // SecureRandom is suitable for tokens, secrets, and security-sensitive values.
        SECURE_RANDOM.nextBytes(buf);
        // Convert the random bytes into a URL-safe Base64 string.
        return Base64
                .getUrlEncoder() // getUrlEncoder() uses '-' and '_' instead of '+' and '/'.
                .withoutPadding() // withoutPadding() removes trailing '=' characters.
                .encodeToString(buf) // encodeToString() converts the encoded bytes into a String.
                .substring(0, length); // Return only the first 'length' characters of the encoded string.
    }
}
