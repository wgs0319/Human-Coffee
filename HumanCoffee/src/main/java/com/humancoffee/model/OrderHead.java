package com.humancoffee.model;

import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import com.humancoffee.model.OrderBottom;

public class OrderHead {
	public String order_id;
	public String member_id;
	public String customer_tel;
	public String customer_name;
	public int tot_price = 0;
	public int status = 0;
	public List<OrderBottom> order_bottom = new Vector<>();
	
}
