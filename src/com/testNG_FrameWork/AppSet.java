package com.testNG_FrameWork;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.*;

import com.testNG_FrameWork.PubModule;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@Test(dependsOnGroups={"createApp"})
public class AppSet {
	 WebDriver driver=new FirefoxDriver();
//	private WebDriver driver=new PhantomJSDriver();
	static String id;  //获取到的短链id，为了方便其它类调用
	static String appid;
	String username="daize@testbird.com";
	String pwd="123456";

	/*某些场合需要row_id，可在这里获取（仅限于在app列表界面，若需要长久有效，请写成静态参数）*/
	public String getRow_id(){   
	       String row_id=driver.findElement(By.className("odd")).getAttribute("id");     //根据class值获取第一个app的rowID值
	  	   return row_id;
	  }
	
	@BeforeClass
	/*登陆后进入应用设置页面*/
	public void setUp() throws InterruptedException, IOException{  
    PubModule.openlink(driver);  
    PubModule.login(username,pwd,driver);
    driver.findElement(By.id("id_menu_1")).click();//多点击一次我的应用button，防止出错
    driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();
 }
	
	/*拿到appid，作为发送sdk的依赖条件*/
	@Test (priority = 1,groups="getAppid") 
	public void getAppID(){
		   driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[1]")).click(); //点击应用信息
	  	   appid= driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/table/tbody/tr[1]/td[2]")).getText();   //用这个完整的xpath定位到appid并获取其值
	  	   System.out.println("appID是："+appid);
	  }
	
