package us.codecraft.webmagic.processor.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.selenium.Selenium;
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

    private final  String cookie = "__wpkreporterwid_=2f011b9b-0c7b-4cb2-362d-8220680d66fc; _uab_collina=160013630816567772773542; t=f95c1dbc23ad910e67b36b67a9fd3146; UM_distinctid=1748b267fbf40d-047f38064ecb34-333769-1fa400-1748b267fc0c9c; thw=cn; lego2_cna=PRER5YMWHYYRT5YC5YUM2CP5; hng=CN%7Czh-CN%7CCNY%7C156; __wpkreporterwid_=25468e2c-6be7-4956-10c6-c9341985f0d2; CNZZDATA30076816=cnzz_eid%3D669265322-1600060067-https%253A%252F%252Ffun.fanli.com%252F%26ntime%3D1600671657; _samesite_flag_=true; cookie2=13d6ad311e7f1657c49fccce8b1bd00b; enc=ygDughRIuVoDdh8HDJU9xftFD6rZXXQRPacrnwP4Xty0oKrzq2OyJwqZ3hvTjj%2BPrArgmXK0P9coRfMooEwBGg%3D%3D; xlly_s=1; _tb_token_=3e55f3e854ee; ctoken=UkX81eifenmXugLp15X-XAK9; mt=ci=0_0; tracknick=; cna=9MflFywxwXoCAW7ksVTTkbgp; _m_h5_tk=3af83c3bbff0e43a181cc9d09d957919_1600770874952; _m_h5_tk_enc=10e9768404b69c6dc0086eb194c09b78; tfstk=ckifBV2xHsfbka8N0x9z_Wdj_brNwQ6QCENmhqMpzNuLOW1mpMPQUt0G6gULF; l=eBEVVMuROz9DVnToXOfanurza77OSIRYYuPzaNbMiOCP9DfB5YUFWZr6dlL6C3GVh6uMR3uV-FgpBeYBqQAonxvTX2uPIODmn; isg=BGBg3T9bK8oi2Jf4ifTwcA7TMW4yaUQzqcBOLNpxLHsO1QD_gnkUwzbnbX3V5fwL";

    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(3000).setUseGzip(true).
                    addHeader("cookie",
                            cookie
                    )
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36")
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

        page.putField("title", page.getHtml().xpath("//*[@id=\"mx_5\"]/ul").get());
//        page.putField("shops", page.getHtml().xpath("//*[@class='page-search']/script"));
        page.putField("shops", page.getHtml().xpath("//*[@class='page-search']/script"));
//        page.putField("description", page.getHtml().xpath("//*[@id='J_mmbuy_list']/li[1]/div[2]/div[3]"));
        page.putField("url",page.getUrl());

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
                Thread.sleep(7000);
                Spider spider = Spider.create(new AiTaoBaoPageProcessor()).thread(5).setDownloader(new SeleniumDownloader("D:\\Desktop\\chromedriver.exe"));
                try{
                int count = 0;

                //ppage为真实页码 淘宝每页120条 每60条需要加载一次 所以page = ppage*2
                String urlTemplate = "https://ai.taobao.com/search/index.htm?pid=mm_13127418_7884048_45121550473&unid=F05xmsisdxjp05ne4dj0&key=JK&taoke_type=1&ppage="+page.getPpage()+"&page="+page.getPage();
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
