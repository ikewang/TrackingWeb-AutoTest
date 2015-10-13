package com.testNG_FrameWork;

import java.io.*;
import org.testng.annotations.*;
import com.testbird.tracking.simulation.mobile.Context;
import com.testbird.tracking.simulation.mobile.click.ClickUtil;
import com.testbird.tracking.simulation.mobile.sdk.LogItemUtil;

@Test(groups={"sendMsg"},dependsOnGroups={"getCampaign"})
public class SendMsg {
    /*	将文件写到本地*/
	@Test(enabled = false)
	public void writeTaskTestClick(String filePath,String fileName,String data){
   	 try{
   	      File file =new File(filePath + fileName);
   	      //if file doesnt exists, then create it
   	      if(!file.exists()){
   	       file.createNewFile();
   	      }
   	      //true = append file
   	      FileWriter fw = new FileWriter(file);
   	      fw.write(data);
   	      fw.close();
   	      System.out.println(fileName+"写入成功!");
   	     }
   	 catch(IOException e){
   	      e.printStackTrace();
   	     }
   }
   
    @Parameters({"filePath","clickFileName","devFileName","dev_count","task_ids","sent_count"})
	@Test(priority=1,enabled = true,groups={"click"})
 public void simulativeClick(String filePath,String clickFileName,String devFileName,String dev_count,String task_ids,String sent_count) throws InterruptedException{
    	String clickUrl;
    	if(PubModule.env=="prod"){
    		clickUrl="http://app.trackingbird.com/";
    	 }else {
    		 if(PubModule.env=="stage"){
    			 clickUrl="http://stage-ui.trackingbird.com/";
    		 }
    		 else{
    			 clickUrl="http://192.168.100.195/";
    		 }
    		}
    	String data = "1="+clickUrl+AppSet.id+"?mac={mac}";
	    if(AppSet.id.length()>0){
	    	writeTaskTestClick(filePath,clickFileName, data);
	    }else{
	    	System.out.println("获取推广活动ID失败!");
	    }
	   
	    //构建模拟参数-click -dev_count 20 -task_ids 1,2,3,4,5 -sent_count 100 -dev_conf G:\workspace\tmpdir\simulation\click\dev.txt -task_conf G:\workspace\tmpdir\simulation\click\task.txt
	   String [] args ={"-click",
	    		         "-dev_count",dev_count,
	    		         "-task_ids",task_ids,
	    		         "-sent_count",sent_count,
	    		         "-dev_conf",filePath+devFileName,
	    		         "-task_conf",filePath+clickFileName};
	   Context.setSimulatorFunc("click");
       TestbirdUtil.getSimulateTaskOptions(args);
       Context.checkTaskParam();
       ClickUtil.simulateClick();
        }

    @Parameters({"filePath","sdkFileName","devFileName","dev_count","task_ids","sent_count","server","event_id"})
	@Test(priority=2,dependsOnGroups={"getAppid","click"})
	public void simulativeSDK(String filePath,String sdkFileName,String devFileName,String dev_count,String task_ids,String sent_count,String event_id){
        String server;
    	if(PubModule.env=="prod"){
    		server="http://app.trackingbird.com:5140/log";
    	 }else {
    		 if(PubModule.env=="stage"){
    			 server="http://stage-ui.trackingbird.com:5140/log";
    		 }
    		 else{
    			 server="http://192.168.100.250:5140/log/";
    		 }
    		} 	
    	String data = "1={\"app_id\":\""+AppSet.appid+"\",\"store_id\":\""+"googleplay"+"\", \"event_id\":\""+event_id+"\", \"parameters\":\"{"+"\\\\\\"+"\"item"+"\\\\\\"+"\":"+"\\\\\\"+"\"sh"+"\\\\\\"+"\"}\"}";
    	System.out.println(data);    
	    if(AppSet.appid.length()>0&&"googleplay".length()>0&&event_id.length()>0){
	    	writeTaskTestClick(filePath,sdkFileName,data);
	    }else{
	    	System.out.println("获取ID失败!");
	    }
	    
	    //构建模拟参数-sdk -dev_count 20 -task_ids 1,2 -sent_count 100 -dev_conf G:\workspace\tmpdir\simulation\sdk\dev.txt -task_conf G:\workspace\tmpdir\simulation\sdk\task.txt -server http://192.168.100.250:5140/log
	    String [] args ={"-sdk",
	    		         "-dev_count",dev_count,
	    		         "-task_ids",task_ids,
	    		         "-sent_count",sent_count,
	    		         "-dev_conf",filePath+devFileName,
	    		         "-task_conf",filePath+sdkFileName,
	    		         "-server",server};
	    Context.setSimulatorFunc("sdk");
	    TestbirdUtil.getSimulateTaskOptions(args);
        Context.checkTaskParam();
        LogItemUtil.simulatorSDK();
    }
}
