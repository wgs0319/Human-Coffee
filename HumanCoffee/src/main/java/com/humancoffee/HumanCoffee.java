package com.humancoffee;

import com.humancoffee.dao.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humancoffee.common.*;
import com.humancoffee.manager.*;

import com.humancoffee.model.*;

import com.humancoffee.websocket.OrderWebSocket;
 
public class HumanCoffee {

	private Common common;
    public OraConnect oraConn;
    private CtrlScanner ctrlScanner = new CtrlScanner();
    
    private OrderWebSocket mOrderWebSocket;
    public List<OrderHead> mOrderHead;
    
    public ManageMemRolls mMemRolls;
    public ManageOrderDetails mOrderDetails;
    public ManageMyOrders mOrders;
    public ManageCupons mCupons;
    public ManageProductImgs mProductImgs;
    public ManageProducts mProducts;
    public ManageComCis mComCis;
    public ManageComHistorys mComHistorys;
    public ManageCompanys mCompanys;
    public ManageComMembers mComMems;
    public ManageSubComs mSubComs;
    public ManageCustomers mCustomers;// = new ManageCustomers();
    public ManageLogin mLogin;

    private Thread workerThread;
    private volatile boolean running = true;
    
    public HumanCoffee() {
    	common = new Common();
        oraConn = new OraConnect("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@1.220.247.78:1522/orcl", "project2_2504_team2", "1234");
        if(!oraConn.connect()) {
            System.out.println("Database connect error");
            System.exit(0);
        }
        
        mOrderWebSocket = new OrderWebSocket();
        mOrderHead = new Vector<>();
        
        mMemRolls = new ManageMemRolls();
//        System.out.println("new mMemRolls");
        mMemRolls.oraConn = this.oraConn;
//        System.out.println("mMemRolls.oraConn = this.oraConn");
        mMemRolls.readMemRoll(mMemRolls.memory_pos);
//        System.out.println("mMemRolls.readMemRoll(" + mMemRolls.memory_pos + ")");

        mOrderDetails = new ManageOrderDetails();
//        System.out.println("new mOrderDetails");
        mOrderDetails.oraConn = this.oraConn;
//        System.out.println("mOrderDetails.oraConn = this.oraConn");
        mOrderDetails.readOrderDetail(mOrderDetails.memory_pos);
//        System.out.println("mOrderDetails.readOrderDetail(" + mOrderDetails.memory_pos + ")");
        
        mOrders = new ManageMyOrders();
//        System.out.println("new mOrders");
        mOrders.oraConn = this.oraConn;
//        System.out.println("mOrders.oraConn = this.oraConn");
        mOrders.readMyOrder(mOrders.memory_pos);
//        System.out.println("mOrders.readMyOrder(" + mOrders.memory_pos + ")");
        
        mCupons = new ManageCupons();
//        System.out.println("new mCupons");
        mCupons.oraConn = this.oraConn;
//        System.out.println("mCupons.oraConn = this.oraConn");
        mCupons.readCupon(mCupons.memory_pos);
//        System.out.println("mCupons.readCupon(" + mCupons.memory_pos + ")");
        
        mProductImgs = new ManageProductImgs();
//        System.out.println("new mProductImgs");
        mProductImgs.oraConn = this.oraConn;
//        System.out.println("mProductImgs.oraConn = this.oraConn");
        mProductImgs.readProductImg(mProductImgs.memory_pos);
//        System.out.println("mProductImgs.readProductImg(" + mProductImgs.memory_pos + ")");
        
        mProducts = new ManageProducts();
//        System.out.println("new mProducts");
        mProducts.oraConn = this.oraConn;
//        System.out.println("mProducts.oraConn = this.oraConn");
        mProducts.readProduct(mProducts.memory_pos);
//        System.out.println("mProducts.readProduct(" + mProducts.memory_pos + ")");
        
        mComCis = new ManageComCis();
//        System.out.println("new mComCis");
        mComCis.oraConn = this.oraConn;
//        System.out.println("mComCis.oraConn = this.oraConn");
        mComCis.readComCi(mComCis.memory_pos);
//        System.out.println("mComCis.readComCi(" + mComCis.memory_pos + ")");
        
        mComHistorys = new ManageComHistorys();
//        System.out.println("new mComHistorys");
        mComHistorys.oraConn = this.oraConn;
//        System.out.println("mComHistorys.oraConn = this.oraConn");
        mComHistorys.readComHistory(mComHistorys.memory_pos);
//        System.out.println("mComHistorys.readComHistory(" + mComHistorys.memory_pos + ")");
        
        mCompanys = new ManageCompanys();
//        System.out.println("new mCompanys");
        mCompanys.oraConn = this.oraConn;
//        System.out.println("mCompanys.oraConn = this.oraConn");
        mCompanys.readCompany(mCompanys.memory_pos);
//        System.out.println("mCompanys.readCompany(" + mCompanys.memory_pos + ")");
        
        mComMems = new ManageComMembers();
//        System.out.println("new mComMems");
        mComMems.oraConn = this.oraConn;
//        System.out.println("mComMems.oraConn = this.oraConn");
        mComMems.readComMember(mComMems.memory_pos);
//        System.out.println("mComMems.readComMember(" + mComMems.memory_pos + ")");
                
        mSubComs = new ManageSubComs();
//        System.out.println("new ManageSubComs");
        mSubComs.oraConn = this.oraConn;
//        System.out.println("mSubComs.oraConn = this.oraConn");
        mSubComs.readSubCom(mSubComs.memory_pos);
//        System.out.println("mSubComs.readSubCom(" + mSubComs.memory_pos + ")");
        
        mCustomers = new ManageCustomers();
//        System.out.println("new ManageCustomers");
        mCustomers.oraConn = this.oraConn;
//        System.out.println("mCustomers.oraConn = this.oraConn");
        mCustomers.readCustomer(mCustomers.memory_pos);
//        System.out.println("mCustomers.readCustomer(" + mCustomers.memory_pos + ")");
        
        
        mLogin = new ManageLogin();
        mLogin.mCustomers = this.mCustomers;
        mLogin.mComMembers = this.mComMems;
        mLogin.mMemRolls = this.mMemRolls;
        mLogin.mMemRollIdComparator = this.mMemRolls.new MemRollIdComparator();
        
        mComMems.mCustomers = this.mCustomers;
        mComMems.mCustomerIdComparator = this.mCustomers.new CustomerIdComparator();
        mCustomers.mComMembers = this.mComMems;
        mCustomers.mComMemberIdComparator = this.mComMems.new ComMemberIdComparator();

    }
    
