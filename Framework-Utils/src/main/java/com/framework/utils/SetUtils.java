package com.framework.utils;

import java.util.Set;

/**
 *
 * @author Administrator
 */
public class SetUtils {
    private SetUtils(){}
      public static String integerSet2Str(Set<Integer> set){
        StringBuilder  result =  new StringBuilder();
        for(Integer str : set){
            result.append(str).append(",");
        
        }
        if(result.length() > 1){
            result.setLength(result.length() -1);
        }
        return result.toString();
    }
       public static String LongSet2Str(Set<Long> set){
        StringBuilder  result =  new StringBuilder();
        for(Long str : set){
            result.append(str).append(",");
        
        }
        if(result.length() > 1){
            result.setLength(result.length() -1);
        }
        return result.toString();
    }
       public static String stringSet2Str(Set<String> set){
        StringBuilder  result =  new StringBuilder();
        for(String str : set){
            result.append("'").append(str).append("'").append(",");
        }
        if(result.length() > 1){
            result.setLength(result.length() -1);
        }
        return result.toString();
    }
}
