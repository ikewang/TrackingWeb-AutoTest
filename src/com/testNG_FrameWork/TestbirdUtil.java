
package com.testNG_FrameWork;
import com.testbird.tracking.simulation.mobile.Context;


public class TestbirdUtil {
	 /**
     * 处理模拟任务信息的参数
     */
    public static void getSimulateTaskOptions(String[] args) {
        try {
            for (int i = 0; i < args.length; ++i) {
                String arg = args[i];
                switch (arg) {
                    case "-dev_count":
                        Context.setDevCount(args[++i]);
                        break;
                    case "-task_ids":
                        Context.setWorkTaskId(args[++i]);
                        break;
                    case "-sent_count":
                        Context.setTotalSentCount(args[++i]);
                        break;
                    case "-dev_conf":
                        Context.setDevFiles(args[++i]);
                        break;
                    case "-task_conf":
                        Context.setTaskFiles(args[++i]);
                        break;
                    case "-rand_ip":
                        Context.setRandomIp(args[++i]);
                        break;
                    case "-dev_style":
                        Context.setSentStyle(args[++i]);
                        break;
                    case "-task_style":
                        Context.setTaskStyle(args[++i]);
                        break;
                    case "-count_per_dev_max":
                        Context.setSentLimitPerDev(args[++i]);
                        break;
                    case "-count_per_task_max":
                        Context.setSentLimitPerTask(args[++i]);
                        break;
                    case "-server":
                        Context.setServer(args[++i]);
                        break;
                    case "-detail":
                        Context.setOutputDevDetail(args[++i]);
                        break;
                    case "-rand_tbid":
                        Context.setRandomTBID(args[++i]);
                        break;
                    case "-ratePerHour":
                        Context.setSendRate(args[++i]);
                }
            }
        }
        catch (IndexOutOfBoundsException ie) {
            throw new RuntimeException("parse param failed");
        }
    }
    
    /**
     * 处理生成模拟设备的参数
     */
    public static void getGenDevOptions(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            switch (arg) {
                case "-source":
                    Context.setSource(args[++i]);
                    break;
                case "-dest":
                    Context.setDestFile(args[++i]);
                    break;
                case "-protocol":
                    Context.setProtocol(args[++i]);
                    break;
                case "-from":
                    Context.setFrom(args[++i]);
                    break;
                case "-count":
                    Context.setDevCount(Integer.parseInt(args[++i]));
                    break;
                case "-fill":
                    Context.setFill(args[++i]);
                    break;
            }
        }
    }
    
    
}

