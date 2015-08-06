package cn.edu.cqnu.forconsumer.spider.model;

public class Sl {

	private String link;
	private byte is_visited;
	
	public void setLink(String link){
		this.link = link;
	}
	public String getLink(){
		return link;
	}
	
	public void setIs_visited(byte is_visited){
		this.is_visited = is_visited;
	}
	public byte getIs_visited(){
		return is_visited;
	}
}
