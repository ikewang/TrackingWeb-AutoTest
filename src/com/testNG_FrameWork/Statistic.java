package com.testNG_FrameWork;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;

@Test(dependsOnGroups={"sendMsg"})
public class Statistic {
	private WebDriver driver=new  FirefoxDriver();
	//WebDriver driver = new PhantomJSDriver();
	String username="daize@testbird.com";
	String pwd="123456";
	
	@BeforeClass
	public void setUp() throws InterruptedException, IOException{
		PubModule.openlink(driver);  
		PubModule.login(username,pwd,driver);
}
	
   /*�򿪵�һ��Ӧ��ͳ��*/
  @Test(priority = 1)
  public void openTo() throws InterruptedException {
	  List<WebElement> statistic= driver.findElements(By.linkText("ͳ�Ʊ���"));
	  System.out.println("��ǰҳ����"+statistic.size()+"��Ӧ��");
	  statistic.get(0).click();	  
	  Thread.sleep(4000);
  }	
 
  /*��ȡ�ſ����һ�е�һ�еĵ��*/
  @Parameters({"sent_count"})
  @Test (priority = 2)
  public void getClick(String send_count) throws InterruptedException{  
	  Thread.sleep(30000);
	  WebElement i=driver.findElement(By.xpath(".//*[@id='overviewOrEventTable']/tbody/tr[1]/td[1]"));
	  String click=  i.getText();
	  System.out.println(click);
	  assertEquals(click,send_count);
  }
  
  /*��ȡ�ſ����һ�еڶ��еļ���*/
  @Parameters({"dev_count"})
  @Test (priority = 3)
  public void getInstall(String dev_count){
	  WebElement i=driver.findElement(By.xpath(".//*[@id='overviewOrEventTable']/tbody/tr[1]/td[2]"));
	  String install=  i.getText();
	  System.out.println(install);
	  assertEquals(install,dev_count);
  }
 
  /*��ȡ�¼����һ�е�һ�е�����*/
  @Parameters({"sent_count"})
  @Test (priority = 4)
  public void getSession(String send_count){
	  driver.findElement(By.xpath(".//*[@id='Tab']/li[2]/a")).click();
	  WebElement i=driver.findElement(By.xpath(".//*[@id='overviewOrEventTable']/tbody/tr[1]/td[1]"));
	  String session=  i.getText();
	  System.out.println(session);
	  assertEquals(session,send_count);
	  System.out.println("-------------------------------");
  }
  
@AfterClass
  public void tearDown(){
  	  driver.quit();
  	}
}



