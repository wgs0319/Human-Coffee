package com.humancoffee.model;

import java.util.Date;

import java.sql.Blob;

public class Com_Ci implements Comparable<Com_Ci>{
	private String 	com_id;
	private String 	id;
	private Date	indate;
	private Date	outdate;
	private String 	filename;
	private Blob 	b_file;
	private int		status;
	
	public void setComId(String id) {
		this.com_id = id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setInDate(Date date) {
		this.indate = date;
	}
	public void setOutDate(Date date) {
		this.outdate = date;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setBFile(Blob bfile) {
		this.b_file = bfile;
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
	public Date getInDate() {
		return this.indate;
	}
	public Date getOutDate() {
		return this.outdate;
	}
	public String getFilename() {
		return this.filename;
	}
	public Blob getBFile() {
		return this.b_file;
	}
	public int getStatus() {
		return this.status;
	}
	
//	Comparable 인터페이스 구현: ID를 기준으로 정렬 및 비교
 	@Override
 	public int compareTo(Com_Ci other){
 		return this.id.compareTo(other.id);
// 		return Integer.compare(this.age, other.age);
 	}
}
