package us.codecraft.webmagic.processor.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.selenium.Selenium;
import jdk.nashorn.internal.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.entity.AiTaoBaoDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.4.0
 */
public class BaiduBaikePageProcessor implements PageProcessor {

    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(1).setSleepTime(3000).setUseGzip(true).
                    addHeader("cookie","_samesite_flag_=true;_tb_token_=f133b3d883371;cookie2=135dee7bed66eb46d4e735bc99fc4ba8;t=d53c330999b898a7cc5b21a16bea8d2f;" )
    .setCycleRetryTimes(1).setUserAgent("Mozilla/5.0");

    //process（抓取过程）
    @Override
    public void process(Page page) {
        //输出html源码
        System.out.println(page.getHtml());

        //采用xpath解析
//        page.addTargetRequests(page.getHtml().css("//*[@id='J_myshop_list'] a").links().all()); //获取列表的超链接地址并通过该地址访问里面的网页
        page.putField("shops", page.getHtml().xpath("//*[@class='page-search']/script"));
//        page.putField("description", page.getHtml().xpath("//*[@id='J_mmbuy_list']/li[1]/div[2]/div[3]"));
        page.putField("url",page.getUrl());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
        Spider spider = Spider.create(new BaiduBaikePageProcessor()).thread(5);

        //ppage为真实页码 淘宝每页120条 每60条需要加载一次 所以page = ppage*2
        String urlTemplate = "https://ai.taobao.com/search/index.htm?unid=F05xmsisdxjp05mv3m20&source_id=search&key=%s&ppage=1&page=1";
        ResultItems resultItems = spider.<ResultItems>get(String.format(urlTemplate, "UI"));
        Object shops = resultItems.get("shops");

        if(shops.toString() == null || "null".equals(shops.toString())){
            //失败数据
            //终止程序
            System.out.println("终止程序");
        }

        Map map = (Map) JSON.parse(subString(replaceAllBlank(shops.toString()),"_pageResult=","</script>"));
        Map result = (Map) map.get("result");

        //转实体类
        List<AiTaoBaoDto> modelList = JSONObject.parseArray(result.get("auction").toString(),AiTaoBaoDto.class);

        spider.close();
    }


    /**
     * 截取指定字段
     */
    public static String subString(String str, String strStart, String strEnd) {

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
        bu.append(str.substring(strStartIndex, strEndIndex).replace("_pageResult=", ""));
        return bu.substring(0,bu.length() - 2);
    }

    /**
     * 去空格换行
     * @param str
     * @return
     */
    public static String replaceAllBlank(String str) {
        String s = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            /*
            \n 回车(\u000a)
            \t 水平制表符(\u0009)
            \s 空格(\u0008)
            \r 换行(\u000d)
            */
            Matcher m = p.matcher(str);
            s = m.replaceAll("");
        }
        return s;
    }
}
