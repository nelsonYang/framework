package com.framework.entity.jdbc;

import com.framework.entity.condition.Condition;
import com.framework.entity.condition.Order;
import com.framework.entity.enumeration.ConditionChoiceTypeEnum;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PrimaryKey;
import com.frameworkLog.factory.LogFactory;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public final class SqlBuilder {

    private static final Logger logger = LogFactory.getInstance().getLogger(SqlBuilder.class);

    private SqlBuilder() {
    }

    public static String getInsertSqlMode(String tableName, String[] fieldNames) {
        if (tableName != null && !tableName.isEmpty()) {
            if (fieldNames != null && fieldNames.length > 0) {
                StringBuilder insertSqlModeBuilder = new StringBuilder(200);
                insertSqlModeBuilder.append(SqlBuilderConfig.INSERT).append(tableName).append("(");
                for (String fieldName : fieldNames) {
                    insertSqlModeBuilder.append(fieldName).append(",");
                }
                insertSqlModeBuilder.setLength(insertSqlModeBuilder.length() - 1);
                insertSqlModeBuilder.append(")").append(SqlBuilderConfig.VALUES).append("(");
                for (int index = 0; index < fieldNames.length; index++) {
                    insertSqlModeBuilder.append(" ?,");
                }
                insertSqlModeBuilder.setLength(insertSqlModeBuilder.length() - 1);
                insertSqlModeBuilder.append(")");
                return insertSqlModeBuilder.toString();
            } else {
                throw new RuntimeException("No Fields cannot insert");
            }
        } else {
            throw new RuntimeException("No tableName cannot insert");
        }
    }

    public static String getUpdateSqlMode(String tableName, String[] fieldNames, String[] keyFields) {
        if (keyFields != null && keyFields.length > 0) {
            if (tableName != null && !tableName.isEmpty()) {
                if (fieldNames != null && fieldNames.length > 0) {
                    StringBuilder updateSqlModeBuilder = new StringBuilder(200);
                    updateSqlModeBuilder.append(SqlBuilderConfig.UPDATE).append(tableName).append(SqlBuilderConfig.SET);
                    for (String fieldName : fieldNames) {
                        updateSqlModeBuilder.append(fieldName).append(SqlBuilderConfig.EQUAL).append("?,");
                    }
                    updateSqlModeBuilder.setLength(updateSqlModeBuilder.length() - 1);
                    updateSqlModeBuilder.append(SqlBuilderConfig.WHERE);
                    for (String keyField : keyFields) {
                        updateSqlModeBuilder.append(keyField).append(SqlBuilderConfig.EQUAL).append("?").append(SqlBuilderConfig.AND);
                    }
                    if (updateSqlModeBuilder.length() > SqlBuilderConfig.AND.length()) {
                        updateSqlModeBuilder.setLength(updateSqlModeBuilder.length() - SqlBuilderConfig.AND.length());
                    }
                    return updateSqlModeBuilder.toString();
                } else {
                    throw new RuntimeException("No Fields cannot update");
                }
            } else {
                throw new RuntimeException("No tableName cannot update");
            }
        } else {
            throw new RuntimeException("No primary key");
        }
    }

    public static String getDeleteSqlMode(String tableName, String[] primaryKey) {
        if (tableName != null && !tableName.isEmpty()) {
            if (primaryKey != null && primaryKey.length > 0) {
                StringBuilder deleteSqlModeBuilder = new StringBuilder(200);
                deleteSqlModeBuilder.append(SqlBuilderConfig.DELETE)
                        .append(SqlBuilderConfig.FROM)
                        .append(tableName)
                        .append(SqlBuilderConfig.WHERE);
                for (String field : primaryKey) {
                    deleteSqlModeBuilder.append(field).append(SqlBuilderConfig.EQUAL).append(" ? ").append(SqlBuilderConfig.AND);
                }
                deleteSqlModeBuilder.setLength(deleteSqlModeBuilder.length() - SqlBuilderConfig.AND.length());
                return deleteSqlModeBuilder.toString();
            } else {
                throw new RuntimeException("No primary key cannot delete");
            }
        } else {
            throw new RuntimeException("No tableName cannot delete");
        }
    }

    public static String getInquireByKeySqlMode(String tableName, String[] primaryKey) {
        if (tableName != null && !tableName.isEmpty()) {
            if (primaryKey != null && primaryKey.length > 0) {
                StringBuilder inquireByKeyModeBuilder = new StringBuilder(200);
                inquireByKeyModeBuilder.append(SqlBuilderConfig.SELECT)
                        .append(" * ")
                        .append(SqlBuilderConfig.FROM)
                        .append(tableName)
                        .append(SqlBuilderConfig.WHERE);
                for (String field : primaryKey) {
                    inquireByKeyModeBuilder.append(field).append(SqlBuilderConfig.EQUAL).append(" ? ").append(SqlBuilderConfig.AND);
                }
                inquireByKeyModeBuilder.setLength(inquireByKeyModeBuilder.length() - SqlBuilderConfig.AND.length());
                return inquireByKeyModeBuilder.toString();
            } else {
                throw new RuntimeException("No primary key cannot delete");
            }
        } else {
            throw new RuntimeException("No tableName cannot delete");
        }
    }

    public static String getInquireSqlMode(String tableName, String[] selectFields) {
        if (tableName != null && !tableName.isEmpty()) {
            StringBuilder inquireSqlModeBuilder = new StringBuilder(200);
            inquireSqlModeBuilder.append(SqlBuilderConfig.SELECT);
            if (selectFields != null && selectFields.length > 0) {
                for (String selectField : selectFields) {
                    inquireSqlModeBuilder.append(selectField).append(",");
                }
                inquireSqlModeBuilder.setLength(inquireSqlModeBuilder.length() - 1);
            } else {
                inquireSqlModeBuilder.append(" * ");
            }
            inquireSqlModeBuilder.append(SqlBuilderConfig.FROM).append(tableName);
            return inquireSqlModeBuilder.toString();
        } else {
            throw new RuntimeException("No tableName cannot inquire");
        }
    }

    public static String getInquireByKeySqlMode(String tableName, String[] selectFields, String[] keyFields) {
        if (tableName != null && !tableName.isEmpty()) {
            StringBuilder inquireSqlModeBuilder = new StringBuilder(200);
            inquireSqlModeBuilder.append(SqlBuilderConfig.SELECT);
            if (selectFields != null && selectFields.length > 0) {
                for (String selectField : selectFields) {
                    inquireSqlModeBuilder.append(selectField).append(",");
                }
                inquireSqlModeBuilder.setLength(inquireSqlModeBuilder.length() - 1);
            } else {
                inquireSqlModeBuilder.append(" * ");
            }
            inquireSqlModeBuilder.append(SqlBuilderConfig.FROM).append(tableName);
            inquireSqlModeBuilder.append(SqlBuilderConfig.WHERE);
            for (String keyFiled : keyFields) {
                inquireSqlModeBuilder.append(keyFiled).append(SqlBuilderConfig.EQUAL).append("?").append(SqlBuilderConfig.AND);
            }
            inquireSqlModeBuilder.setLength(inquireSqlModeBuilder.length() - SqlBuilderConfig.AND.length());
            return inquireSqlModeBuilder.toString();
        } else {
            throw new RuntimeException("No tableName cannot inquire");
        }

    }

    public static String getCountSqlMode(String tableName) {
        if (tableName != null && !tableName.isEmpty()) {
            StringBuilder inquireCountSqlModeBuilder = new StringBuilder(30);
            inquireCountSqlModeBuilder.append(SqlBuilderConfig.SELECT)
                    .append(SqlBuilderConfig.COUNT)
                    .append(SqlBuilderConfig.FROM)
                    .append(tableName);
            return inquireCountSqlModeBuilder.toString();
        } else {
            throw new RuntimeException("No tableName cannot inquire");
        }
    }

    public static String getInquireKeySqlMode(String tableName, String[] primaryKey) {
        if (tableName != null && !tableName.isEmpty()) {
            if (primaryKey != null && primaryKey.length > 0) {
                StringBuilder inquireKeySqlModeBuilder = new StringBuilder(200);
                inquireKeySqlModeBuilder.append(SqlBuilderConfig.SELECT);
                for (String field : primaryKey) {
                    inquireKeySqlModeBuilder.append(field).append(",");
                }
                inquireKeySqlModeBuilder.setLength(inquireKeySqlModeBuilder.length() - 1);
                inquireKeySqlModeBuilder.append(SqlBuilderConfig.FROM).append(tableName);
                return inquireKeySqlModeBuilder.toString();
            } else {
                throw new RuntimeException("No primary key cannot delete");
            }
        } else {
            throw new RuntimeException("No tableName cannot delete");
        }
    }

    public static String inquireByConditionSqlBuilder(String tableName, String inquireSqlMode, String[] selectFields, String columnName1) {
        //String inquireSqlMode = getInquireSqlMode(tableName, selectFields);
        if (columnName1 != null && !columnName1.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumn is null or empty");
        }
    }

    public static String inquireByConditionSqlBuilder(String tableName, String inquireSqlMode, String[] selectFields, String columnName1, String columnName2) {
        //String inquireSqlMode = getInquireSqlMode(tableName, selectFields);
        if (columnName1 != null && !columnName1.isEmpty() && columnName2 != null && !columnName2.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName2).append(SqlBuilderConfig.EQUAL).append("?");
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumns is null or empty");
        }

    }

    public static String inquireByConditionSqlBuilder(String tableName, String inquireSqlMode, String[] selectFields, String columnName1, List<Order> orderList) {
        //    String inquireSqlMode = getInquireSqlMode(tableName, selectFields);
        if (columnName1 != null && !columnName1.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(orderBySqlBuilder(orderList));
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumn is null or empty");
        }

    }

    public static String inquireByConditionSqlBuilder(String tableName, String inquireSqlMode, String[] selectFields, String columnName1, String columnName2, List<Order> orderList) {
        //  String inquireSqlMode = getInquireSqlMode(tableName, selectFields);
        if (columnName1 != null && !columnName1.isEmpty() && columnName2 != null && !columnName2.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName2).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(orderBySqlBuilder(orderList));
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumns is null or empty");
        }

    }

    public static String inquireByConditionSqlBuilder(String tableName, String inquireSqlMode, String[] selectFields, List<Condition> conditionList) {
        //String inquireSqlMode = getInquireSqlMode(tableName, selectFields);
        StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
        inquireByConditionBuilder.append(conditionSqlBuilder(conditionList));
        return inquireByConditionBuilder.toString();

    }

    public static String inquireByConditionSqlBuilder(String tableName, String inquireSqlMode, String[] selectFields, List<Condition> conditionList, List<Order> orderList) {
        //String inquireSqlMode = getInquireSqlMode(tableName, selectFields);
        StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
        inquireByConditionBuilder.append(conditionSqlBuilder(conditionList));
        inquireByConditionBuilder.append(orderBySqlBuilder(orderList));
        return inquireByConditionBuilder.toString();

    }

    public static String inquireKeyByConditionSqlBuilder(String tableName, String inquireKeySql, String[] primaryKey, String columnName1) {
        //String inquireKeySql = getInquireKeySqlMode(tableName, primaryKey);
        if (columnName1 != null && !columnName1.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireKeySql);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumn is null or empty");
        }
    }

    public static String inquireKeyByConditionSqlBuilder(String tableName, String inquireKeySql, String[] primaryKey, String columnName1, String columnName2) {
        //String inquireKeySql = getInquireKeySqlMode(tableName, primaryKey);
        if (columnName1 != null && !columnName1.isEmpty() && columnName2 != null && !columnName2.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireKeySql);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName2).append(SqlBuilderConfig.EQUAL).append("?");
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumns is null or empty");
        }

    }

    public static String inquireKeyByConditionSqlBuilder(String tableName, String inquireKeySql, String[] primaryKey, String columnName1, List<Order> orderList) {
        //String inquireKeySql = getInquireKeySqlMode(tableName, primaryKey);
        if (columnName1 != null && !columnName1.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireKeySql);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(orderBySqlBuilder(orderList));
            return inquireByConditionBuilder.toString();
        } else {
            throw new RuntimeException("condition conlumn is null or empty");
        }
    }

    public static String inquireKeyByConditionSqlBuilder(String tableName, String inquireKeySql, String[] primaryKey, String columnName1, String columnName2, List<Order> orderList) {
        // String inquireKeySql = getInquireKeySqlMode(tableName, primaryKey);
        if (columnName1 != null && !columnName1.isEmpty() && columnName2 != null && !columnName2.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireKeySql);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName2).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(orderBySqlBuilder(orderList));
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumns is null or empty");
        }
    }

    public static String inquireByKeyConditionSqlBuilder(String tableName, String inquireKeySql, String[] primaryKey, List<Condition> conditionList) {
        //String inquireKeySql = getInquireKeySqlMode(tableName, primaryKey);
        StringBuilder inquireByConditionBuilder = new StringBuilder(inquireKeySql);
        inquireByConditionBuilder.append(conditionSqlBuilder(conditionList));
        return inquireByConditionBuilder.toString();
    }

    public static String inquirePageKeyByConditionSqlBuilder(String tableName, String inquireKeySql, String[] primaryKey, List<Condition> conditionList, int start, int rows) {
        //String inquireKeySql = getInquireKeySqlMode(tableName, primaryKey);
        StringBuilder inquireByConditionBuilder = new StringBuilder(inquireKeySql);
        inquireByConditionBuilder.append(conditionSqlBuilder(conditionList));
        inquireByConditionBuilder.append(SqlBuilderConfig.LIMIT).append(start).append(",").append(rows);
        return inquireByConditionBuilder.toString();
    }

    public static String inquirePageKeyByConditionSqlBuilder(String tableName, String inquireKeySql, String[] primaryKey, List<Condition> conditionList, List<Order> orderList, int start, int rows) {
        //String inquireKeySql = getInquireKeySqlMode(tableName, primaryKey);
        StringBuilder inquireByConditionBuilder = new StringBuilder(inquireKeySql);
        inquireByConditionBuilder.append(conditionSqlBuilder(conditionList));
        inquireByConditionBuilder.append(orderBySqlBuilder(orderList));
        inquireByConditionBuilder.append(SqlBuilderConfig.LIMIT).append(start).append(",").append(rows);
        return inquireByConditionBuilder.toString();
    }

    public static String inquireByKeyConditionSqlBuilder(String tableName, String inquireKeySql, String[] primaryKey, List<Condition> conditionList, List<Order> orderList) {
        // String inquireKeySql = getInquireKeySqlMode(tableName, primaryKey);
        StringBuilder inquireByConditionBuilder = new StringBuilder(inquireKeySql);
        inquireByConditionBuilder.append(conditionSqlBuilder(conditionList));
        inquireByConditionBuilder.append(orderBySqlBuilder(orderList));
        return inquireByConditionBuilder.toString();

    }

    public static String inquirePageByConditionSqlBuilder(String tableName, String inquireSqlMode, String[] selectFields, String columnName1, int start, int rows) {
        //String inquireSqlMode = getInquireSqlMode(tableName, selectFields);
        if (columnName1 != null && !columnName1.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(SqlBuilderConfig.LIMIT).append(start).append(",").append(rows);
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumn is null or empty");
        }
    }

    public static String inquirePageByConditionSqlBuilder(String tableName, String inquireSqlMode, String[] selectFields, String columnName1, String columnName2, int start, int rows) {
        // String inquireSqlMode = getInquireSqlMode(tableName, selectFields);
        if (columnName1 != null && !columnName1.isEmpty() && columnName2 != null && !columnName2.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName2).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(SqlBuilderConfig.LIMIT).append(start).append(",").append(rows);
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumns is null or empty");
        }

    }

    public static String inquirePageByConditionSqlBuilder(String tableName, String inquireSqlMode, String[] selectFields, String columnName1, List<Order> orderList, int start, int rows) {
        //String inquireSqlMode = getInquireSqlMode(tableName, selectFields);
        if (columnName1 != null && !columnName1.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(orderBySqlBuilder(orderList));
            inquireByConditionBuilder.append(SqlBuilderConfig.LIMIT).append(start).append(",").append(rows);
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumn is null or empty");
        }

    }

    public static String inquirePageByConditionSqlBuilder(String tableName, String inquireSqlMode, String[] selectFields, String columnName1, String columnName2, List<Order> orderList, int start, int rows) {
        //String inquireSqlMode = getInquireSqlMode(tableName, selectFields);
        if (columnName1 != null && !columnName1.isEmpty() && columnName2 != null && !columnName2.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName2).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(orderBySqlBuilder(orderList));
            inquireByConditionBuilder.append(SqlBuilderConfig.LIMIT).append(start).append(",").append(rows);
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumns is null or empty");
        }

    }

    public static String inquirePageByConditionSqlBuilder(String tableName, String inquireSql, String[] selectFields, List<Condition> conditionList, int start, int rows) {
        // String inquireSql = getInquireSqlMode(tableName, selectFields);
        StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSql);
        inquireByConditionBuilder.append(conditionSqlBuilder(conditionList));
        inquireByConditionBuilder.append(SqlBuilderConfig.LIMIT).append(start).append(",").append(rows);
        return inquireByConditionBuilder.toString();

    }

    public static String inquirePageByConditionSqlBuilder(String tableName, String inquireSql, String[] selectFields, List<Condition> conditionList, List<Order> orderList, int start, int rows) {
        //String inquireSql = getInquireSqlMode(tableName, selectFields);
        StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSql);
        inquireByConditionBuilder.append(conditionSqlBuilder(conditionList));
        inquireByConditionBuilder.append(orderBySqlBuilder(orderList));
        inquireByConditionBuilder.append(SqlBuilderConfig.LIMIT).append(start).append(",").append(rows);
        return inquireByConditionBuilder.toString();

    }

    public static String countByConditionSqlBuilder(String tableName, String countSqlMode, String columnName1) {
        // String countSqlMode = getCountSqlMode(tableName);
        if (columnName1 != null && !columnName1.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(countSqlMode);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumn is null or empty");
        }
    }

    public static String countByConditionSqlBuilder(String tableName, String countSqlMode, String columnName1, String columnName2) {
        //String countSqlMode = getCountSqlMode(tableName);
        if (columnName1 != null && !columnName1.isEmpty() && columnName2 != null && !columnName2.isEmpty()) {
            StringBuilder inquireByConditionBuilder = new StringBuilder(countSqlMode);
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName1).append(SqlBuilderConfig.EQUAL).append("?");
            inquireByConditionBuilder.append(SqlBuilderConfig.AND)
                    .append(columnName2).append(SqlBuilderConfig.EQUAL).append("?");
            return inquireByConditionBuilder.toString();

        } else {
            throw new RuntimeException("condition conlumns is null or empty");
        }
    }

    public static String countByConditionSqlBuilder(String tableName, String countSqlMode, List<Condition> conditionList) {
        //String countSqlMode = getCountSqlMode(tableName);
        StringBuilder inquireByConditionBuilder = new StringBuilder(countSqlMode);
        inquireByConditionBuilder.append(conditionSqlBuilder(conditionList));
        return inquireByConditionBuilder.toString();

    }

    public static String inquireByKeys(String tableNames, String inquireSqlMode, String[] keyFields, List<PrimaryKey> primaryKeyList) {
        StringBuilder inquireByConditionBuilder = new StringBuilder(inquireSqlMode);
        if (keyFields.length == 1) {
            String[] values;
            StringBuilder inValueBuilder = new StringBuilder();
            for (PrimaryKey primaryKey : primaryKeyList) {
                values = primaryKey.getKeyValues(keyFields);
                inValueBuilder.append(values[0]).append(",");
            }
            if (inValueBuilder.length() > 1) {
                inValueBuilder.setLength(inValueBuilder.length() - 1);
            }
            inquireByConditionBuilder.append(SqlBuilderConfig.WHERE).append(keyFields[0]).append(SqlBuilderConfig.IN).append("(").append(inValueBuilder).append(")");
        } else {
            String keyFieldCondition = getPrimaryKeyCondition(keyFields, primaryKeyList);
            inquireByConditionBuilder.append(SqlBuilderConfig.WHERE).append(keyFieldCondition);
        }
        return inquireByConditionBuilder.toString();
    }

