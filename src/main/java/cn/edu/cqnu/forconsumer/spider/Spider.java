package cn.edu.cqnu.forconsumer.spider;

public interface Spider extends Runnable {
	public void initSeedPool();
	public void startWork();
	public String fetchData(String strSeed);
}
