package us.codecraft.webmagic.processor.example;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.File;
import java.io.IOException;
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
        webDriver.get("https://www.taobao.com/");
        System.out.println(" Page title is: " +webDriver.getTitle());
        webDriver.navigate().to("https://login.taobao.com/");
        //帐号密码登陆方式作废，需滑块验证，较难，无法处理。
//        webDriver.findElement(By.cssSelector("#fm-login-id")).sendKeys("ycabiding@163.com");
//        webDriver.findElement(By.cssSelector("#fm-login-password")).sendKeys("ycabiding@163.com");
//        webDriverBean.setValueByCss(webDriver,"#fm-login-id","ycabiding@163.com");
//        webDriverBean.setValueByCss(webDriver,"#fm-login-password","liqianxi123");
//        webDriverBean.clickByCss(webDriver,"#login-form > div.fm-btn > button");
        //此处为扫二维码登陆，需要人为介入，否则系统判定为机器操作，直接拦截
        //debug执行代码，扫完二维码后直接放行
        ((JavascriptExecutor) webDriver).executeScript("document.querySelector(\"#login > div.corner-icon-view.view-type-qrcode > i\").click()", args);
        //此处睡眠三十秒
        baseSleep(30);
        WebDriver.Options manage = webDriver.manage();
        Set<Cookie> cookies = manage.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println("cookie = " + cookie);
        }

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
