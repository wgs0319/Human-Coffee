<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import = "com.humancoffee.manager.*" %>
<%@ page import = "com.humancoffee.model.*" %>
<%@ page import = "com.humancoffee.common.*" %>
<%@ page import = "com.humancoffee.*" %>
<%
    // ServletContext에서 HumanCoffee 객체를 가져옴
    HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");

    if(hcInstance != null){
        ManageSubComs mSubComs = hcInstance.mSubComs;
        List<Sub_Com> merchantList = mSubComs.sub_coms[mSubComs.memory_pos];
%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/merchantModal.css" />

<main class="merchant_list_page">
    <div class="inner">
        <h1>가맹점 리스트</h1>
        <div class="merchant-grid">
    <%
        if (merchantList != null && !merchantList.isEmpty()) {
            for (int i = 0; i < merchantList.size(); i++) {
                Sub_Com sub_com = merchantList.get(i);
                String imgPath = request.getContextPath() + "/images/merchant/human" + (i+1) + ".png";
    %>
        <div class="merchant-card" 
             onclick="openMerchantModal('<%=sub_com.getName()%>', '<%=sub_com.getAddr()%>', '<%=sub_com.getTel()%>', '<%=sub_com.getEmail()%>', '<%=imgPath%>')">
            <img src="<%= imgPath %>" alt="<%=sub_com.getName()%>" />
            <div class="info">
                <h2><%=sub_com.getName()%></h2>
                <p><%=sub_com.getAddr()%></p>
                <p>전화: <%=sub_com.getTel()%></p>
                <p>이메일: <%=sub_com.getEmail()%></p>
            </div>
        </div>
    <%
            }
        } else {
    %>
        <p>등록된 가맹점이 없습니다.</p>
    <%
        }
    %>
        </div>
    </div>
</main>

<!-- ✅ 모달 JSP include -->
<jsp:include page="./merchantModal.jsp" />

<script>
function openMerchantModal(name, addr, tel, email, imgPath) {
    document.getElementById("merchantModal").style.display = "flex";
    document.getElementById("modalName").innerText = name;
    document.getElementById("modalAddr").innerText = addr;
    document.getElementById("modalTel").innerText = "전화: " + tel;
    document.getElementById("modalEmail").innerText = "이메일: " + email;
    document.getElementById("modalImg").src = imgPath;

    // ✅ 구글 지도 iframe src 업데이트
    const mapUrl = "https://www.google.com/maps?q=" + encodeURIComponent(addr) + "&output=embed";
    document.getElementById("modalMap").src = mapUrl;
}


    function closeMerchantModal() {
        document.getElementById("merchantModal").style.display = "none";
    }
</script>

<%
    } else {
%>
<p>가맹점 데이터를 가져올 수 없습니다. HumanCoffee 객체가 null입니다.</p>
<%
    }
%>
