package com.framework.context;

import com.framework.context.configuration.ConfigurationManager;
import com.framework.crypto.CryptoManager;
import com.framework.crypto.ICrypto;
import com.framework.entity.context.DAOContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.Entity;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.FilterTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.field.FieldManager;
import com.framework.fieldtype.FieldTypeHandler;
import com.framework.fieldtype.FieldTypeManager;
import com.framework.httpclient.HttpConnectionFactory;
import com.framework.register.ServiceRegisterManager;
import com.framework.response.ResponseWriter;
import com.framework.response.ResponseWriterManager;
import com.framework.service.api.WorkHandler;
import com.framework.entity.threadlocal.RollBackCacheThreadLocalManager;
import com.framework.spi.entity.CodeEntity;
import com.framework.spi.provider.CodeManager;
import com.framework.spi.provider.KeysManager;
import com.framework.spi.provider.SessionManager;
import com.framework.spi.provider.WebSessionManager;
import com.framework.threadpool.ThreadPoolManager;
import com.framework.utils.filter.FilterHandler;
import com.framework.utils.filter.FilterManager;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
public class ApplicationContext<T extends Entity,V extends InvocationContext> {
    
    private ApplicationContext() {
    }
    public static ApplicationContext CTX = new ApplicationContext();
    private RollBackCacheThreadLocalManager<V> threadLocalManager;
    private ServiceRegisterManager serviceRegisterManager;
    private CryptoManager cryptoManager;
    private FieldTypeManager fieldTypeManager;
    private KeysManager keysManager;
    private ResponseWriterManager responseWriterManager;
    private HttpConnectionFactory httpConnectionFactory;
    private FieldManager fieldManager;
    private SessionManager sessionManager;
    private WebSessionManager webSessionManager;
    private FilterManager filterManager;
    private CodeManager codeManager;
    private ConfigurationManager configurationManager;
    private ThreadPoolManager threadPoolManager;
    private DAOContext daoContext;

    /**
     * @return the threadLocalManager
     */
    public RollBackCacheThreadLocalManager<V> getThreadLocalManager() {
        return threadLocalManager;
    }

    /**
     * @param threadLocalManager the threadLocalManager to set
     */
    void setThreadLocalManager(RollBackCacheThreadLocalManager<V> threadLocalManager) {
        this.threadLocalManager = threadLocalManager;
    }

    /**
     * @return the serviceRegisterManager
     */
    public ServiceRegisterManager getServiceRegisterManager() {
        return serviceRegisterManager;
    }

    /**
     * @param serviceRegisterManager the serviceRegisterManager to set
     */
    void setServiceRegisterManager(ServiceRegisterManager serviceRegisterManager) {
        this.serviceRegisterManager = serviceRegisterManager;
    }
    
    public WorkHandler getService(String act) {
        return this.serviceRegisterManager.getService(act);
    }

    /**
     * @return the cryptoManager
     */
    public CryptoManager getCryptoManager() {
        return cryptoManager;
    }

    /**
     * @param cryptoManager the cryptoManager to set
     */
    void setCryptoManager(CryptoManager cryptoManager) {
        this.cryptoManager = cryptoManager;
    }
    
    public ICrypto getCrypto(CryptEnum cryptEnum) {
        return this.cryptoManager.getCrypto(cryptEnum);
    }

    
    public Map<String, String[]> getBatchMapParameters() {
        return this.threadLocalManager.getValue().getBatchMapParameters();
    }
    
    public Map<String, String> getSimpleMapParameters() {
        return this.threadLocalManager.getValue().getSimpleMapParameters();
    }
    
    public void success() {
        this.threadLocalManager.getValue().setResultCode(0);
        this.threadLocalManager.getValue().setMsg("操作成功");
    }
    
    public void fail(int errorCode) {
        this.threadLocalManager.getValue().setResultCode(errorCode);
    }
    
    public void fail() {
        this.threadLocalManager.getValue().setResultCode(1);
    }
    
    public void setResultCode(int resultCode) {
        this.threadLocalManager.getValue().setResultCode(resultCode);
    }
    
    public int getResultCode() {
        return this.threadLocalManager.getValue().getResultCode();
    }
    