	@Parameters({"newappname"})
	@Test (priority = 2) 
	/*编辑应用名*/
    public void editAppName(String newappname) throws InterruptedException, IOException{
	   driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[1]")).click(); //点击应用信息
   	   Thread.sleep(1000);
   	   driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/table/tbody/tr[2]/td[3]/a")).click();
   	   Thread.sleep(1000);
   	   driver.findElement(By.id("id_name")).clear();
   	   driver.findElement(By.id("id_name")).sendKeys(newappname);
   	   driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click();   
   	   Thread.sleep(2000);
       assertTrue(PubModule.verifyEquals(newappname, driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/table/tbody/tr[2]/td[2]")).getText(),"编辑应用名称：失败！！！",driver));
       System.out.println("编辑应用名称：执行完毕");
	}
	
	@Test (priority = 3)
	/*编辑预警*/
	public void editAlert() throws InterruptedException, IOException{	
		   Thread.sleep(2000);
		   driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[1]")).click(); //点击应用信息  
	   	   driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/table/tbody/tr[3]/td[3]/a")).click();
	   	if(PubModule.enFlag==1){
	   	   Select time=new Select(driver.findElement(By.id("id_alert_check_hours")));
	   	    time.selectByValue("1");
	   	   driver.findElement(By.id("id_alert_threshold_high")).clear();
	   	   driver.findElement(By.id("id_alert_threshold_high")).sendKeys("10");
	   	   driver.findElement(By.id("id_alert_threshold_low")).clear();
	  	   driver.findElement(By.id("id_alert_threshold_low")).sendKeys("9");
	       driver.findElement(By.id("id_alert_mail_address")).clear();
	  	   driver.findElement(By.id("id_alert_mail_address")).sendKeys("daize@testbird.com");  
	  	   driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click();   
	      assertTrue(PubModule.verifyEquals("daize@testbird.com", driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/table/tbody/tr[3]/td[2]/table/tbody/tr[5]/td[2]")).getText(),"编辑预警：失败！！！",driver));
	   	}else{ //test环境不编辑，直接保存
	   	   driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click();   		
	   	}   	      	   
	      System.out.println("编辑预警：执行完毕");
	    }
	
	@Parameters({"urlName","storeUrl"})
    @Test (priority = 4)
	/*商店*/
    public void storeSet(String urlName,String storeUrl) throws InterruptedException, IOException{
	    Thread.sleep(2000);
   	    driver.findElement(By.cssSelector("a.btn.btn-app.btn-sm.btn-primary.no-hover")).click();
        driver.findElement(By.cssSelector("i.ace-icon.fa.fa-plus.bigger-130")).click();
        driver.findElement(By.id("id_name")).sendKeys(urlName);
        driver.findElement(By.id("id_url")).sendKeys(storeUrl);
        driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click(); 
        Thread.sleep(2000);
        assertTrue(PubModule.verifyEquals(urlName, driver.findElements(By.className("action-buttons")).get(1).getText(),"新增下载地址：失败！！！",driver));
        System.out.println("添加商店下载地址：执行完毕");
    }
	
	@Parameters({"eventName","eventId","postbackUrl"})
	@Test (priority = 5)
    /*事件设置*/
    public void eventSet(String eventName,String eventId,String postbackUrl) throws InterruptedException{
	 Thread.sleep(2000);
	 driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[3]")).click();  //点击事件
   	 Thread.sleep(2000);
   	 //driver.findElement(By.linkText("添加事件")).click();
   	 driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/div[1]/div/div/a")).click();  //添加事件
   	 Thread.sleep(2000);
   	 driver.findElement(By.cssSelector("input.required.textinput.textInput.form-control")).sendKeys(eventName);
   	 List<WebElement>eventKey = driver.findElements(By.id("id_key"));
   	 eventKey.get(1).sendKeys(eventId);
   	 List<WebElement>eventUrl = driver.findElements(By.id("id_postback_url"));
   	 eventUrl.get(1).sendKeys(postbackUrl);
   	 driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();
   	 Thread.sleep(2000);
   	 assertTrue(PubModule.verifyEquals(1, driver.findElements(By.cssSelector("i.ace-icon.bigger-130.fa.fa-trash-o")).size(),"新增自定义事件：失败！！！",driver));
   	 System.out.println("新增自定义事件：执行完毕"); 
    }
	
	@Parameters({"adnetworkName","adnetworkId","campaignName"})
	@Test (priority = 6)
    /*添加自定义广告网络*/
    public void addCustomNet(String adnetworkName,String adnetworkId,String campaignName) throws InterruptedException{
	 Thread.sleep(1000);
   	 driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[4]")).click(); 	
   	 Thread.sleep(2000);
   	 driver.findElement(By.xpath(".//*[@id='id_btn_adnetwork_create']")).click(); 	//添加网络
   	 Thread.sleep(2000);
   	 driver.findElement(By.xpath(".//*[@id='id-adnetwork-selection-div']/button")).click(); 	
   	 driver.findElement(By.id("id_customized_name")).sendKeys(adnetworkName);
   	 driver.findElement(By.id("id_customized_key")).sendKeys(adnetworkId);
   	 driver.findElement(By.id("id_campaign_name")).sendKeys(campaignName);
   	 if(PubModule.enFlag==1){  //生产环境选择下拉列表
   	     Select selectURL = new Select(driver.findElement(By.id("id_download_url"))); 
   	     Thread.sleep(2000);
         selectURL.selectByVisibleText("Google Play默认下载");
   	}
   	 else{  //test环境选择商店下拉列表(废弃)
   	      driver.findElement(By.xpath(".//*[@id='id_download_url_chosen']/a/span")).click();
   	      driver.findElement(By.xpath(".//*[@id='id_download_url_chosen']/div/ul/li[2]")).click();
   	}  
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();
        Thread.sleep(2000);
        assertTrue(PubModule.verifyEquals(adnetworkName, driver.findElements(By.cssSelector("td.datatables-master-column")).get(0).getText(),"添加自定义广告网络：失败！！！",driver));   
        System.out.println("添加自定义广告网络：成功");
    }
	
	@Parameters({"installPbUrl","eventPbUrl"})
	@Test (priority = 7,dependsOnMethods="addCustomNet")
    /*配置自定义网络的回传*/
 public void custom_postbackSet(String installPbUrl,String eventPbUrl) throws InterruptedException, IOException{
  try{
	 Thread.sleep(2000); 	
   	 //driver.findElement(By.className("blue")).click(); 
     driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();  //找出设置button，并点击
     Thread.sleep(3000);
   	 driver.findElement(By.id("id_install_full_url")).sendKeys(installPbUrl);
   	 Select pbType=new Select(driver.findElement(By.id("id_install_data_range")));
   	 pbType.selectByValue("all-sys");
   	 Thread.sleep(3000);
   	 driver.findElement(By.xpath(".//*[@id='id_postbackConfig_Tabs']/li[2]/a")).click();
   	 Thread.sleep(3000);
   	 Select installRange=new Select(driver.findElement(By.id("id_normal_data_range")));
   	 installRange.selectByValue("all-sys");
   	 Thread.sleep(2000);
   	 driver.findElement(By.id("append_item_button")).click();
   	 Select eventRange=new Select(driver.findElement(By.id("id_normalpostback-0-trigger_event")));
   	 eventRange.selectByVisibleText("启动");
   	Thread.sleep(2000);
   	 driver.findElement(By.id("id_normalpostback-0-customized_postback_url")).sendKeys(eventPbUrl);
   	 Thread.sleep(2000);
   	 driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click(); 
 	 Thread.sleep(2000);
     driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();  //再次找出设置button，并点击
  }catch(Exception e){  //发现上面有异常则刷新页面重新来
	 Thread.sleep(1000);
	 driver.navigate().refresh();
	 driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();
	 driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[4]")).click(); 	
     driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();  //再次找出设置button，并点击
  }
     assertTrue(PubModule.verifyEquals(installPbUrl,driver.findElement(By.id("id_install_full_url")).getText(),"配置自定义广告网络回传：失败！！！",driver));   
     driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click(); 
     System.out.println("配置自定义广告网络回传：成功"); 	 

 }
	
	 /*添加内置网络*/
	@Parameters({"campaignName_offi"})
	@Test (priority = 8)
    public void addOfficialNet(String campaignName_offi) throws InterruptedException, IOException{
     driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[4]")).click(); 	
   	 Thread.sleep(2000);
   	 driver.findElement(By.xpath(".//*[@id='id_btn_adnetwork_create']")).click(); 	
   	 Thread.sleep(2000);
   	 if(PubModule.enFlag==1){ 
   		 PubModule.getshot("添加内置广告界面",driver);
   		 System.out.println("生产环境选择下拉列表");
   		 Thread.sleep(2000);
   		 Select adnet = new Select(driver.findElement(By.id("id_template")));//只适用于prod
   		 Thread.sleep(2000);
       	 adnet.selectByVisibleText("Avazu");
       	 driver.findElement(By.id("id_campaign_name")).sendKeys(campaignName_offi);
       	 Select downloadUrl = new Select(driver.findElement(By.id("id_download_url")));//只适用于prod
       	 downloadUrl.selectByVisibleText("Google Play默认下载");
       	 }
   	 else{
   		    System.out.println("test环境选择下拉列表");
           	 driver.findElement(By.xpath(".//*[@id='id_template_chosen']/a/div/b")).click();
           	 driver.findElement(By.xpath(".//*[@id='id_template_chosen']/div/ul/li[3]")).click();
           	 driver.findElement(By.id("id_campaign_name")).sendKeys(campaignName_offi);
           	 driver.findElement(By.xpath(".//*[@id='id_download_url_chosen']/a/div/b")).click(); 
           	 driver.findElement(By.xpath(".//*[@id='id_download_url_chosen']/div/ul/li[2]")).click();  
       	 }   	
     	Thread.sleep(2000);
        driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();
        Thread.sleep(2000);
        assertTrue(PubModule.verifyEquals(campaignName_offi, driver.findElements(By.className("action-buttons")).get(0).getText(),"添加内置广告网络：失败！！！",driver));   
        System.out.println("添加内置广告网络：成功");
    }
	
	@Test (priority = 9,dependsOnMethods="addOfficialNet")
    /*配置内置网络的回传，依赖于创建内置网络*/
    public void official_postbackSet() throws InterruptedException{
   Thread.sleep(2000);   
   try{
     driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();
   	 Thread.sleep(1000);   
   	 Select installRange=new Select(driver.findElement(By.id("id_install_data_range")));
   	 installRange.selectByVisibleText("不回传");
   	 Thread.sleep(2000);
   	 driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();       
   	 System.out.println("配置内置广告网络回传：成功");
   	 }catch(Exception e){
   		 System.out.println("配置内置广告网络回传：失败！！！");
   	 }
    } 
	
	/*获得第一个广告网络的短链*/ 
     @Test (priority = 10,groups={"getCampaign"})
     public  void getCampaign() throws InterruptedException{
    	 Thread.sleep(1000); 
    	 driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[4]")).click(); 	
       	 Thread.sleep(2000);   
       	 driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-edit")).click();
       	 Thread.sleep(1000);   
         WebElement campaign=driver.findElement(By.id("id_key_display")); 	
          id= campaign.getAttribute("value"); 
          driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click(); 
          System.out.println("短链是："+id);
          assertFalse(PubModule.verifyEquals(null, id,"",driver));         	   
       	  System.out.println("获取第一个广告网络的短链1：成功");
       	 
     }
      
    /*删除app
    public void deleteApp(){
	  driver.findElement(By.className("menu-text")).click();//点击我的应用
      driver.findElement(By.linkText("删除")).click();//删除第一个app
      driver.findElement(By.cssSelector("button.btn.btn-danger")).click();//确定
      System.out.println("删除app成功");
  }
*/

  @AfterClass
	public void tearDown(){
		driver.quit();
	}
}
