package com.humancoffee.manager;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;


import com.humancoffee.model.Sub_Com;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;


public class ManageSubComs {
	public List<Sub_Com>[] sub_coms = (List<Sub_Com>[]) new List[2];
	public List<Sub_Com> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	private Common common = new Common();
	
	public class SubComIdComparator implements Comparator<Sub_Com>{
		@Override
		public int compare(Sub_Com c1, Sub_Com c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(sub_coms[0] != null)
			sub_coms[0].clear();
		if(sub_coms[1] != null)
			sub_coms[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageSubComs(){
		sub_coms[0] = new Vector<>();
		sub_coms[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	

	public void readSubCom(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select com_id, id, name, tel, fax, addr, email, indate, outdate, status " +
				" from sub_com order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		sub_coms[mem_pos].clear();
		System.out.println("readSubCom cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Sub_Com sub_com = new Sub_Com();
			int col = 0;
//			System.out.println();
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			sub_com.setComId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			sub_com.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			sub_com.setName((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			sub_com.setTel((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			sub_com.setFax((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			sub_com.setAddr((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			sub_com.setEmail((value == null) ? "" : value);
						
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty())
				sub_com.setInDate(null);
			else
				sub_com.setInDate(Timestamp.valueOf(value));
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
//				System.out.println("null 입력");
				sub_com.setOutDate(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				sub_com.setOutDate(Timestamp.valueOf(value));
			}
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			sub_com.setStatus(Integer.parseInt(value));
			
			sub_coms[mem_pos].add(sub_com);
		}
		lists = sub_coms[mem_pos];
	}
	
	public void updateSubComStatus(Sub_Com sub_com) {
		indexSearch = algo.binarySearchIndex(sub_coms[memory_pos], sub_com, new SubComIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(sub_com.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update sub_com set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				sub_com.getStatus(), 
				sub_com.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateSubCom(Sub_Com sub_com) {
		indexSearch = algo.binarySearchIndex(sub_coms[memory_pos], sub_com, new SubComIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(sub_com.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update sub_com set com_id=?, name = ?, tel = ?, fax = ?, addr = ?, email = ?, outdate = ?, status = ? " +
				" where id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				sub_com.getComId(),
				sub_com.getName(), 
				sub_com.getTel(), 
				sub_com.getFax(),
				sub_com.getAddr(),
				sub_com.getEmail(),
				sub_com.getOutDate(),
				sub_com.getStatus(),
				sub_com.getId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public Sub_Com insertSubCom(Sub_Com sub_com) {
		String max_id = null;
		if(sub_coms[memory_pos].size() > 0)
			max_id = sub_coms[memory_pos].get(sub_coms[memory_pos].size() - 1).getId();
		max_id = common.generateDateSequenceId10(max_id);
		sub_com.setId(max_id);
		indexSearch = algo.binarySearchIndex(sub_coms[memory_pos], sub_com, new SubComIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(sub_com.getId() + ":는 존재하는 ID 입니다.");
			return null;
		}
		String sql = "insert into sub_com (com_id, id, name, tel, fax, addr, email, indate) values " +
				" (?, ?, ?, ?, ?, ?, ?, ?) ";
		
		java.sql.Date sqlInDate = null;
		if(sub_com.getInDate() != null) {
		    sqlInDate = new java.sql.Date(sub_com.getInDate().getTime());
		}
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				sub_com.getComId(),
				sub_com.getId(),
				sub_com.getName(), 
				sub_com.getTel(), 
				sub_com.getFax(),
				sub_com.getAddr(),
				sub_com.getEmail(),
//				sub_com.getInDate()
				sqlInDate
				));
		oraConn.queryInfosKey.add(key);
		return sub_com;
	}
	
	public Sub_Com searchSubComById(Sub_Com sub_com) {
		Sub_Com rcv_sub_com = (Sub_Com)algo.binarySearchObj(sub_coms[memory_pos], sub_com, new SubComIdComparator());
		return rcv_sub_com;
	}
	public List<Sub_Com> searchSubComByHeadComId(String id) {
		List<Sub_Com> list = new Vector<>();
		for(Sub_Com sub_com : sub_coms[memory_pos]) {
			if(sub_com.getComId().equals(id))
				list.add(sub_com);
		}
		return list;
	}
	
	public List<Sub_Com> searchSubComByName(String name) {
		List<Sub_Com> list = new Vector<>();
		int index;
		for(Sub_Com sub_com : sub_coms[memory_pos]) {
			index = sub_com.getName().indexOf(name, 0);
			if(index >= 0)
				list.add(sub_com);
		}
		return list;
	}
	
	public List<Sub_Com> searchSubComByStatus(byte status){
		List<Sub_Com> list = new Vector<>();
		for(Sub_Com sub_com : sub_coms[memory_pos]) {
			if(sub_com.getStatus() == status)
				list.add(sub_com);
		}
		return list;
	}
	
}
