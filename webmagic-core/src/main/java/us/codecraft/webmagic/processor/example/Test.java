package us.codecraft.webmagic.processor.example;

import com.google.common.collect.Lists;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test {

    private static ChromeDriverService service;

    public static WebDriver getChromeDriver() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/Google/Chrome/Application/chrome.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，只要在newFile（）的时候写入你放的路径即可）
        service = new ChromeDriverService.Builder().usingDriverExecutable(new File("D:/Desktop/chromedriver.exe")).usingAnyFreePort().build();
        service.start();
        // 创建一个 Chrome 的浏览器实例
        return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        WebDriver webDriver = Test.getChromeDriver();

//        webDriver.manage().window().maximize();

        webDriver.get("https://s.click.taobao.com/t?e=m%3D2%26s%3DvJ8OqoaKGGtw4vFB6t2Z2ueEDrYVVa64qYbrUZilZ4UKwPl3T8wu7FHEyeefvB3LvR7NEFlLEsI1sZpe12KEHNdlXPHrc%2BQOwhsKX1LNgs810JKatjRt8HaQEJ4dc9gIyubz7oN6u5OCW2MUr2mWG3EqY%2Bakgpmw&scm=null&pvid=100_172.18.173.199_10834_3791601027385847527&app_pvid=59590_11.1.93.222_518_1601027385843&ptl=floorId:34371;originalFloorId:34371;pvid:100_172.18.173.199_10834_3791601027385847527;app_pvid:59590_11.1.93.222_518_1601027385843&union_lens=lensId%3AOPT%401601027386%400b015dde_b3b9_174c4ab2b04_99aa%4001");
//        System.out.println(" Page title is: " + webDriver.getTitle()); //页面title

        //睡眠1秒 防反爬取机制拦击
        Thread.sleep(1000);
//        String pageSource = webDriver.getPageSource(); //获取页面

        try {
            //有优惠券页面
            webDriver.findElement(By.xpath("//*[@id=\"mx_9\"]/div/div/a/img")).click();
        } catch (Exception e) {
            //没有优惠券页面
        }

        //睡眠1秒 防反爬取机制拦击
//        Thread.sleep(1000);
        try{
            //有关闭窗口
            webDriver.findElement(By.xpath("//*[@id=\"sufei-dialog-close\"]")).click();
            webDriver.findElement(By.xpath("/html/body/div[6]/div[2]/div]")).click();
        }catch (Exception e){
            //没有关闭窗口
        }

        //商品名称
        String shopName = webDriver.findElement(By.id("J_Title")).getText();


        String specTitle = webDriver.findElement(By.xpath("//*[@id=\"J_isku\"]/div/dl[1]/dt")).getText();

        //总库存
        String stockAll = webDriver.findElement(By.xpath("//*[@id=\"J_SpanStock\"]")).getText();

        System.out.println("总库存====:" + stockAll);

        System.out.println("商品名:" + shopName);
        System.out.println("规格名:" + specTitle);

        //尺码列表 S M L XL 等
        List<WebElement> specElements = webDriver.findElements(By.xpath("//*[@id=\"J_isku\"]/div/dl[1]/dd/ul/li"));
        List<String> specArray = new ArrayList<>();
        for (int i = 1; i < specElements.size() + 1; i++) {
            specElements.get(i - 1).findElement(By.xpath("//*[@id=\"J_isku\"]/div/dl[1]/dd/ul/li[" + i + "]/a")).click();
            String specs = specElements.get(i - 1).findElement(By.xpath("//*[@id=\"J_isku\"]/div/dl[1]/dd/ul/li[" + i + "]/a/span")).getText();
//                specElements.get(i - 1).findElement(By.xpath("//*[@id=\"J_isku\"]/div/dl[1]/dd/ul/li[" + i + "]/a")).click();
            specArray.add(specs);
            System.out.println(specs);

            //颜色列表
            List<WebElement> colorElements = webDriver.findElements(By.xpath("//*[@id=\"J_isku\"]/div/dl[2]/dd/ul/li"));
            List<String> colorArray = new ArrayList<>();

            for (int u = 1; u < colorElements.size() + 1; u++) {

                colorElements.get(u - 1).findElement(By.xpath("//*[@id=\"J_isku\"]/div/dl[2]/dd/ul/li[" + u + "]/a")).click();

                String stock = webDriver.findElement(By.xpath("//*[@id=\"J_SpanStock\"]")).getText();

                String colorImg = null;
                String text = null;
                String outOfStock = null;
                outOfStock = colorElements.get(u - 1).findElement(By.xpath("//*[@id=\"J_isku\"]/div/dl[2]/dd/ul/li[" + u + "]")).getAttribute("className");
//                    System.out.println(outOfStock+"能不能点击");
                //不能点击说明库存为0
                if ("tb-txt tb-out-of-stock".equals(outOfStock)) {
                    stock = "0";
                }
                colorImg = colorElements.get(u - 1).findElement(By.xpath("//*[@id=\"J_isku\"]/div/dl[2]/dd/ul/li[" + u + "]/a")).getAttribute("style");
                text = colorElements.get(u - 1).findElement(By.xpath("//*[@id=\"J_isku\"]/div/dl[2]/dd/ul/li[" + u + "]/a/span")).getAttribute("innerText");
                System.out.println(subString(colorImg, "(\"", "\")"));
                System.out.println(text);

                System.out.println(specs + "码" + text + "精细库存==" + stock);

                //点到最后一个规格/颜色 取消点击
                if (u == colorElements.size()) {
                    System.out.println("取消点击事件");
                    colorElements.get(u - 1).findElement(By.xpath("//*[@id=\"J_isku\"]/div/dl[2]/dd/ul/li[" + u + "]/a")).click();
                }
            }
        }

        //轮播图
        List<WebElement> rotationElements = webDriver.findElements(By.xpath("//*[@id=\"J_UlThumb\"]/li"));
        for (int i = 1; i < rotationElements.size() + 1; i++) {
            String rotationImg = rotationElements.get(i - 1).findElement(By.xpath("//*[@id=\"J_UlThumb\"]/li[" + i + "]/div/a/img")).getAttribute("src");
            System.out.println(rotationImg);

            //400x400的图
            System.out.println(rotationImg.replace("50x50", "400x400"));
        }

        //商品详情属性列表
        List<WebElement> attributesElement = webDriver.findElements(By.xpath("//*[@id=\"attributes\"]/ul/li"));
        for (int i = 1; i < attributesElement.size() + 1; i++) {
            String rotationImg = attributesElement.get(i - 1).findElement(By.xpath("//*[@id=\"attributes\"]/ul/li[" + i + "]")).getAttribute("innerText");

            //属性列表
            System.out.println("属性列表----" + rotationImg);
        }

        //商品详情图列表
        WebElement detailImgElement = webDriver.findElement(By.xpath("//*[@id=\"J_DivItemDesc\"]/div/div"));
        System.out.println("详情图长度" + detailImgElement.getSize());
        String childrenWebElement = detailImgElement.getAttribute("children");
        System.out.println();
//        for(int i = 1; i < detailImgElement.size() + 1; i++){
//            String detailImg = detailImgElement.get(i - 1).findElement(By.xpath("//*[@id=\"J_DivItemDesc\"]/div/div/img[" + i + "]")).getAttribute("src");
//
//            //属性列表
//            System.out.println("商品详情图片----" + detailImg);
//        }

        webDriver.close();
    }

    //静默处理
    private static void baseSleep(int n) {
        try {
            Thread.sleep(n * 1000L);
        } catch (InterruptedException e) {
            // NOTHING
        }
    }

    /**
     * 截取指定字段
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd) + 1;

        /* index为负数 即表示该字符串中没有该字符 */
        if (strStartIndex < 0) {
            return null;
        }
        if (strEndIndex < 0) {
            return null;
        }

        StringBuffer bu = new StringBuffer();
        bu.append(str.substring(strStartIndex, strEndIndex).replace("(\"", ""));
        return bu.substring(0, bu.length() - 1);
    }

}
