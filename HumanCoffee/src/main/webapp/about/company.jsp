<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import = "com.humancoffee.manager.*" %>
<%@ page import = "com.humancoffee.common.*" %>
<%@ page import = "com.humancoffee.model.*" %>
<%@ page import = "com.humancoffee.*" %>
<%@ page import="java.util.Date" %>
<link rel="stylesheet" href="../css/company.css" />
<%
//세션에서 권한 정보 가져오기
String div = (String) session.getAttribute("div");
Integer rollObj = (Integer) session.getAttribute("roll");
int roll = (rollObj != null) ? rollObj.intValue() : 0;

//관리 권한 체크 (4, 5, 6, 7번 권한만 등록/수정/삭제 가능)
boolean ManageCheck = (roll == 4 || roll == 5 || roll == 6 || roll == 7);

HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
ManageCompanys com = hcInstance.mCompanys;

int tot_cnt = com.companys[com.memory_pos].size();
Company company;
String Id = "";
String name = "";
String tel = "";
String fax = "";
String addr = "";
String sale_email = "";
String eng_email = "";
Date indate = null;
Date outdate = null;
int status = 0;
%>

<div class="container">

<h1 id="company-title">[회사 소개]</h1>

<!-- 상단 등록 버튼 -->
<% if(ManageCheck) { %>
<div class="top-btn">
    <button class="btn btn-register" onclick="registerCompany()">등록</button>
</div>
<% } %>

<%
for(int loop = 0; loop < tot_cnt; loop++){
    company = com.companys[com.memory_pos].get(loop);
%>

<div class="company-info-box">
    <div class="company-info-item">
        <strong>ID:</strong> <span class="company-id""><%= company.getId() %></span>
    </div>
    <div class="company-info-item">
        <strong>회사이름:</strong> <span class="company-name""><%= company.getName() %></span>
    </div>
    <div class="company-info-item">
        <strong>tel:</strong> <span class="company-tel"><%= company.getTel() %></span>
    </div>
    <div class="company-info-item">
        <strong>fax:</strong> <span class="company-fax"><%= company.getFax() %></span>
    </div>
    <div class="company-info-item">
        <strong>회사주소:</strong> <span class="company-addr"><%= company.getAddr() %></span>
    </div>
    <div class="company-info-item">
        <strong>sale_email:</strong> <span class="company-sale-email"><%= company.getSaleEmail() %></span>
    </div>
    <div class="company-info-item">
        <strong>eng_email:</strong> <span class="company-eng-email"><%= company.getEngEmail() %></span>
    </div>
    <div class="company-info-item">
        <strong>설립일:</strong> <span class="company-indate"><%= company.getInDate() %></span>
    </div>
    <div class="company-info-item">
        <strong>종료일:</strong> <span class="company-outdate"><%= company.getOutDate() %></span>
    </div>
    <div class="company-info-item">
        <strong>상태:</strong> <span class="company-status"><%= (company.getStatus() == 0) ? "운영중" : "폐업" %></span>
    </div>

    <!-- 각 회사별 수정/삭제 버튼 -->
    <% if(ManageCheck) { %>
    <div class="btn-group">
        <button class="btn btn-edit" onclick="editCompany('<%= company.getId() %>')">수정</button>
        <button class="btn btn-delete" onclick="deleteCompany('<%= company.getId() %>')">삭제</button>
    </div>
    <% } %>
</div>

<%
}
%>

<script>
// 등록 버튼 클릭
function registerCompany() {
    var form = document.createElement('form');
    form.method = 'POST';

    form.action = 'insertCompany_form.jsp';

    document.body.appendChild(form);
    form.submit();
}

// 수정 버튼 클릭
function editCompany(companyId) {
    var form = document.createElement('form');
    form.method = 'POST';
    form.action = '<%= request.getContextPath() %>/about/manageCompany/editCompany_form.jsp';

    var idInput = document.createElement('input');
    idInput.type = 'hidden';
    idInput.name = 'companyId';
    idInput.value = companyId;
    form.appendChild(idInput);

    document.body.appendChild(form);
    form.submit();
}

// 삭제 버튼 클릭
function deleteCompany(companyId) {
    if(!confirm("정말 삭제하시겠습니까?")) return;

    var form = document.createElement('form');
    form.method = 'POST';

    form.action = '<%= request.getContextPath() %>/about/manageCompany/deleteCompany.jsp';


    var idInput = document.createElement('input');
    idInput.type = 'hidden';
    idInput.name = 'companyId';
    idInput.value = companyId;
    form.appendChild(idInput);

    document.body.appendChild(form);
    form.submit();
}
</script>