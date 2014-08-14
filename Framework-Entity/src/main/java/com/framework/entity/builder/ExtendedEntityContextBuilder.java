package com.framework.entity.builder;

import com.framework.entity.handler.ExtendedEntityHandler;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class ExtendedEntityContextBuilder {

    private final Map<String, ExtendedEntityHandler> extendedEntityHandlerMap = new HashMap<String, ExtendedEntityHandler>();

    public void putExtendedEntityHandler(String entityName,ExtendedEntityHandler extendedEntityHandler){
          this.extendedEntityHandlerMap.put(entityName, extendedEntityHandler);
      }
      
      public ExtendedEntityHandler getExtendedEntityHandler(String name){
          return this.extendedEntityHandlerMap.get(name);
      }
      
      public boolean containsExtendedEntityHandler(String name){
          return this.extendedEntityHandlerMap.containsKey(name);
      }
      
      public Map<String,ExtendedEntityHandler> getExtendedEntityHandlerMap(){
          return this.extendedEntityHandlerMap;
      }
}
