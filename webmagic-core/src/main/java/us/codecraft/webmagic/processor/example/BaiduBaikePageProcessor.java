package us.codecraft.webmagic.processor.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.4.0
 */
public class BaiduBaikePageProcessor implements PageProcessor {

    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    @Override
    public void process(Page page) {
        System.out.println(page.getHtml());
        page.putField("name", page.getHtml().xpath("//*[@id=\"J_mmbuy_list\"]/li[1]/div[2]/div[1]/text()").get());
        page.putField("description", page.getHtml().xpath("//*[@id='J_mmbuy_list']/li[1]/div[2]/div[3]"));
        page.putField("url",page.getUrl());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
        Spider spider = Spider.create(new BaiduBaikePageProcessor()).thread(5);
        String urlTemplate = "http://fanli.com";
        ResultItems resultItems = spider.<ResultItems>get(String.format(urlTemplate, "爱情"));
        System.out.println(resultItems);

        //multidownload
//        List<String> list = new ArrayList<String>();
//        list.add(String.format(urlTemplate,"风力发电"));
//        list.add(String.format(urlTemplate,"太阳能"));
//        list.add(String.format(urlTemplate,"地热发电"));
//        list.add(String.format(urlTemplate,"地热发电"));
//        List<ResultItems> resultItemses = spider.<ResultItems>getAll(list);
//        for (ResultItems resultItemse : resultItemses) {
//            System.out.println(resultItemse.getAll());
//        }
        spider.close();
    }
}
