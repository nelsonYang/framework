package com.framework.entity.pojo;

import java.util.Map;

/**
 *
 * @author nelson
 */
public abstract class Entity {
    public abstract PrimaryKey getKeyValue();
    public abstract Map<String,String> toMap();
    public abstract void parseMap(Map<String,String> entityMap);
}
