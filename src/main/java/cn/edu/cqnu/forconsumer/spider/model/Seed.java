package cn.edu.cqnu.forconsumer.spider.model;

/**
 * 种子表Seed类 - -
 * 此类规则：
 * 1、此类也称为POJO类，不能继承任何类，也不实现任何接口
 * 2、要有一个默认构造器
 * 3、有getXXX和setXXX方法，属性均为private
 * @author young
 *
 */
public class Seed {

	private int auto_id;  
	private String link;  //种子链接地址
	private String own; //所属网站
	private byte is_visited; //是否已访问
	private byte is_valid; //byte对应MySQL里的TINYINT数据类型
	
	public Seed(){}
	
	public Seed(String link, String own, byte is_visited, byte is_valid){
		this.link = link;
		this.own = own;
		this.is_visited = is_visited;
		this.is_valid = is_valid;
	}
	
	public int getAuto_id(){
		return this.auto_id;
	}
	
	public void setAuto_id(int auto_id){
		this.auto_id = auto_id;
	}
	
	public String getLink(){
		return this.link;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	
	public String getOwn(){
		return this.own;
	}
	
	public void setOwn(String own){
		this .own =  own;
	}
	
	public byte getIs_visited(){
		return this.is_visited;
	}
	
	public void setIs_visited(byte is_visited){
		this.is_visited = is_visited;
	}
	
	public byte getIs_valid(){
		return this.is_valid;
	}
	
	public void setIs_valid(byte is_valid){
		this.is_valid = is_valid;
	}
}

