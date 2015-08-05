package cn.edu.cqnu.forconsumer.spider.net;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.CoreConnectionPNames;

public class HttpGetMethod {
	HttpGet httpget = null;
	
	public  HttpGet getHttpGet(String url){
		httpget = new HttpGet(url);
		httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
		httpget.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpget.setHeader("Content-Type", "text/html; charset=gb2312");  //解决编码乱码问题
		httpget.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,1000 * 20);
		return httpget;
	}
	
	/**
	 * 设置代理
	 * @param httpget
	 * @param proxyIP
	 * @param proxyPort
	 * @param proxyProtocol
	 * @return
	 */
	public HttpGet setHttpGetProxy(HttpGet httpget, String proxyIP, int proxyPort, String proxyProtocol){
		//getHttpGet(url);
		HttpHost proxy = new HttpHost(proxyIP, proxyPort,proxyProtocol); //这个是设置需要给主机代理的机器相关信息，即实现用proxy来代理target
	    RequestConfig config = RequestConfig.custom()
					   .setProxy(proxy)
					   .build();
	    httpget.setConfig(config);
		return httpget;
	}
}

