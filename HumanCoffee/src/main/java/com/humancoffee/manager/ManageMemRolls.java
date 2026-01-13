package com.humancoffee.manager;

import java.sql.Timestamp;
import java.sql.Blob;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import com.humancoffee.model.Mem_Roll;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;

public class ManageMemRolls {
	public List<Mem_Roll>[] mem_rolls = (List<Mem_Roll>[]) new List[2];
	public List<Mem_Roll> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	private Common common = new Common();
	
	public class MemRollIdComparator implements Comparator<Mem_Roll>{
		@Override
		public int compare(Mem_Roll c1, Mem_Roll c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(mem_rolls[0] != null)
			mem_rolls[0].clear();
		if(mem_rolls[1] != null)
			mem_rolls[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageMemRolls(){
		mem_rolls[0] = new Vector<>();
		mem_rolls[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	
	public void readMemRoll(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select id, name, roll, indate, outdate, status " +
				" from mem_roll order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		mem_rolls[mem_pos].clear();
		System.out.println("readMemRoll cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Mem_Roll mem_roll = new Mem_Roll();
			int col = 0;
//			System.out.println();
						
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			mem_roll.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			mem_roll.setName((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			mem_roll.setRoll(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty())
				mem_roll.setInDate(null);
			else
				mem_roll.setInDate(Timestamp.valueOf(value));
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
//				System.out.println("null 입력");
				mem_roll.setOutDate(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				mem_roll.setOutDate(Timestamp.valueOf(value));
			}
				
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			mem_roll.setStatus(Integer.parseInt(value));
			
			mem_rolls[mem_pos].add(mem_roll);
		}
		lists = mem_rolls[mem_pos];
	}
	
	public void updateMemRollStatus(Mem_Roll mem_roll) {
		indexSearch = algo.binarySearchIndex(mem_rolls[memory_pos], mem_roll, new MemRollIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(mem_roll.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update mem_roll set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				mem_roll.getStatus(), 
				mem_roll.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateMemRoll(Mem_Roll mem_roll) {
		indexSearch = algo.binarySearchIndex(mem_rolls[memory_pos], mem_roll, new MemRollIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(mem_roll.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update mem_roll set name = ?, roll = ?, indate = ?, outdate = ?, status = ? " +
				" where id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				mem_roll.getName(),
				mem_roll.getRoll(),
				mem_roll.getInDate(), 
				mem_roll.getOutDate(), 
				mem_roll.getStatus(), 
				mem_roll.getId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public Mem_Roll insertMemRoll(Mem_Roll mem_roll) {
		String max_id = null;
		if(mem_rolls[memory_pos].size() > 0)
			max_id = mem_rolls[memory_pos].get(mem_rolls[memory_pos].size() - 1).getId();
		max_id = common.generateDateSequenceId10(max_id);
		mem_roll.setId(max_id);
		indexSearch = algo.binarySearchIndex(mem_rolls[memory_pos], mem_roll, new MemRollIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(mem_roll.getId() + ":는 존재하는 ID 입니다.");
			return null;
		}
		String sql = "insert into mem_roll (id, name, roll, indate) values " +
				" (?, ?, ?, sysdate) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				mem_roll.getId(),
				mem_roll.getName(),
				mem_roll.getRoll()
				));
		oraConn.queryInfosKey.add(key);
		return mem_roll;
	}
	public Mem_Roll searchMemRollById(Mem_Roll mem_roll) {
		Mem_Roll rcv_mem_roll = (Mem_Roll)algo.binarySearchObj(mem_rolls[memory_pos], mem_roll, new MemRollIdComparator());
		return rcv_mem_roll;
	}
	
	public List<Mem_Roll> searchMemRollByStatus(byte status){
		List<Mem_Roll> list = new Vector<>();
		for(Mem_Roll mem_roll : mem_rolls[memory_pos]) {
			if(mem_roll.getStatus() == status)
				list.add(mem_roll);
		}
		return list;
	}
	
}
