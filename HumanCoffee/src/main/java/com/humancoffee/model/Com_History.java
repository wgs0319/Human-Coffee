package com.humancoffee.model;

import java.util.Date;

public class Com_History implements Comparable<Com_History>{
	private String com_id;
	private String id;
	private Date	startdate;
	private Date	enddate;
	private String title;
	private String content;
	private int		status;
	
	public void setComId(String id) {
		this.com_id = id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setStartDate(Date date) {
		this.startdate = date;
	}
	public void setEndDate(Date date) {
		this.enddate = date;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Date getStartDate() {
		return this.startdate;
	}
	public Date getEndDate() {
		return this.enddate;
	}
	public String getTitle() {
		return this.title;
	}
	public String getContent() {
		return this.content;
	}
	public int getStatus() {
		return this.status;
	}
	
//	Comparable 인터페이스 구현: ID를 기준으로 정렬 및 비교
 	@Override
 	public int compareTo(Com_History other){
 		return this.id.compareTo(other.id);
// 		return Integer.compare(this.age, other.age);
 	}
}
