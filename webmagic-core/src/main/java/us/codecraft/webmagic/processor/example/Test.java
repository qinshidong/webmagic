package us.codecraft.webmagic.processor.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {

    private static ChromeDriverService service;

    public static WebDriver getChromeDriver() throws IOException {
        System.setProperty("webdriver.chrome.driver","C:/Program Files/Google/Chrome/Application/chrome.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，只要在newFile（）的时候写入你放的路径即可）
        service = new ChromeDriverService.Builder().usingDriverExecutable(new File("D:/Desktop/chromedriver.exe")) .usingAnyFreePort().build();
        service.start();
        // 创建一个 Chrome 的浏览器实例
        return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        WebDriver webDriver = Test.getChromeDriver();
        webDriver.get("https://s.click.taobao.com/t?e=m%3D2%26s%3DOkZ5eNmCb9Jw4vFB6t2Z2ueEDrYVVa64RZKDdhY4Y8EKwPl3T8wu7CAabmcl6HVQ51Z6%2BDcapmk1sZpe12KEHNdlXPHrc%2BQOeM3g4Fpbtigm9hQVBpCwYX3zgeqdDr8Fhsq7LDrhtNohhQs2DjqgEA%3D%3D&scm=null&pvid=100_11.230.60.226_10079_4901600932226222857&app_pvid=59590_11.8.40.219_537_1600932226220&ptl=floorId:34371;originalFloorId:34371;pvid:100_11.230.60.226_10079_4901600932226222857;app_pvid:59590_11.8.40.219_537_1600932226220&union_lens=lensId%3AOPT%401600932226%400b0828db_e6a2_174beff2572_37fc%4001");
        System.out.println(" Page title is: " +webDriver.getTitle()); //页面title

        String pageSource = webDriver.getPageSource(); //获取页面
        try{
            //有关闭窗口
            webDriver.findElement(By.xpath("//*[@id=\"sufei-dialog-close\"]")).click();
        }catch (Exception e){
            //没有关闭窗口
        }

        System.out.println(pageSource);
        Thread.sleep(99999);


//        int rowCout = elements.size();

//        webDriver.switchTo().frame("42dc21d6846284f94e60b8ac1946c769");

//        for (int i = 1; i < rowCout + 1; i++){
//
//            //跳转链接
//            String link = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a")).getAttribute("href");
//            //商品图片
//            String img = null;
//            img = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/img")).getAttribute("src");
//            if(img == null){
//                img = "https://" + elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/img")).getAttribute("data-src");
//            }
//
//            String type = null;
//            try {
//
//                elements.get(i - 1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li[" + i + "]/a/div[1]/img")).getAttribute("src");
//                type = "1";
//            } catch (Exception e) {
//
//                type = "0";
//
//            }
//            //商品平台  淘宝/天猫
////            if(shopTitle.size() == 2){
////                type = "天猫";
////            }else {
////                type = "淘宝";
////            }
//
//            //商品标题
//            String shopName = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/div[1]/span")).getText();
//
//            List<WebElement> elements1 = elements.get(i - 1).findElements(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/div"));
////             for (WebElement el:elements1){
////                 String text = el.findElement(By.xpath("//[@class=tip-con]")).getText();
////                 System.out.println(text);
////             }
//
//            String price = null;
//            String oldPrice = null;
//            String sellerName = null;
//            String sales = null;
//
//            //判断是否有券 如果有券就往下挪一个div
//            if(elements1.size() == 4){
//                //商品现价
//                 price = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/div[2]/span[2]")).getText();
//                //商品原价
//                 oldPrice = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/div[2]/span[3]")).getText();
//                //店铺名称
//                 sellerName = new String(elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/div[3]/div")).getText().getBytes(),"utf-8").replace("\uE667","");
//
//                //商品销量
//                 sales = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/div[4]/div[2]")).getText().replace("月销 ", "");
//
//            }else if(elements1.size() == 5) {
//                //商品现价
//                price = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/div[3]/span[2]")).getText();
//                //商品原价
//                oldPrice = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/div[3]/span[3]")).getText();
//                //店铺名称
//                sellerName = new String(elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/div[4]/div")).getText().getBytes(), "utf-8").replace("\uE667","");
//                //商品销量
//                sales = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/div[5]/div[2]")).getText().replace("月销 ", "");
//            }
//
//            System.out.println(i+"link==="+link);
//            System.out.println(i+"img==="+img);
//            System.out.println(i+"shopName==="+shopName);
//            System.out.println(i+"price==="+price);
//            System.out.println(i+"oldPrice==="+oldPrice);
//            System.out.println(i+"sellerName==="+sellerName);
//            System.out.println(i+"sales==="+sales);
//            System.out.println(i+"type==="+type);


//    }
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

}
