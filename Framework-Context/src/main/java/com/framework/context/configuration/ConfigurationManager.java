package com.framework.context.configuration;

/**
 *
 * @author Administrator
 */
public final class ConfigurationManager {

    private static ConfigurationManager configurationManager = new ConfigurationManager();

    public static ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    /**
     * @param aConfigurationManager the configurationManager to set
     */
    public static void setConfigurationManager(ConfigurationManager aConfigurationManager) {
        configurationManager = aConfigurationManager;
    }
    private String baseIMURL;
    private String baseSMSURL;
    private String smsUserName;
    private String smsPassword;
    private String baseURL;
    private String uploadDir;
    private String uploadURL;
    private long sessionTimeout;
    private long codeTimeout;
    private String sessionURL;
    private String codeURL;
    private String webSessionURL;
    private String keyURL;
    private boolean localSession;
    private String payGateURL;
    private int width;
    private int height;
    private float compressQuality;
    private String registerURL;
    private String productId;
    private String channel;
    private String validateCodeMessage;
    private String downloadMessage;
    private String tradeCodeMessage;
    private String recommandMessage;
    private String chargeMessage;
    private String withDrawMessage;
    private String exchangeMessage;
    private String investMessage;
    private String interestMessage;
    private String interestAndCaptialMessage;
    private String appKey;
    private String masterSecret;
    private String jpushURL;
    private String codeManagerProvider;
    private String keysManagerProvider;
    private String sessionManagerProvider;
    private String webSessionManagerProvider;
    private String packages;

    /**
     * @return the baseIMURL
     */
    public String getBaseIMURL() {
        return baseIMURL;
    }

    /**
     * @param baseIMURL the baseIMURL to set
     */
    public void setBaseIMURL(String baseIMURL) {
        this.baseIMURL = baseIMURL;
    }

    /**
     * @return the baseSMSURL
     */
    public String getBaseSMSURL() {
        return baseSMSURL;
    }

    /**
     * @param baseSMSURL the baseSMSURL to set
     */
    public void setBaseSMSURL(String baseSMSURL) {
        this.baseSMSURL = baseSMSURL;
    }

    /**
     * @return the smsUserName
     */
    public String getSmsUserName() {
        return smsUserName;
    }

    /**
     * @param smsUserName the smsUserName to set
     */
    public void setSmsUserName(String smsUserName) {
        this.smsUserName = smsUserName;
    }

    /**
     * @return the smsPassword
     */
    public String getSmsPassword() {
        return smsPassword;
    }

    /**
     * @param smsPassword the smsPassword to set
     */
    public void setSmsPassword(String smsPassword) {
        this.smsPassword = smsPassword;
    }

    /**
     * @return the baseURL
     */
    public String getBaseURL() {
        return baseURL;
    }

    /**
     * @param baseURL the baseURL to set
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    /**
     * @return the uploadDir
     */
    public String getUploadDir() {
        return uploadDir;
    }

    /**
     * @param uploadDir the uploadDir to set
     */
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    /**
     * @return the uploadURL
     */
    public String getUploadURL() {
        return uploadURL;
    }

    /**
     * @param uploadURL the uploadURL to set
     */
    public void setUploadURL(String uploadURL) {
        this.uploadURL = uploadURL;
    }

    /**
     * @return the sessionTimeout
     */
    public long getSessionTimeout() {
        return sessionTimeout;
    }

    /**
     * @param sessionTimeout the sessionTimeout to set
     */
    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    /**
     * @return the codeTimeout
     */
    public long getCodeTimeout() {
        return codeTimeout;
    }

    /**
     * @param codeTimeout the codeTimeout to set
     */
    public void setCodeTimeout(long codeTimeout) {
        this.codeTimeout = codeTimeout;
    }

    /**
     * @return the sessionURL
     */
    public String getSessionURL() {
        return sessionURL;
    }

    /**
     * @param sessionURL the sessionURL to set
     */
    public void setSessionURL(String sessionURL) {
        this.sessionURL = sessionURL;
    }

    /**
     * @return the codeURL
     */
    public String getCodeURL() {
        return codeURL;
    }

    /**
     * @param codeURL the codeURL to set
     */
    public void setCodeURL(String codeURL) {
        this.codeURL = codeURL;
    }

    /**
     * @return the webSessionURL
     */
    public String getWebSessionURL() {
        return webSessionURL;
    }

    /**
     * @param webSessionURL the webSessionURL to set
     */
    public void setWebSessionURL(String webSessionURL) {
        this.webSessionURL = webSessionURL;
    }

    /**
     * @return the localSession
     */
    public boolean isLocalSession() {
        return localSession;
    }

    /**
     * @param localSession the localSession to set
     */
    public void setLocalSession(boolean localSession) {
        this.localSession = localSession;
    }

    /**
     * @return the keyURL
     */
    public String getKeyURL() {
        return keyURL;
    }

    /**
     * @param keyURL the keyURL to set
     */
    public void setKeyURL(String keyURL) {
        this.keyURL = keyURL;
    }

    /**
     * @return the payGateURL
     */
    public String getPayGateURL() {
        return payGateURL;
    }

