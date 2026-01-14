<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.humancoffee.manager.*"%>
<%@ page import="com.humancoffee.common.*"%>
<%@ page import="com.humancoffee.model.*"%>
<%@ page import="com.humancoffee.*"%>

<%
request.setCharacterEncoding("UTF-8");

try {
    // 폼에서 넘어온 데이터 받기
    String mode = request.getParameter("mode");
    String comId = request.getParameter("comId");
    String historyId = request.getParameter("historyId");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    String status = request.getParameter("status");
    String originalId = request.getParameter("originalId");

    // 데이터 매니저 가져오기
    HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
    ManageComHistorys com = hcInstance.mComHistorys;
    int pos = com.memory_pos;
    List<Com_History> nowCom_HistoryList = com.com_historys[pos];

    // 새로운 연혁 객체 생성
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
        newHistory.setStatus((byte)0);
    }

    if ("update".equals(mode)) {
        // 수정 모드: 기존 데이터 찾아서 업데이트
        boolean updated = false;
        for (int i = 0; i < nowCom_HistoryList.size(); i++) {
            Com_History existing = nowCom_HistoryList.get(i);
            if (originalId.equals(existing.getId())) {
                nowCom_HistoryList.set(i, newHistory);
                updated = true;
                break;
            }
        }
        
        if (updated) {
            out.println("<script>");
            out.println("alert('수정이 완료되었습니다.');");
            out.println("location.href = '../comhistory.jsp';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('수정할 데이터를 찾을 수 없습니다.');");
            out.println("history.back();");
            out.println("</script>");
        }
        
    } else {
        // 등록 모드: 새로 추가
        com.insertComHistory(newHistory);
        nowCom_HistoryList.add(newHistory);
        
        out.println("<script>");
        out.println("alert('등록이 완료되었습니다.');");
        out.println("location.href = '../comhistory.jsp';");
        out.println("</script>");
    }

} catch (Exception e) {
    e.printStackTrace();
    out.println("<script>");
    out.println("alert('처리 중 오류가 발생했습니다: " + e.getMessage() + "');");
    out.println("history.back();");
    out.println("</script>");
}
%>