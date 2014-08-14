package com.framework.entity.builder;

import com.framework.entity.handler.EntityHandler;
import com.framework.entity.pojo.Entity;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class EntityContextBuilder<T extends Entity> {
      private final Map<String,EntityHandler<T>> entityHandlerMap = new HashMap<String,EntityHandler<T>>();
      public void putEntityHandler(String entityName,EntityHandler entityHandler){
          this.entityHandlerMap.put(entityName, entityHandler);
      }
      
      public EntityHandler getEntityHandler(String name){
          return this.entityHandlerMap.get(name);
      }
      
      public boolean containsEntityHandler(String name){
          return this.entityHandlerMap.containsKey(name);
      }
      public Map<String,EntityHandler<T>> getEntityHandlerMap(){
          return entityHandlerMap;
      }
}
