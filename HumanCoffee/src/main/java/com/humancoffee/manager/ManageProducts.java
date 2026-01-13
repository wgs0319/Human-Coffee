package com.humancoffee.manager;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;


import com.humancoffee.model.Product;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;


public class ManageProducts {
	public List<Product>[] products = (List<Product>[]) new List[2];
	public List<Product> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	private Common common = new Common();
	
	public class ProductIdComparator implements Comparator<Product>{
		@Override
		public int compare(Product c1, Product c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(products[0] != null)
			products[0].clear();
		if(products[1] != null)
			products[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageProducts(){
		products[0] = new Vector<>();
		products[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	

	public void readProduct(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select id, name, price, point, div, content, indate, outdate, status " +
				" from product order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		products[mem_pos].clear();
		System.out.println("readProduct cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Product product = new Product();
			int col = 0;
//			System.out.println();
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			product.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			product.setName((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			product.setPrice(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			product.setPoint(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			product.setDiv(Integer.parseInt(value));
			
//			value = Objects.toString(obj[row][col++]);
			value = common.getClobToString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			product.setContent((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty())
				product.setInDate(null);
			else
				product.setInDate(Timestamp.valueOf(value));
			
			value = Objects.toString(obj[row][col++], null);
//			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
//				System.out.println("null 입력");
				product.setOutDate(null); 
			}else {
//				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				product.setOutDate(Timestamp.valueOf(value));
			}
			
			value = Objects.toString(obj[row][col++]);
//			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			product.setStatus(Integer.parseInt(value));
			
			products[mem_pos].add(product);
		}
		lists = products[mem_pos];
	}
	
	public void updateProductStatus(Product product) {
		indexSearch = algo.binarySearchIndex(products[memory_pos], product, new ProductIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(product.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update product set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				product.getStatus(), 
				product.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateProduct(Product product) {
		indexSearch = algo.binarySearchIndex(products[memory_pos], product, new ProductIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(product.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update product set name = ?, price = ?, point = ?, div = ?, content = ?, outdate = ?, status = ? " +
				" where id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				product.getName(), 
				product.getPrice(), 
				product.getPoint(),
				product.getDiv(),
				product.getContent(),
				product.getOutDate(),
				product.getStatus(),
				product.getId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public Product insertProduct(Product product) {
		String max_id = null;
		if(products[memory_pos].size() > 0)
			max_id = products[memory_pos].get(products[memory_pos].size() - 1).getId();
		max_id = common.generateDateSequenceId10(max_id);
		product.setId(max_id);
		indexSearch = algo.binarySearchIndex(products[memory_pos], product, new ProductIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(product.getId() + ":는 존재하는 ID 입니다.");
			return null;
		}
		String sql = "insert into product (id, name, price, point, div, content, indate) values " +
				" (?, ?, ?, ?, ?, ?, ?) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				product.getId(),
				product.getName(), 
				product.getPrice(), 
				product.getPoint(),
				product.getDiv(),
				product.getContent(),
				product.getInDate()
				));
		oraConn.queryInfosKey.add(key);
		return product;
	}
	public Product searchProductById(Product product) {
		Product rcv_product = (Product)algo.binarySearchObj(products[memory_pos], product, new ProductIdComparator());
		return rcv_product;
	}
	public List<Product> searchProductByName(String name) {
		List<Product> list = new Vector<>();
		int index;
		for(Product product : products[memory_pos]) {
			index = product.getName().indexOf(name, 0);
			if(index >= 0)
				list.add(product);
		}
		return list;
	}
	
	public List<Product> searchProductByStatus(byte status){
		List<Product> list = new Vector<>();
		for(Product product : products[memory_pos]) {
			if(product.getStatus() == status)
				list.add(product);
		}
		return list;
	}
	
}
