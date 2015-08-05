package cn.edu.cqnu.forconsumer.spider;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//线程个数
		int iThreadNum = 2;
		new JDSpider(iThreadNum).startWork();
	}
}
