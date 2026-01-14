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

    System.out.println("=== 상품 삭제 처리 시작 ===");
    System.out.println("=== Content-Type: " + request.getContentType());

    // multipart/form-data에서 파라미터 읽기
    String productId = null;

    try {
       
        productId = request.getParameter("id");
        
    } catch(Exception e) {
        System.out.println("파트 읽기 오류: " + e.getMessage());
    }

    System.out.println("파싱된 데이터 - ID: " + productId);

    if(productId == null) {
        out.println("<script>alert('필수 정보 누락'); history.back();</script>");
        return;
    }
    
    try {

        // 기존 상품 찾기
        Product targetProduct = new Product();
        
        targetProduct.setId(productId);
        targetProduct = mgProducts.searchProductById(targetProduct);
        if(targetProduct == null) {
            System.out.println("오류: 상품을 찾을 수 없음 - ID: " + productId);
            out.println("<script>alert('상품을 찾을 수 없습니다.'); location.href='product_List.jsp';</script>");
            return;
        }

        System.out.println("수정 대상 상품 찾음: " + targetProduct.getName());
        targetProduct.setStatus(1);


        mgProducts.updateProductStatus(targetProduct);
         
    } catch(Exception e) {
        System.out.println("데이터베이스 업데이트 오류: " + e.getMessage());
        e.printStackTrace();
        out.println("<script>alert('상품 정보 저장 중 오류가 발생했습니다: " + e.getMessage() + "'); history.back();</script>");
        return;
    }

    System.out.println("=== 상품 삭제 처리 완료 ===");
    out.println("<script>alert('상품 정보 삭제가 완료되었습니다.'); location.href='product_List.jsp';</script>");

%>
