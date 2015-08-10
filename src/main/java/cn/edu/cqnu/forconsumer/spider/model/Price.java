package cn.edu.cqnu.forconsumer.spider.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Price {

	private String link;
	private String product_id;
	private String own;
	private Date date;
	private float price;
	
	//供价格JSON使用
	private List<Prices> prices = new ArrayList<Prices>();

	
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
	
	public void setDate(Date date){
		this.date = date;
	}
	
	public Date getDate(){
		return date;
	}
	
	public void setPrice(float price){
		this.price = price;
	}
	
	public float getPrice(){
		return price;
	}
	
	public List<Prices> getPrices(){
		return prices;
	}
	
	public void setPrices(List<Prices> prices){
		this.prices = prices;
	}
	
	public void addPrice(Prices prices){
		this.prices.add(prices);
	}
}
