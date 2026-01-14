<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.humancoffee.model.Sub_Com" %>
<%@ page import="com.humancoffee.manager.ManageSubComs" %>
<%@ page import="com.humancoffee.HumanCoffee" %>
<%@ page import="java.sql.Date" %>

<%
    request.setCharacterEncoding("UTF-8");

    String id = request.getParameter("id");
    String comId = request.getParameter("com_id");
    String name = request.getParameter("name");
    String tel = request.getParameter("tel");
    String fax = request.getParameter("fax");
    String addr = request.getParameter("addr");
    String email = request.getParameter("email");
    String inDate = request.getParameter("indate");   // readonly용이었을 수 있음, 업데이트 안할 경우 제외 가능
    String outDate = request.getParameter("outdate");
    String statusStr = request.getParameter("status");

    int status = (statusStr != null && !statusStr.isEmpty()) ? Integer.parseInt(statusStr) : 0;

    // Sub_Com 객체 생성 (기존 모델 클래스 setter 이용)
    Sub_Com subCom = new Sub_Com();
    subCom.setId(id);
    subCom.setComId(comId);
    subCom.setName(name);
    subCom.setTel(tel);
    subCom.setFax(fax);
    subCom.setAddr(addr);
    subCom.setEmail(email);

    // 날짜 처리 (null-safe)
    if (outDate != null && !outDate.trim().isEmpty()) {
        subCom.setOutDate(Date.valueOf(outDate));
    } else {
        subCom.setOutDate(null);
    }

    subCom.setStatus(status);

    // HumanCoffee → ManageSubComs 호출
    HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
    if (hcInstance != null) {
        ManageSubComs mSubComs = hcInstance.mSubComs;
        mSubComs.updateSubCom(subCom);
    }

    // 수정 완료 후 리스트 페이지로 이동
    response.sendRedirect(request.getContextPath() + "/?next_page=/admin/merchant/merchantManage.jsp");
%>
