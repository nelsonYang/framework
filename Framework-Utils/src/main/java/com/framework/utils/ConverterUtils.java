package com.framework.utils;

import com.framework.entity.pojo.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class ConverterUtils {

    public static <T extends Entity> List<Map<String, String>> toMapList(List<T> entityList) {
        List<Map<String,String>> mapList = new ArrayList<Map<String,String>>(entityList.size());
        for(T t : entityList){
           mapList.add(t.toMap());
        }
        return mapList;
    }
}
