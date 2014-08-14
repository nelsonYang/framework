package com.framework.context;

import com.framework.entity.FileEntity;
import com.framework.entity.pojo.Entity;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
public class InvocationContext <T extends Entity>{
    private String act;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private String loginSession;
    private String telephoneNum;
    private Map<String, String> simpleMapParameters;
    private Map<String, String[]> batchMapParameters;
    //解析request中的json放入requestMap
    private Map<String,String> requestMap;
    private int resultCode;
    private int encryptCode;
    private Map<String,String> resultMap;
    private Map<String,List<Map<String,String>>> resultListMap;
    private Map<String,Map<String,String>> resultMapData;
    private String msg ="操作失败";
    private String key;
    private int count;
    private long memberId;
    private int totalPage;
    private List<FileEntity> fileList;
    private String userName;
    private long userId;
    private long orgUserId;
    private int orgId;
    private int orgType;
    private int regionId;
    private String childOrgId;
    private T tEntity;
    private List tList;
    
    

    public InvocationContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = this.request.getSession(true);
    }

    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * @return the response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * @return the simpleMapParameters
     */
    public Map<String, String> getSimpleMapParameters() {
        return simpleMapParameters;
    }

    /**
     * @param simpleMapParameters the simpleMapParameters to set
     */
    public void setSimpleMapParameters(Map<String, String> simpleMapParameters) {
        this.simpleMapParameters = simpleMapParameters;
    }

    /**
     * @return the batchMapParameters
     */
    public Map<String, String[]> getBatchMapParameters() {
        return batchMapParameters;
    }

    /**
     * @param batchMapParameters the batchMapParameters to set
     */
    public void setBatchMapParameters(Map<String, String[]> batchMapParameters) {
        this.batchMapParameters = batchMapParameters;
    }

    /**
     * @return the act
     */
    public String getAct() {
        return request.getParameter("act");
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return request.getParameter("sign");
    }

    /**
     * @return the platform
     */
    public String getPlatform() {
        return request.getParameter("platform");
    }

    /**
     * @return the requestJson
     */
    public String getRequestJson() {
        
        String requestValue = request.getParameter("request");
        if(requestValue == null){
           requestValue = (String)request.getAttribute("request");
        }
        return requestValue;
    }
    
    public String getJsonCallback(){
        return request.getParameter("callback");
    }
    
    

    /**
     * @return the requestMap
     */
    public Map<String,String> getRequestMap() {
        return requestMap;
    }

    /**
     * @param requestMap the requestMap to set
     */
    public void setRequestMap(Map<String,String> requestMap) {
        this.requestMap = requestMap;
    }

    /**
     * @return the resultCode
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * @param resultCode the resultCode to set
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * @return the encryptCode
     */
    public int getEncryptCode() {
        return encryptCode;
    }

    /**
     * @param encryptCode the encryptCode to set
     */
    public void setEncryptCode(int encryptCode) {
        this.encryptCode = encryptCode;
    }

    /**
     * @return the resultMap
     */
    public Map<String,String> getResultMap() {
        return resultMap;
    }

    /**
     * @param resultMap the resultMap to set
     */
    public void setResultMap(Map<String,String> resultMap) {
        this.resultMap = resultMap;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return request.getParameter("key");
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the resultListMap
     */
    public Map<String,List<Map<String,String>>> getResultListMap() {
        return resultListMap;
    }

    /**
     * @param resultListMap the resultListMap to set
     */
    public void setResultListMap(Map<String,List<Map<String,String>>> resultListMap) {
        this.resultListMap = resultListMap;
    }

    /**
     * @return the session
     */
    public HttpSession getSession() {
        return this.session;
    }

    /**
     * @return the telephoneNum
     */
    public String getTelephoneNum() {
        //return this.session.getAttribute("telephoneNum").toString();
        return this.telephoneNum;
    }
    
    public void setTelephoneNum(String telephoneNum){
      //   this.session.setAttribute("telephoneNum", telephoneNum);
         this.telephoneNum = telephoneNum;
    }

    /**
     * @return the resultMapData
     */
    public Map<String,Map<String,String>> getResultMapData() {
        return resultMapData;
    }

    /**
     * @param resultMapData the resultMapData to set
     */
    public void setResultMapData(Map<String,Map<String,String>> resultMapData) {
        this.resultMapData = resultMapData;
    }

    /**
     * @return the loginSession
     */
    public String getLoginSession() {
        return loginSession;
    }

    /**
     * @param loginSession the loginSession to set
     */
    public void setLoginSession(String loginSession) {
        this.loginSession = loginSession;
    }
    
    public void putSessionValue(String key,Object value){
        this.session.setAttribute(key, value);
    }

    /**
     * @return the memberId
     */
    public long getMemberId() {
        return memberId;
    }

    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    /**
     * @return the totalPage
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * @param totalPage the totalPage to set
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * @return the fileList
     */
    public List<FileEntity> getFileList() {
        return fileList;
    }

    /**
     * @param fileList the fileList to set
     */
    public void setFileList(List<FileEntity> fileList) {
        this.fileList = fileList;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    public String getUserUuid(){
        return  String.valueOf(this.getUserId());
    }

    /**
     * @param act the act to set
     */
    public void setAct(String act) {
        this.act = act;
    }

    /**
     * @return the orgUserId
     */
    public long getOrgUserId() {
        return orgUserId;
    }

    /**
     * @param orgUserId the orgUserId to set
     */
    public void setOrgUserId(long orgUserId) {
        this.orgUserId = orgUserId;
    }

    /**
     * @return the orgId
     */
    public int getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the orgType
     */
    public int getOrgType() {
        return orgType;
    }

    /**
     * @param orgType the orgType to set
     */
    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }

    /**
     * @return the regionId
     */
    public int getRegionId() {
        return regionId;
    }

    /**
     * @param regionId the regionId to set
     */
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    /**
     * @return the childOrgId
     */
    public String getChildOrgId() {
        return childOrgId;
    }

    /**
     * @param childOrgId the childOrgId to set
     */
    public void setChildOrgId(String childOrgId) {
        this.childOrgId = childOrgId;
    }


    /**
     * @return the tList
     */
    public List gettList() {
        return tList;
    }

    /**
     * @param tList the tList to set
     */
    public void settList(List tList) {
        this.tList = tList;
    }

    /**
     * @return the tEntity
     */
    public T gettEntity() {
        return tEntity;
    }

    /**
     * @param tEntity the tEntity to set
     */
    public void settEntity(T tEntity) {
        this.tEntity = tEntity;
    }
     
}
