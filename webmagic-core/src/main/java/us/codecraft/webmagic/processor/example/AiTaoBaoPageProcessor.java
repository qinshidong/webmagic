package us.codecraft.webmagic.processor.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.entity.AiTaoBaoDto;
import us.codecraft.webmagic.processor.entity.PageUtil;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.SeleniumDownloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.4.0
 */
public class AiTaoBaoPageProcessor implements PageProcessor {


    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(3000).setUseGzip(true)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36")
            .addHeader("cookies","thw=cn; miid=954637331616787875; lid=wuyuan_001; _l_g_=Ug%3D%3D; lgc=wuyuan_001; cookie1=UNjVCmUoYPqubehnMAH7ISdDmJvKkQYykcRFRIYxa%2BY%3D; lLtC1_=1; existShop=MTYwMTExMTQ5OQ%3D%3D; cookie2=1d55986c4a7abe3d4b1afb3d969339e7; sg=15f; cna=xPP1FxAek1ICAW7k9tQfgCqD; skt=e1c87829b179674e; _tb_token_=5ee8883ebe736; xlly_s=1; dnk=wuyuan_001; hng=CN%7Czh-CN%7CCNY%7C156; uc1=cookie16=UtASsssmPlP%2Ff1IHDsDaPRu%2BPw%3D%3D&cookie15=UIHiLt3xD8xYTw%3D%3D&existShop=false&cookie14=Uoe0bHb0MDwUhQ%3D%3D&cookie21=WqG3DMC9Eman&pas=0; uc3=nk2=FOx1QcpuOgHh3w%3D%3D&vt3=F8dCufePf6eI5JehJac%3D&id2=UUphw2eZQavrVz%2B%2BGg%3D%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; tracknick=wuyuan_001; uc4=id4=0%40U2grGNhgag237jFAQDTpaJ3kbUoUf6FX&nk4=0%40Fmd01z6tWWn18%2BmrGrUBOt43Cnc2; unb=2209247964625; XSRF-TOKEN=489ef3bf-9ede-4dc6-bf7e-d7cfa05d6ee0; tfstk=c3UCBVGL9pvQ1jsy8W1ZUlVAHiuPZ0OjNMM3OkgCOIW9ujFCifLqhfR3qLLtDf1..; _samesite_flag_=true; _cc_=VFC%2FuZ9ajQ%3D%3D; l=eBIOxhLROy9gfJ3BBOfZnurza779OIRVguPzaNbMiOCP9L1H5XoCWZr3fd8MCnGcnsQDRuASn5QgBqTiEy4Uh6Yl3ZCzxcjZVdBG.; cookie17=UUphw2eZQavrVz%2B%2BGg%3D%3D; _nk_=wuyuan_001; sgcookie=E100nWOdEjJBM6WFKFt%2FoKaLtw2ITJogF%2BTRQZytaCxUKuOJSWzLT3oet7yYWpS7%2FQHqHUEfQ0EVuNJy5nLaQOyoUg%3D%3D; t=d3f675164256e4ac3d3c52ceed3cf993; csg=28851d3e; v=0; lc=VyT1ROY1D7MuuknrKCJm; isg=BLy8yVXHzyCUHvt8QGIg1XS2jVputWDfWo9TO5Y966eIYV7rvsZ-bkNTRYgZKZg3; cookieCheck=42317")
            .setCycleRetryTimes(3);

