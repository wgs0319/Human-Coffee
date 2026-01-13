package com.humancoffee.manager;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import com.humancoffee.model.Com_History;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;

public class ManageComHistorys {
	public List<Com_History>[] com_historys = (List<Com_History>[]) new List[2];
	public List<Com_History> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	private Common common = new Common();
	
	public class ComHistoryIdComparator implements Comparator<Com_History>{
		@Override
		public int compare(Com_History c1, Com_History c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(com_historys[0] != null)
			com_historys[0].clear();
		if(com_historys[1] != null)
			com_historys[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageComHistorys(){
		com_historys[0] = new Vector<>();
		com_historys[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	
	public void readComHistory(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select com_id, id, startdate, enddate, title, content, status " +
				" from com_history order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		com_historys[mem_pos].clear();
		System.out.println("readComHistory cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Com_History com_history = new Com_History();
			int col = 0;
//			System.out.println();
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_history.setComId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_history.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
				com_history.setStartDate(null);
			}else {
				com_history.setStartDate(Timestamp.valueOf(value));
//				System.out.println("getStartDateStr: " + common.getTypeDateToStr(com_history.getStartDate()));
			}
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
//				System.out.println("null 입력");
				com_history.setEndDate(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				com_history.setEndDate(Timestamp.valueOf(value));
//				System.out.println("getEndDateStr: " + common.getTypeDateToStr(com_history.getEndDate()));
			}
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_history.setTitle((value == null) ? "" : value);
				
//			value = Objects.toString(obj[row][col++]);
			value = common.getClobToString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_history.setContent((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			com_history.setStatus(Integer.parseInt(value));
			
			com_historys[mem_pos].add(com_history);
		}
		lists = com_historys[mem_pos];
	}
	

	public void updateComHistoryStatus(Com_History com_history) {

		indexSearch = algo.binarySearchIndex(com_historys[memory_pos], com_history, new ComHistoryIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(com_history.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update com_history set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				com_history.getStatus(), 
				com_history.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateComHistory(Com_History com_history) {
		indexSearch = algo.binarySearchIndex(com_historys[memory_pos], com_history, new ComHistoryIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(com_history.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update com_history set com_id = ?, startdate = ?, enddate = ?, title = ?, content = ?, status = ? " +
				" where id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				com_history.getComId(),
				com_history.getStartDate(), 
				com_history.getEndDate(), 
				com_history.getTitle(), 
				com_history.getContent(), 
				com_history.getStatus(), 
				com_history.getId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public Com_History insertComHistory(Com_History com_history) {
		String max_id = null;
		if(com_historys[memory_pos].size() > 0)
			max_id = com_historys[memory_pos].get(com_historys[memory_pos].size() - 1).getId();
		max_id = common.generateDateSequenceId10(max_id);
		com_history.setId(max_id);
		indexSearch = algo.binarySearchIndex(com_historys[memory_pos], com_history, new ComHistoryIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(com_history.getId() + ":는 존재하는 ID 입니다.");
			return null;
		}
		String sql = "insert into com_history (com_id, id, startdate, enddate, title, content) values " +
				" (?, ?, ?, ?, ?, ?) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				com_history.getComId(),
				com_history.getId(),
				com_history.getStartDate(), 
				com_history.getEndDate(), 
				com_history.getTitle(), 
				com_history.getContent()
				));
		oraConn.queryInfosKey.add(key);
		return com_history;
	}
	
	public Com_History searchComHistoryById(Com_History com_history) {
		Com_History rcv_com_history = (Com_History)algo.binarySearchObj(com_historys[memory_pos], com_history, new ComHistoryIdComparator());
		return rcv_com_history;
	}

	public List<Com_History> searchComHistoryByTitle(String title) {

		List<Com_History> list = new Vector<>();
		int index;
		for(Com_History com_history : com_historys[memory_pos]) {
			index = com_history.getTitle().indexOf(title, 0);
			if(index >= 0)
				list.add(com_history);
		}
		return list;
	}
	
	public List<Com_History> searchComHistoryByStatus(byte status){
		List<Com_History> list = new Vector<>();
		for(Com_History com_history : com_historys[memory_pos]) {
			if(com_history.getStatus() == status)
				list.add(com_history);
		}
		return list;
	}
	
}
