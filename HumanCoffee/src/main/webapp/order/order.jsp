<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.humancoffee.*" %>
<%@ page import="com.humancoffee.manager.*" %>
<%@ page import="com.humancoffee.model.*" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="com.fasterxml.jackson.core.type.TypeReference" %>
<%@ page isELIgnored="true" %>

<%

	final String CUSTOMER_TEL = "01012345678";

	HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
	List<Product> productList = new Vector<>(hcInstance.mProducts.products[hcInstance.mProducts.memory_pos]);
	ManageProducts mgProducts = hcInstance.mProducts;
	ManageProductImgs mgProductImgs = hcInstance.mProductImgs;
	//POST 요청으로 전달된 파라미터 확인
	String strOrder = request.getParameter("order");
	String orderDataJson = request.getParameter("orderData");

    System.out.println("strOrder: " + strOrder);
    System.out.println("orderDataJson: " + orderDataJson);
	if ("insert".equals(strOrder) && orderDataJson != null && !orderDataJson.isEmpty()){
        // JSON 파싱은 예외가 발생할 수 있으므로 try-catch로 감싸야 합니다.
		try {
			// Jackson의 ObjectMapper를 사용하여 JSON 문자열을 List<Map> 객체로 변환
	        ObjectMapper objectMapper = new ObjectMapper();
	        List<Map<String, Object>> orderItems = objectMapper.readValue(orderDataJson, new TypeReference<List<Map<String, Object>>>() {});
	        
			ManageMyOrders mgOrder = hcInstance.mOrders;
			ManageOrderDetails mgDetail = hcInstance.mOrderDetails;
			
			My_Order mOrder = new My_Order();
			
			mOrder.setCustomerTel(CUSTOMER_TEL);
			int div;
			String paymentMethod = request.getParameter("paymentMethod");
			if("card".equals(paymentMethod))
				div = 1;
			else if("cash".equals(paymentMethod))
				div = 2;
			else
				div = 4;
			
			String totalPrice = request.getParameter("totalPrice");
	        int tot_price = Integer.parseInt(totalPrice);
			mOrder.setDiv(div);
			mOrder.setTotPrice(tot_price);
			
			if(div == 1)
				mOrder.setCard(tot_price);
			else if(div == 2)
				mOrder.setCash(tot_price);
			else
				mOrder.setCupon(1);
			
			mOrder = mgOrder.insertMyOrder(mOrder);
			
			
			
			System.out.println("orderItems:" + orderItems);
			int product_point = 0;
			// Order_Detail(주문 상세) 테이블에 데이터 삽입
	        if (mOrder != null && !orderItems.isEmpty()) {
	        	String id;
	        	int cnt;
	        	int product_tot_price;
	            for (Map<String, Object> itemMap : orderItems) {
	                Order_Detail mDetail = new Order_Detail();
	                System.out.println("itemMap:" + itemMap);
	                mDetail.setOrderId(mOrder.getId());
	                
	                // 맵에서 "id"와 "quantity" 값을 가져와 Order_Detail 객체에 설정
	                id = (String) itemMap.get("id");
	                cnt = (Integer) itemMap.get("quantity");
	                mDetail.setProductId(id);
	                Product product = new Product();
	                product.setId(id);
	                product = mgProducts.searchProductById(product);
	                mDetail.setCnt(cnt);
	                product_tot_price = cnt * product.getPrice();
	                product_point += (product.getPoint() * cnt);
	                mDetail.setTotPrice(product_tot_price);
	                mgDetail.insertOrderDetail(mDetail);
	            }
	            out.println("<script>alert('주문이 완료되었습니다!');</script>");
	            
	            
	            ManageCustomers mgCustomer = hcInstance.mCustomers;
	            Customer customer = new Customer();
	            customer = mgCustomer.searchCustomerByTel(CUSTOMER_TEL);
	            if(customer != null){
	            	int now_point, new_point;
	            	int now_cupon, new_cupon;
	            	now_point = customer.getPoint();
	            	new_point = now_point + product_point;
					now_cupon = customer.getCupon();
					new_cupon = now_cupon;
					
	            	Cupon cupon = new Cupon();
	            	List<Cupon> cupons = new Vector<>(hcInstance.mCupons.cupons[hcInstance.mCupons.memory_pos]);
	            	for(Cupon chk_cupon : cupons){
	            		if(chk_cupon.getStatus() == 0){
	            			cupon = chk_cupon;
	            			break;
	            		}
	            	}
	            	
	            	if(new_point >= cupon.getPoint()){
	            		new_cupon = now_cupon + 1;
	            		new_point -= cupon.getPoint();
	            	}
	            	customer.setPoint(new_point);
	            	customer.setCupon(new_cupon);
	            	mgCustomer.updateCustomer(customer);
	            }
	        }
        } catch (Exception e) {
            out.println("<script>alert('주문 처리 중 오류가 발생했습니다.');</script>");
            e.printStackTrace();
        }
	}
	
    
    

    String categoryParam = request.getParameter("category");
    int categoryFilter = 0;
    if(categoryParam != null && !categoryParam.isEmpty()) {
        try { categoryFilter = Integer.parseInt(categoryParam); } 
        catch(NumberFormatException e) { categoryFilter = 0; }
    }

    Map<Integer, String> categoryMap = new HashMap<>();
    categoryMap.put(1, "커피");
    categoryMap.put(3, "디카페인");
    categoryMap.put(4, "주스");
    categoryMap.put(5, "기타");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 목록</title>



