package cn.edu.cqnu.forconsumer.util;

public class Constant {
	
	/**
	 * 已访问 
	 */
	public static byte IS_VISITED = 1;
	
	/**
	 * 未访问
	 */
	public static byte IS_UN_VISITED = 0;
	
	/**
	 * 有效
	 */
	public static byte IS_VALID = 1;
	
	/**
	 * 无效
	 */
	public static byte IS_UN_VALID = 0;
	
	/**
	 * JINGDONG ： 京东
	 */
	public static String JINGDONG = "JINGDONG";
	
	/**
	 * 所属京东标志
	 */
	public static String JINGDONG_OWN = ".jd.";
	
	/**
	 * DANGDANG：当当
	 */
	public static String DANGDANG = "DANGDANG";
	
	/**
	 * 所属当当标志
	 */
	public static String DANGDANG_OWN = ".dangdang.";
	
	/**
	 * AMAZON：亚马逊
	 */
	public static String AMAZON = "AMAZON";

    /**
	 * 多个种子之间的分割符
	 */
	public static String SEED_SPLIT = "::";
	
	/**
	 * JD: 分类URL正则表达式
	 */
	public static String REGEX_JD_CATEGORY = "http://list.jd.com.*?html";
	
	/**
	 * JD: 分页总数正则表达式
	 */
	public static String REGEX_JD_PAGE = "共<b>.*?</b>页";
	
	/**
	 * JD: 产品URL正则表达式
	 */
	public static String REGEX_JD_PRODUCT = "http://item.jd.com.*?.html";
	
}