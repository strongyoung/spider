package cn.edu.cqnu.forconsumer.spider.statistic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.cqnu.forconsumer.spider.model.Stat;


public class JDStatistic implements Statistic {
	
	public static JDStatistic uniqueJDStatisticInstance = null;
	
	private static Logger log = LogManager.getLogger(JDStatistic.class.getName());
	
	private JDStatistic(){}
	
	public synchronized static JDStatistic getInstance(){
		try{
			if(uniqueJDStatisticInstance == null){
				uniqueJDStatisticInstance = new JDStatistic();
			}
			return uniqueJDStatisticInstance;
		}catch(Exception ex){
			log.error("程序在获取京东统计实例时出现异常 - " + ex.getMessage());
			return null;
		}
	}
	
	public void write(Stat stat){
		log.info(stat.getLink() + " - " + stat.getIp() + ", " + stat.getCount() + ": " + stat.getStatus());
	}
}
