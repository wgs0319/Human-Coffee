<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 세션에서 이용자 정보 제거 (로그아웃 처리)
	session.removeAttribute("id");
	session.removeAttribute("name");
	session.removeAttribute("roll");
	session.removeAttribute("div");
	session.removeAttribute("point");
	session.removeAttribute("coupon");
	
	out.println("<h1>로그아웃 처리중입니다</h1>");
%>

<script>
	var form = document.createElement('form');
    form.method = 'POST';
    form.action = '<%= request.getContextPath() %>/';
    document.body.appendChild(form);
    form.submit();
</script>