    //process（抓取过程）
    @Override
    public void process(Page page) {
        //输出html源码
        System.out.println(page.getHtml());

//        if (page.getHtml().xpath("//*[@id=\"mx_5\"]/ul").get() == null || "null".equals(page.getHtml().xpath("//*[@id=\"mx_5\"]/ul").get())){
////            //skip this page
//            page.setSkip(true);
//        }
        //采用xpath解析
//        page.addTargetRequests(page.getHtml().css("//*[@id='J_myshop_list'] a").links().all()); //获取列表的超链接地址并通过该地址访问里面的网页
        //获取爱淘宝中商品的js

//        page.putField("title", page.getHtml().xpath("//*[@id=\"mx_5\"]/ul").get());
////        page.putField("shops", page.getHtml().xpath("//*[@class='page-search']/script"));
//        page.putField("shops", page.getHtml().xpath("//*[@class='page-search']/script"));
////        page.putField("description", page.getHtml().xpath("//*[@id='J_mmbuy_list']/li[1]/div[2]/div[3]"));
//        page.putField("url",page.getUrl());

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws InterruptedException {

            List<PageUtil> pageUtils =  pageSize(2);
//            Spider spider = new Spider(null);
            for (PageUtil page : pageUtils) {
                //single download
//                Thread.sleep(7000);
                Spider spider = Spider.create(new AiTaoBaoPageProcessor()).thread(5).setDownloader(new SeleniumDownloader("D:\\Desktop\\chromedriver.exe"));
                try{
                int count = 0;

                //ppage为真实页码 淘宝每页120条 每60条需要加载一次 所以page = ppage*2
                String urlTemplate = "https://s.click.taobao.com/t?e=m%3D2%26s%3DqR6IQ9Lvd%2F9w4vFB6t2Z2ueEDrYVVa64qYbrUZilZ4UKwPl3T8wu7P1Dur9W78u5B12aBPe3rCc1sZpe12KEHNdlXPHrc%2BQOwhsKX1LNgs810JKatjRt8HaQEJ4dc9gIyubz7oN6u5OCW2MUr2mWG3EqY%2Bakgpmw&scm=null&pvid=100_172.18.173.199_10893_7331601084065341697&app_pvid=59590_11.88.165.231_545_1601084065333&ptl=floorId:34371;originalFloorId:34371;pvid:100_172.18.173.199_10893_7331601084065341697;app_pvid:59590_11.88.165.231_545_1601084065333&union_lens=lensId%3AOPT%401601084065%400b58a5e7_abd6_174c80c0700_5fab%4001";
                String key = "JK";
                ResultItems resultItems = spider.<ResultItems>get(urlTemplate); 
                Object shops = resultItems.get("shops");

                if(shops.toString() == null || "null".equals(shops.toString())){
                    //失败数据
                    //终止程序
                    System.out.println("终止程序 重新启动");

                    magic(urlTemplate, key, true, 5);

                }

//           System.out.println(shops.toString());

                //切割js中的商品json串
                Map map = (Map) JSON.parse(subString(replaceAllBlank(shops.toString()),"_pageResult=","</script>"));
                Map result = (Map) map.get("result");

                //转实体类
                List<AiTaoBaoDto> modelList = JSONObject.parseArray(result.get("auction").toString(),AiTaoBaoDto.class);


                for (AiTaoBaoDto taobao: modelList
                ) {
                    System.out.println(count++ +"+++==="+taobao.toString());
                }

                Random rand = new Random();

                spider.close();
//            Thread.sleep(5000);
                }catch (Exception e){

                } finally {
                    spider.close();
                }
            }

    }

    /**
     * 抽象单页抓取
     */

    public static void magic(String  urlTemplate, String key, boolean wait, int count) throws InterruptedException {

        //连续多次爬取未成功 重新去获取cookie
        if(count == 0){
            System.out.println("连续5次未成功 重新获取cookie");
            return;
        }

        //判断是否需要等待
        if(wait){
            Thread.sleep(7000);
        }

        Spider spider = Spider.create(new AiTaoBaoPageProcessor()).thread(5);
        try{
            int count2 = 0;

            //ppage为真实页码 淘宝每页120条 每60条需要加载一次 所以page = ppage*2
            ResultItems resultItems = spider.<ResultItems>get(String.format(urlTemplate, key));
            Object shops = resultItems.get("shops");

            if(shops.toString() == null || "null".equals(shops.toString())){
                //失败数据
                //终止程序
                System.out.println("终止程序 重新启动");
                magic(urlTemplate ,key, false, count - 1);
            }

//           System.out.println(shops.toString());

            //切割js中的商品json串
            Map map = (Map) JSON.parse(subString(replaceAllBlank(shops.toString()),"_pageResult=","</script>"));
            Map result = (Map) map.get("result");

            //转实体类
            List<AiTaoBaoDto> modelList = JSONObject.parseArray(result.get("auction").toString(),AiTaoBaoDto.class);


            for (AiTaoBaoDto taobao: modelList
            ) {
                System.out.println(count2++ +"+++==="+taobao.toString());
            }

            Random rand = new Random();

//                        Thread.sleep(7000);
            spider.close();
//            Thread.sleep(5000);
        }catch (Exception e){

        } finally {
            spider.close();
        }
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

    /**
     * 得到页码集合
     * @param ppage
     * @return
     */
    public static List<PageUtil> pageSize(Integer ppage){
        List<PageUtil> pageList = new ArrayList<>();
        for (int i = 1;i < ppage; i++){
            PageUtil ppageModel = new PageUtil();
            ppageModel.setPpage(i);
            ppageModel.setPage(i * 2 -1);
            pageList.add(ppageModel);
            PageUtil pageModel = new PageUtil();
            pageModel.setPpage(i);
            pageModel.setPage(i * 2);
            pageList.add(pageModel);
        }
        return pageList;
    }
}
