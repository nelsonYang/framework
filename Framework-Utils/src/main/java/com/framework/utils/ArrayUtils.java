package com.framework.utils;

import java.util.Set;

/**
 *
 * @author Administrator
 */
public class ArrayUtils {
    private ArrayUtils(){}
      public static String integerArray2Str(Integer[] array){
        StringBuilder  result =  new StringBuilder();
        for(Integer str : array){
            result.append(str).append(",");
        
        }
        if(result.length() > 1){
            result.setLength(result.length() -1);
        }
        return result.toString();
    }
       public static String LongArray2Str(Long[] array){
        StringBuilder  result =  new StringBuilder();
        for(Long str : array){
            result.append(str).append(",");
        
        }
        if(result.length() > 1){
            result.setLength(result.length() -1);
        }
        return result.toString();
    }
       public static String stringArray2Str(String[] array){
        StringBuilder  result =  new StringBuilder();
        for(String str : array){
            result.append("'").append(str).append("'").append(",");
        }
        if(result.length() > 1){
            result.setLength(result.length() -1);
        }
        return result.toString();
    }
}
