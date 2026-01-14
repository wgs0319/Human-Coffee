<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script src="<%=request.getContextPath()%>/js/login.js"></script>
<%@ page import = "java.sql.*" %>
<%
    String id = (String) session.getAttribute("id");
	String name = (String) session.getAttribute("name");
	Integer rollObj = (Integer) session.getAttribute("roll");
	String div = (String) session.getAttribute("div");
	System.out.println("session div: " + session.getAttribute("div"));
	Integer pointObj = (Integer) session.getAttribute("point");
	Integer couponObj = (Integer) session.getAttribute("cupon");

	
	// null 체크 후 int 변환
	int roll = (rollObj != null) ? rollObj.intValue() : 0;
    int point = (pointObj != null) ? pointObj.intValue() : 0;
    int coupon = (couponObj != null) ? couponObj.intValue() : 0;
%> 

<header>
  <div class="header">
    <!-- 로고와 관리자 서비스 버튼을 포함하는 왼쪽 영역 -->
    <div class="left-section">

      <a href="<%= request.getContextPath() %>/" class="logo">

        <img src="<%= request.getContextPath() %>/images/HumanCoffee_Logo.png" alt="HumanCoffee" />
      </a>
      
      <div class="adminService">
        <%
        if (div != null && div.equals("member")) {
        %>
          <form action="<%= request.getContextPath() %>/" method="post">
    		<input type="hidden" name="next_page" value="/admin/main.jsp" />
	        <!-- 가맹점 관리 페이지로 이동 -->
	        <button type="submit" class="btn">
	            관리자 페이지
	        </button>
		  </form>
        <%
        }
        %>
      </div>
    </div>
    
    <ul class="main-menu">
      <li class="item">
        <div class="item__name">회사소개</div>
        <div class="item__contents">
          <div class="contents__menu">
            <ul class="inner">
              <li>
                <ul>
	              <form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/about/company.jsp" />
					<button type="submit" class="link-button">휴먼커피에 대하여</button>
				  </form>
                  <form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/about/comhistory.jsp" />
					<button type="submit" class="link-button">연혁</button>
				  </form>
                  <form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/about/map.jsp" />
					<button type="submit" class="link-button">오시는 길</button>
				  </form>
                </ul>
               </li>
            </ul>
          </div>
        </div>
      </li>
      <li class="item">
        <div class="item__name">제품</div>
        <div class="item__contents">
          <div class="contents__menu">
            <ul class="inner">
               <li>
                <ul>
                  <form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/menu/menu-coffee.jsp" />
					<button type="submit" class="link-button">커피</button>
				  </form>
                  <form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/menu/menu-dikapein.jsp" />
					<button type="submit" class="link-button">디카페인</button>
				  </form> 
				  <form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/menu/menu-juice.jsp" />
					<button type="submit" class="link-button">주스</button>
				  </form> 
<%
if (div != null && div.equals("member")) {
%>				  
  <a href="<%= request.getContextPath() %>/order/receiveOrders.jsp" onclick="popupPage(this.href); return false;" class="link-button">주문받기</a>
<%
} else {
%>				  
  <a href="<%= request.getContextPath() %>/order/order.jsp" onclick="popupPage(this.href); return false;" class="link-button">주문하기</a>	
<%
}
%>			  
                 </ul>
              </li>
            </ul>
          </div>
        </div>
      </li>
      <li class="item">
        <div class="item__name">가맹점</div>
        <div class="item__contents">
          <div class="contents__menu">
            <ul class="inner">
              <li>
                <ul>
                  <form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/merchant/merchantList.jsp" />
					<button type="submit" class="link-button">가맹점 리스트</button>
				  </form> 
                </ul>
                </li>
            </ul>
          </div>
        </div>
      </li>
    </ul>

    <div class="loginService">
      <%
      if (id == null || id.equals("")) {
      %>
        <!-- index.jsp를 통한 로그인 폼 로딩 방식으로 변경 -->
        <form action="<%= request.getContextPath() %>/" method="post">
          <input type="hidden" name="next_page" value="/loginService/login_form.jsp" />
          <button type="submit" class="btn">로그인</button>
        </form>
      <%
      // 일반 사용자 로그인
      } else if (div != null && div.equals("customer")) {
      %>
        <div class="id-info">
          <span>이용자: <%= name %></span>
          <span>쿠폰: <%= coupon %>개  point: <%= point %></span>
        </div>
        <div class="login-btn-container">
        	<form action="<%= request.getContextPath() %>/loginService/logout.jsp" method="post" style="display: inline;">
        		<button type="submit" class="btn">로그아웃</button>
        	</form>
        </div>
      <%
      } else {
      %>
      	<div class="id-info">
      		<%
		     if (roll == 0) {
		    %>
          		<span>직책: 일반</span>
          	<%
		     } else if (roll == 1) {
		    %>
		    	<span>직책: 상품 등록 및 관리</span>
		    <%
		     } else if (roll == 2) {
		    %>
		    	<span>직책: 가맹점 등록 및 관리</span>
		    <%
		     } else if (roll == 3) {
		    %>
		    	<span>직책: 상품 및 가맹점 등록 및 관리</span>
		    <%
		     } else if (roll == 4) {
		    %>
		    	<span>직책: 본사 관리 및 매출현황 관리</span>
		    <%
		     } else if (roll == 5) {
		    %>
		    	<span>직책: 상품 및 본사 등록 및 관리 매출현황 관리</span>
		    <%
		     } else if (roll == 6) {
		    %>
		    	<span>직책: 가맹점 및 본사 등록 및 관리 매출현황 관리</span>
		    <%
		     } else if (roll == 7) {
		    %>
		    	<span>직책: 총괄 관리</span>
		    <%
		     } else {
		    %>
		    	<span>직책: 기타</span>
		    <%
		     }
		    %>
          <span>관리자: <%= name %></span>
        </div>
          <div class="login-btn-container">
          	<form action="<%= request.getContextPath() %>/loginService/logout.jsp" method="post" style="display: inline;">
          		<button type="submit" class="btn">로그아웃</button>
          	</form>
          </div>
        
       <%
      }
      %>
    </div>

  </div>
  <script>
  	function movePage(form){

  		alert(form); console.log(form);
  		document.getElementById(form).submit();
  	}
  
  	function popupPage(url){
  		const screenWidth = window.screen.width;
  	    const screenHeight = window.screen.height;
  	    
  	    // 팝업창 위치와 크기를 설정합니다.
  	    const options = `width=${screenWidth},height=${screenHeight},left=0,top=0,scrollbars=yes,resizable=yes`;
  	    
  	    // 팝업창을 엽니다.
  	    const newWindow = window.open(url, 'fullScreenPopup', options);
  	    
  	    if (newWindow) {
  	        // 팝업이 열린 후 크기와 위치를 강제로 재설정합니다.
  	        newWindow.resizeTo(screenWidth, screenHeight);
  	        newWindow.moveTo(0, 0);
  	    }
  	}
  </script>
</header>