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
	static String id;  //��ȡ���Ķ���id��Ϊ�˷������������
	static String appid;
	String username="daize@testbird.com";
	String pwd="123456";

	/*ĳЩ������Ҫrow_id�����������ȡ����������app�б���棬����Ҫ������Ч����д�ɾ�̬������*/
	public String getRow_id(){   
	       String row_id=driver.findElement(By.className("odd")).getAttribute("id");     //����classֵ��ȡ��һ��app��rowIDֵ
	  	   return row_id;
	  }
	
	@BeforeClass
	/*��½�����Ӧ������ҳ��*/
	public void setUp() throws InterruptedException, IOException{  
    PubModule.openlink(driver);  
    PubModule.login(username,pwd,driver);
    driver.findElement(By.id("id_menu_1")).click();//����һ���ҵ�Ӧ��button����ֹ����
    driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();
 }
	
	/*�õ�appid����Ϊ����sdk����������*/
	@Test (priority = 1,groups="getAppid") 
	public void getAppID(){
		   driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[1]")).click(); //���Ӧ����Ϣ
	  	   appid= driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/table/tbody/tr[1]/td[2]")).getText();   //�����������xpath��λ��appid����ȡ��ֵ
	  	   System.out.println("appID�ǣ�"+appid);
	  }
	
	@Parameters({"newappname"})
	@Test (priority = 2) 
	/*�༭Ӧ����*/
    public void editAppName(String newappname) throws InterruptedException, IOException{
	   driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[1]")).click(); //���Ӧ����Ϣ
   	   Thread.sleep(1000);
   	   driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/table/tbody/tr[2]/td[3]/a")).click();
   	   Thread.sleep(1000);
   	   driver.findElement(By.id("id_name")).clear();
   	   driver.findElement(By.id("id_name")).sendKeys(newappname);
   	   driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click();   
   	   Thread.sleep(2000);
       assertTrue(PubModule.verifyEquals(newappname, driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/table/tbody/tr[2]/td[2]")).getText(),"�༭Ӧ�����ƣ�ʧ�ܣ�����",driver));
       System.out.println("�༭Ӧ�����ƣ�ִ�����");
	}
	
	@Test (priority = 3)
	/*�༭Ԥ��*/
	public void editAlert() throws InterruptedException, IOException{	
		   Thread.sleep(2000);
		   driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[1]")).click(); //���Ӧ����Ϣ  
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
	      assertTrue(PubModule.verifyEquals("daize@testbird.com", driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/table/tbody/tr[3]/td[2]/table/tbody/tr[5]/td[2]")).getText(),"�༭Ԥ����ʧ�ܣ�����",driver));
	   	}else{ //test�������༭��ֱ�ӱ���
	   	   driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click();   		
	   	}   	      	   
	      System.out.println("�༭Ԥ����ִ�����");
	    }
	
	@Parameters({"urlName","storeUrl"})
    @Test (priority = 4)
	/*�̵�*/
    public void storeSet(String urlName,String storeUrl) throws InterruptedException, IOException{
	    Thread.sleep(2000);
   	    driver.findElement(By.cssSelector("a.btn.btn-app.btn-sm.btn-primary.no-hover")).click();
        driver.findElement(By.cssSelector("i.ace-icon.fa.fa-plus.bigger-130")).click();
        driver.findElement(By.id("id_name")).sendKeys(urlName);
        driver.findElement(By.id("id_url")).sendKeys(storeUrl);
        driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click(); 
        Thread.sleep(2000);
        assertTrue(PubModule.verifyEquals(urlName, driver.findElements(By.className("action-buttons")).get(1).getText(),"�������ص�ַ��ʧ�ܣ�����",driver));
        System.out.println("����̵����ص�ַ��ִ�����");
    }
	
	@Parameters({"eventName","eventId","postbackUrl"})
	@Test (priority = 5)
    /*�¼�����*/
    public void eventSet(String eventName,String eventId,String postbackUrl) throws InterruptedException{
	 Thread.sleep(2000);
	 driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[3]")).click();  //����¼�
   	 Thread.sleep(2000);
   	 //driver.findElement(By.linkText("����¼�")).click();
   	 driver.findElement(By.xpath(".//*[@id='page-content']/div[2]/div/div[1]/div/div/a")).click();  //����¼�
   	 Thread.sleep(2000);
   	 driver.findElement(By.cssSelector("input.required.textinput.textInput.form-control")).sendKeys(eventName);
   	 List<WebElement>eventKey = driver.findElements(By.id("id_key"));
   	 eventKey.get(1).sendKeys(eventId);
   	 List<WebElement>eventUrl = driver.findElements(By.id("id_postback_url"));
   	 eventUrl.get(1).sendKeys(postbackUrl);
   	 driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();
   	 Thread.sleep(2000);
   	 assertTrue(PubModule.verifyEquals(1, driver.findElements(By.cssSelector("i.ace-icon.bigger-130.fa.fa-trash-o")).size(),"�����Զ����¼���ʧ�ܣ�����",driver));
   	 System.out.println("�����Զ����¼���ִ�����"); 
    }
	
	@Parameters({"adnetworkName","adnetworkId","campaignName"})
	@Test (priority = 6)
    /*����Զ���������*/
    public void addCustomNet(String adnetworkName,String adnetworkId,String campaignName) throws InterruptedException{
	 Thread.sleep(1000);
   	 driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[4]")).click(); 	
   	 Thread.sleep(2000);
   	 driver.findElement(By.xpath(".//*[@id='id_btn_adnetwork_create']")).click(); 	//�������
   	 Thread.sleep(2000);
   	 driver.findElement(By.xpath(".//*[@id='id-adnetwork-selection-div']/button")).click(); 	
   	 driver.findElement(By.id("id_customized_name")).sendKeys(adnetworkName);
   	 driver.findElement(By.id("id_customized_key")).sendKeys(adnetworkId);
   	 driver.findElement(By.id("id_campaign_name")).sendKeys(campaignName);
   	 if(PubModule.enFlag==1){  //��������ѡ�������б�
   	     Select selectURL = new Select(driver.findElement(By.id("id_download_url"))); 
   	     Thread.sleep(2000);
         selectURL.selectByVisibleText("Google PlayĬ������");
   	}
   	 else{  //test����ѡ���̵������б�(����)
   	      driver.findElement(By.xpath(".//*[@id='id_download_url_chosen']/a/span")).click();
   	      driver.findElement(By.xpath(".//*[@id='id_download_url_chosen']/div/ul/li[2]")).click();
   	}  
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();
        Thread.sleep(2000);
        assertTrue(PubModule.verifyEquals(adnetworkName, driver.findElements(By.cssSelector("td.datatables-master-column")).get(0).getText(),"����Զ��������磺ʧ�ܣ�����",driver));   
        System.out.println("����Զ��������磺�ɹ�");
    }
	
	@Parameters({"installPbUrl","eventPbUrl"})
	@Test (priority = 7,dependsOnMethods="addCustomNet")
    /*�����Զ�������Ļش�*/
 public void custom_postbackSet(String installPbUrl,String eventPbUrl) throws InterruptedException, IOException{
  try{
	 Thread.sleep(2000); 	
   	 //driver.findElement(By.className("blue")).click(); 
     driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();  //�ҳ�����button�������
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
   	 eventRange.selectByVisibleText("����");
   	Thread.sleep(2000);
   	 driver.findElement(By.id("id_normalpostback-0-customized_postback_url")).sendKeys(eventPbUrl);
   	 Thread.sleep(2000);
   	 driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click(); 
 	 Thread.sleep(2000);
     driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();  //�ٴ��ҳ�����button�������
  }catch(Exception e){  //�����������쳣��ˢ��ҳ��������
	 Thread.sleep(1000);
	 driver.navigate().refresh();
	 driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();
	 driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[4]")).click(); 	
     driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();  //�ٴ��ҳ�����button�������
  }
     assertTrue(PubModule.verifyEquals(installPbUrl,driver.findElement(By.id("id_install_full_url")).getText(),"�����Զ���������ش���ʧ�ܣ�����",driver));   
     driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary")).click(); 
     System.out.println("�����Զ���������ش����ɹ�"); 	 

 }
	
	 /*�����������*/
	@Parameters({"campaignName_offi"})
	@Test (priority = 8)
    public void addOfficialNet(String campaignName_offi) throws InterruptedException, IOException{
     driver.findElement(By.xpath(".//*[@id='page-content']/div[1]/div/p/a[4]")).click(); 	
   	 Thread.sleep(2000);
   	 driver.findElement(By.xpath(".//*[@id='id_btn_adnetwork_create']")).click(); 	
   	 Thread.sleep(2000);
   	 if(PubModule.enFlag==1){ 
   		 PubModule.getshot("������ù�����",driver);
   		 System.out.println("��������ѡ�������б�");
   		 Thread.sleep(2000);
   		 Select adnet = new Select(driver.findElement(By.id("id_template")));//ֻ������prod
   		 Thread.sleep(2000);
       	 adnet.selectByVisibleText("Avazu");
       	 driver.findElement(By.id("id_campaign_name")).sendKeys(campaignName_offi);
       	 Select downloadUrl = new Select(driver.findElement(By.id("id_download_url")));//ֻ������prod
       	 downloadUrl.selectByVisibleText("Google PlayĬ������");
       	 }
   	 else{
   		    System.out.println("test����ѡ�������б�");
           	 driver.findElement(By.xpath(".//*[@id='id_template_chosen']/a/div/b")).click();
           	 driver.findElement(By.xpath(".//*[@id='id_template_chosen']/div/ul/li[3]")).click();
           	 driver.findElement(By.id("id_campaign_name")).sendKeys(campaignName_offi);
           	 driver.findElement(By.xpath(".//*[@id='id_download_url_chosen']/a/div/b")).click(); 
           	 driver.findElement(By.xpath(".//*[@id='id_download_url_chosen']/div/ul/li[2]")).click();  
       	 }   	
     	Thread.sleep(2000);
        driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();
        Thread.sleep(2000);
        assertTrue(PubModule.verifyEquals(campaignName_offi, driver.findElements(By.className("action-buttons")).get(0).getText(),"������ù�����磺ʧ�ܣ�����",driver));   
        System.out.println("������ù�����磺�ɹ�");
    }
	
	@Test (priority = 9,dependsOnMethods="addOfficialNet")
    /*������������Ļش��������ڴ�����������*/
    public void official_postbackSet() throws InterruptedException{
   Thread.sleep(2000);   
   try{
     driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();
   	 Thread.sleep(1000);   
   	 Select installRange=new Select(driver.findElement(By.id("id_install_data_range")));
   	 installRange.selectByVisibleText("���ش�");
   	 Thread.sleep(2000);
   	 driver.findElement(By.cssSelector("button.btn.btn-sm.btn-primary ")).click();       
   	 System.out.println("�������ù������ش����ɹ�");
   	 }catch(Exception e){
   		 System.out.println("�������ù������ش���ʧ�ܣ�����");
   	 }
    } 
	
	/*��õ�һ���������Ķ���*/ 
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
          System.out.println("�����ǣ�"+id);
          assertFalse(PubModule.verifyEquals(null, id,"",driver));         	   
       	  System.out.println("��ȡ��һ���������Ķ���1���ɹ�");
       	 
     }
      
    /*ɾ��app
    public void deleteApp(){
	  driver.findElement(By.className("menu-text")).click();//����ҵ�Ӧ��
      driver.findElement(By.linkText("ɾ��")).click();//ɾ����һ��app
      driver.findElement(By.cssSelector("button.btn.btn-danger")).click();//ȷ��
      System.out.println("ɾ��app�ɹ�");
  }
*/

  @AfterClass
	public void tearDown(){
		driver.quit();
	}
}