    private String getKeyToClassName(String key) {
    	int index = key.indexOf("|");
        String rcvClass = key.substring(0, index);
        while(true) {
            index = rcvClass.indexOf(".");
            if(index < 0)
                break;
            rcvClass = rcvClass.substring(index + 1);
        }
        return rcvClass;
    }
    
    private void putWebOrder(OrderHead orderhead) {
    	boolean bComplete = false;
    	int loop = 0, sub = 0;
    	System.out.println("putWebOrder:" + orderhead);
    	if(mOrderHead.size() <= 0) {
    		mOrderHead.add(orderhead);
    		if(orderhead.status == 1) {	//	처리완료
    			System.out.println("1status:1");
    			bComplete = true;
    		}
    			
    	}else {
    		for(loop = 0; loop < mOrderHead.size(); loop++) {
    			if(orderhead.order_id.equals(mOrderHead.get(loop).order_id)) {
    				//	동일한 order_id가 있는 상황
    				int head_price = mOrderHead.get(loop).tot_price;
    				int bottom_price = 0;
    				if(orderhead.order_bottom.size() > 0) {
    					//	입력받은 orderhead의 order_bottom이 있는 경우에만 수행
    					for(sub = 0; sub < mOrderHead.get(loop).order_bottom.size(); sub++) {
    						
    						if(orderhead.order_bottom.get(0).product_id.equals(mOrderHead.get(loop).order_bottom.get(sub).product_id)) {
    							//	order_id와 product_id가 동일한게 존재. 즉, 수정.
    							mOrderHead.get(loop).order_bottom.set(sub, orderhead.order_bottom.get(0));
    							bottom_price += orderhead.order_bottom.get(0).tot_price;
    							break;
    						}
    						bottom_price += mOrderHead.get(loop).order_bottom.get(sub).tot_price;
    					}
    					if(sub >= mOrderHead.get(loop).order_bottom.size()) {
    						//	order_id는 동일하지만 product_id가 존재하지 않음.
    						mOrderHead.get(loop).order_bottom.add((OrderBottom)orderhead.order_bottom.get(0));
    						bottom_price += orderhead.order_bottom.get(0).tot_price;
    					}
    				}else {
//    					입력받은 orderhead의 order_bottom이 없는 경우. 즉, 주문 상품의 상태변경일 가능성이 높음.
    					mOrderHead.set(loop, orderhead);
    					head_price = orderhead.tot_price;
    				}
    				if(head_price == bottom_price) {
    					System.out.println("head_price:" + head_price + ",bottom_price:" + bottom_price);
    	    			bComplete = true;
    				}
    				break;
    			}
    		}
    		
    		if(loop >= mOrderHead.size()) {
    			mOrderHead.add(orderhead);
    			if(orderhead.status == 1) {	//	처리완료
    				System.out.println("2status:1");
    				bComplete = true;
    			}
    		}
    	}
    	if(bComplete)
    		sendOrder();
    }
    private void sendOrder() {
    	System.out.println("1sendOrder[" + mOrderHead.size() + "]");
    	if(mOrderHead.size() >= 0) {
    		System.out.println("2sendOrder[" + mOrderHead.size() + "]: " + mOrderHead);
    		try {
				mOrderWebSocket.broadcast(mOrderHead);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		mOrderHead.clear();
    	}
    }
    private boolean chkDuplicateClass(String rcvClass) {
    	String myClass;
    	for(int loop = 0; loop < oraConn.queryEndsKey.size(); loop++) {
    		myClass = oraConn.queryEndsKey.get(loop);
    		if(myClass.equals(rcvClass))
    			return true;
    	}
    	return false;
    }
    
    public void queryReadExe() {
        synchronized(oraConn.queryInfosKey) { // oraConn.queryInfosKey 객체를 기준으로 동기화
            if(oraConn.queryInfosKey == null || oraConn.conn == null)
                return;
            if(oraConn.queryInfosKey.size() <= 0)
                return;
//            System.out.println("oraConn.queryInfosKey : " + oraConn.queryInfosKey);
            System.out.println("oraConn.queryInfosKey.size() : " + oraConn.queryInfosKey.size());
            int loop;
            try {
                oraConn.conn.setAutoCommit(false);
                while(oraConn.queryInfosKey.size() > 0) {
                    String key = oraConn.queryInfosKey.get(0);
                    System.out.println("2 queryInfoExe key : " + key);
                    QueryInfo qi = oraConn.queryInfos.get(key);
                    if(qi.status == 0) {
                    	System.out.println("rcv sql: " + qi.getSql());
                        try(PreparedStatement pstmt = oraConn.conn.prepareStatement(qi.getSql())){
	                        System.out.println("qi : " + qi.getParams() + ", len : " + qi.getParams().length);
	                        if(qi.getParams() != null) {
	                            for(loop = 0; loop < qi.getParams().length; loop++) {
	                            	System.out.println("pre pos[" + loop + "] : " + qi.getParams()[loop]);
	                                pstmt.setObject(loop + 1, qi.getParams()[loop]);
	                            }
	                        }
	 //                       System.out.println("pre addBatch");
	                        pstmt.addBatch();
	 //                       System.out.println("pre executeBatch");
	                        pstmt.executeBatch();
	                        qi.status = 1;
	 //                       System.out.println("pre replace");
	                        oraConn.queryInfos.replace(key, qi);
                        }catch(Exception e) {
                        	String strException = common.getStackTraceAsString(e);
                        	System.out.println("queryReadExe Exception:\n" + strException);
                        }
                        
                    }
                    System.out.println("insert key : " + key);
                    String className = getKeyToClassName(key);
                    System.out.println("className : " + className);
                    if(className.equals("ManageMyOrders")) {
                    	// 	Query문 변경 시(항목위치) 확인 필요.
                    	//	주문 정보가 들어옴.
                    	//	입력, 수정 판단이 필요.
                    	OrderHead orderhead = new OrderHead();
                    	String sql = qi.getSql().toLowerCase();
                    	Customer customer = new Customer();
                    	int idx = sql.indexOf("insert", 0);
                    	if(idx >= 0) {
                    		//	입력
                    		orderhead.order_id = Objects.toString(qi.getParams()[0]);
                    		orderhead.member_id = Objects.toString(qi.getParams()[1]);
                    		orderhead.customer_tel = Objects.toString(qi.getParams()[2]);
                    		customer = mCustomers.searchCustomerByTel(orderhead.customer_tel);
                    		if(customer != null)
                    			orderhead.customer_name = customer.getName();
                    		orderhead.tot_price = Integer.parseInt(Objects.toString(qi.getParams()[3]));
                    		orderhead.status = 0;
                    	}else {
                    		//	수정
                    		idx = sql.indexOf("member_id", 0);
                    		if(idx >= 0) {
                    			//	전체 수정
                    			orderhead.order_id = Objects.toString(qi.getParams()[9]);
                    			orderhead.member_id = Objects.toString(qi.getParams()[0]);
                        		orderhead.customer_tel = Objects.toString(qi.getParams()[1]);
                        		customer = mCustomers.searchCustomerByTel(orderhead.customer_tel);
                        		if(customer != null)
                        			orderhead.customer_name = customer.getName();
                        		orderhead.tot_price = Integer.parseInt(Objects.toString(qi.getParams()[2]));
                        		orderhead.status = Integer.parseInt(Objects.toString(qi.getParams()[8]));
                    		}else {
                    			//	status만 수정
                    			orderhead.order_id = Objects.toString(qi.getParams()[1]);
                        		orderhead.status = Integer.parseInt(Objects.toString(qi.getParams()[0]));
                    		}
                    	}
                    	putWebOrder(orderhead);
                    }
                    if(className.equals("ManageOrderDetails")) {
                    	//	Query문 변경 시(항목위치) 확인 필요.
                    	// 	주문 정보가 들어옴.
                    	//	입력, 수정 판단이 필요.
                    	OrderHead orderhead = new OrderHead();
                    	OrderBottom orderbottom = new OrderBottom();
                    	Product product = new Product();
                    	String sql = qi.getSql().toLowerCase();
                    	int idx = sql.indexOf("insert", 0);
                    	if(idx >= 0) {
                    		//	입력
                    		orderhead.order_id = Objects.toString(qi.getParams()[0]);
                    		orderbottom.product_id = Objects.toString(qi.getParams()[1]);
                    		product.setId(orderbottom.product_id);
                    		product = mProducts.searchProductById(product);
                    		if(product != null)
                    			orderbottom.product_name = product.getName();
                    		orderbottom.cnt = Integer.parseInt(Objects.toString(qi.getParams()[2]));
                    		orderbottom.tot_price = Integer.parseInt(Objects.toString(qi.getParams()[3]));
                    		orderhead.order_bottom.add(orderbottom);
//                    		orderhead.order_bottom.set(0, orderbottom);
                    	}
                    	idx = sql.indexOf("delete", 0);
                    	if(idx >= 0)
                    	{
                    		//	주문취소
                    		orderhead.order_id = Objects.toString(qi.getParams()[0]);
                    		orderbottom.product_id = Objects.toString(qi.getParams()[1]);
                    		product.setId(orderbottom.product_id);
                    		product = mProducts.searchProductById(product);
                    		if(product != null)
                    			orderbottom.product_name = product.getName();
                    		orderbottom.cnt = 0;
                    		orderbottom.delete = 1;
                    		orderhead.order_bottom.add(orderbottom);
                    	}
                    	idx = sql.indexOf("update", 0);
                    	if(idx >= 0)
                    	{
                    		//	수정
                    		orderhead.order_id = Objects.toString(qi.getParams()[3]);
                    		orderbottom.product_id = Objects.toString(qi.getParams()[2]);
                    		product.setId(orderbottom.product_id);
                    		product = mProducts.searchProductById(product);
                    		if(product != null)
                    			orderbottom.product_name = product.getName();
                    		orderbottom.cnt = Integer.parseInt(Objects.toString(qi.getParams()[0]));
                    		orderbottom.tot_price = Integer.parseInt(Objects.toString(qi.getParams()[1]));
                    		orderhead.order_bottom.add(orderbottom);
                    	}
                    	putWebOrder(orderhead);
                    }
                    if(!chkDuplicateClass(className)) {
                    	oraConn.queryEndsKey.add(className);
                    	System.out.println("Add className : " + className);
                    }else {
                    	System.out.println("Duplicate className : " + className);
                    }
                    System.out.println("remove key : " + key);
                    oraConn.queryInfos.remove(key);
                    oraConn.queryInfosKey.remove(key);
//                    String strScanner = ctrlScanner.getStrByScanner();
                }
                oraConn.conn.commit();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                if(oraConn.conn != null) {
                    try {
                        oraConn.conn.rollback();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }finally {
                if(oraConn.conn != null) {
                    try {
                        oraConn.conn.setAutoCommit(true);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("oraConn.queryEndsKey size : " + oraConn.queryEndsKey.size());
            while(oraConn.queryEndsKey.size() > 0) {
/*                String key = oraConn.queryEndsKey.get(0);
                System.out.println("key : " + key);
                
                int index = key.indexOf("|");
                String className = key.substring(0, index);
                while(true) {
                    index = className.indexOf(".");
                    if(index < 0)
                        break;
                    className = className.substring(index + 1);
                }
                System.out.println("className : " + className);*/
            	String className = oraConn.queryEndsKey.get(0);
            	System.out.println("EndsKey className : " + className);
                byte new_pos;
                switch(className) {
                    case "ManageCustomers":
                        new_pos = (byte)(mCustomers.memory_pos ^ 0x01);
                        mCustomers.readCustomer(new_pos);
                        mCustomers.memory_pos = new_pos;
                        break;
                    case "ManageSubComs":
                    	new_pos = (byte)(mSubComs.memory_pos ^ 0x01);
                    	mSubComs.readSubCom(new_pos);
                    	mSubComs.memory_pos = new_pos;
                    	break;
                    case "ManageMemRolls":
                    	new_pos = (byte)(mMemRolls.memory_pos ^ 0x01);
                    	mMemRolls.readMemRoll(new_pos);
                    	mMemRolls.memory_pos = new_pos;
                    	break;
                    case "ManageComMembers":
                    	new_pos = (byte)(mComMems.memory_pos ^ 0x01);
                    	mComMems.readComMember(new_pos);
                    	mComMems.memory_pos = new_pos;
                    	break;
                    case "ManageCompanys":
                    	new_pos = (byte)(mCompanys.memory_pos ^ 0x01);
                    	mCompanys.readCompany(new_pos);
                    	mCompanys.memory_pos = new_pos;
                    	break;
                    case "ManageComHistorys":
                    	new_pos = (byte)(mComHistorys.memory_pos ^ 0x01);
                    	mComHistorys.readComHistory(new_pos);
                    	mComHistorys.memory_pos = new_pos;
                    	break;
                    case "ManageComCis":
                    	new_pos = (byte)(mComCis.memory_pos ^ 0x01);
                    	mComCis.readComCi(new_pos);
                    	mComCis.memory_pos = new_pos;
                    	break;
                    case "ManageProducts":
                    	new_pos = (byte)(mProducts.memory_pos ^ 0x01);
                    	mProducts.readProduct(new_pos);
                    	mProducts.memory_pos = new_pos;
                    	break;
                    case "ManageProductImgs":
                    	new_pos = (byte)(mProductImgs.memory_pos ^ 0x01);
                    	mProductImgs.readProductImg(new_pos);
                    	mProductImgs.memory_pos = new_pos;
                    	break;
                    case "ManageCupons":
                    	new_pos = (byte)(mCupons.memory_pos ^ 0x01);
                    	mCupons.readCupon(new_pos);
                    	mCupons.memory_pos = new_pos;
                    	break;
                    case "ManageMyOrders":
                    	new_pos = (byte)(mOrders.memory_pos ^ 0x01);
                    	mOrders.readMyOrder(new_pos);
                    	mOrders.memory_pos = new_pos;
                    	break;
                    case "ManageOrderDetails":
                    	new_pos = (byte)(mOrderDetails.memory_pos ^ 0x01);
                    	mOrderDetails.readOrderDetail(new_pos);
                    	mOrderDetails.memory_pos = new_pos;
                    	break;
                    
    /* case "ManageOrders":
                            new_pos = (byte)(mOrders.memory_pos ^ 0x01);
                            mOrders.readOrder(new_pos);
                            mOrders.memory_pos = new_pos;
                            break;
                        case "ManagePayments":
                            new_pos = (byte)(mPayments.memory_pos ^ 0x01);
                            mPayments.readPayment(new_pos);
                            mPayments.memory_pos = new_pos;
                            break;
                        case "ManageProducts":
                            new_pos = (byte)(mProducts.memory_pos ^ 0x01);
                            mProducts.readProduct(new_pos);
                            mProducts.memory_pos = new_pos;
                            break;
                        case "ManageSellers":
                            new_pos = (byte)(mSellers.memory_pos ^ 0x01);
                            mSellers.readSeller(new_pos);
                            mSellers.memory_pos = new_pos;
                            break;
                        case "ManageShippings":
                            new_pos = (byte)(mShippings.memory_pos ^ 0x01);
                            mShippings.readShipping(new_pos);
                            mShippings.memory_pos = new_pos;
                            break;
                            */
                }
//                oraConn.queryEndsKey.remove(key);
//                System.out.println("oraConn.queryEndsKey.remove(key) : " + key);
                oraConn.queryEndsKey.remove(className);
                System.out.println("oraConn.queryEndsKey.remove(className) : " + className);
//                System.out.println("End oraConn.queryEndsKey : " + oraConn.queryEndsKey);
                System.out.println("End oraConn.queryEndsKey.size() : " + oraConn.queryEndsKey.size());
                
            }
        }
    }
    
    public void exit() {
    	running = false;
    	try {
    		if(workerThread != null) {
    			workerThread.interrupt();	//	스레드 인터럽트
    			workerThread.join(5000);	//	5초간 스레드 종료 대기
    		}
    	}catch(InterruptedException e) {
    		Thread.currentThread().interrupt();
    	}
    	
    	if(mOrderDetails != null)
        	mOrderDetails.exit();
        if(mOrders != null)
        	mOrders.exit();
        if(mCupons != null)
        	mCupons.exit();
        if(mProductImgs != null)
        	mProductImgs.exit();
        if(mProducts != null)
        	mProducts.exit();
        if(mComCis != null)
        	mComCis.exit();
        if(mComHistorys != null)
        	mComHistorys.exit();
        if(mCompanys != null)
        	mCompanys.exit();
        if(mComMems != null)
        	mComMems.exit();
        if(mSubComs != null)
        	mSubComs.exit();
        if(mCustomers != null)
        	mCustomers.exit();
 //       if(mLogin != null)
 //       	mLogin.logout();
        
        oraConn.close();
        ctrlScanner.close();
//        System.exit(0);	//	웹 서버까지 종료되므로 제거.
    }
    
    public void runMenu() {
        try {
            if(workerThread != null && workerThread.isAlive()) {
                System.out.println("Thread 실행 중");
            }else {
                running = true;
                workerThread = new Thread(() -> {
                    while(running && !Thread.currentThread().isInterrupted()) {
                        queryReadExe();
 /*                       try {
                            Thread.sleep(100); // 바쁜 대기(Busy-Waiting) 방지를 위해 100ms 휴식
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }*/
                    }
                    System.out.println("Thread 종료");
                }, "queryReadExe_Thread");
                workerThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Tomcat과 같은 웹 애플리케이션에서는 while(true) 루프가 불필요
        // 사용자와 상호작용하는 UI/메뉴 로직은 웹 요청에 따라 처리됨
    }
    
    // 웹 애플리케이션 환경에서는 main 메서드를 사용하지 않음
    
    public static void main(String[] args) {
    	//	
        System.out.println("HumanCoffee main");
//        HumanCoffee system = new HumanCoffee();
//        system.runMenu();
        
/*        int sel;
        while(true) {
            System.out.println("------------------------------------------------------------");
            if(system.mLogin.currentUser != null)
                System.out.println("\n온라인 쇼핑몰 주문 처리 시스템[ID: " + system.mLogin.currentUser + "]");
            else
                System.out.println("\n온라인 쇼핑몰 주문 처리 시스템");
            System.out.println("------------------------------------------------------------");
            System.out.println("1. 로그인/로그아웃");
            System.out.println("2. 판매자 관리");
            System.out.println("3. 고객 관리");
            System.out.println("4. 제품 관리");
            System.out.println("5. 주문 관리");
            System.out.println("6. 프로그램 종료");
            System.out.print("선택: ");
            sel = system.ctrlScanner.getIntByScanner();
            if(sel == 0)
                sel = 1;
            switch(sel) {
            case 1:
                system.mLogin.manageLogin();
                break;
            case 3:
                system.mCustomers.manageCustomers();
                break;
            case 6:
                system.exit();
                break;
            default:
                System.out.println("잘 못 입력하셨습니다. 다시 시도해 주세요.");
            }
        }*/
    }
    
}