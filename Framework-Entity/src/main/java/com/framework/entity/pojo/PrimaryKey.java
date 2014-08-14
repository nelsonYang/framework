package com.framework.entity.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class PrimaryKey {

    private Map<String, String> keyFieldsMap = new HashMap<String, String>();

    
    public String[] getKeyValues(String[] keyFields) {
        List<String> values = new ArrayList<String>(this.keyFieldsMap.size());
        String keyValue;
        for (String keyField : keyFields) {
           keyValue = keyFieldsMap.get(keyField);
           if(keyValue == null){
               throw new RuntimeException("keyField's value is null");
           }else{
               values.add(keyValue);
           }
        }
        return values.toArray(new String[]{});

    }
    
    
    
    /**
     * @param keyFieldsMap the keyFieldsMap to set
     */
    public void setKeyFieldsMap(Map<String, String> keyFieldsMap) {
        this.keyFieldsMap = keyFieldsMap;
    }
    
    public void putKeyField(String keyField,String value){
        this.keyFieldsMap.put(keyField, value);
    }
    public String getKeyField(String keyField){
        return this.keyFieldsMap.get(keyField);
    
    }
    
    public Map<String,String> getKeyFieldsMap(){
        return this.keyFieldsMap;
    }


  
}
