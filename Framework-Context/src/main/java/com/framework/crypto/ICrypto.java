package com.framework.crypto;

/**
 *
 * @author Administrator
 */
public interface ICrypto {

    public String decrypt(String content, String key);

    public String encrypt(String content, String key);
}
