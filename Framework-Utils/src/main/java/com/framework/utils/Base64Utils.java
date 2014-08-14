package com.framework.utils;

import com.frameworkLog.factory.LogFactory;
import java.io.IOException;
import org.slf4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Administrator
 */
public class Base64Utils {
    private  static final Logger logger = LogFactory.getInstance().getLogger(Base64Utils.class);
    private Base64Utils() {
    }
    private static final BASE64Decoder base64Decoder = new BASE64Decoder();
    private static final BASE64Encoder base64Encoder = new BASE64Encoder();

    public static byte[] decode(String content) {
        byte[] result = null;
        try {
            result = base64Decoder.decodeBuffer(content);
        } catch (IOException ex) {
            logger.error("decode 解密时候出现异常", ex);
        }
        return result;
    }

    public static String decodeToString(String content) {
        String result = null;
        try {
            byte[] contents = base64Decoder.decodeBuffer(content);
            result = new String(contents, "UTF-8");
        } catch (Exception ex) {
            logger.error("decodeToString 解密时候出现异常", ex);
        }
        return result;
    }

    public static String encode(byte[] bytes) {
        String result = base64Encoder.encode(bytes);
        return result;
    }
   
}
