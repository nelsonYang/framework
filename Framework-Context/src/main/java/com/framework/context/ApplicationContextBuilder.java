package com.framework.context;

import com.framework.context.configuration.ConfigurationManager;
import com.framework.spi.provider.CodeManager;
import com.framework.spi.provider.KeysManager;
import com.framework.spi.provider.SessionManager;
import com.framework.spi.provider.WebSessionManager;
import com.frameworkLog.factory.LogFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class ApplicationContextBuilder extends AbstractApplicationContextBuilder {

    private final Logger logger = LogFactory.getInstance().getLogger(ApplicationContextBuilder.class);

    @Override
    protected void buildConfiguration() {
        Reader reader = null;
        InputStream is = this.getClass().getResourceAsStream("/system.properties");
        Properties property = new Properties();
        try {
            reader = new InputStreamReader(is, "UTF-8");
            property.load(reader);
            ConfigurationManager configurationManager = ConfigurationManager.getConfigurationManager();
            configurationManager.setBaseIMURL(property.getProperty("baseIMURL"));
            configurationManager.setBaseSMSURL(property.getProperty("baseSMSURL"));
            configurationManager.setSmsUserName(property.getProperty("smsUserName"));
            configurationManager.setSmsPassword(property.getProperty("smsPassword"));
            configurationManager.setBaseURL(property.getProperty("baseURL"));
            configurationManager.setUploadDir(property.getProperty("uploadDir"));
            configurationManager.setUploadURL(property.getProperty("uploadURL"));
            configurationManager.setSessionTimeout(Long.parseLong(property.getProperty("sessionTimeOut")));
            configurationManager.setCodeTimeout(Long.parseLong(property.getProperty("codeTimeOut")));
            configurationManager.setCodeURL(property.getProperty("codeURL"));
            configurationManager.setSessionURL(property.getProperty("sessionURL"));
            configurationManager.setWebSessionURL(property.getProperty("webSessionURL"));
            configurationManager.setKeyURL(property.getProperty("keyURL"));
            configurationManager.setLocalSession(Boolean.parseBoolean(property.getProperty("localSession")));
            configurationManager.setPayGateURL(property.getProperty("payGateURL"));
            configurationManager.setWidth(Integer.parseInt(property.getProperty("width")));
            configurationManager.setHeight(Integer.parseInt(property.getProperty("height")));
            configurationManager.setCompressQuality(Float.parseFloat(property.getProperty("compressQuality")));
            configurationManager.setRegisterURL(property.getProperty("registerURL"));
            configurationManager.setProductId(property.getProperty("productId"));
            configurationManager.setChannel(property.getProperty("channel"));
            configurationManager.setDownloadMessage(property.getProperty("downloadMessage"));
            configurationManager.setValidateCodeMessage(property.getProperty("validateCodeMessage"));
            configurationManager.setTradeCodeMessage(property.getProperty("tradeCodeMessage"));
            configurationManager.setRecommandMessage(property.getProperty("recommandMessage"));
            configurationManager.setAppKey(property.getProperty("appKey"));
            configurationManager.setMasterSecret(property.getProperty("masterSecret"));
            configurationManager.setJpushURL(property.getProperty("jpushURL"));
            configurationManager.setChargeMessage(property.getProperty("chargeMessge"));
            configurationManager.setWithDrawMessage(property.getProperty("withDrawMessage"));
            configurationManager.setExchangeMessage(property.getProperty("exchangeMessage"));
            configurationManager.setInvestMessage(property.getProperty("investMessage"));
            configurationManager.setInterestMessage(property.getProperty("interestMessage"));
            configurationManager.setInterestAndCaptialMessage(property.getProperty("interestAndCapitalMessage"));
            configurationManager.setWebSessionManagerProvider(property.getProperty("webSessionManagerProvider"));
            configurationManager.setSessionManagerProvider(property.getProperty("sessionManagerProvider"));
            configurationManager.setCodeManagerProvider(property.getProperty("codeManagerProvider"));
            configurationManager.setKeysManagerProvider(property.getProperty("keysManagerProvider"));
            configurationManager.setPackages(property.getProperty("packages"));
            ApplicationContext.CTX.setConfigurationManager(configurationManager);
        } catch (Exception ex) {
            logger.error("加载系统system.properties配置出错", ex);
            throw new RuntimeException("加载系统配置出错");

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    logger.error("关闭输入流出错", ex);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    logger.error("关闭输入流出错", ex);
                }
            }

        }

    }

    @Override
    protected void buildSessionManager() {
        try {
            ConfigurationManager configurationManager = ConfigurationManager.getConfigurationManager();
            String sessionManangerProviderConfig = configurationManager.getSessionManagerProvider();
            String webSessionManagerProviderConfig = configurationManager.getWebSessionManagerProvider();
            String keysManagerProviderConfig = configurationManager.getKeysManagerProvider();
            String codeManagerProviderConfig = configurationManager.getCodeManagerProvider();
            Class<SessionManager> sessionManager = (Class<SessionManager>) Class.forName(sessionManangerProviderConfig);
            ApplicationContext.CTX.setSessionManager(sessionManager.newInstance());
            //build webSessionManager
            Class<WebSessionManager> webSessionManager = (Class<WebSessionManager>) Class.forName(webSessionManagerProviderConfig);
            ApplicationContext.CTX.setWebSessionManager(webSessionManager.newInstance());
            //build code manager
            Class<CodeManager> codeManager = (Class<CodeManager>) Class.forName(codeManagerProviderConfig);
            ApplicationContext.CTX.setCodeManager(codeManager.newInstance());
            //build keys
            Class<KeysManager> keysManager = (Class<KeysManager>) Class.forName(keysManagerProviderConfig);
            ApplicationContext.CTX.setKeysManager(keysManager.newInstance());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
