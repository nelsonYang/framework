package com.framework.utils;

import com.framework.enumeration.FieldTypeEnum;
import com.framework.utils.enumeration.SortTypeEnum;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class SortUtils {
    private SortUtils(){}
    public static void sort(List<Map<String, String>> contentMapList, final String key, final SortTypeEnum sortType, final FieldTypeEnum fieldTypeEnum) {
        Collections.sort(contentMapList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> contentMap1, Map<String, String> contentMap2) {
                String value1 = contentMap1.get(key);
                String value2 = contentMap2.get(key);
                if (value1 != null && value2 != null) {
                    int result;
                    switch (fieldTypeEnum) {
                        case TYINT:
                            result = Integer.parseInt(value1) - Integer.parseInt(value2);
                            break;
                        case SMALL_INT:
                            result = Integer.parseInt(value1) - Integer.parseInt(value2);
                            break;
                        case BIG_INT:
                            result = Integer.parseInt(value1) - Integer.parseInt(value2);
                            break;
                        case DOUBLE:
                            result = (int) (Double.parseDouble(value1) - Double.parseDouble(value2));
                            break;
                        case CHAR8:
                            result = value1.compareTo(value2);
                            break;
                        default:
                            result = value1.compareTo(value2);
                            break;
                    }

                    if (sortType == SortTypeEnum.DESC) {
                        if (result < 0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        if (result > 0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                } else {
                    return 0;
                }
            }
        });

    }
}
