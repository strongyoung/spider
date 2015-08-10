package cn.edu.cqnu.forconsumer.spider;

import java.util.HashSet;
import java.util.Iterator;

import cn.edu.cqnu.forconsumer.spider.hibernate.DBManager;
import cn.edu.cqnu.forconsumer.spider.model.Price;
import cn.edu.cqnu.forconsumer.spider.parser.JDParser;
import cn.edu.cqnu.forconsumer.util.Constant;

public class JDGetPrice implements GetPrice {

	private HashSet<String> hsProduct = null;
	
	private int PRODUCT_NUM = 100;
	
	public JDGetPrice(){
		hsProduct = new HashSet<String>();
	}
	
	public void run() {
		// 先获取产品ID
		hsProduct = getProduct();
		
		String url = organizeURL(hsProduct);
		
		//再获取价格数据
		JDSpider jDSpider = new JDSpider();
		String strJson = jDSpider.fetchData(url);
		if(strJson.equals("skuids input error")){
			//log
		}
		else{
			//然后解析价格数据
			Price price = new JDParser().parseProductPrice(strJson , Constant.JINGDONG);
			//写入数据库
			DBManager db = new DBManager();
			db.insertPrice(price);
		}
	}

	/**
	 * 获取产品ID
	 * @return
	 */
	public HashSet<String> getProduct(){
		DBManager db = new DBManager();
		return db.getProduct(0, PRODUCT_NUM, Constant.JINGDONG);
	}

	/**
	 * 根据产品ID，组织URL
	 * @param hsProduct
	 * @return
	 */
	public String organizeURL(HashSet<String> hsProduct){
		StringBuilder sb = new StringBuilder();
		sb.append(Constant.JINGDONG_PRICE_URL);
		Iterator<String> iter = hsProduct.iterator();
		while(iter.hasNext()){
			sb.append("J_");
			sb.append(iter.next());
			sb.append("%2C");
		}
		return sb.toString();
	}
	//最大长度2048个字符
	//http://p.3.cn/prices/mgets?callback=jQuery&skuIds=
}
