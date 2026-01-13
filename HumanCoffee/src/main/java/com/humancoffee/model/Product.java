package com.humancoffee.model;

import java.util.Date;


public class Product implements Comparable<Product>{
	private String 	id;
	private String 	name;
	private int		price;
	private int		point;
	private int		div;
	private String	content;
	private Date	indate;
	private Date	outdate;
	private int		status;
	
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public void setDiv(int div) {
		this.div = div;				//	1. 커피, 3 : 무카페인, 4 : 쥬스
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setInDate(Date indate) {
		this.indate = indate;
	}
	public void setOutDate(Date outdate) {
		this.outdate = outdate;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public int getPrice() {
		return this.price;
	}
	public int getPoint() {
		return this.point;
	}
	public int getDiv() {
		return this.div;
	}
	public String getContent() {
		return this.content;
	}
	public Date getInDate() {
		return this.indate;
	}
	public Date getOutDate() {
		return this.outdate;
	}
	public int getStatus() {
		return this.status;
	}
	
//	Comparable 인터페이스 구현: ID를 기준으로 정렬 및 비교
 	@Override
 	public int compareTo(Product other){
 		return this.id.compareTo(other.id);
// 		return Integer.compare(this.age, other.age);
 	}
}
