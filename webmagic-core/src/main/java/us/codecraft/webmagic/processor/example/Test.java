package us.codecraft.webmagic.processor.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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

    public static void main(String[] args) throws IOException {
        WebDriver webDriver = Test.getChromeDriver();
        webDriver.get("https://ai.taobao.com/search/index.htm?key=JK&taoke_type=1&pnum=1");
        System.out.println(" Page title is: " +webDriver.getTitle());
        String pageSource = webDriver.getPageSource();
//        System.out.println(pageSource);

        List<WebElement> elements = webDriver.findElements(By.xpath("//*[@id=\"mx_5\"]/ul/li"));
        int rowCout = elements.size();
        for (int i = 1; i < rowCout + 1; i++){
            String link = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a")).getAttribute("href");
//            String img = elements.get(i).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a")).getCssValue("pc-items-item-img img-loaded");
            String img = elements.get(i-1).findElement(By.xpath("//*[@id=\"mx_5\"]/ul/li["+i+"]/a/img")).getAttribute("src");

            System.out.println(i+"link==="+link);
            System.out.println(i+"src==="+img);

        }
//        for (WebElement el: elements) {
////            System.out.println(el.findElement(By.linkText("//*[@id=\"mx_5\"]/ul/li[1]/a")).getText());
////            System.out.println(el.toString());
//            List<WebElement> elements1 = el.findElements(By.xpath("//*[@id=\"mx_5\"]/ul/li/a"));
//            System.out.println(elements1.toArray().toString());
////            System.out.println(text);
//        }
//        webDriver.navigate().to("https://login.taobao.com/");
        //帐号密码登陆方式作废，需滑块验证，较难，无法处理。
//        webDriver.findElement(By.cssSelector("#fm-login-id")).sendKeys("ycabiding@163.com");
//        webDriver.findElement(By.cssSelector("#fm-login-password")).sendKeys("ycabiding@163.com");
//        webDriverBean.setValueByCss(webDriver,"#fm-login-id","ycabiding@163.com");
//        webDriverBean.setValueByCss(webDriver,"#fm-login-password","liqianxi123");
//        webDriverBean.clickByCss(webDriver,"#login-form > div.fm-btn > button");
        //此处为扫二维码登陆，需要人为介入，否则系统判定为机器操作，直接拦截
        //debug执行代码，扫完二维码后直接放行
//        ((JavascriptExecutor) webDriver).executeScript("document.querySelector(\"#login > div.corner-icon-view.view-type-qrcode > i\").click()", args);
        //此处睡眠三十秒
//        baseSleep(30);
//        WebDriver.Options manage = webDriver.manage();
//        Set<Cookie> cookies = manage.getCookies();
//        for (Cookie cookie : cookies) {
//            System.out.println("cookie = " + cookie);
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

}
