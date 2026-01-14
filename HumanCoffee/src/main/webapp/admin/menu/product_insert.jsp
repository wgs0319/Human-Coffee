<!-- product_insert.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.annotation.MultipartConfig" %>
<%@ page import="jakarta.servlet.http.Part" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.humancoffee.common.*" %>
<%@ page import="com.humancoffee.dao.*" %>
<%@ page import="com.humancoffee.model.*" %>
<%@ page import="com.humancoffee.manager.*" %>  
<%@ page import = "com.humancoffee.*" %>  
<%
	// 먼저 multipart 요청 확인
	String contentType = request.getContentType();
	System.out.println("실제 Content-Type: " + contentType);

	if (contentType == null || !contentType.toLowerCase().contains("multipart/form-data")) {
		out.println("<script>alert('잘못된 요청입니다.'); location.href='productForm.jsp';</script>");
		return;
	}
	Common common = new Common();
	String jspPath = Common.getProjectPath(application.getRealPath("/"), request.getContextPath());
	String savePath = request.getContextPath() + "/";
	
	// 파라미터 받기
	String name = request.getParameter("name");
	String price = request.getParameter("price");
    String category = request.getParameter("category");
    System.out.println("name: " + name);
	System.out.println("price: " + price);
	System.out.println("category: " + category);
	
	
//	최종 저장 경로
	jspPath += "images/";
	savePath += "images/";
	// 카테고리에 따라 div 값 설정
	int div = 1; // 기본값
	if ("커피".equals(category)) {
	    div = 1;
	    jspPath += "coffee/";
	    savePath += "coffee/";
	} else if ("디카페인".equals(category)) {
	    div = 3;
	    jspPath += "coffee/";
	    savePath += "coffee/";
	} else if ("주스".equals(category)) {
	    div = 4;
	    jspPath += "juice/";
	    savePath += "juice/";
	} else if ("기타".equals(category)) {
	    div = 5; // 기타 카테고리를 위한 새로운 값
	}
	
//	jspPath = "D:\\MyGit_root\\human_project2_2504_team2\\project2_2504_team2\\HumanCoffee\\src\\main\\webapp\\";
//	jspPath += "images/";
	File uploadDir = new File(jspPath);
	if (!uploadDir.exists()){
		System.out.println("make path: " + jspPath);
		uploadDir.mkdirs();
	}
	
    Part filePart = request.getPart("product_image");
	String fileName = filePart.getSubmittedFileName();
	
	savePath += fileName;
	
	Blob blob = common.getPartToBlob(filePart, application);
/*	InputStream fileContent = null;
	if(filePart != null){
		// 파일 내용을 읽어오기
		System.out.println("getinputstream1: " + jspPath);
        fileContent = filePart.getInputStream();
        System.out.println("getinputstream2: " + jspPath);
	}
	System.out.println("inputstream: " + jspPath);*/
//	해당 경로에 파일 넣기
	System.out.println("path: " + jspPath);
	filePart.write(jspPath);
	
	// ServletContext에서 HumanCoffee 객체를 가져옴
	HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
	// HumanCoffee 객체로부터 ManageLogin 객체를 가져옴
	ManageProducts mgProducts = hcInstance.mProducts;
	ManageProductImgs mgProductImgs = hcInstance.mProductImgs;
	
	Product product = new Product();
	Product_Img product_img = new Product_Img();
	
	int point = 1;
	product.setName(name);
	product.setPrice(Integer.parseInt(price));
	product.setPoint(point);	//	제품 1개 구매 시 적립 포인트
		
	product.setDiv(div);
	System.out.println("설정된 div 값: " + div);
	
	String content = null;		//	제품 설명
	product.setContent(content);
	
	Product chk_product;
	System.out.println("product insert start");
	chk_product = mgProducts.insertProduct(product);
	if(chk_product == null){
		System.out.println("product insert fail");
	}
	product_img.setProductId(chk_product.getId());
	int div_img = 0;			//	0 : 일반, 1 : 메인
	product_img.setDiv(div_img);
	product_img.setFilename(savePath);
/*	
	OraConnect conn = hcInstance.oraConn;
	Blob blob = conn.conn.createBlob();
	OutputStream os = blob.setBinaryStream(1);
	byte[] bytes = fileContent.readAllBytes();
	os.write(bytes);
	os.close();
	fileContent.close();*/

	product_img.setBFile(blob);
	mgProductImgs.insertProductImg(product_img);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h2>상품 등록 완료</h2>
    <div class="success">상품이 성공적으로 등록되었습니다!</div>
    
    <div class="info">
        <h3>등록된 상품 정보:</h3>
        <p><strong>상품 ID:</strong> <%= chk_product.getId() %></p>
        <p><strong>상품명:</strong> <%= name %></p>
        <p><strong>가격:</strong> <%= price %>원</p>
        <p><strong>카테고리:</strong> <%= category %> (div: <%= div %>)</p>
        <p><strong>적립 포인트:</strong> <%= point %>점</p>
        <p><strong>이미지 파일:</strong> <%= fileName %></p>
        <p><strong>저장 경로:</strong> <%= savePath %></p>
    </div>
    
    <a href="productForm.jsp" class="btn">다시 등록하기</a>
    <a href="product_List.jsp" class="btn">상품 목록 보기</a>
</body>
</html>