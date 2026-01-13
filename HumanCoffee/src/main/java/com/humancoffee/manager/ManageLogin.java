package com.humancoffee.manager;

import com.humancoffee.common.*;
import com.humancoffee.model.Customer;
import com.humancoffee.model.Com_Member;
import com.humancoffee.model.Mem_Roll;


public class ManageLogin {

	private GenerateAlgorithm algo = new GenerateAlgorithm();
	
	public ManageCustomers mCustomers;// = new ManageCustomers();
	public ManageComMembers mComMembers;// = new ManageSellers();
	public ManageMemRolls mMemRolls;
	public ManageMemRolls.MemRollIdComparator mMemRollIdComparator;

//	public String currentUser;
	
//	private byte	divCurrentUser;		//	1 : admin, 2 : seller, 4 : customer
//	private int		userRoll;
	private final static byte DIV_ADMIN = 1;
	private final static byte DIV_MEMBER = 2;
	private final static byte DIV_CUSTOMER = 4;
	
	public final String ADMIN_ID = "admin";
	private final static String ADMIN_PWD = "password";
	
	/*********************	LogIn Out ***********************/
	//	return login user의 roll값 리턴으로 수정
	public int login(String id, String password) {
		String	currentUser;
		byte	divCurrentUser;
		int		userRoll;
		System.out.println("id: " + id + ", pwd: " + password);
		System.out.println("customer size: " + mCustomers.customers[mCustomers.memory_pos].size());
		System.out.println("com_member size: " + mComMembers.com_members[mComMembers.memory_pos].size());

		if((id.equals(ADMIN_ID)) && password.equals(ADMIN_PWD)) {
			currentUser = id;
			divCurrentUser = DIV_ADMIN;
			userRoll = 7;
			return userRoll;
//			return true;
		}
		String password_sha256 = algo.generateSha256(id, password);
		for(Customer customer : mCustomers.customers[mCustomers.memory_pos]) {
//			if(customer.getCustomer_id().equals(id) && customer.getCustomer_pwd().equals(password)) {
//			if(customer.getId().equals(id) && customer.getPwd().equals(password_sha256)) {
			if(customer.getId().equals(id)) {
				System.out.println("customer");
				System.out.println("new pwd : " + password_sha256);
				System.out.println("db pwd : " + customer.getPwd());
				if(customer.getPwd().equals(password_sha256)) {
					currentUser = id;
					divCurrentUser = DIV_CUSTOMER;
					userRoll = 0;
					return userRoll;
	//				return true;
				}
			}
		}
		for(Com_Member com_member : mComMembers.com_members[mComMembers.memory_pos]) {
//			if(com_member.getId().equals(id) && com_member.getPwd().equals(password_sha256)) {
			if(com_member.getId().equals(id)) {
				System.out.println("com_member");
				System.out.println("new pwd : " + password_sha256);
				System.out.println("db pwd : " + com_member.getPwd());
				if(com_member.getPwd().equals(password_sha256)) {
					currentUser = id;
					divCurrentUser = DIV_MEMBER;
	
					Mem_Roll mem_roll = new Mem_Roll();
					mem_roll.setId(com_member.getRollId());
					
					Mem_Roll chk_mem_roll = (Mem_Roll)algo.binarySearchObj(mMemRolls.mem_rolls[mMemRolls.memory_pos], mem_roll, mMemRollIdComparator);
					if(chk_mem_roll == null) {
						System.out.println(mem_roll.getId() + ":는 없는 Roll ID 입니다.");
						userRoll = 0;
					}else {
						System.out.println(mem_roll.getId() + ":는 있는 Roll ID 입니다.");
						userRoll = chk_mem_roll.getRoll();
					}
	
					return userRoll;
				}
			}
		}
		return -1;
		
	}
}
