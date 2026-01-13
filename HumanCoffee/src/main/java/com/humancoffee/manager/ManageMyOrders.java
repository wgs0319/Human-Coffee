package com.humancoffee.manager;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;


import com.humancoffee.model.My_Order;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;


public class ManageMyOrders {
	public List<My_Order>[] my_orders = (List<My_Order>[]) new List[2];
	public List<My_Order> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	private Common common = new Common();
	
	public class MyOrderIdComparator implements Comparator<My_Order>{
		@Override
		public int compare(My_Order c1, My_Order c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(my_orders[0] != null)
			my_orders[0].clear();
		if(my_orders[1] != null)
			my_orders[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageMyOrders(){
		my_orders[0] = new Vector<>();
		my_orders[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	

	public void readMyOrder(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select id, member_id, customer_tel, tot_price, pay_div, cupon, card, cash, indate, outdate, status " +
				" from my_order order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		my_orders[mem_pos].clear();
		System.out.println("readMyOrder cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			My_Order my_order = new My_Order();
			int col = 0;
//			System.out.println();
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			my_order.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			my_order.setMemberId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			my_order.setCustomerTel((value == null) ? "" : value);

			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			my_order.setTotPrice(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			my_order.setDiv(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			my_order.setCupon(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			my_order.setCard(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			my_order.setCash(Integer.parseInt(value));
									
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty())
				my_order.setInDate(null);
			else
				my_order.setInDate(Timestamp.valueOf(value));
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
//				System.out.println("null 입력");
				my_order.setOutDate(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				my_order.setOutDate(Timestamp.valueOf(value));
			}
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			my_order.setStatus(Integer.parseInt(value));
			
			my_orders[mem_pos].add(my_order);
		}
		lists = my_orders[mem_pos];
	}
	
	public void updateMyOrderStatus(My_Order my_order) {
		indexSearch = algo.binarySearchIndex(my_orders[memory_pos], my_order, new MyOrderIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(my_order.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update my_order set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				my_order.getStatus(), 
				my_order.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateMyOrder(My_Order my_order) {
		indexSearch = algo.binarySearchIndex(my_orders[memory_pos], my_order, new MyOrderIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(my_order.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update my_order set member_id = ?, customer_tel = ?, tot_price = ?, pay_div = ?, cupon = ?, card = ?, cash = ?, outdate = ?, status = ? " +
				" where id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				my_order.getMemberId(),
				my_order.getCustomerTel(),
				my_order.getTotPrice(), 
				my_order.getDiv(), 
				my_order.getCupon(),
				my_order.getCard(),
				my_order.getCash(),
				my_order.getOutDate(),
				my_order.getStatus(),
				my_order.getId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public My_Order insertMyOrder(My_Order my_order) {
		String max_id = null;
		if(my_orders[memory_pos].size() > 0)
			max_id = my_orders[memory_pos].get(my_orders[memory_pos].size() - 1).getId();
		max_id = common.generateDateSequenceId16(max_id);
		my_order.setId(max_id);
		indexSearch = algo.binarySearchIndex(my_orders[memory_pos], my_order, new MyOrderIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(my_order.getId() + ":는 존재하는 ID 입니다.");
			return null;
		}
		String sql = "insert into my_order (id, member_id, customer_tel, tot_price, pay_div, cupon, card, cash, indate) values " +
				" (?, ?, ?, ?, ?, ?, ?, ?, sysdate) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				my_order.getId(),
				my_order.getMemberId(),
				my_order.getCustomerTel(),
				my_order.getTotPrice(), 
				my_order.getDiv(), 
				my_order.getCupon(),
				my_order.getCard(),
				my_order.getCash()
				));
		oraConn.queryInfosKey.add(key);
		return my_order;
	}
	
	public My_Order searchMyOrderById(My_Order my_order) {
		My_Order rcv_my_order = (My_Order)algo.binarySearchObj(my_orders[memory_pos], my_order, new MyOrderIdComparator());
		return rcv_my_order;
	}
	
	public List<My_Order> searchMyOrderByStatus(byte status){
		List<My_Order> list = new Vector<>();
		for(My_Order my_order : my_orders[memory_pos]) {
			if(my_order.getStatus() == status)
				list.add(my_order);
		}
		return list;
	}
	
}
