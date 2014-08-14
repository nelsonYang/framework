package com.framework.annotation;

import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.enumeration.UploadEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Administrator
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceConfig {
    public String act();
    public String[] importantParameters() default{};
    public String[] minorParameters() default{};
    public ParameterWrapperTypeEnum parametersWrapperType() ;
    public String validateName() default "";
    public LoginEnum requireLogin() default  LoginEnum.REQUIRE;
    public CryptEnum requestEncrypt() default CryptEnum.PLAIN;
    public String[] returnParameters() default{};
   public CryptEnum responseEncrypt() default CryptEnum.PLAIN;
   public ResponseTypeEnum responseType() default ResponseTypeEnum.MAP_DATA_JSON;
   public String description() default "";
   public Field[] validateParameters() ;
   public TerminalTypeEnum terminalType() default TerminalTypeEnum.MOBILE;
   public UploadEnum upload() default UploadEnum.NO;
   public boolean requireTranscation() default true;
   
}
