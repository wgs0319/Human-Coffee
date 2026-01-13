package com.humancoffee.manager;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import com.humancoffee.model.Cupon;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;

public class ManageCupons {
	public List<Cupon>[] cupons = (List<Cupon>[]) new List[2];
	public List<Cupon> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	private Common common = new Common();
	
	public class CuponIdComparator implements Comparator<Cupon>{
		@Override
		public int compare(Cupon c1, Cupon c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(cupons[0] != null)
			cupons[0].clear();
		if(cupons[1] != null)
			cupons[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageCupons(){
		cupons[0] = new Vector<>();
		cupons[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	

	public void readCupon(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select id, name, point, indate, outdate, status " +
				" from cupon order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		cupons[mem_pos].clear();
		System.out.println("readCupon cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Cupon cupon = new Cupon();
			int col = 0;
//			System.out.println();
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			cupon.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			cupon.setName((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			cupon.setPoint(Integer.parseInt(value));

			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty())
				cupon.setInDate(null);
			else
				cupon.setInDate(Timestamp.valueOf(value));
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
//				System.out.println("null 입력");
				cupon.setOutDate(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				cupon.setOutDate(Timestamp.valueOf(value));
			}
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			cupon.setStatus(Integer.parseInt(value));
			
			cupons[mem_pos].add(cupon);
		}
		lists = cupons[mem_pos];
	}
	
	public void updateCuponStatus(Cupon cupon) {
		indexSearch = algo.binarySearchIndex(cupons[memory_pos], cupon, new CuponIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(cupon.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update cupon set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				cupon.getStatus(), 
				cupon.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateCupon(Cupon cupon) {
		indexSearch = algo.binarySearchIndex(cupons[memory_pos], cupon, new CuponIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(cupon.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update cupon set name = ?, point = ?, outdate = ?, status = ? " +
				" where id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				cupon.getName(), 
				cupon.getPoint(), 
				cupon.getOutDate(), 
				cupon.getStatus(), 
				cupon.getId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public Cupon insertCupon(Cupon cupon) {
		String max_id = null;
		if(cupons[memory_pos].size() > 0)
			max_id = cupons[memory_pos].get(cupons[memory_pos].size() - 1).getId();
		max_id = common.generateDateSequenceId10(max_id);
		cupon.setId(max_id);
		indexSearch = algo.binarySearchIndex(cupons[memory_pos], cupon, new CuponIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(cupon.getId() + ":는 존재하는 ID 입니다.");
			return null;
		}
		String sql = "insert into cupon (id, name, point, indate) values " +
				" (?, ?, ?, ?) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				cupon.getId(),
				cupon.getName(), 
				cupon.getPoint(),
				cupon.getInDate()
				));
		oraConn.queryInfosKey.add(key);
		return cupon;
	}

	public Cupon searchCuponById(Cupon cupon) {
		Cupon rcv_cupon = (Cupon)algo.binarySearchObj(cupons[memory_pos], cupon, new CuponIdComparator());
		return rcv_cupon;
	}
	public List<Cupon> searchCuponByName(String name) {
		List<Cupon> list = new Vector<>();
		int index;
		for(Cupon cupon : cupons[memory_pos]) {
			index = cupon.getName().indexOf(name, 0);
			if(index >= 0)
				list.add(cupon);
		}
		return list;
	}
	
	public List<Cupon> searchCuponByStatus(byte status){
		List<Cupon> list = new Vector<>();
		for(Cupon cupon : cupons[memory_pos]) {
			if(cupon.getStatus() == status)
				list.add(cupon);
		}
		return list;
	}

}
