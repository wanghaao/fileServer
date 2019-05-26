package file_server.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class DataToJsonUtils {
    private DataToJsonUtils() {
    }

    public static String objectToJson(Object object, int status){
        return "{"+"\""+"status"+"\""+":"+status+","+"\""+"data"+"\""+":"+ JSONObject.fromObject(object).toString()+"}";
    }

    public static String objectListToJson(List<?> list, int status){
        return "{"+"\""+"status"+"\""+":"+status+","+"\""+"data"+"\""+":"+ JSONArray.fromObject(list).toString()+"}";
    }

    public static String JsonNoStatusObject(Object object){
        return JSONObject.fromObject(object).toString();
    }
    public static String jsonNoStatusList(List<?> list){
        return JSONArray.fromObject(list).toString();
    }
}
