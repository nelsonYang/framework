package com.framework.parser;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.enumeration.UploadEnum;
import com.framework.field.FieldHandler;
import com.framework.field.FieldHandlerImpl;
import com.framework.field.FieldManager;
import com.framework.register.ServiceRegisterManager;
import com.framework.service.api.Parser;
import com.framework.service.api.Service;
import com.framework.service.api.TransactionSessionBeanLocal;
import com.framework.service.api.WorkHandler;
import com.framework.service.handler.BatchMapDataJsonParseWorkHanlder;
import com.framework.service.handler.BatchMapParameterReceiveWorkHanlder;
import com.framework.service.handler.BatchMapParameterValidateWorkHandler;
import com.framework.service.handler.DecryptWorkHandler;
import com.framework.service.handler.FlashSessionValidateWorkHandler;
import com.framework.service.handler.FlashUploadParameterReceiveWorkHandler;
import com.framework.service.handler.RequestJsonParseWorkHanlder;
import com.framework.service.handler.JsonWriteWorkHandler;
import com.framework.service.handler.MobileSessionValidateWorkHandler;
import com.framework.service.handler.RollbackCacheWorkHandler;
import com.framework.service.handler.SignValidateWorkHandler;
import com.framework.service.handler.SimpleMapDataJsonParseWorkHanlder;
import com.framework.service.handler.SimpleMapParameterReceiveWorkHanlder;
import com.framework.service.handler.SimpleMapParameterValidateWorkHandler;
import com.framework.service.handler.TransactionWorkHandler;
import com.framework.service.handler.UploadParameterReceiveWorkHandler;
import com.framework.service.handler.WebSessionValidateWorkHandler;
import com.frameworkLog.factory.LogFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.slf4j.Logger;

/**
 *
 * @author nelson.yang
 */
public final class ServiceConfigParser implements Parser {

    private final Logger logger = LogFactory.getInstance().getLogger(ServiceConfigParser.class);

