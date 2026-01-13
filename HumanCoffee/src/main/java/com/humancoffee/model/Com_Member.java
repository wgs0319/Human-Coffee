package com.humancoffee.model;

import java.util.Date;


public class Com_Member implements Comparable<Com_Member>{

	private String 	com_id;
	private String 	id;
	private String 	pwd;
	private String 	name;
	private String 	tel;
	private String	roll_id;

	private Date	indate;
	private Date	outdate;
	private int		status;
	
	public void setComId(String id) {
		this.com_id = id;
	}
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
	public void setRollId(String roll) {
		this.roll_id = roll;
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
	public String getPwd() {
		return this.pwd;
	}
	public String getName() {
		return this.name;
	}
	public String getTel() {
		return this.tel;
	}
	public String getRollId() {
		return this.roll_id;
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
 	public int compareTo(Com_Member other){
 		return this.id.compareTo(other.id);
// 		return Integer.compare(this.age, other.age);
 	}
}
