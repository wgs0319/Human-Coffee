package com.humancoffee.manager;

import java.sql.Timestamp;
import java.sql.Blob;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import com.humancoffee.model.Com_Ci;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;

public class ManageComCis {
	public List<Com_Ci>[] com_cis = (List<Com_Ci>[]) new List[2];
	public List<Com_Ci> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	private Common common = new Common();
	
	public class ComCiIdComparator implements Comparator<Com_Ci>{
		@Override
		public int compare(Com_Ci c1, Com_Ci c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(com_cis[0] != null)
			com_cis[0].clear();
		if(com_cis[1] != null)
			com_cis[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageComCis(){
		com_cis[0] = new Vector<>();
		com_cis[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	
	public void readComCi(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select com_id, id, indate, outdate, filename, bfile, status " +
				" from com_ci order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		com_cis[mem_pos].clear();
		System.out.println("readComCi cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Com_Ci com_ci = new Com_Ci();
			int col = 0;
//			System.out.println();
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_ci.setComId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_ci.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty())
				com_ci.setInDate(null);
			else
				com_ci.setInDate(Timestamp.valueOf(value));
			
			value = Objects.toString(obj[row][col++], null);
			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
//				System.out.println("null 입력");
				com_ci.setOutDate(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				com_ci.setOutDate(Timestamp.valueOf(value));
			}
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			com_ci.setFilename((value == null) ? "" : value);
			
			Object bFile = obj[row][col++];
			if( bFile == null ) {
//				System.out.println("null 입력");
				com_ci.setBFile(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				com_ci.setBFile((Blob) bFile);
			}
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			com_ci.setStatus(Integer.parseInt(value));
			
			com_cis[mem_pos].add(com_ci);
		}
		lists = com_cis[mem_pos];
	}
	
	public void updateComCiStatus(Com_Ci com_ci) {
		indexSearch = algo.binarySearchIndex(com_cis[memory_pos], com_ci, new ComCiIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(com_ci.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update com_ci set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				com_ci.getStatus(), 
				com_ci.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateComCi(Com_Ci com_ci) {
		indexSearch = algo.binarySearchIndex(com_cis[memory_pos], com_ci, new ComCiIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(com_ci.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update com_ci set com_id = ?, indate = ?, outdate = ?, filename = ?, bfile = ?, status = ? " +
				" where id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				com_ci.getComId(),
				com_ci.getInDate(), 
				com_ci.getOutDate(), 
				com_ci.getFilename(), 
				com_ci.getBFile(), 
				com_ci.getStatus(), 
				com_ci.getId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public Com_Ci insertComCi(Com_Ci com_ci) {
		String max_id = null;
		if(com_cis[memory_pos].size() > 0)
			max_id = com_cis[memory_pos].get(com_cis[memory_pos].size() - 1).getId();
		max_id = common.generateDateSequenceId10(max_id);
		com_ci.setId(max_id);
		indexSearch = algo.binarySearchIndex(com_cis[memory_pos], com_ci, new ComCiIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(com_ci.getId() + ":는 존재하는 ID 입니다.");
			return null;
		}
		String sql = "insert into com_ci (com_id, id, indate, outdate, filename, bfile) values " +
				" (?, ?, ?, ?, ?, ?) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				com_ci.getComId(),
				com_ci.getId(),
				com_ci.getInDate(), 
				com_ci.getOutDate(), 
				com_ci.getFilename(), 
				com_ci.getBFile()
				));
		oraConn.queryInfosKey.add(key);
		return com_ci;
	}
/*	
	private List<Com_Ci> searchComHistoryByTitle(String title) {
		List<Com_History> list = new Vector<>();
		int index;
		for(Com_History com_history : com_historys[memory_pos]) {
			index = com_history.getTitle().indexOf(title, 0);
			if(index >= 0)
				list.add(com_history);
		}
		return list;
	}*/
	public Com_Ci searchComCiById(Com_Ci com_ci) {
		Com_Ci rcv_com_ci = (Com_Ci)algo.binarySearchObj(com_cis[memory_pos], com_ci, new ComCiIdComparator());
		return rcv_com_ci;
	}
	
	public List<Com_Ci> searchComCiByStatus(byte status){
		List<Com_Ci> list = new Vector<>();
		for(Com_Ci com_ci : com_cis[memory_pos]) {
			if(com_ci.getStatus() == status)
				list.add(com_ci);
		}
		return list;
	}
	
}
