package com.framework.entity.condition;

import com.framework.entity.enumeration.OrderEnum;

/**
 *
 * @author nelson
 */
public final class Order {
    private String fieldName;
    private OrderEnum orderEnum;
    
    public Order(String fieldName,OrderEnum orderEnum){
        this.fieldName = fieldName;
        this.orderEnum = orderEnum;
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
     * @return the orderEnum
     */
    public OrderEnum getOrderEnum() {
        return orderEnum;
    }

    /**
     * @param orderEnum the orderEnum to set
     */
    public void setOrderEnum(OrderEnum orderEnum) {
        this.orderEnum = orderEnum;
    }
    
}
