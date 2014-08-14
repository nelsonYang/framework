package com.framework.response;

import com.framework.enumeration.ResponseTypeEnum;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author nelson
 */
public final class ResponseWriterManager {

    private static ResponseWriterManager responseWriterManager;
    private static final Map<ResponseTypeEnum, ResponseWriter> responseWriterMap = new ConcurrentHashMap<ResponseTypeEnum, ResponseWriter>(8, 1);

    static {
        responseWriterMap.put(ResponseTypeEnum.NO_DATA_JSON, new NoDataJsonResponseWriter());
        responseWriterMap.put(ResponseTypeEnum.ENTITY_JSON, new EntityJsonResponseWriter());
        responseWriterMap.put(ResponseTypeEnum.ENTITY_LIST_JSON, new EntityListJsonResponseWriter());
        responseWriterMap.put(ResponseTypeEnum.ENTITY_LIST_JSON_PAGE, new EntityListJsonPageResponseWriter());
        responseWriterMap.put(ResponseTypeEnum.MAP_DATA_JSON, new MapDataJsonResponseWriter());
        responseWriterMap.put(ResponseTypeEnum.MAP_DATA_LIST_JSON, new MapListDataJsonResponseWriter());
        responseWriterMap.put(ResponseTypeEnum.MAP_DATA_LIST_JSON_PAGE, new MapListDataJsonPageResponseWriter());
    }

    public static synchronized ResponseWriterManager getInstance() {
        if (responseWriterManager == null) {
            responseWriterManager = new ResponseWriterManager();
        }
        return responseWriterManager;
    }

    public ResponseWriter getResponseWriter(ResponseTypeEnum responseTypeEnum) {
        return responseWriterMap.get(responseTypeEnum);
    }
}