    public void setEncryptCode(int encryptCode) {
        this.threadLocalManager.getValue().setResultCode(encryptCode);
    }
    
    public int getEncryptCode() {
        return this.threadLocalManager.getValue().getEncryptCode();
    }
    
    public void setMapData(Map<String, String> map) {
        this.threadLocalManager.getValue().setResultMap(map);
    }
    
    public Map<String, String> getMapData() {
        return this.threadLocalManager.getValue().getResultMap();
    }
    
    public void setListMapData(Map<String, List<Map<String, String>>> dataListMap) {
        this.threadLocalManager.getValue().setResultListMap(dataListMap);
    }
    
    public Map<String, List<Map<String, String>>> getListMapData() {
        return this.threadLocalManager.getValue().getResultListMap();
    }

    /**
     * @return the fieldTypeManager
     */
    public FieldTypeManager getFieldTypeManager() {
        return fieldTypeManager;
    }

    /**
     * @param fieldTypeManager the fieldTypeManager to set
     */
    void setFieldTypeManager(FieldTypeManager fieldTypeManager) {
        this.fieldTypeManager = fieldTypeManager;
    }
    
    public FieldTypeHandler getFieldTypeHandler(FieldTypeEnum fieldTypeEnum) {
        return this.fieldTypeManager.getFieldTypeHandler(fieldTypeEnum);
    }
    
    public void setMsg(String msg) {
        this.threadLocalManager.getValue().setMsg(msg);
    }
    
    public String getMsg() {
        return this.threadLocalManager.getValue().getMsg();
    }
    
    public void setCount(int count) {
        this.threadLocalManager.getValue().setCount(count);
    }
    
    public int getCount() {
        return this.threadLocalManager.getValue().getCount();
    }
    
    public void setTotalPage(int totalPage) {
        this.threadLocalManager.getValue().setTotalPage(totalPage);
    }
    
    public int getTotalPage() {
        return this.threadLocalManager.getValue().getTotalPage();
    }
    
    public String getTelephoneNum() {
        return this.threadLocalManager.getValue().getTelephoneNum();
    }
    
    public void setTelephoneNum(String telephoneNum) {
        this.threadLocalManager.getValue().setTelephoneNum(telephoneNum);
    }
    
    public HttpSession getHttpSession() {
        return this.threadLocalManager.getValue().getSession();
    }
    
    public HttpServletResponse getHttpServletResponse() {
        return this.threadLocalManager.getValue().getResponse();
    }
    
    public HttpServletRequest getHttpServletRequest() {
        return this.threadLocalManager.getValue().getRequest();
    }

    /**
     * @return the keysManager
     */
    public KeysManager getKeysManager() {
        return keysManager;
    }

    /**
     * @param keysManager the keysManager to set
     */
    void setKeysManager(KeysManager keysManager) {
        this.keysManager = keysManager;
    }
    
    public String getKey(String telephoneNum) {
        return this.keysManager.getKey(telephoneNum);
    }
    
    public boolean putKey(String key, String value) {
        return this.keysManager.putKey(key, value);
    }
    
    public String getSign() {
        return this.threadLocalManager.getValue().getSign();
    }

    /**
     * @return the responseWriter
     */
    public ResponseWriter getResponseWriter(ResponseTypeEnum responseTypeEnum) {
        return responseWriterManager.getResponseWriter(responseTypeEnum);
    }

    /**
     * @param responseWriter the responseWriter to set
     */
    void setResponseWriterManager(ResponseWriterManager responseWriterManager) {
        this.responseWriterManager = responseWriterManager;
    }

    /**
     * @return the httpConnectionFactory
     */
    public HttpConnectionFactory getHttpConnectionFactory() {
        return httpConnectionFactory;
    }

    /**
     * @param httpConnectionFactory the httpConnectionFactory to set
     */
    void setHttpConnectionFactory(HttpConnectionFactory httpConnectionFactory) {
        this.httpConnectionFactory = httpConnectionFactory;
    }

    /**
     * @return the fieldManager
     */
    public FieldManager getFieldManager() {
        return fieldManager;
    }

    /**
     * @param fieldManager the fieldManager to set
     */
    void setFieldManager(FieldManager fieldManager) {
        this.fieldManager = fieldManager;
    }

