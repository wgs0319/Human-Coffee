package com.humancoffee.model;

import java.util.Date;

public class Company implements Comparable<Company>{
	private String id;
	private String name;
	private String tel;
	private String fax;
	private String addr;
	private String sale_email;
	private String eng_email;
	private Date	indate;
	private Date	outdate;
	private int		status;
	
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public void setSaleEmail(String email) {
		this.sale_email = email;
	}
	public void setEngEmail(String email) {
		this.eng_email = email;
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
	public String getTel() {
		return this.tel;
	}
	public String getFax() {
		return this.fax;
	}
	public String getAddr() {
		return this.addr;
	}
	public String getSaleEmail() {
		return this.sale_email;
	}
	public String getEngEmail() {
		return this.eng_email;
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
 	public int compareTo(Company other){
 		return this.id.compareTo(other.id);
// 		return Integer.compare(this.age, other.age);
 	}
}
