<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.io.*"%>
<%@ page import="java.sql.PreparedStatement, java.sql.Timestamp"%>
<%@ page import="jakarta.servlet.http.Part"%>
<%@ page import="com.humancoffee.*, com.humancoffee.manager.*, com.humancoffee.model.*"%>
<%@ page import="com.humancoffee.common.*" %>
<%@ page import="java.sql.*" %>

<%
    // HumanCoffee 인스턴스 가져오기
    HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
    ManageProducts mgProducts = hcInstance.mProducts;
    ManageProductImgs mgProductImgs = hcInstance.mProductImgs;

    System.out.println("=== 상품 수정 처리 시작 ===");
    System.out.println("=== Content-Type: " + request.getContentType());

    // multipart/form-data에서 파라미터 읽기
    String productId = null;
    String productName = null;
    String productPrice = null;
    String productDiv = null;
    String productDescription = null;
    Part filePart = null;

    try {
        // multipart에서 각 파트 읽기
        Collection<Part> parts = request.getParts();
        System.out.println("받은 파트 개수: " + parts.size());
        
        productId = request.getParameter("id");
        productName = request.getParameter("name");
        productPrice = request.getParameter("price");
        productDiv = request.getParameter("category");
        filePart = request.getPart("product_image");
        
    } catch(Exception e) {
        System.out.println("파트 읽기 오류: " + e.getMessage());
        // fallback으로 일반 파라미터 시도
        productId = request.getParameter("id");
        productName = request.getParameter("name");
        productPrice = request.getParameter("price");
        productDiv = request.getParameter("category");
        productDescription = request.getParameter("description");
    }

    System.out.println("파싱된 데이터 - ID: " + productId + ", Name: " + productName + ", Price: " + productPrice + ", div: " + productDiv);

    if(productId == null || productName == null || productPrice == null) {
        out.println("<script>alert('필수 정보 누락'); history.back();</script>");
        return;
    }

    // 변수 선언
    int price = 0;
    int div = 0;
    
    try {
        price = Integer.parseInt(productPrice);
        
        if(productDiv.equals("커피"))
        	div = 1;
        else if(productDiv.equals("디카페인"))
        	div = 3;
        else
        	div = 4;

        // 기존 상품 찾기
        Product targetProduct = null;
        int productIndex = -1;
        for(int i = 0; i < mgProducts.products[mgProducts.memory_pos].size(); i++) {
            Product p = mgProducts.products[mgProducts.memory_pos].get(i);
            if(p.getId().equals(productId)) { 
                targetProduct = p; 
                productIndex = i;
                break; 
            }
        }
        
        if(targetProduct == null) {
            System.out.println("오류: 상품을 찾을 수 없음 - ID: " + productId);
            out.println("<script>alert('상품을 찾을 수 없습니다.'); location.href='product_List.jsp';</script>");
            return;
        }

        System.out.println("수정 대상 상품 찾음: " + targetProduct.getName());

        // 상품 정보 업데이트
        targetProduct.setName(productName);
        targetProduct.setPrice(price);
        targetProduct.setDiv(div);
        targetProduct.setContent(productDescription != null ? productDescription : "");

        // 메모리에서도 업데이트
        mgProducts.products[mgProducts.memory_pos].set(productIndex, targetProduct);
        System.out.println("메모리 상품 정보 업데이트 완료");

        mgProducts.updateProduct(targetProduct);
        // 데이터베이스에 직접 업데이트
/*        String sql = "UPDATE product SET name = ?, price = ?, div = ?, content = ? WHERE id = ?";
        PreparedStatement pstmt = mgProducts.oraConn.conn.prepareStatement(sql);
        pstmt.setString(1, productName);
        pstmt.setInt(2, price);
        pstmt.setInt(3, div);
        pstmt.setString(4, productDescription != null ? productDescription : "");
        pstmt.setString(5, productId);
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        if(result > 0) {
            System.out.println("데이터베이스 상품 정보 업데이트 완료 - 영향받은 행: " + result);
        } else {
            System.out.println("데이터베이스 업데이트 실패 - 영향받은 행: 0");
            out.println("<script>alert('상품 정보 수정에 실패했습니다.'); history.back();</script>");
            return;
        }
*/
		System.out.println("새 이미지 업로드 시작 - 파일 크기: " + filePart.getSize());	
        // 이미지 처리
        if(filePart != null && filePart.getSize() > 0) {
            System.out.println("새 이미지 업로드 시작 - 파일 크기: " + filePart.getSize());
            
            Common common = new Common();
            String jspPath = Common.getProjectPath(application.getRealPath("/"), request.getContextPath());
        	String savePath = request.getContextPath() + "/";
        	
			//    최종 저장 경로
        	jspPath += "images/";
        	savePath += "images/";
        	
        	if (div == 1) {
        	    jspPath += "coffee/";
        	    savePath += "coffee/";
        	} else if (div == 3) {
        	    jspPath += "coffee/";
        	    savePath += "coffee/";
        	} else if (div == 4) {
        	    jspPath += "juice/";
        	    savePath += "juice/";
        	}        	
        	
        	File dir = new File(jspPath); 
            if(!dir.exists()) {
                boolean created = dir.mkdirs();
                System.out.println("디렉토리 생성: " + created);
            }
            
        	String fileName = filePart.getSubmittedFileName();
            
            
            jspPath += fileName;
            savePath += fileName;
            
            System.out.println("업로드 파일명 1: " + jspPath);
            System.out.println("업로드 파일명 2: " + savePath);
            
            Blob blob = common.getPartToBlob(filePart, application);
            
            // 파일 저장
            filePart.write(jspPath);
            System.out.println("이미지 파일 저장 완료: " + savePath);

            // 기존 이미지 있는지 확인
            List<Product_Img> existingImages = mgProductImgs.searchProductImgByProductId(productId);
            Product_Img existingImg = new Product_Img();
            existingImg.setBFile(blob);
            existingImg.setFilename(savePath);
            existingImg.setDiv(div);
            existingImg.setProductId(productId);
            if(existingImages.size() > 0) {
                // 기존 이미지 업데이트 
	            existingImg = existingImages.get(0);
                existingImg.setBFile(blob);
                existingImg.setFilename(savePath);
                existingImg.setDiv(div);
                existingImg.setProductId(productId);
                
                mgProductImgs.updateProductImg(existingImg);
            } else {
                // 새 이미지 등록
                existingImg = mgProductImgs.insertProductImg(existingImg);
            }
            
        } else {
            System.out.println("업로드된 이미지 없음 - 파일 크기: " + (filePart != null ? filePart.getSize() : "Part가 null"));
        }
        
    } catch(Exception e) {
        System.out.println("데이터베이스 업데이트 오류: " + e.getMessage());
        e.printStackTrace();
        out.println("<script>alert('상품 정보 저장 중 오류가 발생했습니다: " + e.getMessage() + "'); history.back();</script>");
        return;
    }

    System.out.println("=== 상품 수정 처리 완료 ===");
    out.println("<script>alert('상품 정보 수정이 완료되었습니다.'); location.href='product_List.jsp';</script>");

%>
