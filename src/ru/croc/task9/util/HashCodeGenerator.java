package ru.croc.task9.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashCodeGenerator {
    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private byte[] bytes = new byte[7];
    private byte[] md5HashBytes = new byte[32];

    public int getHashCodeOfMd5Hash(String md5Hash) {
        int result = Arrays.hashCode(md5Hash.getBytes());
        return result;
    }

    private int getHashCodeOfMd5HashOfBytesArray(byte[] bytes) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update(bytes);
        byte[] updatedBytes = digest.digest();

        for (int i = 0; i < 16; i++) {
            md5HashBytes[i * 2] = (byte) HEX_DIGITS[(updatedBytes[i] & 0xff) >> 4];
            md5HashBytes[i * 2 + 1] = (byte) HEX_DIGITS[updatedBytes[i] & 0x0f];
        }

        return Arrays.hashCode(md5HashBytes);
    }

    public int getHashCodeOfPasswordNumber(long passwordNumber) {
        for (int i = 6; i >= 0; i--) {
            bytes[i] = (byte) ((passwordNumber % 26) + 97);
            passwordNumber /= 26;
        }

        return getHashCodeOfMd5HashOfBytesArray(bytes);
    }

    private String toHexString(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(HEX_DIGITS[(b & 0xff) >> 4]);
            hex.append(HEX_DIGITS[b & 0x0f]);
        }
        return hex.toString();
    }

    public String getMd5Hash(String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update(password.getBytes());
        byte[] bytes = digest.digest();
        return toHexString(bytes);
    }

}
