package com.framework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.framework.utils.filter.FilterHandler;
import com.frameworkLog.factory.LogFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class JsonUtils {

    private static final Logger logger = LogFactory.getInstance().getLogger(JsonUtils.class);

    private JsonUtils() {
    }

    /**
     * 将JSON字符串解析为MAP
     *
     * @param context
     * @return
     * @throws JSONException
     */
    public static Map<String, String> parseJsonToMap(String json) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, Object> map = JSON.parseObject(json, Map.class);
        Object jsonObject;
        for (Entry<String, Object> entry : map.entrySet()) {
            jsonObject = entry.getValue();
            resultMap.put(entry.getKey(), jsonObject.toString());
        }
        return resultMap;
    }

    public static List<Map<String, String>> parseJsonArrayToMap(String content) {
        List<Map> mapList = JSON.parseArray(content, Map.class);
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(mapList.size());
        for (Map map : mapList) {
            resultMapList.add((Map<String, String>) map);
        }
        return resultMapList;
    }

    public static String mapToJson(Map<String, String> map) {
        return JSON.toJSONString(map);
    }

    public static String mapListToJsonArray(List<Map<String, String>> mapList) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Map<String, String> map : mapList) {
            sb.append(mapToJson(map)).append(",");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

    public static String mapJsonToJson(Map<String, String> map) {
        StringBuilder sb = new StringBuilder(200);
        sb.append("{");
        String value;
        for (String parameter : map.keySet()) {
            value = map.get(parameter);
            if (value != null) {
                if (value.startsWith("[") && value.endsWith("]")) {
                    sb.append("\"").append(parameter).append("\":").append(value).append(",");
                } else if (value.startsWith("{") && value.endsWith("}")) {
                    sb.append("\"").append(parameter).append("\":").append(value).append(",");
                } else {
                    sb.append("\"").append(parameter).append("\":\"").append(value).append("\",");
                }
            } else {
                sb.append("\"").append(parameter).append("\":\"").append("").append("\",");
            }
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    public static String mapToJson(Map<String, String> map, String[] returnParameters, FilterHandler filterHandler) {
        StringBuilder sb = new StringBuilder(200);
        sb.append("{");
        String value;
        for (String parameter : returnParameters) {
            value = map.get(parameter);
            if (value != null) {
                if (value.startsWith("[") && value.endsWith("]")) {
                    sb.append("\"").append(parameter).append("\":").append(value).append(",");
                } else if (value.startsWith("{") && value.endsWith("}")) {
                    sb.append("\"").append(parameter).append("\":").append(value).append(",");
                } else {
                    sb.append("\"").append(parameter).append("\":\"").append(filterHandler.doFilter(value)).append("\",");
                }
            } else {
                sb.append("\"").append(parameter).append("\":\"").append("").append("\",");
            }
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    public static String mapToJson(Map<String, String> map, String[] returnParameters) {
        StringBuilder sb = new StringBuilder(200);
        sb.append("{");
        String value;
        for (String parameter : returnParameters) {
            value = map.get(parameter);
            if (value != null) {
                if (value.startsWith("[") && value.endsWith("]")) {
                    sb.append("\"").append(parameter).append("\":").append(value).append(",");
                } else if (value.startsWith("{") && value.endsWith("}")) {
                    sb.append("\"").append(parameter).append("\":").append(value).append(",");
                } else {
                    sb.append("\"").append(parameter).append("\":\"").append(value).append("\",");
                }
            } else {
                sb.append("\"").append(parameter).append("\":\"").append("").append("\",");
            }
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    public static String mapListToJsonArray(List<Map<String, String>> mapList, String[] returnParameters, FilterHandler filterHandler) {
        StringBuilder sb = new StringBuilder(202);
        sb.append("[");
        for (Map<String, String> map : mapList) {
            sb.append(mapToJson(map, returnParameters, filterHandler)).append(",");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

    public static String mapListToSingleJsonArray(List<Map<String, String>> mapList, String field) {
        StringBuilder sb = new StringBuilder(100);
        sb.append("[");
        for (Map<String, String> map : mapList) {
            sb.append("\"").append(map.get(field)).append("\",");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }
}
