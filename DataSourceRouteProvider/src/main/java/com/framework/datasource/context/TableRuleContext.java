package com.framework.datasource.context;

import com.framework.datasource.entity.TableRule;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class TableRuleContext {

    private Map<String, TableRule> tableRuleMap;

    /**
     * @param tableRuleMap the tableRuleMap to set
     */
    public void setTableRuleMap(Map<String, TableRule> tableRuleMap) {
        this.tableRuleMap = tableRuleMap;
    }
    
    public TableRule getTableRule(String table){
        return this.tableRuleMap.get(table);
    }
    

}
