package cn.edu.cqnu.forconsumer.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.cqnu.forconsumer.spider.hibernate.DBManager;
import cn.edu.cqnu.forconsumer.spider.model.Stat;
import cn.edu.cqnu.forconsumer.spider.net.HttpConnectionManager;
import cn.edu.cqnu.forconsumer.spider.net.HttpGetMethod;
import cn.edu.cqnu.forconsumer.spider.parser.JDParser;
import cn.edu.cqnu.forconsumer.spider.parser.Parser;
import cn.edu.cqnu.forconsumer.spider.seed.JDSeedPool;
import cn.edu.cqnu.forconsumer.spider.seed.SeedPool;
import cn.edu.cqnu.forconsumer.spider.statistic.JDStatistic;
import cn.edu.cqnu.forconsumer.spider.statistic.Statistic;

public class JDSpider implements Spider {
	
	/**
	 *  种子池
	 */
	public static SeedPool seedPool ;
	
	/**
	 * 线程个数
	 */
	public static int iThreadNum ;  
	
	/**
	 * 爬虫请求次数(统计信息)
	 */
	public static int iRequstNum = 0;
	
	private static Logger log = LogManager.getLogger(JDSpider.class.getName());
	
	private static Statistic statistic = JDStatistic.getInstance();
	
	public JDSpider(){};
	
	public JDSpider(int iNum){
		iThreadNum = iNum;
		seedPool = JDSeedPool.getInstance();
		initSeedPool();
	}

	/**
	 * 初始始化种子池
	 */
	public void initSeedPool() {
		seedPool.initSeedPool();
	}
	
	public void run() {
		String strSeed = seedPool.getSeed(); 
		while(strSeed != null){
			Parser parser = new JDParser();
			DBManager db = new DBManager();
			//种子是分类网址时
			if(strSeed.indexOf("allSort")>=0){
				String strContent = fetchData(strSeed);
				HashSet<String> hsSeed = parser.parseCatSeed(strContent);
				db.insertSeed(hsSeed);
			}
			else if(strSeed.indexOf("list")>=0){//种子是全类时,把数据写入数据库
				String strContent = fetchData(strSeed +  "&stock=0&JL=6_0_0");
				if(strContent!=null){
					int iPage = parser.parseProductPageSeed(strContent);  //解析分页
					HashSet<String> hsProduct = parser.parseProductSeed(strContent);  //解析第一页的产品种子
					db.insertProduct(hsProduct);
					
					int iCurrentPage = 2;
					while(iCurrentPage <= iPage){
						strContent = fetchData(strSeed + "&stock=0&JL=6_0_0&page="+iCurrentPage);
						hsProduct = parser.parseProductSeed(strContent);  //解析第二页及之后的产品种子
						db.insertProduct (hsProduct);
						iCurrentPage ++ ;
					}
				}
				else{
					log.error("获取数据失败 - " + strSeed);
				}
			}
			//再取种子
			strSeed = seedPool.getSeed();
			if(strSeed==null){
				seedPool.initSeedPool();
			}
			strSeed = seedPool.getSeed();
		}
	}
	
	/**
	 * 爬虫程序入口
	 */
	public void startWork(){
		for(int i = 0 ; i < iThreadNum ; i++ ){
			new Thread(new JDSpider()).start();
		}
	}
	
	public String fetchData(String strSeed) {
		
		HttpGet httpget = null;
		InputStream in = null;
		StringBuilder sb = null;
		CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
		try {
		    httpget = new HttpGetMethod().getHttpGet(strSeed);
		    CloseableHttpResponse resp = httpClient.execute(httpget);;
		    iRequstNum++ ;
		    
			int respCode = resp . getStatusLine().getStatusCode();

			if (respCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp. getEntity();
				if (entity != null) {
					in = entity .getContent();
					BufferedReader br =  new BufferedReader(new InputStreamReader(in));
				    String strTmp = br.readLine();
				    sb = new StringBuilder();
				    while(strTmp !=null){
				    	sb.append(strTmp);
				    	strTmp =  br.readLine();
				    }
					System.out.println("请求成功..."
							+ Thread.currentThread().getName() + " : " + strSeed);
					//Thread.sleep(2000);
					resp.close();
					return sb.toString();
				}
			} else if(respCode == HttpStatus.SC_FORBIDDEN){
				//如果发生这种情况，则使用代理。
				//TODO 添加使用代理处理
				//写日志
				log.error("禁止访问 - " + strSeed);
				Stat statModel = new Stat();
				statModel.setCount(iRequstNum);
				statModel.setIp("127.0.0.1");
				statModel.setLink(strSeed);
				statModel.setStatus(HttpStatus.SC_FORBIDDEN);
				statistic.write(statModel);
				iRequstNum = 0;
			}
			else{
				System.out.println("请求失败了..."
						+ Thread.currentThread().getName() + strSeed);
				if(httpget!=null)
					httpget.abort();
				resp.close();
				log.error("请求失败 - " + strSeed);
				return null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			if(httpget!=null)
				httpget.abort();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			if(httpget!=null)
				httpget.abort();
			log.error(e.getMessage());
		} catch (Exception e) { 
			e.printStackTrace();
			if(httpget!=null)
				httpget.abort();
			log.error(e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
		}
		return null;
	}

}
