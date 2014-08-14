package com.framework.crypto;

import com.framework.utils.Base64Utils;
import com.framework.utils.JsonUtils;
import com.frameworkLog.factory.LogFactory;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class AESCrypto implements ICrypto {

    private final Logger logger = LogFactory.getInstance().getLogger(AESCrypto.class);

    @Override
    public String encrypt(String content, String key) {
        byte[] encryptResult = null;
        String result = null;
        try {
            byte[] contentBytes = content.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encryptResult = cipher.doFinal(contentBytes);
        } catch (Exception ex) {
            logger.error("encrypt AES加密出现异常", ex);
        }
        if (encryptResult != null) {
            result = Base64Utils.encode(encryptResult);
        }
        return result;
    }

    @Override
    public String decrypt(String content, String key) {
        String result = null;
        byte[] decryptResult = null;
        try {
            byte[] contentBytes = Base64Utils.decode(content);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decryptResult = cipher.doFinal(contentBytes);
            if (decryptResult != null) {
                result = new String(decryptResult, "UTF-8");
            }
        } catch (Exception ex) {
            logger.error("decrypt AES解密出现异常", ex);
        }
        return result;

    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("session", "xxxx");
        map.put("currentIndex", "1");
        map.put("pageCount", "10");
        String json = JsonUtils.mapToJson(map);
        String key = "76666194368845ec";
        String content = new AESCrypto().encrypt(json, key);
        String result1 = new AESCrypto().decrypt(content, key);
        System.out.println(result1);
    }
//    private String parseByte2HexStr(byte buf[]) {
//        StringBuilder sb = new StringBuilder(32);
//        for (int i = 0; i < buf.length; i++) {
//            String hex = Integer.toHexString(buf[i] & 0xFF);
//            if (hex.length() == 1) {
//                hex = '0' + hex;
//            }
//            sb.append(hex.toUpperCase());
//        }
//        return sb.toString();
//    }
//
//    private byte[] parseHexStr2Byte(String hexStr) {
//        if (hexStr.length() < 1) {
//            return null;
//        }
//        byte[] result = new byte[hexStr.length() / 2];
//        for (int i = 0; i < hexStr.length() / 2; i++) {
//            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
//            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
//            result[i] = (byte) (high * 16 + low);
//        }
//        return result;
//    }
}
