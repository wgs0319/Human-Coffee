package com.humancoffee.manager;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import com.humancoffee.model.Com_Member;
import com.humancoffee.model.Customer;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;

public class ManageCustomers {
	public List<Customer>[] customers = (List<Customer>[]) new List[2];
	public List<Customer>	lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public ManageComMembers mComMembers;
	public ManageComMembers.ComMemberIdComparator mComMemberIdComparator;
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	
	public class CustomerIdComparator implements Comparator<Customer>{
		@Override
		public int compare(Customer c1, Customer c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(customers[0] != null)
			customers[0].clear();
		if(customers[1] != null)
			customers[1].clear();
		if(lists != null)
			lists.clear();
	}
	
	public ManageCustomers(){
		customers[0] = new Vector<>();
		customers[1] = new Vector<>();
		
		lists = new Vector<>();
	}

	public void readCustomer(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select id, pwd, name, tel, point, cupon, indate, outdate, status " +
				" from customer order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		customers[mem_pos].clear();
		System.out.println("readCustomer cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Customer customer = new Customer();
			int col = 0;
//			System.out.println();
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			customer.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			customer.setPwd((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			customer.setName((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			customer.setTel((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			customer.setPoint(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			customer.setCupon(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty())
				customer.setInDate(null);
			else
				customer.setInDate(Timestamp.valueOf(value));
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
//				System.out.println("null 입력");
				customer.setOutDate(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				customer.setOutDate(Timestamp.valueOf(value));
			}
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			customer.setStatus(Integer.parseInt(value));
			
			customers[mem_pos].add(customer);
		}
		lists = customers[mem_pos];
		
		System.out.println("customers size:" + customers[mem_pos].size());
		System.out.println("list size:" + lists.size());
		
		System.out.println("customers last : " + customers[mem_pos].get(customers[mem_pos].size() - 1));
		System.out.println("lists last : " + lists.get(lists.size() - 1));
	}

	public void updateCustomerStatus(Customer customer) {

		indexSearch = algo.binarySearchIndex(customers[memory_pos], customer, new CustomerIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(customer.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update customer set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				customer.getStatus(), 
				customer.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateCustomer(Customer customer) {
		indexSearch = algo.binarySearchIndex(customers[memory_pos], customer, new CustomerIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(customer.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String pwd = algo.generateSha256(customer.getId(), customer.getPwd());
		customer.setPwd(pwd);
		String sql = "update customer set pwd = ?, name = ?, tel = ?, point = ?, cupon = ?, outdate = ?, status = ? " +
				" where id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				customer.getPwd(), 
				customer.getName(), 
				customer.getTel(), 
				customer.getPoint(), 
				customer.getCupon(), 
				customer.getOutDate(), 
				customer.getStatus(), 
				customer.getId()));
		oraConn.queryInfosKey.add(key);
	}

	public Customer insertCustomer(Customer customer) {
		indexSearch = algo.binarySearchIndex(customers[memory_pos], customer, new CustomerIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(customer.getId() + ":는 customer 존재하는 ID 입니다.");
			return null;
		}else {
			Com_Member com_member = new Com_Member();
			com_member.setId(customer.getId());
			indexSearch = algo.binarySearchIndex(mComMembers.com_members[mComMembers.memory_pos], com_member, mComMemberIdComparator);
			if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
				System.out.println(customer.getId() + ":는 com_member 존재하는 ID 입니다.");
				return null;
			}
		}
		String pwd = algo.generateSha256(customer.getId(), customer.getPwd());
		customer.setPwd(pwd);
		String sql = "insert into customer (id, pwd, name, tel, indate) values " +
				" (?, ?, ?, ?, sysdate) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				customer.getId(),
				customer.getPwd(), 
				customer.getName(), 
				customer.getTel()
				));
		oraConn.queryInfosKey.add(key);
		return customer;
	}
	
	public Customer searchCustomerById(Customer customer) {
		Customer rcv_customer = (Customer)algo.binarySearchObj(customers[memory_pos], customer, new CustomerIdComparator());
		return rcv_customer;
	}
	
	public Customer searchCustomerByTel(String tel) {
		Customer chk_customer = null;
		for(Customer customer : customers[memory_pos]) {
			if(customer.getTel().equals(tel)) {
				chk_customer = customer;
				break;
			}
		}
		return chk_customer;
	}
	
	public List<Customer> searchCustomerByName(String name) {
		List<Customer> list = new Vector<>();
		int index;
		for(Customer customer : customers[memory_pos]) {
			index = customer.getName().indexOf(name, 0);

			if(index >= 0)
				list.add(customer);
		}
		return list;
	}
	
	public List<Customer> searchCustomerByStatus(byte status){
		List<Customer> list = new Vector<>();
		for(Customer customer : customers[memory_pos]) {
			if(customer.getStatus() == status)
				list.add(customer);
		}
		return list;
	}
}