<style>
body { margin-right: 320px; /* 오른쪽 결제 박스 공간 */ }

.category-nav { text-align: center; margin: 20px 0; }
.category-nav a { display:inline-block; padding:10px 20px; margin:0 5px; text-decoration:none; background:#f0f0f0; border-radius:5px; color:#333; }
.category-nav a.active { background:#007bff; color:#fff; }

.product-grid { display:grid; grid-template-columns:repeat(auto-fill,minmax(250px,1fr)); gap:20px; padding:20px; }
.product-card { border:1px solid #ddd; border-radius:10px; text-align:center; background:#fff; padding:15px; }
.product-card img { width:100%; height:150px; object-fit:cover; border-radius:5px; cursor:pointer; }

#orderBox {
    position: fixed;
    top: 120px;
    right: 20px;
    width: 280px;
    max-height: 80%;
    background: #fff;
    border: 2px solid #007bff;
    border-radius: 10px;
    padding: 15px;
    box-shadow: 0 0 15px rgba(0,0,0,0.3);
    overflow-y: auto;
    
    z-index: 100;
}

#orderBox h3 { margin-top:0; text-align:center; }
.order-item { display:flex; justify-content: space-between; align-items:center; margin-bottom:8px; font-size:0.9em; }
.order-item img { width:40px; height:40px; object-fit:cover; border-radius:5px; margin-right:5px; cursor:pointer; }

#totalPrice { font-weight:bold; margin-top:10px; text-align:right; }
#paymentMethod { margin-top:10px; text-align:center; }
#payBtn { margin-top:10px; padding:8px 15px; background:#28a745; color:#fff; border:none; border-radius:5px; cursor:pointer; width:100%; }

#orderBox #orderList{
	font-size: 16px;
	height: 25px:
	
	display: flex;
    justify-content: center;   /* 가로 가운데 정렬 */
    align-items: center;       /* 세로 가운데 정렬 */
    flex-wrap: wrap;           /* 여러 개면 줄바꿈 가능 (원하지 않으면 삭제) */
/*    height: 100vh;*/             /* 화면 전체 높이 기준 세로 중앙 배치 */
    text-align: center;
}

#orderBox #orderList .order-item #name_price:
#orderBox #orderList .order-item #quantity{
	display:flex;
	justify-content: center; 
	align-items:center;
}
#orderBox #orderList #changeQuantity{
	width:15px;
	height:20px;
}

#orderBox #orderList #removeOrder{
	width:30px;
	height:20px;
}

</style>









</head>
<body>

<h1>상품 목록</h1>

<div class="category-nav">

<%
    for(Map.Entry<Integer, String> entry : categoryMap.entrySet()) {
        int key = entry.getKey();
        String name = entry.getValue();
%>
        <a href="?category=<%= key %>" class="<%= (categoryFilter == key) ? "active" : "" %>"><%= name %></a>
<%
    }
%>
    
</div>

<div class="product-grid">
<%
    if(!productList.isEmpty()) {
    	int pos = 0;
        for(Product product : productList) {
%>
			<input type="hidden" name="position" value="<%=pos++ %>" >
<%        	
            if(categoryFilter != 0 && product.getDiv() != categoryFilter) continue;

            List<Product_Img> imgs = mgProductImgs.searchProductImgByProductId(product.getId());
            String imgPath = "#";
            if(!imgs.isEmpty() && imgs.get(0) != null && !imgs.get(0).getFilename().isEmpty()) {
                imgPath = imgs.get(0).getFilename();
            }

            String categoryName = categoryMap.getOrDefault(product.getDiv(), "기타");
%>
    <div class="product-card">
        <% if(!"#".equals(imgPath)) { %>
            <img src="<%= imgPath %>" alt="<%= product.getName() %>" 
                 onclick="addToOrder('<%= product.getId() %>', '<%= product.getName().replace("'", "\\'") %>', <%= product.getPrice() %>)">
        <% } else { %>
            <div style="height:150px; background:#f0f0f0; display:flex; align-items:center; justify-content:center;"
                 onclick="addToOrder('<%= product.getId() %>', '<%= product.getName().replace("'", "\\'") %>', <%= product.getPrice() %>)">
                 이미지 없음
            </div>
        <% } %>
        <h3><%= product.getName() %></h3>
        <p>position: <%=pos - 1 %></p>
        <p>가격: <%= product.getPrice() %>원</p>
        <p>카테고리: <%= categoryName %></p>
    </div>
<%
        }
    } else {
%>
    <div style="grid-column:1/-1; text-align:center; padding:50px;">상품이 없습니다.</div>
<%
    }
