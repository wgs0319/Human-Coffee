<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import = "com.humancoffee.manager.*" %>
<%@ page import = "com.humancoffee.common.*" %>
<%@ page import = "com.humancoffee.model.*" %>
<%@ page import = "com.humancoffee.*" %>
<%@ page import="java.util.Date" %>

<%
// 권한 체크
String div = (String) session.getAttribute("div");
Integer rollObj = (Integer) session.getAttribute("roll");
int roll = (rollObj != null) ? rollObj.intValue() : 0;
boolean ManageCheck = (roll == 4 || roll == 5 || roll == 6 || roll == 7);

if(!ManageCheck) {
    out.println("<script>alert('권한이 없습니다.'); history.back();</script>");
    return;
}

// 삭제할 회사 ID 받기
String companyId = request.getParameter("companyId");

if(companyId == null || companyId.trim().isEmpty()) {
    out.println("<script>alert('회사 ID가 없습니다.'); history.back();</script>");
    return;
}

try {
    // HumanCoffee 인스턴스 가져오기
    HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
    ManageCompanys com = hcInstance.mCompanys;

    // 해당 회사 찾기 및 삭제
    boolean found = false;

    for(int i = 0; i < com.companys[com.memory_pos].size(); i++) {
        Company company = com.companys[com.memory_pos].get(i);
        if(companyId.equals(company.getId())) {
            // 회사 삭제
            com.companys[com.memory_pos].remove(i);
            found = true;
            break;
        }
    }

    if(found) {
        // 삭제 완료 후 이전 페이지로 리다이렉트
        out.println("<script>alert('삭제가 완료되었습니다.'); history.back();</script>");
    } else {
        out.println("<script>alert('해당 회사를 찾을 수 없습니다.'); history.back();</script>");
    }

} catch(Exception e) {
    out.println("<script>alert('삭제 중 오류가 발생했습니다: " + e.getMessage() + "'); history.back();</script>");
}
%>