package us.codecraft.webmagic.processor.example;

import org.openqa.selenium.WebDriver;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.4.0
 */
public class BaiduBaikePageProcessor implements PageProcessor {

    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(3000).setUseGzip(true).
                    addHeader("cookie","__wpkreporterwid_=2f011b9b-0c7b-4cb2-362d-8220680d66fc; _uab_collina=160013630816567772773542; t=f95c1dbc23ad910e67b36b67a9fd3146; UM_distinctid=1748b267fbf40d-047f38064ecb34-333769-1fa400-1748b267fc0c9c; thw=cn; lego2_cna=PRER5YMWHYYRT5YC5YUM2CP5; cookie2=1e62f18ac72788b74c62c7f6b1c0fc8d; _samesite_flag_=true; ctoken=pMUObgLvzInyGhvwrTWirhllor; hng=CN%7Czh-CN%7CCNY%7C156; xlly_s=1; lLtC1_=1; _tb_token_=7f98ee5571335; cna=9MflFywxwXoCAW7ksVTTkbgp; mt=ci=11_1; v=0; enc=0vDagTAFmlXOGKsULYV%2B9r7ircyZO1G74stqGKcZ8Q6ljIfhucV9LpyN5vGYyqKqOsV69OwOwg0SUNUMI%2BqrFQ%3D%3D; _m_h5_tk=498c5a00a2a91185c829c78a3161b6f8_1600167710642; _m_h5_tk_enc=bf249e6a3bfc15d40dedeaf15b7fe5c5; CNZZDATA30076816=cnzz_eid%3D669265322-1600060067-https%253A%252F%252Ffun.fanli.com%252F%26ntime%3D1600160287; x5sec=7b2279656c6c6f7773746f6e653b32223a223730383139383238323234383933336534663065626637343736363936353035434a365a67767346454f37756a2b6638774c712b7277456144444d774e54417a4e4449324d6a67374d513d3d227d; sgcookie=E100Sbus%2F2cqmGjMy1BW55qVV9gqkU1o5u1QkDA6WEsBDNiLbXkglvGWP8kifgnqItqyH2LcH%2Bo4ZKXY%2FXKmCWgyyA%3D%3D; unb=2200811426035; uc3=nk2=F5RMHl3U1H0UhA0%3D&lg2=VT5L2FSpMGV7TQ%3D%3D&id2=UUphyuFBSMv8BmCrvg%3D%3D&vt3=F8dCufbCH1iC8ykshrg%3D; csg=4accf241; lgc=tb977283177; cookie17=UUphyuFBSMv8BmCrvg%3D%3D; dnk=tb977283177; skt=2a986a3cb9a860a1; existShop=MTYwMDE2MzAxOA%3D%3D; uc4=id4=0%40U2grEadJ%2FlMucfSrKNPGzng%2BPVfb91uX&nk4=0%40FY4HWyg0LCCs1PNV3Ivd6uJdO7n9OQ%3D%3D; tracknick=tb977283177; _cc_=U%2BGCWk%2F7og%3D%3D; _l_g_=Ug%3D%3D; sg=754; _nk_=tb977283177; cookie1=BvLcmw3LtqvetmDDoqKpTWEr4CKBmngfRKjsNK7BEaQ%3D; uc1=pas=0&existShop=false&cookie15=V32FPkk%2Fw0dUvg%3D%3D&cookie16=V32FPkk%2FxXMk5UvIbNtImtMfJQ%3D%3D&cookie21=VT5L2FSpczFp&cookie14=Uoe0bUnKMhZ9Hw%3D%3D; JSESSIONID=32832E44C66EFE2893A19CB47A126BCD; tfstk=cTN1BN0TWhx_voW2_P_E0v_C3DlVZdYSlOig5RwbRLDR_4a1i4drV28gnndKe21..; l=eBEVVMuROz9DVy6LBOfZhurza779dIRAguPzaNbMiOCPOy5H5gZFWZrvYE8MCnGVhsGWR3uV-FgLBeYBqCbjbDLzX2uPI_Mmn; isg=BCkpBk2zUjh55W6zOFs5Ww9UONWD9h0oyGeXp8seh5BPkkmkE0VU-BeIVDakCrVg")
    .setCycleRetryTimes(3);

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
    }//*[@id="brix_brick_29"]/script

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
        Spider spider = Spider.create(new BaiduBaikePageProcessor()).thread(5);
        //ppage为真实页码 淘宝每页120条 每60条需要加载一次 所以page = ppage*2
        String urlTemplate = "https://ai.taobao.com/search/index.htm?unid=F05xmsisdxjp05mv3m20&source_id=search&key=%s&ppage=1&page=1";
        ResultItems resultItems = spider.<ResultItems>get(String.format(urlTemplate, "手机壳"));
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
