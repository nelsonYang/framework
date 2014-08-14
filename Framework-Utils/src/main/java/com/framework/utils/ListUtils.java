package com.framework.utils;

import java.util.List;

/**
 *
 * @author Administrator
 */
public class ListUtils {
    private ListUtils(){}
    public static String list2Str(List<String> list){
        StringBuilder  result =  new StringBuilder();
        for(String str : list){
            result.append(str).append(",");
        
        }
        if(result.length() > 1){
            result.setLength(result.length() -1);
        }
        return result.toString();
    }
    
     public static String integerList2Str(List<Long> list){
        StringBuilder  result =  new StringBuilder();
        for(Long str : list){
            result.append(str).append(",");
        
        }
        if(result.length() > 1){
            result.setLength(result.length() -1);
        }
        return result.toString();
    }     
     
}
