package com.framework.crypto;

import com.framework.utils.Base64Utils;
import com.frameworkLog.factory.LogFactory;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class SHACrypto implements ICrypto {

    private final Logger logger = LogFactory.getInstance().getLogger(SHACrypto.class);

      @Override
    public String encrypt(String content, String key) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("sha-1");
            md.update(content.getBytes("utf8"));
            byte[] md5Result = md.digest();
            result = parseByte2HexStr(md5Result).toLowerCase();
        } catch (Exception ex) {
            logger.error("encrypt md5加密异常",ex);
        }
        return result;
    }

    private String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String content, String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
