<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.humancoffee.manager.*" %>
<%@ page import="com.humancoffee.common.*" %>
<%@ page import="com.humancoffee.model.*" %>
<%@ page import="com.humancoffee.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!-- css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/editCompany.css" />

<%
	request.setCharacterEncoding("UTF-8");

	//HumanCoffee 인스턴스에서 ManageCompanys 가져오기
	HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
	ManageCompanys com = hcInstance.mCompanys;

	// Parameter와 Attribute 모두 확인
	String companyId = request.getParameter("companyId");
	if(companyId == null || companyId.isEmpty()) {
		companyId = (String) request.getAttribute("companyId");
	}
	
	// 기본값 설정
    String name = "";
    String tel = "";
    String fax = "";
    String addr = "";
    String saleEmail = "";
    String engEmail = "";
    String status = "0";
    Date outDate = null;
    
 	// 회사 정보 조회
    if (companyId != null && !companyId.isEmpty()) {
        Company searchCompany = new Company();
        searchCompany.setId(companyId);
        
        Company foundCompany = com.searchCompanyById(searchCompany);
        if (foundCompany != null) {
            name = foundCompany.getName() != null ? foundCompany.getName() : "";
            tel = foundCompany.getTel() != null ? foundCompany.getTel() : "";
            fax = foundCompany.getFax() != null ? foundCompany.getFax() : "";
            addr = foundCompany.getAddr() != null ? foundCompany.getAddr() : "";
            saleEmail = foundCompany.getSaleEmail() != null ? foundCompany.getSaleEmail() : "";
            engEmail = foundCompany.getEngEmail() != null ? foundCompany.getEngEmail() : "";
            status = String.valueOf(foundCompany.getStatus());
            outDate = foundCompany.getOutDate();
        }
    }
 	
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String outDateStr = (outDate != null) ? sdf.format(outDate) : "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회사 정보 수정</title>
</head>
<body>
    <div class="editCompany-form">
        <h2>Human Coffee 본사 정보 수정</h2>
        <form action="<%= request.getContextPath() %>/about/manageCompany/editCompany.jsp" method="post" class="editCompany-form">
            <input type="hidden" name="Id" value="<%= companyId %>">
            
            <div>
                <label>아이디</label>
                <label><span><%= companyId %></span></label>
            </div>
            
            <div class="form-group">
                <label for="name">회사명</label>
                <input type="text" id="name" name="name" value="<%= name %>" placeholder="회사명을 입력해주세요." required>
            </div>
            
            <div class="form-group">
                <label for="tel">대표 전화</label>
                <input type="tel" id="tel" name="tel" value="<%= tel %>" placeholder="010-1234-5678" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}" required />
            </div>
            
            <div class="form-group">
                <label for="fax">팩스 번호</label>
                <input type="tel" id="fax" name="fax" value="<%= fax %>" placeholder="02-3015-1106 (선택사항)" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}" />
            </div>
            
            <div class="form-group">
                <label for="addr">주소</label>
                <input type="text" id="addr" name="addr" value="<%= addr %>" placeholder="도로명/상세주소" required />
            </div>

            <div class="form-group">
                <label for="saleEmail">영업대표 이메일</label>
                <input type="email" id="saleEmail" name="saleEmail" value="<%= saleEmail %>" placeholder="example@domain.com" required />
            </div>
            
            <div class="form-group">
                <label for="engEmail">기술대표 이메일</label>
                <input type="email" id="engEmail" name="engEmail" value="<%= engEmail %>" placeholder="example@domain.com" required />
            </div>
            
            <div class="form-group">
                <label for="outDate">폐업일 (선택사항)</label>
                <input type="date" id="outDate" name="outDate" value="<%= outDateStr %>" />
            </div>
            
            <div class="form-group">
                <label for="status">상태</label>
                <select id="status" name="status" required>
                    <option value="0" <%= "0".equals(status) ? "selected" : "" %>>정상운영</option>
                    <option value="1" <%= "1".equals(status) ? "selected" : "" %>>폐업</option>
                </select>
            </div>
            
            <div class="btn-group">
                <button type="submit">수정</button>
                <button type="button" class="btn" onclick="cancelEdit()">취소</button>
            </div>
        </form>
    </div>
    
    <script>
        function cancelEdit() {
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '<%= request.getContextPath() %>/';
            
            var nextPageInput = document.createElement('input');
            nextPageInput.type = 'hidden';
            nextPageInput.name = 'next_page';
            nextPageInput.value = '/about/company.jsp';
            form.appendChild(nextPageInput);
            
            document.body.appendChild(form);
            form.submit();
        }
    </script>
</body>
</html>