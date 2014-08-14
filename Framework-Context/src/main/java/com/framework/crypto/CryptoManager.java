package com.framework.crypto;

import com.framework.enumeration.CryptEnum;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class CryptoManager {

    private CryptoManager() {
    }
    private static CryptoManager cryptoManager;
    private static final Map<CryptEnum, ICrypto> cryptoMap = new HashMap<CryptEnum, ICrypto>(2, 1);

    static {
        cryptoMap.put(CryptEnum.AES, new AESCrypto());
        cryptoMap.put(CryptEnum.MD5, new MD5Crypto());
        cryptoMap.put(CryptEnum.SHA, new SHACrypto());
    }

    public static synchronized CryptoManager getInstance() {
        if (cryptoManager == null) {
            cryptoManager = new CryptoManager();
        }
        return cryptoManager;
    }

    public ICrypto getCrypto(CryptEnum cryptEnum) {
        return cryptoMap.get(cryptEnum);
    }
}
