<!-- productForm_update.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.humancoffee.*, com.humancoffee.manager.*, com.humancoffee.model.*" %>
<%@ page import="java.util.*" %>

<%
    String idStr = request.getParameter("id");
    if(idStr == null){
        out.println("<script>alert('상품 ID가 없습니다.'); location.href='product_List.jsp';</script>");
        return;
    }
    int id = Integer.parseInt(idStr);

    HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
    ManageProducts mgProducts = hcInstance.mProducts;
    ManageProductImgs mgProductImgs = hcInstance.mProductImgs;

    // Product 객체 생성 후 ID 세팅
    Product tmpProduct = new Product();
    tmpProduct.setId(idStr);

    // searchProductById 사용
    Product product = mgProducts.searchProductById(tmpProduct);
    
    if(product == null){
        out.println("<script>alert('상품을 찾을 수 없습니다.'); location.href='product_List.jsp';</script>");
        return;
    }

    List<Product_Img> imgList = mgProductImgs.searchProductImgByProductId(idStr);

    Product_Img product_img = null;
    if(imgList != null && !imgList.isEmpty()){
        product_img = imgList.get(0); // 첫 번째 이미지 사용
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 수정</title>
</head>
<body>
<h2>상품 수정</h2>

<form action="product_update.jsp" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="<%= product.getId() %>">
    
    <p>상품명: <input type="text" name="name" value="<%= product.getName() %>" required></p>
    <p>가격: <input type="text" name="price" value="<%= product.getPrice() %>" required></p>
    
    <p>카테고리:
        <select name="category">
            <option value="커피" <%= product.getDiv() == 1 ? "selected" : "" %>>커피</option>
            <option value="디카페인" <%= product.getDiv() == 3 ? "selected" : "" %>>디카페인</option>
            <option value="주스" <%= product.getDiv() == 4 ? "selected" : "" %>>주스</option>
            <option value="기타" <%= product.getDiv() == 5 ? "selected" : "" %>>기타</option>
        </select>
    </p>
    
    <p>기존 이미지: 
        <% if(product_img != null) { %>
            <img src="<%= product_img.getFilename() %>" alt="상품 이미지" width="100">
        <% } else { %>
            없음
        <% } %>
    </p>
    
    <p>새 이미지 업로드: <input type="file" name="product_image"></p>
    
    <button type="submit">수정 완료</button>
    <a href="product_List.jsp">취소</a>
</form>

</body>
</html>
