<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import = "com.humancoffee.manager.*" %>
<%@ page import = "com.humancoffee.common.*" %>
<%@ page import = "com.humancoffee.model.*" %>
<%@ page import = "com.humancoffee.*" %>
<%@ page import="java.util.Date" %>
<link rel="stylesheet" href="../css/comhistory.css" />

<%
// 디버깅: 단계별 확인
System.out.println("=== 연혁 데이터 로딩 디버깅 시작 ===");

// 1. ServletContext에서 HumanCoffee 객체 확인
HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
System.out.println("1. HumanCoffee 인스턴스: " + (hcInstance != null ? "존재" : "NULL"));

if (hcInstance == null) {
    System.out.println("ERROR: HumanCoffee 인스턴스가 ServletContext에 없습니다!");
}

// 2. ManageComHistorys 확인
ManageComHistorys com = null;
if (hcInstance != null) {
    com = hcInstance.mComHistorys;
    System.out.println("2. ManageComHistorys: " + (com != null ? "존재" : "NULL"));
}

// 3. memory_pos 확인
int pos = -1;
if (com != null) {
    pos = com.memory_pos;
    System.out.println("3. memory_pos: " + pos);
}

// 4. com_historys 배열 확인
List<Com_History> nowCom_HistoryList = null;
if (com != null && com.com_historys != null) {
    System.out.println("4. com_historys 배열 길이: " + com.com_historys.length);
    if (pos >= 0 && pos < com.com_historys.length) {
        if (com.com_historys[pos] != null) {
            nowCom_HistoryList = new Vector<>(com.com_historys[pos]);
            System.out.println("5. 현재 위치의 리스트 크기: " + nowCom_HistoryList.size());
        } else {
            System.out.println("5. com_historys[" + pos + "] 이 NULL입니다!");
        }
    } else {
        System.out.println("5. memory_pos(" + pos + ")가 배열 범위를 벗어났습니다!");
    }
} else {
    System.out.println("4. com_historys 배열이 NULL입니다!");
}

int tot_cnt = (nowCom_HistoryList != null) ? nowCom_HistoryList.size() : 0;
System.out.println("6. 최종 카운트: " + tot_cnt);

// 5. DB에서 직접 데이터 확인 (대안)
int dbCount = 0;
try {
    // DB 연결 정보는 실제 환경에 맞게 수정하세요
    // 여기서는 예시로 작성합니다
    /*
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_db", "user", "password");
    PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM com_history");
    ResultSet rs = pstmt.executeQuery();
    if (rs.next()) {
        dbCount = rs.getInt(1);
    }
    rs.close();
    pstmt.close();
    conn.close();
    System.out.println("7. DB에서 직접 조회한 카운트: " + dbCount);
    */
} catch (Exception e) {
    System.out.println("DB 직접 조회 실패: " + e.getMessage());
}

System.out.println("=== 연혁 데이터 로딩 디버깅 종료 ===");

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>

<div class="container comhistory_container">
    <div class="comhistory_title">연혁 관리</div>
    
    
    
    <div class="comhistory-box comhistory_box">
        <div class="comhistory-list-box">
            <div class="list-header">
                <h3 class="list-title">연혁 목록 <span class="record-count">(총 <%= tot_cnt %>건)</span></h3>
                <a href="about/manageHistory/comhistory_insert_form.jsp">
                    <button class="btn btn-search">등록</button>
                </a>
            </div>
            
            <div class="table-container">
                <div class="table-header">
                    <div class="table-columns">
                        <div class="table-column">회사 ID</div>
                        <div class="table-column">ID</div>
                        <div class="table-column">개업일</div>
                        <div class="table-column">폐업일</div>
                        <div class="table-column">기업활동</div>
                        <div class="table-column">내용</div>
                        <div class="table-column">상태</div>
                        <div class="table-column">관리</div>
                    </div>
                </div>
                
                <div class="table-body">
                    <%
                    if (tot_cnt == 0) {
                    %>
                    <div class="empty-state">등록된 연혁이 없습니다.</div>
                    <%
                    } else {
                        for (Com_History history : nowCom_HistoryList) {
                    %>
                    <div class="table-row">
                        <div class="table-cell"><%= (history.getComId() != null) ? history.getComId() : "" %></div>
                        <div class="table-cell"><%= (history.getId() != null) ? history.getId() : "" %></div>
                        <div class="table-cell"><%= (history.getStartDate() != null) ? sdf.format(history.getStartDate()) : "" %></div>
                        <div class="table-cell"><%= (history.getEndDate() != null) ? sdf.format(history.getEndDate()) : "" %></div>
                        <div class="table-cell"><%= (history.getTitle() != null) ? history.getTitle() : "" %></div>
                        <div class="table-cell"><%= (history.getContent() != null) ? history.getContent() : "" %></div>
                        <div class="table-cell">
                            <%= (history.getStatus() == 0) ? "운영중" : "폐업" %>
                        </div>
                        <div class="table-cell">
                            <button class="btn btn-edit" onclick="editHistory('<%= history.getId() %>')">수정</button>
                        </div>
                    </div>
                    <%
                        }
                    }
                    %>
                </div>
            </div>
        </div>
    </div>
</div>
form.action = '<%= request.getContextPath() %>/about/manageCompany/editCompany_form.jsp';
<script>
function editHistory(historyId) {
    window.location.href = '<%= request.getContextPath() %>/about/manageHistory/comhistory_update_form.jsp?id=' + historyId;
}
</script>