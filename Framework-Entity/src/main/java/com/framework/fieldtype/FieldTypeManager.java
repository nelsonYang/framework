package com.framework.fieldtype;

import com.framework.enumeration.FieldTypeEnum;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class FieldTypeManager {

    private static FieldTypeManager fieldTypeManager;
    private static final Map<FieldTypeEnum, FieldTypeHandler> fieldTypeMap = new HashMap<FieldTypeEnum, FieldTypeHandler>(64, 1);

    private FieldTypeManager() {
    }

    static {
        fieldTypeMap.put(FieldTypeEnum.TYINT, new TinyIntFieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.INT, new IntFieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.SMALL_INT, new SmallIntFieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.BIG_INT, new BigIntFieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.DOUBLE, new DoubleFieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR8, new Char8FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR11, new Char11FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR16, new Char16FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR24, new Char24FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR32, new Char32FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR36, new Char36FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR64, new Char64FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR128, new Char128FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR256, new Char256FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR512, new Char512FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR1024, new Char1024FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.CHAR2048, new Char2048FieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.DATE, new DateFieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.DATETIME, new DateTimeFieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.DATE_OR_EMPTY, new DateOrEmptyFieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.DATETIME_OR_EMPTY, new DateTimeOrEmptyFieldTypeHandler());
        fieldTypeMap.put(FieldTypeEnum.TIME, new TimeFieldTypeHandler());

    }

    public static synchronized FieldTypeManager getInstance() {
        if (fieldTypeManager == null) {
            fieldTypeManager = new FieldTypeManager();
        }
        return fieldTypeManager;
    }

    public FieldTypeHandler getFieldTypeHandler(FieldTypeEnum fieldTypeEnum) {
        return fieldTypeMap.get(fieldTypeEnum);
    }
}
