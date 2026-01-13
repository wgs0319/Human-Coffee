package com.humancoffee.model;

import java.util.Date;


public class My_Order implements Comparable<My_Order>{
	private String id;
	private String member_id;
	private String customer_tel;
	private int		tot_price;
	private int		pay_div;
	private int		cupon;
	private int		card;
	private int		cash;
	private Date	indate;
	private Date	outdate;
	private int		status;
	
	public void setId(String id) {
		this.id = id;
	}
	public void setMemberId(String id) {
		this.member_id = id;
	}
	public void setCustomerTel(String tel) {
		this.customer_tel = tel;
	}
	public void setTotPrice(int price) {
		this.tot_price = price;
	}
	public void setDiv(int div) {
		this.pay_div = div;
	}
	public void setCupon(int cupon) {
		this.cupon = cupon;
	}
	public void setCard(int price) {
		this.card = price;
	}
	public void setCash(int price) {
		this.cash = price;
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
	public String getMemberId() {
		return this.member_id;
	}
	public String getCustomerTel() {
		return this.customer_tel;
	}
	public int getTotPrice() {
		return this.tot_price;
	}
	public int getDiv() {
		return this.pay_div;
	}
	public int getCupon() {
		return this.cupon;
	}
	public int getCard() {
		return this.card;
	}
	public int getCash() {
		return this.cash;
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
 	public int compareTo(My_Order other){
 		return this.id.compareTo(other.id);
// 		return Integer.compare(this.age, other.age);
 	}
}
