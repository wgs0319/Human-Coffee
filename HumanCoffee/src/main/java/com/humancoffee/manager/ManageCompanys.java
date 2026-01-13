package com.humancoffee.manager;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;


import com.humancoffee.model.Company;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;


public class ManageCompanys {
	public List<Company>[] companys = (List<Company>[]) new List[2];
	public List<Company> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	private Common common = new Common();
	
	public class CompanyIdComparator implements Comparator<Company>{
		@Override
		public int compare(Company c1, Company c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(companys[0] != null)
			companys[0].clear();
		if(companys[1] != null)
			companys[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageCompanys(){
		companys[0] = new Vector<>();
		companys[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	

	public void readCompany(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select id, name, tel, fax, addr, sale_email, eng_email, indate, outdate, status " +
				" from company order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		companys[mem_pos].clear();
		System.out.println("readCompany cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Company company = new Company();
			int col = 0;
//			System.out.println();
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			company.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			company.setName((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			company.setTel((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			company.setFax((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			company.setAddr((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			company.setSaleEmail((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			company.setEngEmail((value == null) ? "" : value);
						
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty())
				company.setInDate(null);
			else
				company.setInDate(Timestamp.valueOf(value));
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
//				System.out.println("null 입력");
				company.setOutDate(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				company.setOutDate(Timestamp.valueOf(value));
			}
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			company.setStatus(Integer.parseInt(value));
			
			companys[mem_pos].add(company);
		}
		lists = companys[mem_pos];
	}
	
	public void updateCompanyStatus(Company company) {
		indexSearch = algo.binarySearchIndex(companys[memory_pos], company, new CompanyIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(company.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update company set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				company.getStatus(), 
				company.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateCompany(Company company) {
		indexSearch = algo.binarySearchIndex(companys[memory_pos], company, new CompanyIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(company.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update company set name = ?, tel = ?, fax = ?, addr = ?, sale_email = ?, eng_email = ?, outdate = ?, status = ? " +
				" where id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				company.getName(), 
				company.getTel(), 
				company.getFax(),
				company.getAddr(),
				company.getSaleEmail(),
				company.getEngEmail(),
				company.getOutDate(),
				company.getStatus(),
				company.getId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public Company insertCompany(Company company) {
		String max_id = null;
		if(companys[memory_pos].size() > 0)
			max_id = companys[memory_pos].get(companys[memory_pos].size() - 1).getId();
		max_id = common.generateDateSequenceId10(max_id);
		company.setId(max_id);
		indexSearch = algo.binarySearchIndex(companys[memory_pos], company, new CompanyIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(company.getId() + ":는 존재하는 ID 입니다.");
			return null;
		}
		String sql = "insert into company (id, name, tel, fax, addr, sale_email, eng_email, indate) values " +
				" (?, ?, ?, ?, ?, ?, ?, ?) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				company.getId(),
				company.getName(), 
				company.getTel(), 
				company.getFax(),
				company.getAddr(),
				company.getEngEmail(),
				company.getSaleEmail(),
				company.getInDate()
				));
		oraConn.queryInfosKey.add(key);
		return company;
	}
	
	public Company searchCompanyById(Company company) {
		Company rcv_company = (Company)algo.binarySearchObj(companys[memory_pos], company, new CompanyIdComparator());
		return rcv_company;
	}
	public List<Company> searchCompanyByName(String name) {
		List<Company> list = new Vector<>();
		int index;
		for(Company company : companys[memory_pos]) {
			index = company.getName().indexOf(name, 0);
			if(index >= 0)
				list.add(company);
		}
		return list;
	}
	
	public List<Company> searchCompanyByStatus(byte status){
		List<Company> list = new Vector<>();
		for(Company company : companys[memory_pos]) {
			if(company.getStatus() == status)
				list.add(company);
		}
		return list;
	}
	
}
