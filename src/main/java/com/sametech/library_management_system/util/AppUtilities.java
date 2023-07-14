package com.sametech.library_management_system.util;

import java.security.SecureRandom;
import java.util.Base64;

public class AppUtilities {
    public static final String EMAIL_REGEX_STRING = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    public static String generateRandomString(int tokenLength) {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[tokenLength];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