//    public static String updateByConditionSqlBuilder(String tableName, List<Condition> conditionList, PrimaryKey primaryKey) {
//    
//    
//    }
    public static String getPrimaryKeyCondition(String[] keyFields, List<PrimaryKey> primaryKeyList) {
        StringBuilder primaryKeyBuilder = new StringBuilder();
        for (PrimaryKey primaryKey : primaryKeyList) {
            primaryKeyBuilder.append("( ");
            for (String keyField : keyFields) {
                primaryKeyBuilder.append(keyField).append("='").append(primaryKey.getKeyField(keyField)).append("'").append(SqlBuilderConfig.AND);
            }
            if (primaryKeyBuilder.length() > SqlBuilderConfig.AND.length()) {
                primaryKeyBuilder.setLength(primaryKeyBuilder.length() - SqlBuilderConfig.AND.length());
            }
            primaryKeyBuilder.append(" )");
            primaryKeyBuilder.append(SqlBuilderConfig.OR);
        }
        if (primaryKeyBuilder.length() > SqlBuilderConfig.OR.length()) {
            primaryKeyBuilder.setLength(primaryKeyBuilder.length() - SqlBuilderConfig.OR.length());
        }
        return primaryKeyBuilder.toString();


    }

    public static String[] getConditionValues(List<Condition> conditionList) {
        List<String> values = new ArrayList<String>(conditionList.size());
        if (conditionList != null && !conditionList.isEmpty()) {
            List<Condition> andConditionList = new ArrayList<Condition>();
            List<Condition> orConditionList = new ArrayList<Condition>();
            for (Condition condition : conditionList) {
                if (condition.getConditionChoiceType() == ConditionChoiceTypeEnum.AND) {
                    andConditionList.add(condition);
                } else {
                    orConditionList.add(condition);
                }
            }
            if (!andConditionList.isEmpty()) {
                for (Condition condition : andConditionList) {
                    if (condition.getConditionType() != ConditionTypeEnum.IN && condition.getConditionType() != ConditionTypeEnum.NOT_IN) {
                        values.add(condition.getValue());
                    }
                }
            }
            if (!orConditionList.isEmpty()) {
                for (Condition condition : andConditionList) {
                    if (condition.getConditionType() != ConditionTypeEnum.IN && condition.getConditionType() != ConditionTypeEnum.NOT_IN) {
                        values.add(condition.getValue());
                    }
                }
            }
        }
        return values.toArray(new String[]{});
    }

    public static String conditionSqlBuilder(List<Condition> conditionList) {
        StringBuilder conditionSqlBuilder = new StringBuilder(100);
        if (conditionList != null && !conditionList.isEmpty()) {
            conditionSqlBuilder.append(SqlBuilderConfig.WHERE);
            List<Condition> andConditionList = new ArrayList<Condition>();
            List<Condition> orConditionList = new ArrayList<Condition>();
            for (Condition condition : conditionList) {
                if (condition.getConditionChoiceType() == ConditionChoiceTypeEnum.AND) {
                    andConditionList.add(condition);
                } else {
                    orConditionList.add(condition);
                }
            }
            if (!andConditionList.isEmpty()) {
                for (Condition condition : andConditionList) {
                    if (condition.getConditionType() == ConditionTypeEnum.IN) {
                        conditionSqlBuilder.append(condition.getFieldName()).append(ConditionTypeEnum.IN).append("(").append(condition.getValue()).append(")").append(SqlBuilderConfig.AND);
                    } else if (condition.getConditionType() == ConditionTypeEnum.NOT_IN) {
                        conditionSqlBuilder.append(condition.getFieldName()).append(ConditionTypeEnum.NOT_IN).append("(").append(condition.getValue()).append(")").append(SqlBuilderConfig.AND);
                    } else {
                        conditionSqlBuilder.append(condition.getFieldName()).append(condition.getConditionType()).append("?").append(SqlBuilderConfig.AND);
                    }
                }
                conditionSqlBuilder.setLength(conditionSqlBuilder.length() - SqlBuilderConfig.AND.length());
            }
            if (!orConditionList.isEmpty()) {
                if (conditionSqlBuilder.length() > 0 && !andConditionList.isEmpty()) {
                    conditionSqlBuilder.append(SqlBuilderConfig.AND);
                }
                conditionSqlBuilder.append("(");
                for (Condition condition : orConditionList) {
                    if (condition.getConditionType() == ConditionTypeEnum.IN) {
                        conditionSqlBuilder.append(condition.getFieldName()).append(ConditionTypeEnum.IN).append("(").append(condition.getValue()).append(")");
                    } else if (condition.getConditionType() == ConditionTypeEnum.NOT_IN) {
                        conditionSqlBuilder.append(SqlBuilderConfig.OR).append(condition.getFieldName()).append(ConditionTypeEnum.NOT_IN).append("(").append(condition.getValue()).append(")");
                    } else {
                        conditionSqlBuilder.append(SqlBuilderConfig.OR).append(condition.getFieldName()).append(condition.getConditionType()).append("?");
                    }
                }
                conditionSqlBuilder.append(")");
            }
        }
        return conditionSqlBuilder.toString();
    }

    private static String orderBySqlBuilder(List<Order> orderList) {
        StringBuilder orderByBuilder = new StringBuilder(20);
        orderByBuilder.append(SqlBuilderConfig.ORDER_BY);
        for (Order order : orderList) {
            orderByBuilder.append(order.getFieldName()).append(order.getOrderEnum()).append(",");
        }
        orderByBuilder.setLength(orderByBuilder.length() - 1);
        return orderByBuilder.toString();

    }

    public static String valuesBuild(String[] values) {
        StringBuilder valuesBuilder = new StringBuilder();
        for (String value : values) {
            valuesBuilder.append(value).append(",");
        }
        if (valuesBuilder.length() > 2) {
            valuesBuilder.setLength(valuesBuilder.length() - 1);
        }
        return valuesBuilder.toString();
    }
}
