<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import = "com.humancoffee.manager.*" %>
<%@ page import = "com.humancoffee.common.*" %>
<%@ page import = "com.humancoffee.model.*" %>
<%@ page import = "com.humancoffee.*" %>
<%@ page import="java.util.Date" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/comhistory_update_form.css" />

<%
// 넘어온 ID로 데이터 찾기
String selectedId = request.getParameter("id");

HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
ManageComHistorys com = hcInstance.mComHistorys;
int pos = hcInstance.mComHistorys.memory_pos;
List<Com_History> nowCom_HistoryList = new Vector<>(hcInstance.mComHistorys.com_historys[pos]);

// 기본값 설정
String comId = "";
String historyId = "";
String startDate = "";
String endDate = "";
String title = "";
String content = "";
int status = 0;
String mode = "insert";
String formTitle = "연혁 정보 입력";

// ID가 있으면 해당 데이터 찾아서 값 설정
if (selectedId != null && !selectedId.isEmpty()) {
    for (Com_History history : nowCom_HistoryList) {
        if (selectedId.equals(history.getId())) {
            comId = (history.getComId() != null) ? history.getComId() : "";
            historyId = (history.getId() != null) ? history.getId() : "";
            title = (history.getTitle() != null) ? history.getTitle() : "";
            content = (history.getContent() != null) ? history.getContent() : "";
            status = history.getStatus();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (history.getStartDate() != null) {
                startDate = sdf.format(history.getStartDate());
            }
            if (history.getEndDate() != null) {
                endDate = sdf.format(history.getEndDate());
            }
            
            mode = "update";
            formTitle = "연혁 정보 수정";
            break;
        }
    }
}
%>

<div class="form-container">
    <h3 class="form-title"><%= formTitle %></h3>
    
    <form id="comHistoryForm" method="post" action="comhistory_update.jsp">
        <input type="hidden" name="mode" value="<%= mode %>">
        <input type="hidden" name="originalId" value="<%= historyId %>">
        
        <div class="form-group">
            <label for="comId">회사 ID</label>
            <input type="text" name="comId" value="<%= comId %>" required>
        </div>
        
        <div class="form-group">
            <label for="historyId">연혁 ID</label>
            <input type="text" name="historyId" value="<%= historyId %>" required 
                   <%= mode.equals("update") ? "readonly" : "" %>>
        </div>
        
        <div class="form-group">
            <label for="startDate">개업일</label>
            <input type="date" name="startDate" value="<%= startDate %>">
        </div>
        
        <div class="form-group">
            <label for="endDate">폐업일</label>
            <input type="date" name="endDate" value="<%= endDate %>">
        </div>
        
        <div class="form-group">
            <label for="title">기업 활동</label>
            <input type="text" name="title" value="<%= title %>" required>
        </div>
        
        <div class="form-group">
            <label for="content">내용</label>
            <textarea name="content" rows="4" required><%= content %></textarea>
        </div>
        
        <div class="form-group">
            <label for="status">상태</label>
            <select name="status" required>
                <option value="0" <%= (status == 0) ? "selected" : "" %>>운영중</option>
                <option value="1" <%= (status == 1) ? "selected" : "" %>>폐업</option>
            </select>
        </div>
        
        <div class="form-actions">
            <button type="submit" class="btn btn-save">저장</button>
            <button type="button" class="btn btn-reset" onclick="goBack()">취소</button>
        </div>
    </form>
</div>

<script>
function goBack() {
    window.location.href = '../comhistory.jsp';
}
</script>