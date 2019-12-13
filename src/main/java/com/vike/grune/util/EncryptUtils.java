package com.vike.grune.util;


import java.security.MessageDigest;

/**
 * @author: lsl
 * @createDate: 2019/3/15
 */
public class EncryptUtils {

    private static final String MESSAGE_DIGEST_MD2 = "MD2";
    private static final String MESSAGE_DIGEST_MD5 = "MD5";
    private static final String MESSAGE_DIGEST_SHA = "SHA";
    private static final String MESSAGE_DIGEST_SHA_224 = "SHA-224";
    private static final String MESSAGE_DIGEST_SHA_256 = "SHA-256";
    private static final String MESSAGE_DIGEST_SHA_384 = "SHA-384";
    private static final String MESSAGE_DIGEST_SHA_512 = "SHA-512";
    private static final String CHARSET_NAME = "utf-8";
    private static final int ITERATIONS = 10;

    public static String MD2(String str){
        return encrypt(str,MESSAGE_DIGEST_MD2);
    }

    public static String MD5(String str){
        return encrypt(str,MESSAGE_DIGEST_MD5);
    }

    public static String SHA1(String str){
        return encrypt(str,MESSAGE_DIGEST_SHA);
    }

    public static String SHA224(String str){
        return encrypt(str,MESSAGE_DIGEST_SHA_224);
    }

    public static String SHA256(String str){
        return encrypt(str,MESSAGE_DIGEST_SHA_256);
    }

    public static String SHA384(String str){
        return encrypt(str,MESSAGE_DIGEST_SHA_384);
    }

    public static String SHA512(String str){
        return encrypt(str,MESSAGE_DIGEST_SHA_512);
    }

    private static String encrypt(String str, String algorithm){
        MessageDigest md;
        try{
            md = MessageDigest.getInstance(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        byte[] mdBytes = md.digest(str.getBytes());

        return bytesToHexString(mdBytes).toLowerCase();
    }

    private static String bytesToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer(bytes.length);
        for (int i = 0;i< bytes.length;i++){
            String temp = Integer.toHexString(0xFF & bytes[i]);
            if (temp.length() < 2){
                sb.append(0);
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    public static String password(String password, String salt) {

        try {
            byte [] passwordByte = password.getBytes(CHARSET_NAME);

            byte[] saltByte = salt.getBytes(CHARSET_NAME);

            MessageDigest digest =  MessageDigest.getInstance(MESSAGE_DIGEST_SHA_256);

            digest.reset();
            digest.update(saltByte);

            byte[] hashed = digest.digest(passwordByte);

            int iterations = ITERATIONS - 1;

            for(int i = 0; i < iterations; ++i) {
                digest.reset();
                hashed = digest.digest(hashed);
            }

            return bytesToHexString(hashed);

        }catch (Exception e){
            return e.getMessage();
        }
    }

    public static void main(String [] args){
        String str = "123456salt";
        System.out.println("MD2: "+MD2(str));
        System.out.println("MD5: "+MD5(str));
        System.out.println("SHA1: "+SHA1(str));
        System.out.println("SHA224: "+SHA224(str));
        System.out.println("SHA256: "+SHA256(str));
        System.out.println("SHA384: "+SHA384(str));
        System.out.println("SHA512: "+SHA512(str));
        System.out.println("password: "+password("123456","salt"));
    }
}

