package com.humancoffee.manager;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;


import com.humancoffee.model.Order_Detail;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;


public class ManageOrderDetails {
	public List<Order_Detail>[] order_details = (List<Order_Detail>[]) new List[2];
	public List<Order_Detail> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	
	public class OrderDetailIdComparator implements Comparator<Order_Detail>{
		@Override
		public int compare(Order_Detail c1, Order_Detail c2) {
			// 1. OrderId로 먼저 비교
			int orderIdComparison = c1.getOrderId().compareTo(c2.getOrderId());
			
			// 2. OrderId가 같다면 ProductId로 비교
	        if (orderIdComparison == 0) {
	            return c1.getProductId().compareTo(c2.getProductId());
	        }
	        return orderIdComparison;
		}
	}
	
	public void exit() {
		if(order_details[0] != null)
			order_details[0].clear();
		if(order_details[1] != null)
			order_details[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageOrderDetails(){
		order_details[0] = new Vector<>();
		order_details[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	

	public void readOrderDetail(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select order_id, product_id, cnt, tot_price " +
				" from order_detail order by order_id, product_id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		order_details[mem_pos].clear();
		System.out.println("readOrderDetail cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Order_Detail order_detail = new Order_Detail();
			int col = 0;
//			System.out.println();
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			order_detail.setOrderId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			order_detail.setProductId((value == null) ? "" : value);

			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			order_detail.setCnt(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			order_detail.setTotPrice(Integer.parseInt(value));
					
			order_details[mem_pos].add(order_detail);
		}
		lists = order_details[mem_pos];
	}
	
	public void deleteOrderDetail(Order_Detail order_detail) {
		indexSearch = algo.binarySearchIndex(order_details[memory_pos], order_detail, new OrderDetailIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println("주문ID [" + order_detail.getOrderId() + "], 상품ID [" + order_detail.getProductId() + "] :는 없는 ID 입니다.");
			return;
		}
		String sql = "delete from order_detail " +
				" where order_id = ? and product_id = ? ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				order_detail.getOrderId(),
				order_detail.getProductId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateOrderDetail(Order_Detail order_detail) {
		indexSearch = algo.binarySearchIndex(order_details[memory_pos], order_detail, new OrderDetailIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println("주문ID [" + order_detail.getOrderId() + "], 상품ID [" + order_detail.getProductId() + "] :는 없는 ID 입니다.");
			return;
		}
		String sql = "update order_detail set cnt = ?, tot_price = ? " +
				" where product_id = ? and order_id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				order_detail.getCnt(),
				order_detail.getTotPrice(), 
				order_detail.getProductId(),
				order_detail.getOrderId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public Order_Detail insertOrderDetail(Order_Detail order_detail) {
		indexSearch = algo.binarySearchIndex(order_details[memory_pos], order_detail, new OrderDetailIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println("주문ID [" + order_detail.getOrderId() + "], 상품ID [" + order_detail.getProductId() + "] :는 있는 ID 입니다.");
			return null;
		}
		String sql = "insert into order_detail (order_id, product_id, cnt, tot_price ) values " +
				" (?, ?, ?, ?) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				order_detail.getOrderId(),
				order_detail.getProductId(),
				order_detail.getCnt(),
				order_detail.getTotPrice()				
				));
		oraConn.queryInfosKey.add(key);
		return order_detail;
	}
	
	public Order_Detail searchOrderDetailByOrderIdNProductId(Order_Detail order_detail) {
		Order_Detail rcv_order_detail = (Order_Detail)algo.binarySearchObj(order_details[memory_pos], order_detail, new OrderDetailIdComparator());
		return rcv_order_detail;
	}
	
	public List<Order_Detail> searchOrderDetailByOrderId(String id){
		List<Order_Detail> list = new Vector<>();
		int index;
		for(Order_Detail order_detail : order_details[memory_pos]) {
			if(order_detail.getOrderId().equals(id))
				list.add(order_detail);
		}
		return list;
	}
/*	public List<My_Order> searchSubComByName(String name) {
		List<Sub_Com> list = new Vector<>();
		int index;
		for(Sub_Com sub_com : sub_coms[memory_pos]) {
			index = sub_com.getName().indexOf(name, 0);
			if(index >= 0)
				list.add(sub_com);
		}
		return list;
	}
	
	public List<My_Order> searchMyOrderByStatus(byte status){
		List<My_Order> list = new Vector<>();
		for(My_Order my_order : my_orders[memory_pos]) {
			if(my_order.getStatus() == status)
				list.add(my_order);
		}
		return list;
	}*/
	
}
