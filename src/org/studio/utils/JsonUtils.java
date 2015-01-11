package org.studio.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * org.studio.utils
 * Created by lzim on 11/9/14.
 */
public class JsonUtils {

    /**
     * 将java对象转换成json字符串
     * @param javaObj
     * @return
     */
    public static String JsonString4Object(Object javaObj) {
        JSONObject json;
        json = JSONObject.fromObject(javaObj);
        return json.toString();
    }

    /**
     * 从Map对象得到Json字串
     * @param map
     * @return
     */
    public static String JsonString4Map(Map map) {
        JSONObject json = JSONObject.fromObject(map);
        return json.toString();
    }

    /**
     * 用map构造一个JsonString
     * @param m
     * @return
     */
    public static String JsonStringer4Map (Map m) {
        JSONStringer stringer = new JSONStringer();
        stringer.object();
        for (Object key : m.keySet()) {
            stringer.key((String) key)
                    .value((String)m.get(key));
        }
        stringer.endObject();
        return stringer.toString();
    }

    /**
     * 从json字串中得到相应java数组
     *
     * Note:
     * A JSONObject text must begin with '{'
     *
     * @param jsonString
     * @return
     */
    public static JSONObject Object4JsonString(String jsonString) {
        if(jsonString.indexOf("[")!=-1){
            jsonString=jsonString.replace("[", "");
        }
        if(jsonString.indexOf("]")!=-1){
            jsonString=jsonString.replace("]", "");
        }
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        return jsonObject;
    }

    /**
     * 从json字串中得到相应java数组
     *
     * Note:
     * A JSONArray text must begin with '['
     *
     * @param jsonString
     * @return
     */
    public static JSONArray Array4JsonString(String jsonString) {
        if(jsonString.indexOf("{")!=-1 && jsonString.indexOf("}")!=-1){
            jsonString="["+jsonString;
            jsonString=jsonString+"]";
        }
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray;
    }

    /**
     * 将list转换成Array
     * @param list
     * @return
     */
    public static Object[] Array4List(List list) {
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray.toArray();
    }

    /**
     * 从json HASH表达式中获取一个map
     * @param jsonString
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map Map4JsonString(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Iterator keyIter = jsonObject.keys();
        String key;
        Object value;
        Map valueMap = new HashMap();
        while (keyIter.hasNext()) {
            key = (String) keyIter.next();
            value = jsonObject.get(key);
            valueMap.put(key, value);
        }
        return valueMap;
    }
}
