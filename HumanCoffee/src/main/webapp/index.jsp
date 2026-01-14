<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>    
<%
	String next_page = request.getParameter("next_page");
	System.out.println("rcv : " + next_page);
	if(next_page == null || next_page.isEmpty())
		next_page = "./home.jsp";
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>HumanCoffee Korea</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.2/reset.min.css">
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700&display=swap" rel="stylesheet" />
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/6.8.4/swiper-bundle.min.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.21/lodash.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.5.1/gsap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.5.1/ScrollToPlugin.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/6.8.4/swiper-bundle.min.js"></script>

<script src="./js/login.js"></script> 
<script src="./js/home.js"></script> 

<link rel="stylesheet" href="./css/index.css" />
<link rel="stylesheet" href="./css/header.css" />
<link rel="stylesheet" href="./css/home.css" />
<link rel="stylesheet" href="./css/footer.css">
<link rel="stylesheet" href="./css/login.css" />
<link rel="stylesheet" href="./css/signup.css" />
<link rel="stylesheet" href="./css/menu-coffee.css" />
<link rel="stylesheet" href="./css/menu-dikapein.css" />
<link rel="stylesheet" href="./css/menu-juice.css" />
<link rel="stylesheet" href="./css/map.css" />
<link rel="stylesheet" href="./css/merchantList.css" />
<link rel="stylesheet" href="./css/menu-juice.css" />
<link rel="stylesheet" href="./css/comhistory.css" />
<link rel="stylesheet" href="./css/company.css" />
<link rel="stylesheet" href="./css/adminMain.css">
<link rel="stylesheet" href="./css/merchantManage.css">
<link rel="stylesheet" href="./css/merchantUpdate.css">
<link rel="stylesheet" href="./css/merchantModal.css">
<link rel="stylesheet" href="./css/merchantCreate.css">

</head>

<body>
<div class="index_desktop">
	<div class="index_top_pos">
	  <jsp:include page="./common-jsp/header.jsp" />
	</div>
  	<div class="index_middle_pos">
		<%
		if (next_page != null && !next_page.isEmpty()) {
		%>
		    <jsp:include page="<%= next_page %>" />
		<%
		} else {
		%>
		    <jsp:include page="./home.jsp" />
		<%
		}
		%>
	</div>
	<div class="index_bottom_pos">
	  <jsp:include page="./common-jsp/footer.jsp" />
	</div>
</div>

</body>
</html>