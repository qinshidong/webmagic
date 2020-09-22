package us.codecraft.webmagic.processor.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.entity.AiTaoBaoDto;
import us.codecraft.webmagic.processor.entity.PageUtil;

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
public class ALiMaMaProcessor implements PageProcessor {

    private final  String cookie = "t=b4d0133130ba969e4a1ce278b8953dbe; cna=9MflFywxwXoCAW7ksVTTkbgp; cookie2=16c4e11e83e44e7324df6cd6afc728f0; _tb_token_=3e61e031eb17d; xlly_s=1; v=0; alimamapwag=TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzg1LjAuNDE4My4xMDIgU2FmYXJpLzUzNy4zNg%3D%3D; cookie32=1b1e45a2a2147cc5254540a747c95dc0; alimamapw=SlwVXlkKXVkObANTBQwHBVVSA1RXAAxUV15TBgBRUgtTVVVUVQJTU1AE; cookie31=MTQwMDczMDExNCx5andpbGxpYW0seWp3aWxsaWFtQDEyNi5jb20sVEI%3D; login=V32FPkk%2Fw0dUvg%3D%3D; rurl=aHR0cHM6Ly9wdWIuYWxpbWFtYS5jb20vP3NwbT1hMmUxMTQuMTQ5MjIzOTIuMC4wLjU1ZjM3NWE1YkJsTWNt; x5sec=7b2268616c6c65792d736f6c61723b32223a226163613964316662363665383433663334623762633337633331663264633064434e6e4f6f667346454f6e496f3576502b4c71372f77453d227d; isg=BHV1IdFrRlwEsKLXYmHrNar2hPEv8ikEvCvbQ_eaBew7zpTAv0EI1ZBPHJJ4jkG8; l=eB_N_oqVOz9v_gWpBOfZlurza779JIRfguPzaNbMiOCP9Qfp57flWZrttlY9CnGVnsfkR3uV-FgpBW8gPy4ehVZ_WWk6dKagZdTh.; tfstk=cR0RBOZSVKvuLtFx_0K0OAIGXCAcZMpLh_whJCBhhZwG8JbdiwgiWcZMVW68kIC..";

    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(3000).setUseGzip(true).
                    addHeader("cookie",
                            cookie
                    )
            .setCycleRetryTimes(3).setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36");

    //process（抓取过程）
    @Override
    public void process(Page page) {
        //输出html源码
        System.out.println(page.getHtml());

        //采用xpath解析
//        page.addTargetRequests(page.getHtml().css("//*[@id='J_myshop_list'] a").links().all()); //获取列表的超链接地址并通过该地址访问里面的网页
        //获取爱淘宝中商品的js
//        page.putField("shops", page.getHtml().xpath("//*[@class='page-search']/script"));
//        page.putField("description", page.getHtml().xpath("//*[@id='J_mmbuy_list']/li[1]/div[2]/div[3]"));
//        page.putField("url",page.getUrl());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws InterruptedException {
//
//            List<PageUtil> pageUtils =  pageSize(10);
////            Spider spider = new Spider(null);
//            for (PageUtil page : pageUtils) {
                //single download
//                Thread.sleep(7000);
                Spider spider = Spider.create(new ALiMaMaProcessor()).thread(5);
//                try{
//                int count = 0;

                //ppage为真实页码 淘宝每页120条 每60条需要加载一次 所以page = ppage*2
                String urlTemplate = "https://pub.alimama.com/openapi/json2/1/gateway.unionpub/optimus.query.json?t=1600737032442&_tb_token_=f7136e837f761";
                String key = "JK";
                ResultItems resultItems = spider.<ResultItems>get(String.format(urlTemplate, key));
                System.out.println(resultItems.toString());
//                Object shops = resultItems.get("shops");

//                if(shops.toString() == null || "null".equals(shops.toString())){
//                    //失败数据
//                    //终止程序
//                    System.out.println("终止程序 重新启动");
//
//                    magic(urlTemplate, key, true, 5);
//
//                }

//           System.out.println(shops.toString());

//                //切割js中的商品json串
//                Map map = (Map) JSON.parse(subString(replaceAllBlank(shops.toString()),"_pageResult=","</script>"));
//                Map result = (Map) map.get("result");

                //转实体类
//                List<AiTaoBaoDto> modelList = JSONObject.parseArray(result.get("auction").toString(),AiTaoBaoDto.class);


//                for (AiTaoBaoDto taobao: modelList
//                ) {
//                    System.out.println(count++ +"+++==="+taobao.toString());
//                }
//
//                Random rand = new Random();
//
//                spider.close();
////            Thread.sleep(5000);
//                }catch (Exception e){
//
//                } finally {
                    spider.close();
//                }
//            }/

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

        Spider spider = Spider.create(new ALiMaMaProcessor()).thread(5);
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
     * 页码
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
//            1 1  1 2 2 3 2 4 3 5 36 47 48
        return pageList;
    }
}