    /**
     * @return the sessionManager
     */
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * @param sessionManager the sessionManager to set
     */
    void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * @return the filterManager
     */
    public FilterManager getFilterManager() {
        return filterManager;
    }

    /**
     * @param filterManager the filterManager to set
     */
    void setFilterManager(FilterManager filterManager) {
        this.filterManager = filterManager;
    }
    
    public FilterHandler getFilterHandler(FilterTypeEnum filterTypeEnum) {
        return this.filterManager.getFieldTypeHandler(filterTypeEnum);
    }

    /**
     * @return the codeManager
     */
    public CodeManager getCodeManager() {
        return codeManager;
    }

    /**
     * @param codeManager the codeManager to set
     */
    void setCodeManager(CodeManager codeManager) {
        this.codeManager = codeManager;
    }
    
    public boolean putCode(String key, CodeEntity value) {
        return this.codeManager.putKey(key, value);
    }
    
    public void remove(String key) {
        this.codeManager.remove(key);
    }
    
    public String getCode(String key) {
        return this.codeManager.getKey(key);
    }
    
    public void setMemberId(long memberId) {
        this.threadLocalManager.getValue().setMemberId(memberId);
    }
    
    public long getMemberId() {
        return this.threadLocalManager.getValue().getMemberId();
    }

