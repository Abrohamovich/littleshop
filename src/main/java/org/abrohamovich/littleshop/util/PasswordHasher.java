package org.abrohamovich.littleshop.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {
    private static final int BCRYPT_COST = 12;

    private PasswordHasher() {}

    public static String hashPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(BCRYPT_COST, plainPassword.toCharArray());
    }

    public static boolean verify(String plainPassword, String hashedPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            return false;
        }
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }
        BCrypt.Result result = BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword.toCharArray());
        return result.verified;
    }
}
