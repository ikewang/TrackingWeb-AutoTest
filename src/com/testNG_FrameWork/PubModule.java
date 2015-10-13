package com.testNG_FrameWork;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PubModule {
  public static  int enFlag;
  static String env="prod"; //����ͳһ�޸Ļ���

  public static void openlink(WebDriver driver){   
	  driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);  //40s������ҳ�棬����ʱ
	  if(env=="prod"){
 		  driver.get(" http://analytics.trackingbird.com"); 
 	  }
 	 else{
 		 if(env=="stage"){
			 driver.get("http://stage-ui.trackingbird.com");   			 
		  }  
 		 else{
 			 driver.get("http://192.168.100.195");   			  
 		 }
 	 }
        driver.manage().window().maximize();  //��󻯴���
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);  //60s�ҵ�Ԫ�أ�����ʱ
         if(env=="stage"|env=="prod"){  //�ж���prod/stage����test����       	   
      	    enFlag=1;
      }else{
           enFlag=0;
      }
 }
  
 public static void login(String username,String pwd,WebDriver driver) throws InterruptedException, IOException{
	  Thread.sleep(4000);
	  WebElement user = driver.findElement(By.id("id_email"));
      user.sendKeys(username);
      WebElement psw = driver.findElement(By.id("id_password"));
      psw.sendKeys(pwd);
      WebElement login = driver.findElement(By.id("id_btn_login"));
      login.click();
     // System.out.println(login.getText()); 
     try{
    	 if(env=="prod"){
    		 Assert.assertEquals("��ӭʹ�� analytics.trackingbird.com", driver.getTitle());
    	 }else {
    		 if(env=="stage"){
    			 Assert.assertEquals("��ӭʹ�� stage-ui.trackingbird.com", driver.getTitle());
    		 }
    		 else{
    			 Assert.assertEquals("��ӭʹ�� 192.168.100.195", driver.getTitle());
    		 }
    	 }  	 
    	 System.out.println("��¼�ɹ�~");
     }
     catch(Exception e){	
    	 Thread.sleep(3000);
    	 getshot("��½ʧ��",driver);
    	 System.out.println("��½ʧ�ܣ�");      
     }         
   }
 
  /*��ͼ*/
   public static void getshot(String shotName,WebDriver driver) throws IOException{
	         File srcFile = ((TakesScreenshot)driver). 
             getScreenshotAs(OutputType.FILE); 
        	 FileUtils.copyFile (srcFile,new File("d:\\"+shotName+".png")); 
 }
 
   /*�ҵ�Ԫ���Ͷ��Խ����ʧ�ܾͽ�ͼ*/
	public static boolean IsElementPresent(By element,String success_output,String fail_output,WebDriver driver) throws IOException, InterruptedException {
	    Thread.sleep(2000);    
		try {
           driver.findElement(element);
           System.out.println(success_output);           
           return true;          
       } 
       catch (Exception e) {
       	   System.out.println(fail_output);      
       	   getshot(fail_output,driver);
           return false;
       }
   }
	 /*�ҵ�Ԫ�ص�������ֵ�Ͷ��Խ����ʧ�ܾͽ�ͼ*/
	public static void assertEqualsAndShot(By element, String expect,String success_output,String fail_output,WebDriver driver) throws IOException {
		try{	  
		if(expect==driver.findElement(element).getText()){
	           System.out.println(success_output);           
			   }      	      
			   else {
	       	   System.out.println(fail_output);      
	       	   getshot(fail_output,driver);
	       }
		}catch(Exception e){
			   System.out.println(fail_output);      
	       	   getshot(fail_output,driver);
		}
	   }
	
/*����ʧ�ܺ����ִ�к�������*/
	public static Boolean verifyEquals(Object actual,Object except,String message,WebDriver driver) throws InterruptedException{
		Thread.sleep(2000);
		try{
			assertEquals(actual,except);
			return true;
		}catch(Error e){
			System.out.println(message);
//			e.printStackTrace();
			driver.navigate().refresh();
			driver.findElement(By.cssSelector("i.ace-icon.bigger-130.fa.fa-gear")).click();
			return false;
		}
		
	}
	
	
 public static void find(String cut_xpath,String by,String attr,int location,WebDriver driver){
	StringBuffer xpath =new StringBuffer(cut_xpath);  //����һ����ʱ�ַ����洢ȷ��button��xpath����·��
    String id=driver.findElement(By.className(by)).getAttribute(attr);     //����classֵ��ȡ��һ��app��rowIDֵ
 	System.out.println("����app��id���ڣ�"+id);
    xpath.insert(location,id);   //��row����xpath·������ϳ�һ��������xpath
    System.out.println("����app��xpath���ڣ�"+xpath);
    }
 }
