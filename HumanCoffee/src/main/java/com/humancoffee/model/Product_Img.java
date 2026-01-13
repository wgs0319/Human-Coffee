package com.humancoffee.model;

import java.sql.Blob;
import java.util.Date;


public class Product_Img implements Comparable<Product_Img>{
	private String	product_id;
	private String 	id;
	private int		div;
	private String	filename;
	private Blob 	b_file;
	private Date	indate;
	private Date	outdate;
	private int		status;
	
	public void setProductId(String id) {
		this.product_id = id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setDiv(int div) {
		this.div = div;				//	1. 커피, 3 : 무카페인, 4 : 쥬스
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setBFile(Blob bfile) {
		this.b_file = bfile;
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
	
	public String getProductId() {
		return this.product_id;
	}
	public String getId() {
		return this.id;
	}
	public int getDiv() {
		return this.div;
	}
	public String getFilename() {
		return this.filename;
	}
	public Blob getBFile() {
		return this.b_file;
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
 	public int compareTo(Product_Img other){
 		return this.id.compareTo(other.id);
// 		return Integer.compare(this.age, other.age);
 	}
}
