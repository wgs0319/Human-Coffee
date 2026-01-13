package com.humancoffee.model;

import java.util.Date;

public class Sub_Com implements Comparable<Sub_Com>{
	private String com_id;
	private String id;
	private String name;
	private String tel;
	private String fax;
	private String addr;
	private String email;
	private Date	indate;
	private Date	outdate;
	private int		status;
	
	public void setComId(String id) {
		this.com_id = id;
	}
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
	public void setEmail(String email) {
		this.email = email;
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
	
	public String getComId() {
		return this.com_id;
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
	public String getEmail() {
		return this.email;
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
 	public int compareTo(Sub_Com other){
 		return this.id.compareTo(other.id);
// 		return Integer.compare(this.age, other.age);
 	}
}
