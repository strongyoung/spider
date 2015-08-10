package cn.edu.cqnu.forconsumer.spider.model;

public class Prices {
	private String id;
	private float p;
	private float m;
	

	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setP(float p){
		this.p = p;
	}
	
	public float getP(){
		return p;
	}
	
	public void setM(float m){
		this.m = m;
	}
	
	public float getM(){
		return m;
	}
}