    /**
     * @return the configurationManager
     */
    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    /**
     * @param configurationManager the configurationManager to set
     */
    void setConfigurationManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }
    
    public String getBaseIMURL() {
        return this.configurationManager.getBaseIMURL();
    }
    
    public String getBaseSMSURL() {
        return this.configurationManager.getBaseSMSURL();
    }
    
    public String getSmsUserName() {
        return this.configurationManager.getSmsUserName();
    }
    
    public String getSmsPassword() {
        return this.configurationManager.getSmsPassword();
    }
    
    public String getBaseURL() {
        return this.configurationManager.getBaseURL();
    }
    
    public String getUploadDir() {
        return this.configurationManager.getUploadDir();
    }
    
    public String getUploadURL() {
        return this.configurationManager.getUploadURL();
    }
    
    public long getSessionTimeout() {
        return this.configurationManager.getSessionTimeout();
    }
    
    public long getCodeTimeout() {
        return this.configurationManager.getCodeTimeout();
    }
    
    public String getRealPath() {
        return this.getHttpServletRequest().getServletContext().getRealPath("/");
    }
    
    public String getJsonCallback() {
        return this.getThreadLocalManager().getValue().getJsonCallback();
    }
    
    public String getUserName() {
        return this.threadLocalManager.getValue().getUserName();
    }
    
    public void setUserName(String userName) {
        this.threadLocalManager.getValue().setUserName(userName);
    }
    
    public void setUserId(long userId) {
        this.threadLocalManager.getValue().setUserId(userId);
    }
    
    public String getUserUuid() {
        return this.threadLocalManager.getValue().getUserUuid();
    }
    
    public long getUserId() {
        return this.threadLocalManager.getValue().getUserId();
    }
    
    public void noData() {
        this.threadLocalManager.getValue().setResultCode(0);
        this.threadLocalManager.getValue().setMsg("查询没有数据");
    }
    
    public String getAct() {
        return this.threadLocalManager.getValue().getAct();
    }
    
    public String getCodeURL() {
        return this.configurationManager.getCodeURL();
    }
    
    public String getSessionURL() {
        return this.configurationManager.getSessionURL();
    }
    
    public String getWebSessionURL() {
        return this.configurationManager.getWebSessionURL();
    }
    
    public boolean getLocalSession() {
        return this.configurationManager.isLocalSession();
    }
    public String getPackages(){
        return this.configurationManager.getPackages();
    }
    
    public String getKeyURL() {
        return this.configurationManager.getKeyURL();
    }

    /**
     * @return the webSessionManager
     */
    public WebSessionManager getWebSessionManager() {
        return webSessionManager;
    }

    /**
     * @param webSessionManager the webSessionManager to set
     */
    void setWebSessionManager(WebSessionManager webSessionManager) {
        this.webSessionManager = webSessionManager;
    }
    
    public long getOrgUserId() {
        return this.threadLocalManager.getValue().getOrgUserId();
    }
    
    public void setOrgUserId(long orgUserId) {
        this.threadLocalManager.getValue().setOrgUserId(orgUserId);
    }
    
    public int getOrgId() {
        return this.threadLocalManager.getValue().getOrgId();
    }
    
    public void setOrgId(int orgId) {
        this.threadLocalManager.getValue().setOrgId(orgId);
    }
    
    public int getOrgType() {
        return this.threadLocalManager.getValue().getOrgType();
    }
    
    public void setOrgType(int orgType) {
        this.threadLocalManager.getValue().setOrgType(orgType);
    }
    
    public void setRegionId(int regionId) {
        this.threadLocalManager.getValue().setRegionId(regionId);
    }
    
    public int getRegionId() {
        return this.threadLocalManager.getValue().getRegionId();
    }
    
    public String getChildOrgIds() {
        return this.threadLocalManager.getValue().getChildOrgId();
    }
    
    public void setChildOrgIds(String childOrgIds) {
        this.threadLocalManager.getValue().setChildOrgId(childOrgIds);
    }
    
    public String getPayGateURL() {
        return this.configurationManager.getPayGateURL();
    }
    
    public int getWidth() {
        return this.configurationManager.getWidth();
    }
    
    public int getHeight() {
        return this.configurationManager.getHeight();
    }
    
    public float getCompressQuality() {
        return this.configurationManager.getCompressQuality();
    }
    
    public String getRegisterURL() {
        return this.configurationManager.getRegisterURL();
    }

    /**
     * @return the threadPoolManager
     */
    public ThreadPoolManager getThreadPoolManager() {
        return threadPoolManager;
    }

    /**
     * @param threadPoolManager the threadPoolManager to set
     */
    void setThreadPoolManager(ThreadPoolManager threadPoolManager) {
        this.threadPoolManager = threadPoolManager;
    }
    
    public String getProductId() {
        return this.configurationManager.getProductId();
    }
    
    public String getChannel() {
        return this.configurationManager.getChannel();
    }
    
    public String getValidateCodeMessage() {
        return this.configurationManager.getValidateCodeMessage();
    }
    
    public String getTradeCodeMessage() {
        return this.configurationManager.getTradeCodeMessage();
    }
    
    public String getRecommandMessage() {
        return this.configurationManager.getRecommandMessage();
    }
    
    public String getDownloadMessage() {
        return this.configurationManager.getDownloadMessage();
    }

    /**
     * @param daoContext the daoContext to set
     */
    public void setDaoContext(DAOContext daoContext) {
        this.daoContext = daoContext;
    }
    
    public EntityDao getEntityDAO(String daoName) {
        return getDaoContext().getEntityDao(daoName);
    }
    
    public void setEntityData(T t) {
        this.threadLocalManager.getValue().settEntity(t);
    }

    public T getEnttiyData() {
        return  (T) this.threadLocalManager.getValue().gettEntity();
    }

    public void setEntityList(List<T> tList) {
        this.threadLocalManager.getValue().settList(tList);
    }
    
    public List<T> getEnityList(){
        return this.threadLocalManager.getValue().gettList();
    }
    
    public V getInvocationContext(){
        return this.threadLocalManager.getValue();
    }

    /**
     * @return the daoContext
     */
    public DAOContext getDaoContext() {
        return daoContext;
    }
    //
    public String getChargeMessage(){
        return this.configurationManager.getChargeMessage();
    }
    public String getWithDrawMessage(){
        return this.configurationManager.getWithDrawMessage();
    }
    public String getExchangeMessage(){
        return this.configurationManager.getExchangeMessage();
    }
    public String getInvestMessage(){
        return this.configurationManager.getInvestMessage();
    }
    public String getInterestMessage(){
        return this.configurationManager.getInterestMessage();
    }
    public String getInterestAndCaptialMessage(){
        return this.configurationManager.getInterestAndCaptialMessage();
    }
    public String getAppKey(){
        return this.configurationManager.getAppKey();
    }
    public String getMasterSecret(){
        return this.configurationManager.getMasterSecret();
    }
    public String getJpushURL(){
        return this.configurationManager.getJpushURL();
    }
    
}
