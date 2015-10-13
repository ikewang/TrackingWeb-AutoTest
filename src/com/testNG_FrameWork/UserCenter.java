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
  /*�û����ϱ༭*/
 public  void userMsessage() throws InterruptedException{
    driver.findElement(By.cssSelector("b.fa.arrow.fa-angle-down")).click();  //չ���˻�����
	driver.findElement(By.id("id_menu_2_1")).click();  //�˻�����ҳ��
	driver.findElement(By.linkText("�༭")).click();
	driver.findElement(By.xpath(".//*[@id='id_first_name']")).clear();
	driver.findElement(By.xpath(".//*[@id='id_first_name']")).sendKeys("ze");
	driver.findElement(By.xpath(".//*[@id='id_surname']")).clear();
	driver.findElement(By.xpath(".//*[@id='id_surname']")).sendKeys("dai");	
	driver.findElement(By.xpath(".//*[@id='id_company']")).clear();
	driver.findElement(By.xpath(".//*[@id='id_company']")).sendKeys("TB");
	driver.findElement(By.xpath(".//*[@id='id_website_url']")).clear();
	driver.findElement(By.xpath(".//*[@id='id_website_url']")).sendKeys("http://www.testbird.com");
	if("1".equals(Login.enFlag)){
		Select language = new Select(driver.findElement(By.xpath(".//*[@id='id_language']")));//������������
		language.selectByValue("zh-cn");
	}else{
		driver.findElement(By.cssSelector("a.chosen-single>span")).click();	//test��������
		Thread.sleep(1000);	
		driver.findElement(By.cssSelector("li.active-result.highlighted")).click();	
	}  
		Thread.sleep(2000);	
	    driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();
	    System.out.println("�༭�û����ϣ��ɹ�");
    }

@Test (priority = 2)
 /*�����豸*/
 public void testDeviceSet() throws InterruptedException{
      Thread.sleep(3000);     
      driver.findElement(By.xpath(".//*[@id='id_menu_2_2']")).click();  //�����豸ҳ��       
      Thread.sleep(2000);     
      driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/h1/div/div/div/a")).click(); //��Ӳ����豸
      Thread.sleep(2000);     
      List<WebElement> deviceName= driver.findElements(By.xpath(".//*[@id='id_device_name']"));
      // System.out.println(deviceName.size());
       deviceName.get(1).sendKeys("�Զ�������ר��demo");     
       List<WebElement> id=driver.findElements(By.xpath(".//*[@id='id_unique_id']"));
      // System.out.println(id.size());
        id.get(1).sendKeys("0bde456e-39fb-4ed9-9d37-a27349475dc8");
   
        Select Platform=new Select(driver.findElement(By.id("id_platform")));
        Platform.selectByVisibleText("ios");
        Thread.sleep(2000); 
        driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();      
        System.out.println("��Ӳ����豸���ɹ�");
        System.out.println("-------------------------------");
    }
   
   @AfterClass
   public void tearDown(){
 		driver.quit();
	}
  }


