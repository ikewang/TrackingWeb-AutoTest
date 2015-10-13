package com.testNG_FrameWork;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.*;

import com.testNG_FrameWork.PubModule;

//import java.io.File;
import java.io.IOException;
import java.util.List;

//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;

@Test(groups={"createApp"})
public class CreateApp {
	//System.setProperty("phantomjs.binary.path", "C:/Users/Administrator/Desktop/study/phantomjs-1.9.7-windows/phantomjs.exe");//�ѽ�������ӵ�ϵͳ��������������˲���
	//private WebDriver driver=new PhantomJSDriver();
	private   WebDriver driver=new FirefoxDriver();
	static String row_id;
	String username="daize@testbird.com";
	String pwd="123456";

	@BeforeClass
	public void setUp() throws InterruptedException, IOException{
		PubModule.openlink(driver);  
	    PubModule.login(username,pwd,driver);	
	}
	
	/*����Test����֮ǰ���ȴ���app*/
	@Parameters({"appname","storeappid","downloadurl"})	
    @Test (priority = 1) 
   public void createApp(String appname,String storeappid,String downloadurl) throws InterruptedException, IOException{			
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/h1/div/div/div/a")).click();   
        Thread.sleep(2000);
	    List<WebElement> appnames = driver.findElements(By.name("name"));     //ȡ����ǰҳ��name����ֵ��Ϊname�Ķ���ͬʱֻ�Եڶ����������
	   // System.out.println("��ǰҳ����"+appnames.size()+"�����������");
	    appnames.get(1).sendKeys(appname);          
	    if(PubModule.enFlag==1){  //��������ѡ���̵������б�
	   	    System.out.println("�ж�Ϊprod��stage������enFlag="+PubModule.enFlag);
	    	Thread.sleep(2000);
	   	    Select select = new Select(driver.findElement(By.id("id_store_template")));  
	        select.selectByValue("1");   //1Ϊ�ȸ��̵꣬4Ϊappstore
	    }
	    else{   //test����ѡ���̵������б�
	        System.out.println("�ж�Ϊtest������enFlag="+PubModule.enFlag);
	        WebElement chose = driver.findElement(By.xpath(".//*[@id='id_store_template_chosen']/a/span"));
	        chose.click();
	        WebElement chose1 = driver.findElement(By.xpath(".//*[@id='id_store_template_chosen']/div/ul/li[1]"));  //1Ϊ�ȸ��̵꣬4Ϊappstore
	        chose1.click();          
	    }       
	    driver.findElement(By.xpath(".//*[@id='id_store_app_id']")).sendKeys(storeappid);        
	    driver.findElement(By.name("download_url_address")).sendKeys(downloadurl);
	    Thread.sleep(3000);
	    driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click();    
	    assertTrue(PubModule.IsElementPresent(By.className("center"),"����app�ɹ�","����appʧ��",driver)); //����
	    driver.findElement(By.xpath(".//*[@id='id_modal_form_container_slave']/div/div[2]/button")).click();   //������
	    Thread.sleep(2000);   
	   }	
	 
  @AfterClass
  public void tearDown(){
		driver.quit();
	} 
}