    /**
     * @param payGateURL the payGateURL to set
     */
    public void setPayGateURL(String payGateURL) {
        this.payGateURL = payGateURL;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the compressQuality
     */
    public float getCompressQuality() {
        return compressQuality;
    }

    /**
     * @param compressQuality the compressQuality to set
     */
    public void setCompressQuality(float compressQuality) {
        this.compressQuality = compressQuality;
    }

    /**
     * @return the registerURL
     */
    public String getRegisterURL() {
        return registerURL;
    }

    /**
     * @param registerURL the registerURL to set
     */
    public void setRegisterURL(String registerURL) {
        this.registerURL = registerURL;
    }

    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @return the validateCodeMessage
     */
    public String getValidateCodeMessage() {
        return validateCodeMessage;
    }

    /**
     * @param validateCodeMessage the validateCodeMessage to set
     */
    public void setValidateCodeMessage(String validateCodeMessage) {
        this.validateCodeMessage = validateCodeMessage;
    }

    /**
     * @return the downloadMessage
     */
    public String getDownloadMessage() {
        return downloadMessage;
    }

    /**
     * @param downloadMessage the downloadMessage to set
     */
    public void setDownloadMessage(String downloadMessage) {
        this.downloadMessage = downloadMessage;
    }

    /**
     * @return the tradeCodeMessage
     */
    public String getTradeCodeMessage() {
        return tradeCodeMessage;
    }

    /**
     * @param tradeCodeMessage the tradeCodeMessage to set
     */
    public void setTradeCodeMessage(String tradeCodeMessage) {
        this.tradeCodeMessage = tradeCodeMessage;
    }

    /**
     * @return the recommandMessage
     */
    public String getRecommandMessage() {
        return recommandMessage;
    }

    /**
     * @param recommandMessage the recommandMessage to set
     */
    public void setRecommandMessage(String recommandMessage) {
        this.recommandMessage = recommandMessage;
    }

    /**
     * @return the withDrawMessage
     */
    public String getWithDrawMessage() {
        return withDrawMessage;
    }

    /**
     * @param withDrawMessage the withDrawMessage to set
     */
    public void setWithDrawMessage(String withDrawMessage) {
        this.withDrawMessage = withDrawMessage;
    }

    /**
     * @return the exchangeMessage
     */
    public String getExchangeMessage() {
        return exchangeMessage;
    }

    /**
     * @param exchangeMessage the exchangeMessage to set
     */
    public void setExchangeMessage(String exchangeMessage) {
        this.exchangeMessage = exchangeMessage;
    }

    /**
     * @return the investMessage
     */
    public String getInvestMessage() {
        return investMessage;
    }

    /**
     * @param investMessage the investMessage to set
     */
    public void setInvestMessage(String investMessage) {
        this.investMessage = investMessage;
    }

    /**
     * @return the interestMessage
     */
    public String getInterestMessage() {
        return interestMessage;
    }

    /**
     * @param interestMessage the interestMessage to set
     */
    public void setInterestMessage(String interestMessage) {
        this.interestMessage = interestMessage;
    }

    /**
     * @return the interestAndCaptialMessage
     */
    public String getInterestAndCaptialMessage() {
        return interestAndCaptialMessage;
    }

    /**
     * @param interestAndCaptialMessage the interestAndCaptialMessage to set
     */
    public void setInterestAndCaptialMessage(String interestAndCaptialMessage) {
        this.interestAndCaptialMessage = interestAndCaptialMessage;
    }

    /**
     * @return the chargeMessage
     */
    public String getChargeMessage() {
        return chargeMessage;
    }

    /**
     * @param chargeMessage the chargeMessage to set
     */
    public void setChargeMessage(String chargeMessage) {
        this.chargeMessage = chargeMessage;
    }

    /**
     * @return the appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * @param appKey the appKey to set
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * @return the masterSecret
     */
    public String getMasterSecret() {
        return masterSecret;
    }

    /**
     * @param masterSecret the masterSecret to set
     */
    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    /**
     * @return the jpushURL
     */
    public String getJpushURL() {
        return jpushURL;
    }

    /**
     * @param jpushURL the jpushURL to set
     */
    public void setJpushURL(String jpushURL) {
        this.jpushURL = jpushURL;
    }

    /**
     * @return the codeManagerProvider
     */
    public String getCodeManagerProvider() {
        return codeManagerProvider;
    }

    /**
     * @param codeManagerProvider the codeManagerProvider to set
     */
    public void setCodeManagerProvider(String codeManagerProvider) {
        this.codeManagerProvider = codeManagerProvider;
    }

    /**
     * @return the keysManagerProvider
     */
    public String getKeysManagerProvider() {
        return keysManagerProvider;
    }

    /**
     * @param keysManagerProvider the keysManagerProvider to set
     */
    public void setKeysManagerProvider(String keysManagerProvider) {
        this.keysManagerProvider = keysManagerProvider;
    }

    /**
     * @return the sessionManagerProvider
     */
    public String getSessionManagerProvider() {
        return sessionManagerProvider;
    }

    /**
     * @param sessionManagerProvider the sessionManagerProvider to set
     */
    public void setSessionManagerProvider(String sessionManagerProvider) {
        this.sessionManagerProvider = sessionManagerProvider;
    }

    /**
     * @return the webSessionManagerProvider
     */
    public String getWebSessionManagerProvider() {
        return webSessionManagerProvider;
    }

    /**
     * @param webSessionManagerProvider the webSessionManagerProvider to set
     */
    public void setWebSessionManagerProvider(String webSessionManagerProvider) {
        this.webSessionManagerProvider = webSessionManagerProvider;
    }

    /**
     * @return the packages
     */
    public String getPackages() {
        return packages;
    }

    /**
     * @param packages the packages to set
     */
    public void setPackages(String packages) {
        this.packages = packages;
    }
}
