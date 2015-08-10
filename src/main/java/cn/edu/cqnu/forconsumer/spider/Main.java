package cn.edu.cqnu.forconsumer.spider;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//线程个数
		int iThreadNum = 2;
		//抓取京东物品分类链接，物品链接
		//new JDSpider(iThreadNum).startWork();
		
		//抓取京东物品价格信息
		new Thread(new JDGetPrice()).start();
	}
}
