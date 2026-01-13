package com.humancoffee.model;

import java.util.Date;


public class Cupon implements Comparable<Cupon>{
	private String 	id;
	private String 	name;
	private int		point;
	private Date	indate;
	private Date	outdate;
	private int		status;
	
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPoint(int point) {
		this.point = point;
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
	public int getPoint() {
		return this.point;
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
 	public int compareTo(Cupon other){
 		return this.id.compareTo(other.id);
// 		return Integer.compare(this.age, other.age);
 	}
}
