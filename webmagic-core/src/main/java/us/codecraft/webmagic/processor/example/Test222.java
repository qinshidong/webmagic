package us.codecraft.webmagic.processor.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test222 {

    private static ChromeDriverService service;

    public static WebDriver getChromeDriver() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/Google/Chrome/Application/chrome.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，只要在newFile（）的时候写入你放的路径即可）
        service = new ChromeDriverService.Builder().usingDriverExecutable(new File("D:/Desktop/chromedriver.exe")).usingAnyFreePort().build();
        service.start();
        // 创建一个 Chrome 的浏览器实例
        return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
    }

    public static void main(String[] args) throws IOException {
        WebDriver webDriver = Test222.getChromeDriver();
        webDriver.get("https://www.taobao.com/");
        System.out.println(" Page title is: " +webDriver.getTitle());
        webDriver.navigate().to("https://login.taobao.com/");
        //帐号密码登陆方式作废，需滑块验证，较难，无法处理。
//        webDriver.findElement(By.cssSelector("#fm-login-id")).sendKeys("%%%%%@163.com");
//        webDriver.findElement(By.cssSelector("#fm-login-password")).sendKeys("$$$$$$");
//        webDriverBean.setValueByCss(webDriver,"#fm-login-id","%%%%%@163.com");
//        webDriverBean.setValueByCss(webDriver,"#fm-login-password","$$$$$$");
//        webDriverBean.clickByCss(webDriver,"#login-form > div.fm-btn > button");
        //此处为扫二维码登陆，需要人为介入，否则系统判定为机器操作，直接拦截
        //debug执行代码，扫完二维码后直接放行
        ((JavascriptExecutor) webDriver).executeScript("document.querySelector(\"#login > div.corner-icon-view.view-type-qrcode > i\").click()", args);
        //此处睡眠三十秒
        baseSleep(5);
        WebDriver.Options manage = webDriver.manage();
        Set<Cookie> cookies = manage.getCookies();

        List<CookiesDto> cookiesDtoList = new ArrayList<>();
        for (Cookie cookie : cookies) {
            CookiesDto cookiesDto = new CookiesDto();
            cookiesDto.setName(cookie.getName());
            cookiesDto.setValue(cookie.getValue());
            cookiesDtoList.add(cookiesDto);
        }

        String cookJson = JSON.toJSONString(cookiesDtoList);

        byte[] sb = cookJson.getBytes();
        FileOutputStream fos = new FileOutputStream("D://Desktop/cookies1.txt",false);//若是true 在原有的文本上追加写入 false覆盖原有的文本 ，不写默认为false
        fos.write(sb);
        fos.close();

        WebElement search = webDriver.findElement(By.cssSelector("#q"));
        search.sendKeys("iwatch");
        final String submit  = "div[class='search-button']";
        WebElement submitButton = webDriver.findElement(By.cssSelector(submit));
        submitButton.click();
        WebElement element = webDriver.findElement(By.cssSelector("#mainsrp-pager > div > div > div > div.total"));
        String text1 = element.getText();
        String middle = findMiddle(text1, "共", "页").trim();
        int i = Integer.parseInt(middle);
        for (int j = 1; j < i; j++) {
            System.out.println("j = " + j);
            ((JavascriptExecutor) webDriver).executeScript("document.querySelector(\"#mainsrp-pager > div > div > div > div.form > input\").value ='"+j+"'");
            baseSleep(1);
            webDriver.findElement(By.cssSelector("#mainsrp-pager > div > div > div > div.form > span.btn.J_Submit")).click();
            String text = webDriver.findElement(By.cssSelector("#mainsrp-itemlist > div > div")).getText();
            System.out.println("text = " + text);
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

    public static String findMiddle(String origin, String start, String end) {
        String ret = "";
        Pattern pattern = Pattern.compile(start + "(.*?)" + end);
        Matcher matcher = pattern.matcher(origin);
        while (matcher.find()) {
            ret = matcher.group(1);
        }
        return ret;
    }

}
