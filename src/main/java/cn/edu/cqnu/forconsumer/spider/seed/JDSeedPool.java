package cn.edu.cqnu.forconsumer.spider.seed;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.cqnu.forconsumer.spider.hibernate.DBManager;
import cn.edu.cqnu.forconsumer.util.Constant;

public class JDSeedPool implements SeedPool{
	/**
	 * 真正存储种子的数据结构
	 */
	private volatile static HashSet<String> seed;
	
	/**
	 * 种子池只允许一个实例。
	 */
	private static SeedPool uniqueSeedPoolInstance ;
	
	/**
	 * 初始化种子数量
	 */
	private static int INIT_SEED_NUM = 200;
	
	private static Logger log = LogManager.getLogger(JDSeedPool.class.getName());
	
	/**
	 * 私有构造函数
	 */
	private JDSeedPool(){}
	
	/**
	 * 获取种子池实例
	 * @return
	 */
	public synchronized static SeedPool getInstance(){
		try{
			if(uniqueSeedPoolInstance == null){
				uniqueSeedPoolInstance = new JDSeedPool();
				seed = new HashSet<String>();
			}
			return uniqueSeedPoolInstance;
		}catch(Exception ex){
			log.error("程序在获取种子池实例时出现异常 - " + ex.getMessage());
			return null;
		}
	}
	
	/**
	 * 初始化种子池
	 * @throws SQLException 
	 */
	public synchronized void initSeedPool(){
		try{
			DBManager db = new DBManager();
			seed = db.getSeed(0,INIT_SEED_NUM,Constant.JINGDONG);
		}catch(Exception ex){
			log.error("初始化种子池时出错异常 - " + ex.getMessage());
		}
	}
	
	/**
	 * 获取一个种子
	 * @return
	 * @throws SQLException 
	 */
	public  synchronized String getSeed() {
		try{
			if(seed.size()>0){
				Iterator<String> iter = seed.iterator();
				String strSeed = iter.next();
				seed.remove(strSeed);
				if(seed.size()==0){
					
					System.out.println("当前种子池里的种子已使用完，是否继续抓取数据？");
					System.out.println("继续请输入y，停止请输入n");
					System.out.println("请输入：");
					Scanner sc = new Scanner(System.in);
					String tmp = sc.nextLine();
					if(tmp.startsWith("y")  || tmp.startsWith("Y")){
						initSeedPool();
					}
					
					//initSeedPool();
				}
				return strSeed;
			}
			else
				return null;
		}catch(Exception ex){
			log.error("程序在获取种子时出现异常 - " + ex.getMessage());
			return null;
		}
	}
}
