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
  static String env="prod"; //这里统一修改环境

  public static void openlink(WebDriver driver){   
	  driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);  //40s加载完页面，否则超时
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
        driver.manage().window().maximize();  //最大化窗口
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);  //60s找到元素，否则超时
         if(env=="stage"|env=="prod"){  //判断是prod/stage还是test环境       	   
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
    		 Assert.assertEquals("欢迎使用 analytics.trackingbird.com", driver.getTitle());
    	 }else {
    		 if(env=="stage"){
    			 Assert.assertEquals("欢迎使用 stage-ui.trackingbird.com", driver.getTitle());
    		 }
    		 else{
    			 Assert.assertEquals("欢迎使用 192.168.100.195", driver.getTitle());
    		 }
    	 }  	 
    	 System.out.println("登录成功~");
     }
     catch(Exception e){	
    	 Thread.sleep(3000);
    	 getshot("登陆失败",driver);
    	 System.out.println("登陆失败！");      
     }         
   }
 
  /*截图*/
   public static void getshot(String shotName,WebDriver driver) throws IOException{
	         File srcFile = ((TakesScreenshot)driver). 
             getScreenshotAs(OutputType.FILE); 
        	 FileUtils.copyFile (srcFile,new File("d:\\"+shotName+".png")); 
 }
 
   /*找到元素型断言结果，失败就截图*/
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
	 /*找到元素等于期望值型断言结果，失败就截图*/
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
	
/*断言失败后继续执行后续代码*/
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
	StringBuffer xpath =new StringBuffer(cut_xpath);  //定义一个临时字符串存储确定button的xpath部分路径
    String id=driver.findElement(By.className(by)).getAttribute(attr);     //根据class值获取第一个app的rowID值
 	System.out.println("创建app的id等于："+id);
    xpath.insert(location,id);   //将row插入xpath路径，组合成一个完整的xpath
    System.out.println("创建app的xpath等于："+xpath);
    }
 }
