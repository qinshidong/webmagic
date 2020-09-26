package us.codecraft.webmagic.processor.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.*;
import java.util.*;

public class test3 {

    public static void main(String[] args) {
        String jsonString = readFileByChars("D://Desktop/cookies1.txt");
        List<Object> listJson = getListJson(jsonString, CookiesDto.class);
//        List<CookiesDto> list= JSONArray.parseArray(jsonString,CookiesDto.class);
        CookiesDto[] array = new Gson().fromJson(jsonString,CookiesDto[].class);
//        List<CookiesDto> list = jsonString;
//        Map<String, Object> stringToMap = getStringToMap(s);
        System.out.println(listJson);
    }

    /**
     * Json中String 字符串转换为List
     * @param jsonStr
     * @param classT
     * @return
     */
    public static List<Object> getListJson(String jsonStr,Class<?>classT){
        List<Object> list=new ArrayList<Object>();
        JsonArray jsonArray=new Gson().fromJson(jsonStr, JsonArray.class);
        for (int i=0;i<jsonArray.size();i++){
            list.add(new Gson().fromJson(jsonArray.get(i),classT));
        }
        return list;
    }

    public static String readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            String cookies = "";
            while ((tempchar = reader.read()) != -1) {
                //对于windows下，rn这两个字符在一起时，表示一个换行。
                //但如果这两个字符分开显示时，会换两次行。
                //因此，屏蔽掉r，或者屏蔽n。否则，将会多出很多空行。
                if (((char) tempchar) != 'r') {
                    cookies += (char) tempchar;
                }

            }
            reader.close();
             return cookies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     *
     * String转map
     * @param str
     * @return
     */
    public static Map<String,Object> getStringToMap(String str){
        //根据逗号截取字符串数组
        String[] str1 = str.split(",");
        //创建Map对象
        Map<String,Object> map = new HashMap<>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            String[] str2 = str1[i].split(":");
            //str2[0]为KEY,str2[1]为值
            map.put(str2[0],str2[1]);
        }
        return map;
    }

}
