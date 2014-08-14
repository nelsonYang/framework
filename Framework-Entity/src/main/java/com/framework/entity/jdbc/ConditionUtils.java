package com.framework.entity.jdbc;

import com.framework.entity.condition.Condition;
import com.frameworkLog.factory.LogFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class ConditionUtils {

    private static final Logger logger = LogFactory.getInstance().getLogger(ConditionUtils.class);

    public static Map<String, String> getConditonMap(List<Condition> conditionList) {
        Map<String, String> conditionMap = new HashMap<String, String>(conditionList.size(), 1);

        for (Condition condition : conditionList) {
            conditionMap.put(condition.getFieldName(), condition.getValue());
        }
        logger.debug("conditionMap:{}", conditionMap);
        return conditionMap;
    }
}
