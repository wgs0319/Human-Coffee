package com.humancoffee.model;

import java.util.Date;


public class Customer implements Comparable<Customer>{
	private String id;
	private String pwd;
	private String name;
	private String tel;
	private int		point;
	private int		cupon;
	private Date	indate;
	private Date	outdate;
	private int		status;
	
	public void setId(String id) {
		this.id = id;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public void setCupon(int cupon) {
		this.cupon = cupon;
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
	public String getPwd() {
		return this.pwd;
	}
	public String getName() {
		return this.name;
	}
	public String getTel() {
		return this.tel;
	}
	public int getPoint() {
		return this.point;
	}
	public int getCupon() {
		return this.cupon;
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
 	public int compareTo(Customer other){
 		return this.id.compareTo(other.id);
// 		return Integer.compare(this.age, other.age);
 	}
}
