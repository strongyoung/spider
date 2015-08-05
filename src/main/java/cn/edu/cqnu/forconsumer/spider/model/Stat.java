package cn.edu.cqnu.forconsumer.spider.model;

import java.util.Date;

public class Stat {
	private String own ;
	private String ip;
	private int count;
	private int status;
	private String link;
	private Date date;
		
	public void setOwn(String own){
		this.own = own;
	}
	
	public String getOwn(){
		return this.own;
	}
	
	public void setIp(String ip){
		this.ip = ip ;
	}
	
	public String getIp(){
		return this.ip;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	
	public int getCount(){
		return this.count;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	
	public String getLink(){
		return this.link;
	}
	
	public void setDate(Date date){
		this.date = date;
	}
	
	public Date getDate(){
		return this.date;
	}
}
