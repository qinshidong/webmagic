package us.codecraft.webmagic.processor.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class UnitDriver {

    public static void main(String[] args) {

        //实例化虚拟浏览器对象
        WebDriver driver = new HtmlUnitDriver();
        //打开百度首页
        String url = "http://www.baidu.com";
        driver.get(url);
        //定位搜索框元素
        WebElement ele = driver.findElement(By.id("kw"));
        //输入需查询内容
        ele.sendKeys("Cheese");
        ele.submit();

        //获取页面标题
        System.out.println("Page title is :" + driver.getTitle());
        //获取页面url
        System.out.println("Page url is :" + driver.getCurrentUrl());
        //关闭driver
        driver.close();
    }

}