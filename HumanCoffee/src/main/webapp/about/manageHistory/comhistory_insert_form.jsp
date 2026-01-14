<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
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
// 현재 날짜를 기본값으로 설정
Date now = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String today = sdf.format(now);

// HumanCoffee 인스턴스로부터 다음 ID 생성
HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
ManageComHistorys com = hcInstance.mComHistorys;
int pos = hcInstance.mComHistorys.memory_pos;
List<Com_History> nowCom_HistoryList = new Vector<>(hcInstance.mComHistorys.com_historys[pos]);

// 자동 ID 생성 (기존 ID 목록을 보고 다음 번호 생성)
String nextId = "";
int maxId = 0;
for (Com_History history : nowCom_HistoryList) {
    try {
        int currentId = Integer.parseInt(history.getId());
        if (currentId > maxId) {
            maxId = currentId;
        }
    } catch (NumberFormatException e) {
        // ID가 숫자가 아닌 경우 무시
    }
}
nextId = String.valueOf(maxId + 1);
%>

<!-- 연혁 등록 폼 -->
<div class="form-container">
    <div class="form-header">
        <h3 class="form-title">연혁 정보 등록</h3>
        <button type="button" class="btn btn-back" onclick="goBack()">← 목록으로</button>
    </div>
    
    <form id="comHistoryForm" method="post" action="comhistory_insert.jsp">
        <input type="hidden" name="mode" value="insert">
        
        <div class="form-row">
            <div class="form-group">
                <label for="comId">회사 ID <span class="required">*</span></label>
                <input type="text" id="comId" name="comId" required placeholder="회사 ID를 입력하세요">
                <small class="form-help">고유한 회사 ID를 입력하세요</small>
            </div>
            
            <div class="form-group">
                <label for="historyId">연혁 ID <span class="required">*</span></label>
                <input type="text" id="historyId" name="historyId" value="<%= nextId %>" required placeholder="연혁 ID를 입력하세요">
                <small class="form-help">고유한 연혁 ID를 입력하세요</small>
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-group">
                <label for="startDate">개업일</label>
                <input type="date" id="startDate" name="startDate" value="<%= today %>">
                <small class="form-help">사업 시작일을 선택하세요</small>
            </div>
            
            <div class="form-group">
                <label for="endDate">폐업일</label>
                <input type="date" id="endDate" name="endDate">
                <small class="form-help">운영중인 경우 비워두세요</small>
            </div>
        </div>
        
        <div class="form-group">
            <label for="title">기업 활동 <span class="required">*</span></label>
            <input type="text" id="title" name="title" required placeholder="기업 활동을 입력하세요" maxlength="100">
            <small class="form-help">최대 100자까지 입력 가능합니다</small>
        </div>
        
        <div class="form-group">
            <label for="content">내용 <span class="required">*</span></label>
            <textarea id="content" name="content" rows="4" required placeholder="상세 내용을 입력하세요" maxlength="500"></textarea>
            <small class="form-help">최대 500자까지 입력 가능합니다</small>
        </div>
        
        <div class="form-group">
            <label for="status">상태 <span class="required">*</span></label>
            <select id="status" name="status" required>
                <option value="0" selected>운영중</option>
                <option value="1">폐업</option>
            </select>
        </div>
        
        <div class="form-actions">
            <button type="submit" class="btn btn-save" >등록</button>
            <button type="button" class="btn btn-reset" onclick="resetForm()">초기화</button>
            <button type="button" class="btn btn-cancel" onclick="goBack()">취소</button>
        </div>
    </form>
</div>

<script>
// 목록으로 돌아가기
function goBack() {
    if (confirm('작성중인 내용이 있다면 저장되지 않습니다. 목록으로 돌아가시겠습니까?')) {
    	location.href = '<%= request.getContextPath() %>/about/comhistory.jsp';
    }
}

// 폼 초기화
function resetForm() {
    if (confirm('입력한 내용이 모두 삭제됩니다. 계속하시겠습니까?')) {
        document.getElementById('comHistoryForm').reset();
        // 기본값 다시 설정
        document.getElementById('startDate').value = '<%= today %>';
        document.getElementById('status').value = '0';
        document.getElementById('historyId').value = '<%= nextId %>';
    }
}

// ID 중복 체크
function checkDuplicateId() {
    const historyId = document.getElementById('historyId').value.trim();
    if (historyId === '') return;
    
    // 기존 ID 목록과 비교
    const existingIds = [
        <% for (int i = 0; i < nowCom_HistoryList.size(); i++) { %>
            '<%= nowCom_HistoryList.get(i).getId() %>'<%= (i < nowCom_HistoryList.size() - 1) ? "," : "" %>
        <% } %>
    ];
    
    if (existingIds.includes(historyId)) {
        alert('이미 존재하는 연혁 ID입니다. 다른 ID를 입력해주세요.');
        document.getElementById('historyId').focus();
        return false;
    }
    return true;
}

// 폼 유효성 검사
document.getElementById('comHistoryForm').addEventListener('submit', function(e) {
    // ID 중복 체크
    if (!checkDuplicateId()) {
        e.preventDefault();
        return false;
    }
    
    // 날짜 유효성 검사
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    
    if (startDate && endDate && startDate > endDate) {
        e.preventDefault();
        alert('개업일은 폐업일보다 이전이어야 합니다.');
        document.getElementById('endDate').focus();
        return false;
    }
    
    // 상태와 폐업일 일관성 체크
    const status = document.getElementById('status').value;
    if (status === '1' && !endDate) {
        if (confirm('폐업 상태로 설정되었지만 폐업일이 입력되지 않았습니다. 계속하시겠습니까?')) {
            return true;
        } else {
            e.preventDefault();
            document.getElementById('endDate').focus();
            return false;
        }
    }
    
    if (status === '0' && endDate) {
        if (confirm('운영중 상태로 설정되었지만 폐업일이 입력되어 있습니다. 계속하시겠습니까?')) {
            return true;
        } else {
            e.preventDefault();
            document.getElementById('status').focus();
            return false;
        }
    }
});

// 상태 변경 시 자동 날짜 처리
document.getElementById('status').addEventListener('change', function() {
    const status = this.value;
    const endDateInput = document.getElementById('endDate');
    
    if (status === '0') {
        // 운영중으로 변경 시 폐업일 비우기
        if (endDateInput.value && confirm('운영중 상태로 변경하면 폐업일이 삭제됩니다. 계속하시겠습니까?')) {
            endDateInput.value = '';
        }
    } else if (status === '1') {
        // 폐업으로 변경 시 오늘 날짜 설정 제안
        if (!endDateInput.value && confirm('폐업일을 오늘 날짜로 설정하시겠습니까?')) {
            endDateInput.value = '<%= today %>';
        }
    }
});

// 실시간 글자 수 카운트
document.getElementById('content').addEventListener('input', function() {
    const maxLength = 500;
    const currentLength = this.value.length;
    
    // 도움말 텍스트 업데이트
    const helpText = this.nextElementSibling;
    helpText.textContent = `${currentLength}/${maxLength}자 입력됨`;
});

document.getElementById('title').addEventListener('input', function() {
    const maxLength = 100;
    const currentLength = this.value.length;
    
    // 도움말 텍스트 업데이트
    const helpText = this.nextElementSibling;
    helpText.textContent = `${currentLength}/${maxLength}자 입력됨`;
});

// 페이지 로드 완료 후 첫 번째 입력 필드에 포커스
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('comId').focus();
});
</script>