    @Override
    public void parse(final String[] packageNames) {
        logger.info("开始解析ServiceConfig.....");
        List<String> classNameList = new ClassParser().parse(packageNames);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ServiceConfig serviceConfig;
        String act;
        String[] parameters;
        String[] minorParameters;
        CryptEnum requestSecurityType;
        CryptEnum responseSecurityType;
        String[] returnParameters;
        WorkHandler service;
        ResponseTypeEnum responseType;
        ParameterWrapperTypeEnum parameterWrapperType;
        LoginEnum loginEnum;
        String fieldName;
        String defaultValue;
        FieldTypeEnum fieldTypeEnum;
        FieldHandler fieldHandler;
        TerminalTypeEnum terminalTypeEnum;
        UploadEnum uploadEnum;
        boolean requireTransaction;
        for (String className : classNameList) {
            try {
                Class clazz = classLoader.loadClass(className);
                if (clazz.isAnnotationPresent(ServiceConfig.class) && Service.class.isAssignableFrom(clazz)) {
                    serviceConfig = (ServiceConfig) clazz.getAnnotation(ServiceConfig.class);
                    act = serviceConfig.act();
                    parameters = serviceConfig.importantParameters();
                    minorParameters = serviceConfig.minorParameters();
                    requestSecurityType = serviceConfig.requestEncrypt();
                    responseSecurityType = serviceConfig.responseEncrypt();
                    returnParameters = serviceConfig.returnParameters();
                    responseType = serviceConfig.responseType();
                    parameterWrapperType = serviceConfig.parametersWrapperType();
                    loginEnum = serviceConfig.requireLogin();
                    Field[] fields = serviceConfig.validateParameters();
                    terminalTypeEnum = serviceConfig.terminalType();
                    uploadEnum = serviceConfig.upload();
                    requireTransaction = serviceConfig.requireTranscation();
                    Map<String, FieldHandler> fieldMap = new HashMap<String, FieldHandler>(fields.length);
                    for (Field field : fields) {
                        fieldName = field.fieldName();
                        defaultValue = field.defaultValue();
                        fieldTypeEnum = field.fieldType();
                        fieldHandler = new FieldHandlerImpl(fieldName, fieldTypeEnum, defaultValue);
                        fieldMap.put(fieldName, fieldHandler);
                    }
                    FieldManager.getInstance().regiester(act, fieldMap);
                    service = (Service) clazz.newInstance();
                    //是否开启事务
                    if (requireTransaction) {
                        //回滚cache
                        service = new RollbackCacheWorkHandler(service);
                        TransactionSessionBeanLocal transactionSessionBeanLocal;
                        Context ctx = new InitialContext();
                        transactionSessionBeanLocal = (TransactionSessionBeanLocal) ctx.lookup("java:comp/env/framework/TransactionSessionBean");
                        service = new TransactionWorkHandler(transactionSessionBeanLocal, service);
                    }
                    //web或者手机端接口
                    if (terminalTypeEnum == TerminalTypeEnum.MOBILE || terminalTypeEnum == TerminalTypeEnum.WEB) {
                        if (parameterWrapperType == ParameterWrapperTypeEnum.SIMPLE_MAP) {
                            service = new SimpleMapParameterValidateWorkHandler(service, parameters, minorParameters, act);
                        } else {
                            service = new BatchMapParameterValidateWorkHandler(service, parameters, minorParameters, act);
                        }
                        if (parameterWrapperType == ParameterWrapperTypeEnum.SIMPLE_MAP) {
                            service = new SimpleMapDataJsonParseWorkHanlder(service, parameters, minorParameters);
                        } else {
                            service = new BatchMapDataJsonParseWorkHanlder(service, parameters, minorParameters);
                        }
                        if (requestSecurityType != CryptEnum.PLAIN && requestSecurityType != CryptEnum.SIGN) {
                            service = new DecryptWorkHandler(service, requestSecurityType, terminalTypeEnum);
                        }
                        if (requestSecurityType == CryptEnum.SIGN) {
                            service = new SignValidateWorkHandler(service);
                        }
                        if (terminalTypeEnum == TerminalTypeEnum.MOBILE) {
                            if (loginEnum == LoginEnum.REQUIRE) {
                                service = new MobileSessionValidateWorkHandler(service);
                            }
                        } else {
                            if (loginEnum == LoginEnum.REQUIRE) {
                                if (uploadEnum == UploadEnum.FLASH) {
                                    service = new FlashSessionValidateWorkHandler(service);
                                } else {
                                    service = new WebSessionValidateWorkHandler(service);
                                }
                            }
                        }
                        service = new RequestJsonParseWorkHanlder(service);
//                        if (responseType == ResponseTypeEnum.JSON) {
//                            service = new JsonWriteWorkHandler(service, responseSecurityType, terminalTypeEnum, returnParameters);
//                        } else {
//                            service = new JsonPageWriteWorkHandler(service, responseSecurityType, terminalTypeEnum, returnParameters);
//                        }
                        service = new JsonWriteWorkHandler(service, responseSecurityType, terminalTypeEnum, responseType, returnParameters);
                        if (uploadEnum == UploadEnum.YES) {
                            service = new UploadParameterReceiveWorkHandler(service);
                        } else if (uploadEnum == UploadEnum.FLASH) {
                            service = new FlashUploadParameterReceiveWorkHandler(service);
                        }

                        //openfire端接口
                    } else if (terminalTypeEnum == TerminalTypeEnum.OPEN_FIRE) {
                        if (parameterWrapperType == ParameterWrapperTypeEnum.SIMPLE_MAP) {
                            service = new SimpleMapParameterValidateWorkHandler(service, parameters, minorParameters, act);
                        } else if (parameterWrapperType == ParameterWrapperTypeEnum.BATCH_MAP) {
                            service = new BatchMapParameterValidateWorkHandler(service, parameters, minorParameters, act);
                        }
                        if (parameterWrapperType == ParameterWrapperTypeEnum.SIMPLE_MAP) {
                            service = new SimpleMapParameterReceiveWorkHanlder(service);
                        } else {
                            service = new BatchMapParameterReceiveWorkHanlder(service);
                        }
//                        if (responseType == ResponseTypeEnum.JSON) {
//                            service = new JsonWriteWorkHandler(service, CryptEnum.PLAIN, terminalTypeEnum, returnParameters);
//                        } else {
//                            service = new JsonPageWriteWorkHandler(service, CryptEnum.PLAIN, terminalTypeEnum, returnParameters);
//                        }
                        service = new JsonWriteWorkHandler(service, responseSecurityType, terminalTypeEnum, responseType, returnParameters);
                        if (uploadEnum == UploadEnum.YES) {
                            service = new UploadParameterReceiveWorkHandler(service);
                        }
                    }
                    ServiceRegisterManager.getInstance().addService(act, service);
                }
            } catch (Exception ex) {
                logger.error("parse 解析serviceConfig出现异常", ex);
                throw new RuntimeException("parse 解析serviceConfig出现异常");
            }
        }

    }
}
