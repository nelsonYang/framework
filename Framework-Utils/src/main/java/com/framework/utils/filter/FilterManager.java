package com.framework.utils.filter;

import com.framework.enumeration.FilterTypeEnum;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class FilterManager {

    private static FilterManager filterManager;
    private static final Map<FilterTypeEnum, FilterHandler> filterMap = new HashMap<FilterTypeEnum, FilterHandler>(4, 1);

    private FilterManager() {
    }

    static {
        filterMap.put(FilterTypeEnum.ESCAPE, new JsonFilterHandler());
    }

    public static synchronized FilterManager getInstance() {
        if (filterManager == null) {
            filterManager = new FilterManager();
        }
        return filterManager;
    }

    public FilterHandler getFieldTypeHandler(FilterTypeEnum filterTypeEnum) {
        return filterMap.get(filterTypeEnum);
    }
}
