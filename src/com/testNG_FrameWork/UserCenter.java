package com.testNG_FrameWork;

import org.testng.annotations.*;

import com.selenium.webdriver.Login;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

@Test
public class UserCenter {
	
private WebDriver driver=new FirefoxDriver();
	String username="daize@testbird.com";
	String pwd="123456";
	
	@BeforeClass
public void setUp() throws InterruptedException, IOException{
    PubModule.openlink(driver);  
    PubModule.login(username,pwd,driver);
}
   
 @Test	 (priority = 1)
  /*用户资料编辑*/
 public  void userMsessage() throws InterruptedException{
    driver.findElement(By.cssSelector("b.fa.arrow.fa-angle-down")).click();  //展开账户中心
	driver.findElement(By.id("id_menu_2_1")).click();  //账户资料页面
	driver.findElement(By.linkText("编辑")).click();
	driver.findElement(By.xpath(".//*[@id='id_first_name']")).clear();
	driver.findElement(By.xpath(".//*[@id='id_first_name']")).sendKeys("ze");
	driver.findElement(By.xpath(".//*[@id='id_surname']")).clear();
	driver.findElement(By.xpath(".//*[@id='id_surname']")).sendKeys("dai");	
	driver.findElement(By.xpath(".//*[@id='id_company']")).clear();
	driver.findElement(By.xpath(".//*[@id='id_company']")).sendKeys("TB");
	driver.findElement(By.xpath(".//*[@id='id_website_url']")).clear();
	driver.findElement(By.xpath(".//*[@id='id_website_url']")).sendKeys("http://www.testbird.com");
	if("1".equals(Login.enFlag)){
		Select language = new Select(driver.findElement(By.xpath(".//*[@id='id_language']")));//生产环境适用
		language.selectByValue("zh-cn");
	}else{
		driver.findElement(By.cssSelector("a.chosen-single>span")).click();	//test环境适用
		Thread.sleep(1000);	
		driver.findElement(By.cssSelector("li.active-result.highlighted")).click();	
	}  
		Thread.sleep(2000);	
	    driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();
	    System.out.println("编辑用户资料：成功");
    }

@Test (priority = 2)
 /*测试设备*/
 public void testDeviceSet() throws InterruptedException{
      Thread.sleep(3000);     
      driver.findElement(By.xpath(".//*[@id='id_menu_2_2']")).click();  //测试设备页面       
      Thread.sleep(2000);     
      driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/h1/div/div/div/a")).click(); //添加测试设备
      Thread.sleep(2000);     
      List<WebElement> deviceName= driver.findElements(By.xpath(".//*[@id='id_device_name']"));
      // System.out.println(deviceName.size());
       deviceName.get(1).sendKeys("自动化测试专用demo");     
       List<WebElement> id=driver.findElements(By.xpath(".//*[@id='id_unique_id']"));
      // System.out.println(id.size());
        id.get(1).sendKeys("0bde456e-39fb-4ed9-9d37-a27349475dc8");
   
        Select Platform=new Select(driver.findElement(By.id("id_platform")));
        Platform.selectByVisibleText("ios");
        Thread.sleep(2000); 
        driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();      
        System.out.println("添加测试设备：成功");
        System.out.println("-------------------------------");
    }
   
   @AfterClass
   public void tearDown(){
 		driver.quit();
	}
  }


