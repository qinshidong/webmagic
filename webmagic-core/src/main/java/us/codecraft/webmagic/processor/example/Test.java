package us.codecraft.webmagic.processor.example;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Test {

    private static ChromeDriverService service;

    public static WebDriver getChromeDriver(String cookies) throws IOException {
        System.setProperty("webdriver.chrome.driver", "D:/Desktop/chromedriver.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，只要在newFile（）的时候写入你放的路径即可）
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        //options.addArguments("headless"); 加载浏览器的静默模式，使浏览器在后台运行
        //去掉开发者模式
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        if(cookies != null && !"".equals(cookies)){
            options.addArguments("cookies="+"UM_distinctid=1748b267fbf40d-047f38064ecb34-333769-1fa400-1748b267fc0c9c; thw=cn; hng=CN%7Czh-CN%7CCNY%7C156; enc=ygDughRIuVoDdh8HDJU9xftFD6rZXXQRPacrnwP4Xty0oKrzq2OyJwqZ3hvTjj%2BPrArgmXK0P9coRfMooEwBGg%3D%3D; mt=ci=0_0; tracknick=; cna=9MflFywxwXoCAW7ksVTTkbgp; miid=908805321437727521; lLtC1_=1; _m_h5_tk=c4806d72bcf359659191a42c4def1bbd_1601115898523; _m_h5_tk_enc=bed178763dba8042b87ce2694a98860c; xlly_s=1; cookie2=17529db8f8716b9cc79308fe74d55670; t=79329c77a3ca127e54d41af41a97aee9; _tb_token_=e937e5f061eb3; v=0; l=eBEVVMuROz9DVOHGBO5wnurza77OEQRjHsPzaNbMiInca6i5sQYDxNQ4xyWH-dtjgt5jcexyNxVOSR36Sy4_WjkDBeYIOC0eQn9B1e1..; tfstk=cCVGBOcFtRk1nhD3F1G_PCc7g0bRaQRqqSPQTWv2bJ7gAFaS7sDMLLW6WwmgjSpf.; isg=BKKiGassCZKZdRV-Hz7SHkA58ygE86YN7CBDHOw7zpXAv0I51IP2HSgt6f1DtB6l");
        }
        WebDriver derver = new ChromeDriver(options);

//        service = new ChromeDriverService.Builder().usingDriverExecutable(new File("D:/Desktop/chromedriver.exe")).usingAnyFreePort().build();
//        service.start();
        // 创建一个 Chrome 的浏览器实例
        return derver;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //从本地获取cookies
        String cookies = readFileByChars("D://Desktop/cookies1.txt");

        WebDriver webDriver = Test.getChromeDriver(cookies);

        String url = "https://s.click.taobao.com/t?e=m%3D2%26s%3DMHhZXPvtiblw4vFB6t2Z2ueEDrYVVa64Vb0yt%2F5tJWX%2BmkB8Ys0cBa1pAOKD%2FdwChh%2B1tlF6mDX5r45Wg077gW1aRFN3r8%2FX7n%2Fj8bo2oExDUJPUIHN4EtjXjs1HhSd5fghaZJ1GY5nGDF1NzTQoPw%3D%3D&scm=null&pvid=100_172.18.173.199_10893_7331601084065341697&app_pvid=59590_11.88.165.231_545_1601084065333&ptl=floorId:34371;originalFloorId:34371;pvid:100_172.18.173.199_10893_7331601084065341697;app_pvid:59590_11.88.165.231_545_1601084065333&union_lens=lensId%3AOPT%401601084065%400b58a5e7_abd6_174c80c0700_5fae%4001";
        webDriver.get(url);

        //先打开网页再加载cookies
        webDriver.navigate().refresh();

        Thread.sleep(1000);
//        System.out.println(" Page title is: " + webDriver.getTitle()); //页面title

        //睡眠 防反爬取机制拦击
//        Thread.sleep(200);//等待0.2秒
//        String pageSource = webDriver.getPageSource(); //获取页面

        try {
            //有优惠券页面
            webDriver.findElement(By.xpath("//*[@id=\"mx_9\"]/div/div/a/img")).click();
        } catch (Exception e) {
            //没有优惠券页面
        }

        //睡眠1秒 防反爬取机制拦击
        Thread.sleep(200);//等待0.2秒

        //商品名称
        String shopName = webDriver.findElement(By.id("J_Title")).getText();

        String specTitle = webDriver.findElement(By.xpath("//*[@id=\"J_isku\"]/div/dl[1]/dt")).getText();

        List<WebElement> detailImgElement = webDriver.findElements(By.xpath("//*[@id=\"J_DivItemDesc\"]/div/div/img"));

        //总库存
        String stockAll = webDriver.findElement(By.xpath("//*[@id=\"J_SpanStock\"]")).getText();

        if(detailImgElement.size() == 0){
            //登陆淘宝
            loginAiTaoBao(webDriver, url);

            //登陆后重新获取库存
            stockAll = webDriver.findElement(By.xpath("//*[@id=\"J_SpanStock\"]")).getText();
        }

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
       detailImgElement = webDriver.findElements(By.xpath("//*[@id=\"J_DivItemDesc\"]/div/div/img"));
        System.out.println();
        for (int i = 1; i < detailImgElement.size() + 1; i++) {
            String detailImg = detailImgElement.get(i - 1).findElement(By.xpath("//*[@id=\"J_DivItemDesc\"]/div/div/img[" + i + "]")).getAttribute("src");

            //属性列表
            System.out.println("商品详情图片----" + detailImg);
        }

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

    /**
     * 刷新页面
     */
    public static void reflush(WebDriver webDriver) throws InterruptedException {

        WebElement usernameWebElement = webDriver.findElement(By.xpath("//*[@id=\"fm-login-id\"]"));
        WebElement passwordWebElement = webDriver.findElement(By.xpath("//*[@id=\"fm-login-password\"]"));
        usernameWebElement.click();
        Thread.sleep(500);
        usernameWebElement.sendKeys("wuyuan_001");
        Thread.sleep(500);
        passwordWebElement.click();
        Thread.sleep(500);
        passwordWebElement.sendKeys("aB123456.");
        Thread.sleep(500);
        WebElement btnWebElement = webDriver.findElement(By.xpath("//button[@class='fm-button fm-submit password-login']"));

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].click();", btnWebElement);
    }

    public static void loginAiTaoBao(WebDriver webDriver, String redUrl){
        try {
            //有关闭窗口
//            webDriver.findElement(By.xpath("//*[@id=\"sufei-dialog-close\"]")).click();
//            webDriver.findElement(By.xpath("//*[@id=\"sufei-dialog-close\"]")).click();
//            webDriver.findElement(By.xpath("/html/body/div[6]/div[2]/div]")).click();
            webDriver.navigate().to("https://login.taobao.com/");

            //账号密码登陆
            //*[@id="login"]/div[1]/div

            String innerText = webDriver.findElement(By.xpath("//*[@id=\"login\"]/div[1]/div")).getAttribute("innerText");
            System.out.println(innerText);
            if (!"扫码登录更安全".equals(innerText)) {
                webDriver.findElement(By.xpath(" //*[@id=\"login\"]/div[1]/i")).click();
            }
            //刷新页面
            webDriver.navigate().refresh();
            JavascriptExecutor js = (JavascriptExecutor) webDriver;

            Thread.sleep(500);
            //获取账号密码框
            WebElement usernameWebElement = webDriver.findElement(By.xpath("//*[@id=\"fm-login-id\"]"));
            WebElement passwordWebElement = webDriver.findElement(By.xpath("//*[@id=\"fm-login-password\"]"));

            //点击账号文本框
            usernameWebElement.click();
            Thread.sleep(500);
            String usern = "var element=document.getElementById('fm-login-id'); element.value='" + "wuyuan_001" + "';";

            //输入账号
            js.executeScript(usern);

            Thread.sleep(500);

            //点击密码文本框
            passwordWebElement.click();
            String pass = "var element=document.getElementById('fm-login-password'); element.value='" + "aB123456." + "';";

            //输入密码
            js.executeScript(pass);

            Thread.sleep(500);
            WebElement btnWebElement = webDriver.findElement(By.xpath("//button[@class='fm-button fm-submit password-login']"));
            //点击登陆按钮
            js.executeScript("arguments[0].click();", btnWebElement);


//            webDriver.navigate().refresh();
//            webDriver.navigate().refresh();
//            webDriver.navigate().refresh();

//            reflush(webDriver); //重新登
//
//            //滑动登陆
//            WebElement draggable = webDriver.findElement(By.id("nc_1_n1z"));//定位元素
//            Actions bu = new Actions(webDriver); // 声明action对象
//            bu.clickAndHold(draggable).build().perform(); // clickAndHold鼠标左键按下draggable元素不放
//            bu.moveByOffset(380, 2).perform(); // 平行移动鼠标
//            Thread.sleep(200);
//            bu.moveByOffset(400, 2).perform(); // 平行移动鼠标
//            Thread.sleep(200);
//            bu.moveByOffset(420, 2).perform(); // 平行移动鼠标

            Thread.sleep(500);

//            webDriver.switchTo().window(webDriver.getWindowHandles());
//            String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,"t");
//            webDriver.findElement(By.linkText("https://s.click.taobao.com/t?e=m%3D2%26s%3DvJ8OqoaKGGtw4vFB6t2Z2ueEDrYVVa64qYbrUZilZ4UKwPl3T8wu7FHEyeefvB3LvR7NEFlLEsI1sZpe12KEHNdlXPHrc%2BQOwhsKX1LNgs810JKatjRt8HaQEJ4dc9gIyubz7oN6u5OCW2MUr2mWG3EqY%2Bakgpmw&scm=null&pvid=100_172.18.173.199_10834_3791601027385847527&app_pvid=59590_11.1.93.222_518_1601027385843&ptl=floorId:34371;originalFloorId:34371;pvid:100_172.18.173.199_10834_3791601027385847527;app_pvid:59590_11.1.93.222_518_1601027385843&union_lens=lensId%3AOPT%401601027386%400b015dde_b3b9_174c4ab2b04_99aa%4001")).sendKeys(selectLinkOpeninNewTab);

            webDriver.navigate().to(redUrl);
            //读取登陆后的cookies
            WebDriver.Options manage = webDriver.manage();
            Set<Cookie> cookies = manage.getCookies();
            //写入cookies文件
            writeCookies(cookies);
        } catch (Exception e) {
            //没有关闭窗口
            e.printStackTrace();
        }
    }
    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     *
     * @param fileName
     * @return
     */
    public static String readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
//            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            String cookies = "";
            while ((tempchar = reader.read()) != -1) {
                //对于windows下，rn这两个字符在一起时，表示一个换行。
                //但如果这两个字符分开显示时，会换两次行。
                //因此，屏蔽掉r，或者屏蔽n。否则，将会多出很多空行。
                if (((char) tempchar) != 'r') {
                    cookies += (char) tempchar;
                }

            }
            reader.close();
            return cookies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Json中String 字符串转换为List
     *
     * @param jsonStr
     * @param classT
     * @return
     */
    public static List<Object> getListJson(String jsonStr, Class<?> classT) {
        List<Object> list = new ArrayList<Object>();
        JsonArray jsonArray = new Gson().fromJson(jsonStr, JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(new Gson().fromJson(jsonArray.get(i), classT));
        }
        return list;
    }

    /**
     * 写入cookies文件
     *
     * @param cookies
     * @return
     */
    public static void writeCookies(Set<Cookie> cookies) {

//        List<CookiesDto> cookiesDtoList = new ArrayList<>();
        String cookieStr = "";
        for (Cookie cookie : cookies) {
            cookieStr += cookie.getName().toString() + "=" + cookie.getValue().toString() + "; ";
        }

        System.out.println("cookies==========="+cookieStr);
        byte[] sb = cookieStr.substring(0, cookieStr.length() - 2).getBytes();
        try {
            //若是true 在原有的文本上追加写入 false覆盖原有的文本 ，不写默认为false
            FileOutputStream fos = new FileOutputStream("D://Desktop/cookies1.txt", false);
            fos.write(sb);
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.err.println("写入cookies文件失败");
        }
    }
}
