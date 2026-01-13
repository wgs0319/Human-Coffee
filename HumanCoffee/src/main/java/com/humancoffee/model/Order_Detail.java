package com.humancoffee.model;


public class Order_Detail implements Comparable<Order_Detail>{
	private String order_id;
	private String product_id;
	private int		cnt;
	private int		tot_price;
	
	public void setOrderId(String id) {
		this.order_id = id;
	}
	public void setProductId(String id) {
		this.product_id = id;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public void setTotPrice(int price) {
		this.tot_price = price;
	}
		
	public String getOrderId() {
		return this.order_id;
	}
	public String getProductId() {
		return this.product_id;
	}
	public int getCnt() {
		return this.cnt;
	}
	public int getTotPrice() {
		return this.tot_price;
	}
	
//	Comparable 인터페이스 구현: ID를 기준으로 정렬 및 비교
 	@Override
 	public int compareTo(Order_Detail other){
//		return this.order_id.compareTo(other.order_id);
 		// First, compare by order_id
 	    int orderIdComparison = this.order_id.compareTo(other.order_id);

 	    // If order IDs are different, return the result
 	    if (orderIdComparison != 0) {
 	        return orderIdComparison;
 	    }

 	    // If order IDs are the same, compare by product_id
 	    return this.product_id.compareTo(other.product_id);
 	}
}
