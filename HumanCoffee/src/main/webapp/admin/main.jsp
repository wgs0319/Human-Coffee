<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//POST 방식의 한글 파라미터 처리를 위한 인코딩 설정
request.setCharacterEncoding("UTF-8");

//세션에서 권한 정보 가져오기
Integer rollObj = (Integer) session.getAttribute("roll");
int roll = (rollObj != null) ? rollObj.intValue() : 0;

//가맹점 관리 권한 체크 (2, 3, 6, 7번 권한만 등록/수정/삭제 가능)
boolean ManageCheck1 = (roll == 2 || roll == 3 || roll == 6 || roll == 7);
//본사 관리 권한 체크 (4, 5, 6, 7번 권한만 등록/수정/삭제 가능)
boolean ManageCheck2 = (roll == 4 || roll == 5 || roll == 6 || roll == 7);

%>
<div class="adminMain_container">
    <div class="adminMain_title">관리자 메인 페이지</div>
	<%
	if (ManageCheck1) {
	%>
    <div class="adminMain_buttons">
    	<form action="<%= request.getContextPath() %>/" method="post">
    		<input type="hidden" name="next_page" value="/admin/merchant/merchantManage.jsp" />
	        <!-- 가맹점 관리 페이지로 이동 -->
	        <button type="submit" class="adminMain_btn">
	            가맹점 관리
	        </button>
		</form>
		<form action="<%= request.getContextPath() %>/" method="post">
    		<input type="hidden" name="next_page" value="/admin/menu/product_List.jsp" />
	        <!-- 가맹점 관리 페이지로 이동 -->
	        <button type="submit" class="adminMain_btn">
	            제품 관리
	        </button>
		</form>
    </div>
    <%
	}
    %>
</div>
