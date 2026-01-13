package com.humancoffee.manager;

import java.sql.Timestamp;
import java.sql.Blob;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;


import com.humancoffee.model.Product_Img;
import com.humancoffee.model.QueryInfo;

import com.humancoffee.common.*;
import com.humancoffee.dao.OraConnect;


public class ManageProductImgs {
	public List<Product_Img>[] product_imgs = (List<Product_Img>[]) new List[2];
	public List<Product_Img> lists;
	public byte memory_pos = 0;
	private int[] indexSearch = new int[2];
	
	public OraConnect oraConn;// = new OraConnect();
	private GenerateAlgorithm algo = new GenerateAlgorithm();
	private Common common = new Common();
	
	public class ProductImgIdComparator implements Comparator<Product_Img>{
		@Override
		public int compare(Product_Img c1, Product_Img c2) {
			return c1.getId().compareTo(c2.getId());
		}
	}
	
	public void exit() {
		if(product_imgs[0] != null)
			product_imgs[0].clear();
		if(product_imgs[1] != null)
			product_imgs[1].clear();
		
		if(lists != null)
			lists.clear();
	}
	
	public ManageProductImgs(){
		product_imgs[0] = new Vector<>();
		product_imgs[1] = new Vector<>();
		
		lists = new Vector<>();
	}
	

	public void readProductImg(byte mem_pos) {
		Object[][] obj;
		QueryInfo qi;
		String sql = "select product_id, id, div, filename, bfile, indate, outdate, status " +
				" from product_img order by id";
		qi = new QueryInfo(sql, new Object[0]);
		obj = oraConn.exeSelect(qi);
		if(obj == null)
			return;
		String value = "";
		product_imgs[mem_pos].clear();
		System.out.println("readProductImg cnt:" + obj.length);
		for(int row = 0; row < obj.length; row++) {
			Product_Img product_img = new Product_Img();
			int col = 0;
//			System.out.println();
			
			value = Objects.toString(obj[row][col++]);
			System.out.println(row + ":" + col + ":" + value);
			product_img.setProductId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
			System.out.println(row + ":" + col + ":" + value);
			product_img.setId((value == null) ? "" : value);
			
			value = Objects.toString(obj[row][col++]);
			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			product_img.setDiv(Integer.parseInt(value));
			
			value = Objects.toString(obj[row][col++]);
			System.out.println(row + ":" + col + ":" + value);
			product_img.setFilename((value == null) ? "" : value);
			
			System.out.println(row + ":" + (col + 1) + ":" + obj[row][col]);
			Object bFile = obj[row][col++];
			if( bFile == null ) {
				System.out.println("null 입력");
				product_img.setBFile(null); 
			}else {
				System.out.println("bFile Input ");
				product_img.setBFile((Blob) bFile);
			}
			
			value = Objects.toString(obj[row][col++], null);
			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty())
				product_img.setInDate(null);
			else
				product_img.setInDate(Timestamp.valueOf(value));
			
			value = Objects.toString(obj[row][col++], null);
			System.out.println(row + ":" + col + ":" + value);
			if(value == null || value.isEmpty()) {
				System.out.println("null 입력");
				product_img.setOutDate(null); 
			}else {
				System.out.println("setOutDate: " + Timestamp.valueOf(value));
				product_img.setOutDate(Timestamp.valueOf(value));
			}
			
			value = Objects.toString(obj[row][col++]);
			System.out.println(row + ":" + col + ":" + value);
			value = (value == null) ? "0" : value;
			product_img.setStatus(Integer.parseInt(value));
						
			product_imgs[mem_pos].add(product_img);
		}
		lists = product_imgs[mem_pos];
	}
	
	public void updateProductImgStatus(Product_Img product_img) {
		indexSearch = algo.binarySearchIndex(product_imgs[memory_pos], product_img, new ProductImgIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(product_img.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update product_img set status = ? " +
				" where id = ?";

		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				product_img.getStatus(), 
				product_img.getId()));
		oraConn.queryInfosKey.add(key);
	}
	
	public void updateProductImg(Product_Img product_img) {
		indexSearch = algo.binarySearchIndex(product_imgs[memory_pos], product_img, new ProductImgIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] != 0) {
			System.out.println(product_img.getId() + ":는 없는 ID 입니다.");
			return;
		}
		String sql = "update product_img set product_id = ?, div = ?, filename = ?, bfile = ?, outdate = ?, status = ? " +
				" where id = ?";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				product_img.getProductId(), 
				product_img.getDiv(), 
				product_img.getFilename(),
				product_img.getBFile(),
				product_img.getOutDate(),
				product_img.getStatus(),
				product_img.getId()
				));
		oraConn.queryInfosKey.add(key);
	}
	
	public Product_Img insertProductImg(Product_Img product_img) {
		String max_id = null;
		if(product_imgs[memory_pos].size() > 0)
			max_id = product_imgs[memory_pos].get(product_imgs[memory_pos].size() - 1).getId();
		max_id = common.generateDateSequenceId10(max_id);
		product_img.setId(max_id);
		indexSearch = algo.binarySearchIndex(product_imgs[memory_pos], product_img, new ProductImgIdComparator());
		if(indexSearch[algo.DEF_SEARCH_RESULT_POS] == 0) {
			System.out.println(product_img.getId() + ":는 존재하는 ID 입니다.");
			return null;
		}
		String sql = "insert into product_img (product_id, id, div, filename, bfile, indate) values " +
				" (?, ?, ?, ?, ?, ?) ";
		
		String key = this.getClass().getName() + "|" + String.valueOf(System.currentTimeMillis());
		oraConn.queryInfos.put(key, new QueryInfo(sql, 
				product_img.getProductId(), 
				product_img.getId(),
				product_img.getDiv(), 
				product_img.getFilename(),
				product_img.getBFile(),
				product_img.getInDate()
				));
		oraConn.queryInfosKey.add(key);
		return product_img;
	}
	
	public List<Product_Img> searchProductImgByProductId(String id) {
		List<Product_Img> list = new Vector<>();
		for(Product_Img product_img : product_imgs[memory_pos]) {
			if(product_img.getProductId().equals(id))
				list.add(product_img);
		}
		return list;
	}
	
	public List<Product_Img> searchProductImgByStatus(byte status){
		List<Product_Img> list = new Vector<>();
		for(Product_Img product_img : product_imgs[memory_pos]) {
			if(product_img.getStatus() == status)
				list.add(product_img);
		}
		return list;
	}
	
}
