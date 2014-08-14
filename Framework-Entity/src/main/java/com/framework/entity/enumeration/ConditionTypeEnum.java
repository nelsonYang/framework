package com.framework.entity.enumeration;

/**
 *
 * @author nelson
 */
public enum ConditionTypeEnum {
    EQUAL{
        @Override
        public String toString(){
            return " = ";
        }
    },
    GRATE{
        @Override
        public String toString(){
            return " > ";
        }
    },
    GRATE_EQUAL{
        @Override
        public String toString(){
            return " >= ";
        }
    },
    LESS{
        @Override
        public String toString(){
            return " < ";
        }
    },
    LESS_EQUAL{
        @Override
        public String toString(){
            return " < ";
        }
    },
    LIKE{
         @Override
        public String toString(){
            return " like ";
        }
    },
    NOT_EQUAL{
        @Override
        public String toString(){
            return " != ";
        }
    },
    IN{
        @Override
        public String toString(){
            return " IN ";
        }
    },
    NOT_IN{
        @Override
        public String toString(){
            return " NOT IN ";
        }
    }
}
