package cn.edu.cqnu.forconsumer.spider.model;

public class Product {
	private String link;
	private String product_id;
	private String own;
	private String title;
	private byte is_visited;
	private byte is_valid;
	
	public void setLink(String link){
		this.link = link;
	}
	
	public String getLink(){
		return link;
	}
	
	public void setProduct_id(String product_id){
		this.product_id = product_id;
	}
	
	public String getProduct_id(){
		return product_id;
	}
	
	public void setOwn(String own){
		this.own = own;
	}
	
	public String getOwn(){
		return own;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
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
