<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.humancoffee.model.Sub_Com" %>
<%@ page import="com.humancoffee.HumanCoffee" %>
<%@ page import="com.humancoffee.manager.ManageSubComs" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%
    // 폼에서 전달된 값 받기
    String name = request.getParameter("name");
    String tel = request.getParameter("tel");
    String fax = request.getParameter("fax");
    String addr = request.getParameter("addr");
    String email = request.getParameter("email");
    String indate = request.getParameter("indate");

    // 날짜 변환
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date inDateObj = null;
    try {
        if(indate != null && !indate.isEmpty()) {
            inDateObj = sdf.parse(indate);
        }
    } catch(Exception e) {
        e.printStackTrace();
        out.println("<script>alert('날짜 형식이 잘못되었습니다.'); history.back();</script>");
        return;
    }

    // HumanCoffee 인스턴스 가져오기
    HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
    if(hcInstance == null) {
        out.println("<script>alert('서버에서 HumanCoffee를 가져올 수 없습니다.'); history.back();</script>");
        return;
    }

    ManageSubComs mSubComs = hcInstance.mSubComs;

    // Sub_Com 객체 생성
    Sub_Com subCom = new Sub_Com();
    subCom.setName(name);
    subCom.setTel(tel);
    subCom.setFax(fax);
    subCom.setAddr(addr);
    subCom.setEmail(email);
    subCom.setInDate(inDateObj);
    subCom.setComId("2025081901");

    // 저장
    Sub_Com result = mSubComs.insertSubCom(subCom);

    if(result != null) {
    	%>
    	    <form id="redirectForm" method="post" action="<%= request.getContextPath() %>/">
    	        <input type="hidden" name="next_page" value="/admin/merchant/merchantManage.jsp" />
    	    </form>
    	    <script type="text/javascript">
    	        document.getElementById('redirectForm').submit();
    	    </script>
    	<%
    	} else {
    	    out.println("<script>alert('가맹점 등록에 실패했습니다.'); history.back();</script>");
    	}
%>
