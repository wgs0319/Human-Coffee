<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.humancoffee.manager.*" %>
<%@ page import="com.humancoffee.common.*" %>
<%@ page import="com.humancoffee.model.*" %>
<%@ page import="com.humancoffee.*" %>

<%
request.setCharacterEncoding("UTF-8");

try {
    // 파라미터 받기
    String comId = request.getParameter("comId");
    String historyId = request.getParameter("historyId");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    String status = request.getParameter("status");

    // HumanCoffee 인스턴스 가져오기
    HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
    ManageComHistorys com = hcInstance.mComHistorys;
    int pos = com.memory_pos;

    // Com_History 객체 생성
    Com_History newHistory = new Com_History();
    newHistory.setId(historyId);
    newHistory.setComId(comId);
    newHistory.setTitle(title);
    newHistory.setContent(content);

    // 날짜 설정
    if (startDate != null && !startDate.trim().isEmpty()) {
        newHistory.setStartDate(java.sql.Date.valueOf(startDate));
    }
    if (endDate != null && !endDate.trim().isEmpty()) {
        newHistory.setEndDate(java.sql.Date.valueOf(endDate));
    }

    // 상태 설정
    if (status != null && !status.trim().isEmpty()) {
        newHistory.setStatus(Byte.parseByte(status));
    } else {
        newHistory.setStatus((byte)0); // 기본값
    }

    // DB에 삽입
    com.insertComHistory(newHistory);

    // 메모리 리스트에 추가
    List<Com_History> nowCom_HistoryList = com.com_historys[pos];
    nowCom_HistoryList.add(newHistory);

    // 등록완료 팝업 후 목록으로 이동
    out.println("<script>");
    out.println("alert('등록이 완료되었습니다.');");
    out.println("location.href = '" + request.getContextPath() + "/about/comhistory.jsp';");
    out.println("</script>");
    return; // 중요: 스크립트 실행 후 JSP 실행 중단

} catch (Exception e) {
    e.printStackTrace(); // 서버 콘솔에서 에러 확인용
    out.println("<script>alert('등록 중 오류 발생: " + e.getMessage() + "'); history.back();</script>");
}
%>