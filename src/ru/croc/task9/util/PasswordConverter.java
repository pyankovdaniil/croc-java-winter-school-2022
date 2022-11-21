package ru.croc.task9.util;

public class PasswordConverter {
    private static final int RADIX = 26;
    private PasswordConverter() {
    }

    public static String getPasswordFromNumber(long number) {
        char[] chars = new char[7];

        for (int i = 6; i >= 0; i--) {
            chars[i] = (char) ((number % RADIX) + 'a');
            number /= RADIX;
        }

        return String.valueOf(chars);
    }

    public static long convertPasswordToNumber(String password) {
        long result = 0;
        byte[] bytes = password.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            result += (bytes[i] - 'a') * Math.pow(RADIX, 6 - i);
        }
        return result;
    }
}
