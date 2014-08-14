package com.framework.enumeration;

/**
 *
 * @author Administrator
 */
public enum CryptEnum {
     AES{
         @Override
         public String toString(){
             return "1";
         }
         public String desc(){
             return "AES加密";
         }
     },
     MD5,
    SIGN{
        @Override
         public String toString(){
             return "2";
         } 
         public String desc(){
             return "签名验证";
         }
    },
    PLAIN{
        @Override
         public String toString(){
             return "0";
         } 
         public String desc(){
             return "明文";
         }
    },
    SHA{
        @Override
         public String toString(){
             return "3";
         } 
         public String desc(){
             return "SHA";
         }
    }
}
