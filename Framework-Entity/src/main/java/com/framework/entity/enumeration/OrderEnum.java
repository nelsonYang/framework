package com.framework.entity.enumeration;

/**
 *
 * @author nelson
 */
public enum OrderEnum {
    DESC{
        @Override
        public String toString(){
            return " DESC ";
        }
    },
    ASC{
        @Override
        public String toString(){
            return " ASC ";
        }
    }
}
