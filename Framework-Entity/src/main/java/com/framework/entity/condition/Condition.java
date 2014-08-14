package com.framework.entity.condition;

import com.framework.entity.enumeration.ConditionChoiceTypeEnum;
import com.framework.entity.enumeration.ConditionTypeEnum;

/**
 *
 * @author nelson
 */
public final class Condition {
    private String fieldName;
    private String value;
    private ConditionTypeEnum conditionType ;
    private ConditionChoiceTypeEnum conditionChoiceType = ConditionChoiceTypeEnum.AND;

    public Condition(String fieldName, ConditionTypeEnum conditionTypeEnum, String value) {
       this.fieldName = fieldName;
       this.conditionType = conditionTypeEnum;
       this.value = value;
    }
     public Condition(String fieldName, ConditionTypeEnum conditionTypeEnum, String value,ConditionChoiceTypeEnum conditionChoiceType) {
       this.fieldName = fieldName;
       this.conditionType = conditionTypeEnum;
       this.value = value;
       this.conditionChoiceType = conditionChoiceType;
    }
  

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the conditionType
     */
    public ConditionTypeEnum getConditionType() {
        return conditionType;
    }

    /**
     * @param conditionType the conditionType to set
     */
    public void setConditionType(ConditionTypeEnum conditionType) {
        this.conditionType = conditionType;
    }

    /**
     * @return the conditionChoiceType
     */
    public ConditionChoiceTypeEnum getConditionChoiceType() {
        return conditionChoiceType;
    }

    /**
     * @param conditionChoiceType the conditionChoiceType to set
     */
    public void setConditionChoiceType(ConditionChoiceTypeEnum conditionChoiceType) {
        this.conditionChoiceType = conditionChoiceType;
    }
    
}
