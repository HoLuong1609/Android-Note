package com.example.mobileteam1.encrypt;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    public static final String TEXT = "ifs.com.vn";

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        oneWayCryptography();
    }

    private static void oneWayCryptography() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // MD5 algorithm
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(TEXT.getBytes("UTF-8"));
        byte raw[] = md.digest();
        String hash = new BigInteger(1, raw).toString(16);
        System.out.println("MD5: " + hash);

        // SHA algorithm
        MessageDigest md2 = MessageDigest.getInstance("SHA");
        md2.update(TEXT.getBytes("UTF-8"));
        byte raw2[] = md2.digest();
        String hash2 = new BigInteger(1, raw2).toString(16);
        System.out.println("SHA: " + hash2);
    }
}
