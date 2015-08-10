package cn.edu.cqnu.forconsumer.spider.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.edu.cqnu.forconsumer.spider.model.Price;
import cn.edu.cqnu.forconsumer.util.Constant;

public class JDParser implements Parser{
	
	private static Logger log = LogManager.getLogger(JDParser.class.getName());
	
	/**
	 * JD:解析出各个分类
	 * @return
	 */
	public HashSet<String> parseCatSeed(String strContent){
		HashSet<String> hs = new HashSet<String>();
		Pattern pattern = Pattern.compile(Constant.REGEX_JD_CATEGORY);
		Matcher matcher = pattern.matcher(strContent);
		while(matcher.find()){
			//System.out.println(matcher.group());
			hs.add(matcher.group());
		}
		return hs;
	}
	
	/**
	 * 解析出分类下分页的大小
	 * @param 
	 * @return
	 */
	public int parseProductPageSeed(String strContent){
		StringBuilder sbSeed = new StringBuilder();
		
		//先解析共此分类下共有多少页
		int iPage =  0;
		String strPage = "";
		Pattern pattern = Pattern.compile(Constant.REGEX_JD_PAGE);
		Matcher matcher = pattern.matcher(strContent);
		while(matcher.find()){
			//System.out.println(matcher.group());
			strPage = matcher.group();
		}
		//System.out.println(iPage);
		try{
			iPage = Integer.parseInt(strPage.replace("共<b>", "").replace("</b>页", ""));
			return iPage;
		}catch(Exception ex){
			log.error("解析分类分页数量时出现异常 - " + ex.getMessage());
			return 0;
		}
	}
	
	/**
	 * 解析每个分页下的产品种子
	 * @param iPage 
	 * @return
	 */
	public HashSet<String> parseProductSeed(String strContent){
		HashSet<String> hs = new HashSet<String>();
		Pattern pattern = Pattern.compile(Constant.REGEX_JD_PRODUCT);
		Matcher matcher = pattern.matcher(strContent);
		while(matcher.find()){
			//System.out.println(matcher.group());
			hs.add(matcher.group());
		}
		return hs;
	}
	
	/**
	 * 解析物品价格JSON
	 * @param strContent
	 * @return
	 */
	public Price parseProductPrice(String strContent , String strOwn){
		//HashMap<String,Float> hs = new HashMap<String,Float>();
		//{"id":0,"name":"admin","users":[{"id":2,"name":"guest"},{"id":3,"name":"root"}]}
		strContent = strContent.replaceAll("jQuery227170[(]", "{\"prices\":").replaceAll("[)];","}"); //注意，这里的（要用[]括起来。否则异常
		Price price= JSON.parseObject (strContent,Price.class); 
		price.setOwn( strOwn);
		return price;
	}
}