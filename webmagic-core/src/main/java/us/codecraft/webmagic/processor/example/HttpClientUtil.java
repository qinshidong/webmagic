package us.codecraft.webmagic.processor.example;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class HttpClientUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    /**
     * 返回成功状态码
     */
    private static final int SUCCESS_CODE = 200;

    /**
     * 设备key(固定)
     */
    private static final int APPKEY = 12574478;
    /**
     * 发送GET请求
     * @param url   请求url
     * @param nameValuePairList    请求参数
     * @return JSON或者字符串
     * @throws Exception
     */
    public static Object sendGet(String url) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            /**
             * 创建HttpClient对象
             */
            client = HttpClients.createDefault();
            /**
             * 创建URIBuilder
             */
            URIBuilder uriBuilder = new URIBuilder(url);
            /**
             * 设置参数
             */
//            uriBuilder.addParameters(nameValuePairList);
            /**
             * 创建HttpGet
             */
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            /**
             * 设置请求头部编码
             */
            httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            /**
             * 设置返回编码
             */
            httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            httpGet.setHeader(new BasicHeader("cookie", "_m_h5_tk=41baceb301f99f4263158eff8bb708f5_1600849565865; _m_h5_tk_enc=50c39933d7b867b3b55553abe8fed799;"));
            /**
             * 请求服务
             */
            response = client.execute(httpGet);


            /**
             * 获取响应吗
             */
            int statusCode = response.getStatusLine().getStatusCode();
            Header[] allHeaders = response.getAllHeaders();

            Object _m_h5_tk = Array.get(allHeaders, 5);
            Object _m_h5_tk_enc =  Array.get(allHeaders, 6);

            String tk = subString(_m_h5_tk.toString(), " _m_h5_tk=", ";Path", "_m_h5_tk=");
            String tk_enc = subString(_m_h5_tk_enc.toString(), " _m_h5_tk_enc=", ";Path", "_m_h5_tk_enc=");
            String tkSplit =tk.substring(0, tk.indexOf("_"));
            String t = tk.substring(tkSplit.length()+1, tk.length()); //截取时间戳

            System.out.println(tk);
            System.out.println(t);
            System.out.println(tk_enc);

            if (SUCCESS_CODE == statusCode){
                /**
                 * 获取返回对象
                 */
                HttpEntity entity = response.getEntity();
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString(entity,"UTF-8");
                /**
                 * 转换成json,根据合法性返回json或者字符串
                 */
                try{
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                }catch (Exception e){
                    return result;
                }
            }else{
                LOGGER.error("HttpClientService-line: {}, errorMsg{}", 97, "GET请求失败！");
            }
        }catch (Exception e){
            LOGGER.error("HttpClientService-line: {}, Exception: {}", 100, e);
        } finally {
            response.close();
            client.close();
        }
        return null;
    }

    /**
     * 发送POST请求
     * @param url
     * @param nameValuePairList
     * @return JSON或者字符串
     * @throws Exception
     */
    public static Object sendPost(String url, List<NameValuePair> nameValuePairList) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            /**
             *  创建一个httpclient对象
             */
            client = HttpClients.createDefault();
            /**
             * 创建一个post对象
             */
            HttpPost post = new HttpPost(url);
            /**
             * 包装成一个Entity对象
             */
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            /**
             * 设置请求的内容
             */
            post.setEntity(entity);
            /**
             * 设置请求的报文头部的编码
             */
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            /**
             * 设置请求的报文头部的编码
             */
            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            /**
             * 执行post请求
             */
            response = client.execute(post);
            /**
             * 获取响应码
             */
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                /**
                 * 转换成json,根据合法性返回json或者字符串
                 */
                try{
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                }catch (Exception e){
                    return result;
                }
            }else{
                LOGGER.error("HttpClientService-line: {}, errorMsg：{}", 146, "POST请求失败！");
            }
        }catch (Exception e){
            LOGGER.error("HttpClientService-line: {}, Exception：{}", 149, e);
        }finally {
            response.close();
            client.close();
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        Object o = sendGet("https://h5api.m.taobao.com/h5/mtop.alimama.union.xt.en.api.entry/1.0/?appKey=12574478&t=1600840719650&sign=f29654260a023434b8ff5375e2725e78&timeout=9999999&data=%7b%22pNum%22%3a0%2c%22pSize%22%3a%2260%22%2c%22refpid%22%3a%22mm_13127418_7884048_45121550473%22%2c%22variableMap%22%3a%22%7b%5c%22q%5c%22%3a%5c%22JK%5c%22%2c%5c%22navigator%5c%22%3atrue%2c%5c%22union_lens%5c%22%3a%5c%22recoveryid%3a201_11.10.227.244_2470021_1600825816731%3bprepvid%3a201_11.23.83.213_14112_1600830998782%5c%22%2c%5c%22recoveryId%5c%22%3a%5c%22201_11.88.26.84_44199_1600840582063%5c%22%7d%22%2c%22qieId%22%3a%2234374%22%2c%22spm%22%3a%22a2e1u.19484427.29996460%22%2c%22app_pvid%22%3a%22201_11.88.26.84_44199_1600840582063%22%2c%22ctm%22%3a%22spm-url%3aa2e1u.19484427.search.1%3bpage_url%3ahttps%253A%252F%252Fai.taobao.com%252Fsearch%252Findex.htm%253Fpid%253Dmm_13127418_7884048_45121550473%2526unid%253DF05xmsisdxjp05ngk4i5%2526key%253DJK%2526taoke_type%253D1%2526spm%253Da2e1u.19484427.search.1%2526union_lens%253Drecoveryid%25253A201_11.10.227.244_2470021_1600825816731%25253Bprepvid%25253A201_11.23.83.213_14112_1600830998782%22%7d");
        System.out.println(o.toString());
    }

    /**
     * 截取指定字段
     */
    public static String subString(String str, String strStart, String strEnd, String split) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd)+1;

        /* index为负数 即表示该字符串中没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :" + str + "中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :" + str + "中不存在 " + strEnd + ", 无法截取目标字符串";
        }

        StringBuffer bu = new StringBuffer();
        bu.append(str.substring(strStartIndex, strEndIndex).replace(split, ""));
        return bu.substring(0,bu.length() - 2);
    }
}
