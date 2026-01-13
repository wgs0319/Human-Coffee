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

public class ManageComMembers {
	public List<Com_Member>[] com_members = (List<Com_Member>[]) new List[2];
	public List<Com_Member> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	private Common common = new Common();
	
	public ManageCustomers mCustomers;
	public ManageCustomers.CustomerIdComparator mCustomerIdComparator;
	
	public class ComMemberIdComparator implements Comparator<Com_Member>{
		@Override
		public int compare(Com_Member c1, Com_Member c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(com_members[0] != null)
			com_members[0].clear();
		if(com_members[1] != null)
			com_members[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageComMembers(){
		com_members[0] = new Vector<>();
		com_members[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	
	public void readComMember(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;

		String sql = "select com_id, id, pwd, name, tel, roll_id, indate, outdate, status " +

				" from com_member order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		com_members[mem_pos].clear();
		System.out.println("readComMember cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Com_Member com_member = new Com_Member();
			int col = 0;
//			System.out.println();
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_member.setComId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_member.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_member.setPwd((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_member.setName((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_member.setTel((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_member.setRollId((value == null) ? "" : value);
		
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty())
				com_member.setInDate(null);
			else
				com_member.setInDate(Timestamp.valueOf(value));
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
//				System.out.println("null 입력");
				com_member.setOutDate(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				com_member.setOutDate(Timestamp.valueOf(value));
			}
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			com_member.setStatus(Integer.parseInt(value));
			
			com_members[mem_pos].add(com_member);
		}
		lists = com_members[mem_pos];
	}
	

	public void updateComMemberStatus(Com_Member com_member) {

		indexSearch = algo.binarySearchIndex(com_members[memory_pos], com_member, new ComMemberIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(com_member.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update com_member set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				com_member.getStatus(), 
				com_member.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateComMember(Com_Member com_member) {
		indexSearch = algo.binarySearchIndex(com_members[memory_pos], com_member, new ComMemberIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(com_member.getId() + ":는 없는 ID 입니다.");
			return;
		}

		String sql = "update com_member set com_id = ?, pwd = ?, name = ?, tel = ?, roll_id = ?, outdate = ?, status = ? " +
				" where id = ?";
		String pwd = algo.generateSha256(com_member.getId(), com_member.getPwd());
		com_member.setPwd(pwd);
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				com_member.getComId(),
				com_member.getPwd(), 
				com_member.getName(), 
				com_member.getTel(), 
				com_member.getRollId(), 

				com_member.getOutDate(), 
				com_member.getStatus(), 
				com_member.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public Com_Member insertComMember(Com_Member com_member) {
		indexSearch = algo.binarySearchIndex(com_members[memory_pos], com_member, new ComMemberIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(com_member.getId() + ":는 com_member 존재하는 ID 입니다.");
			return null;
		}else {
			Customer customer = new Customer();
			customer.setId(com_member.getId());
			indexSearch = algo.binarySearchIndex(mCustomers.customers[mCustomers.memory_pos], customer, mCustomerIdComparator);
			if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
				System.out.println(com_member.getId() + ":는 customer 존재하는 ID 입니다.");
				return null;
			}
		}
		String pwd = algo.generateSha256(com_member.getId(), com_member.getPwd());
		com_member.setPwd(pwd);
		String sql = "insert into com_member (com_id, id, pwd, name, tel, roll_id, indate) values " +

				" (?, ?, ?, ?, ?, ?, sysdate) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				com_member.getComId(),
				com_member.getId(),
				com_member.getPwd(), 
				com_member.getName(), 
				com_member.getTel(), 
				com_member.getRollId()

				));
		oraConn.queryInfosKey.add(key);
		return com_member;
	}
	
	public Com_Member searchComMemberById(Com_Member com_member) {
		Com_Member rcv_com_member = (Com_Member)algo.binarySearchObj(com_members[memory_pos], com_member, new ComMemberIdComparator());
		return rcv_com_member;
	}

	public List<Com_Member> searchComMemberByName(String name) {

		List<Com_Member> list = new Vector<>();
		int index;
		for(Com_Member com_member : com_members[memory_pos]) {
			index = com_member.getName().indexOf(name, 0);
			if(index >= 0)
				list.add(com_member);
		}
		return list;
	}
	
	public List<Com_Member> searchComMemberByStatus(byte status){
		List<Com_Member> list = new Vector<>();
		for(Com_Member com_member : com_members[memory_pos]) {
			if(com_member.getStatus() == status)
				list.add(com_member);
		}
		return list;
	}
	
}