%>
</div>

<!-- 오른쪽 주문 박스 -->
<div id="orderBox">
    <h3>주문 현황</h3><br>
    <div id="orderList">
    </div>
    <div id="totalPrice">총액: 0원</div>
    <div id="paymentMethod">
        결제 방법:
        <select id="paySelect">
            <option value="card">카드</option>
            <option value="cash">현금</option>
            <option value="kakao">카카오페이</option>
        </select>
    </div>
    <button id="payBtn" onclick="confirmOrder()">결제하기</button>
</div>

<script>
let orderItems = [];

function addToOrder(id, name, price) {
	let item = orderItems.find(i => String(i.id) === String(id));
	console.log("addToOrder");
    if(item) { 
        item.quantity += 1; 
    } else {
        orderItems.push({id: id, name: name, price: Number(price), quantity: 1}); 
    }
    renderOrderBox();
}

function removeFromOrder(id) {
    orderItems = orderItems.filter(i => String(i.id) !== String(id));
    renderOrderBox();
}

function changeQuantity(id, delta) {
    let item = orderItems.find(i => String(i.id) === String(id));
    if(item) {
        item.quantity += delta;
        if(item.quantity < 1) removeFromOrder(id);
    }
    renderOrderBox();
}

function renderOrderBox() {
    let listDiv = document.getElementById('orderList');
    listDiv.innerHTML = '';
    
    let total = 0;
    let name_price = "아이스커피 (2000)원";
    let quantity = 1;
    orderItems.forEach(i => {
        console.log("렌더링 중. id:", i.id, ", name:", i.name, ", price:", i.price, ", quantity:", i.quantity);
        
        total += i.price * i.quantity;
        let div = document.createElement('div');
        div.className = 'order-item';
        div.innerHTML = `
            <span id="name_price">${i.name} (${i.price}원)</span>
            <div class="quantity-control">
                <button id="changeQuantity" onclick="changeQuantity('${i.id}', -1)">-</button>
                <span id="quantity" style="margin:0 5px;">${i.quantity}</span>
                <button id="changeQuantity" onclick="changeQuantity('${i.id}', 1)">+</button>
                <button id="removeOrder" onclick="removeFromOrder('${i.id}')">삭제</button>
            </div>
        `;
        listDiv.appendChild(div);
    });

    document.getElementById('totalPrice').innerText = '총액: ' + total + '원';
}


// 기존 confirmOrder 함수
function confirmOrder() {
    if(orderItems.length === 0) {
        alert('주문할 상품을 선택해주세요.');
        return;
    }

    let payment = document.getElementById('paySelect').value;
    let total = orderItems.reduce((acc, i) => acc + i.price * i.quantity, 0);

    // 폼을 생성하여 데이터 전송
    let form = document.createElement('form');
    form.method = 'POST';
    form.action = 'order.jsp';
	
 // 이 부분을 추가합니다: 주문 처리 로직을 실행하기 위한 파라미터
    let orderActionInput = document.createElement('input');
    orderActionInput.type = 'hidden';
    orderActionInput.name = 'order'; // JSP 상단에서 확인하는 파라미터 이름과 일치
    orderActionInput.value = 'insert';
    form.appendChild(orderActionInput);
    
    // 주문 아이템 데이터를 JSON 문자열로 변환하여 hidden input에 저장
    let orderDataInput = document.createElement('input');
    orderDataInput.type = 'hidden';
    orderDataInput.name = 'orderData';
    orderDataInput.value = JSON.stringify(orderItems);

    // 결제 방법과 총액도 함께 전송
    let paymentInput = document.createElement('input');
    paymentInput.type = 'hidden';
    paymentInput.name = 'paymentMethod';
    paymentInput.value = payment;

    let totalInput = document.createElement('input');
    totalInput.type = 'hidden';
    totalInput.name = 'totalPrice';
    totalInput.value = total;

    form.appendChild(orderDataInput);
    form.appendChild(paymentInput);
    form.appendChild(totalInput);

    document.body.appendChild(form);
    form.submit();
}


</script>

</body>
</html>