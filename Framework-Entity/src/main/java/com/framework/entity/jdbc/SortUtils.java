package com.framework.entity.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class SortUtils {

    public static void sortByResult(String[] keyFields, List<Map<String, String>> keyMapList, List<Map<String, String>> cacheMapList) {
        String keyField = keyFields[0];
        List<Map<String, String>> tempCacheMapList = new ArrayList<Map<String, String>>(cacheMapList.size());
        String value;
        String cacheValue;
        for (Map<String, String> keyMap : keyMapList) {
            value = keyMap.get(keyField);
            for (Map<String, String> cacheMap : cacheMapList) {
                cacheValue = cacheMap.get(keyField);
                if (value.equals(cacheValue)) {
                    tempCacheMapList.add(cacheMap);
                    break;
                }
            }
        }
        cacheMapList.clear();
        for(Map<String,String> tempCacheMap : tempCacheMapList){
            cacheMapList.add(tempCacheMap);
        }
    }
}
