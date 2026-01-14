<!-- productList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.humancoffee.common.*" %>
<%@ page import="com.humancoffee.dao.*" %>
<%@ page import="com.humancoffee.model.*" %>
<%@ page import="com.humancoffee.manager.*" %>  
<%@ page import="com.humancoffee.*" %>


<%
    // 1. HumanCoffee 인스턴스 가져오기
    HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");

    // 2. ManageProducts & ManageProductImgs 가져오기
    ManageProducts mgProducts = hcInstance.mProducts;
    ManageProductImgs mgProductImgs = hcInstance.mProductImgs;

    // 3. DB에서 상품 데이터 로드
    try {
        mgProducts.readProduct(mgProducts.memory_pos);
        System.out.println("상품 데이터 로드 완료");
    } catch(Exception e) {
        System.out.println("상품 데이터 로드 오류: " + e.getMessage());
    }

    // 4. 카테고리 파라미터 받기
    String categoryParam = request.getParameter("category");
    int categoryFilter = 0;
    if(categoryParam != null && !categoryParam.equals("")) {
        try {
            categoryFilter = Integer.parseInt(categoryParam);
        } catch(NumberFormatException e) {
            categoryFilter = 0;
        }
    }

    // 5. 상품 리스트 가져오기
    List<Product> productList = new Vector<>(mgProducts.products[mgProducts.memory_pos]);
%>
    <!DOCTYPE html>
    <html>
    <head>
    <meta charset="UTF-8">
    <title>상품 목록</title>
    
	<link rel="stylesheet" href="../../css/product-list.css" />

    </head>
    <body>

    <h1>상품 목록</h1>

    <!-- 카테고리 네비게이션 -->
    <div class="category-nav">
        <a href="?category=0" class="<%= (categoryFilter == 0) ? "active" : "" %>">전체</a>
        <a href="?category=1" class="<%= (categoryFilter == 1) ? "active" : "" %>">커피</a>
        <a href="?category=3" class="<%= (categoryFilter == 3) ? "active" : "" %>">디카페인</a>
        <a href="?category=4" class="<%= (categoryFilter == 4) ? "active" : "" %>">주스</a>
        <a href="?category=5" class="<%= (categoryFilter == 5) ? "active" : "" %>">기타</a>
    </div>

    <div class="product-grid">
    <%
        // 4. 상품 목록 반복
        if(productList.size() > 0){
            for(Product product : productList) {
            	if(product.getStatus() != 0)
            		continue;
                // 카테고리 필터링
                if(categoryFilter != 0 && product.getDiv() != categoryFilter) {
                    continue; // 선택된 카테고리가 아니면 건너뛰기
                }
                
                // Map에서 이미지 가져오기
                List<Product_Img> product_imgs = mgProductImgs.searchProductImgByProductId(product.getId());
                Product_Img product_img = null;
                if(product_imgs.size() > 0)
                	product_img = product_imgs.get(0);
                
                System.out.println("product id: " + product.getId());
                System.out.println("product div: " + product.getDiv());
                System.out.println("product img size: " + product_imgs.size());
                
                String filePath = "#";
                if(product_img != null && !product_img.getFilename().isEmpty())
                	filePath = product_img.getFilename();
          //      System.out.println("filePath: " + filePath);
          
                
                // 카테고리 이름
                String categoryName = "";
                switch(product.getDiv()) {
                    case 1: 
                    	categoryName = "커피"; 
                    	break;
                    case 3: 
                    	categoryName = "디카페인"; 
                    	break;
                    case 4: 
                    	categoryName = "주스"; 
                    	break;
                    case 5: 
                    	categoryName = "기타"; 
                    	break;
                    default: 
                    	categoryName = "기타"; 
                    	break;
                }
                System.out.println("categoryName: " + categoryName);
    %>
                <!-- HTML 출력 -->
                <div class="product-card">
                    <% if(!filePath.equals("#")) { %>
                        <img src="<%= filePath %>" alt="<%= product.getName() %>">
                    <% } else { %>
                        <div style="height: 150px; background-color: #f0f0f0; display: flex; align-items: center; justify-content: center;">
                            이미지 없음
                        </div>
                    <% } %>
                    <h3><%= product.getName() %></h3>
                    <p>가격: <%= product.getPrice() %>원</p>
                    <p>카테고리: <%= categoryName %></p>
                    <p>상품ID: <%= product.getId() %> (div: <%= product.getDiv() %>)</p>
					<a href="productForm_update.jsp?id=<%= product.getId() %>"><button>수정</button></a>
					<a href="product_delete.jsp?id=<%= product.getId() %>"><button>삭제</button></a>
                    
                </div>
    <%
            } 
        } else {
    %>
        <div style="grid-column: 1/-1; text-align: center; padding: 50px;">
            <p>상품이 없습니다.</p>
        </div>
    <%
        }
    %>
    </div>

    <div style="text-align: center; margin: 30px;">
        <a href="productForm.jsp" style="padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px;">새 상품 등록</a>
    </div>

    </body>
    </html>