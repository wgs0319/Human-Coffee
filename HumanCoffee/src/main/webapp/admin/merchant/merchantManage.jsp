<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.humancoffee.model.Sub_Com" %>
<%@ page import="com.humancoffee.manager.ManageSubComs" %>
<%@ page import="com.humancoffee.HumanCoffee" %>

<script type="text/javascript">
    // 삭제 확인 함수
    function merchantManage_delete(id) {
        if (confirm("해당 가맹점을 삭제하시겠습니까?")) {
            alert("삭제 요청 예정: " + id);
            // TODO: 실제 삭제 기능은 추후 구현
        }
    }
    
    // 수정 이동 함수
    function merchantManage_update(id) {
        // form 생성
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = '<%= request.getContextPath() %>/';
        form.acceptCharset = 'UTF-8';
        
        // next_page 파라미터를 위한 hidden input
        var nextPageInput = document.createElement('input');
        nextPageInput.type = 'hidden';
        nextPageInput.name = 'next_page';
        nextPageInput.value = '/admin/merchant/merchantUpdate.jsp';
        
        // id 파라미터를 위한 hidden input
        var idInput = document.createElement('input');
        idInput.type = 'hidden';
        idInput.name = 'id';
        idInput.value = id;
        
        // form에 input들 추가
        form.appendChild(nextPageInput);
        form.appendChild(idInput);
        
        // body에 form 추가하고 submit 후 제거
        document.body.appendChild(form);
        form.submit();
        document.body.removeChild(form);
    }
</script>

<div class="merchantManage_container">
	<div class="merchantManage_titlecontainer">
		<div class="merchantManage_title">가맹점 리스트</div>
		 <form action="<%= request.getContextPath() %>/" method="post">
    		<input type="hidden" name="next_page" value="/admin/merchant/merchantCreate.jsp" />
	        <!-- 가맹점 관리 페이지로 이동 -->
	        <button type="submit" class="adminMain_btn">
	            가맹점 등록하기
	        </button>
		</form>
	</div>
 

    <table class="merchantManage_table">
        <thead>
            <tr>
                <th>가맹점명</th>
                <th>대표전화</th>
                <th>FAX</th>
                <th>주소</th>
                <th>대표 이메일</th>
                <th>창립일</th>
                <th>폐업일</th>
                <th>상태</th>
                <th>관리</th>
            </tr>
        </thead>
        <tbody>
            <%
                HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");

                if (hcInstance != null) {
                    ManageSubComs mSubComs = hcInstance.mSubComs;
                    List<Sub_Com> merchantList = mSubComs.sub_coms[mSubComs.memory_pos];

                    if (merchantList != null && !merchantList.isEmpty()) {
                        for (Sub_Com merchant : merchantList) {
            %>
            <tr>
                <td><%= merchant.getName() %></td>
                <td><%= merchant.getTel() %></td>
                <td><%= merchant.getFax() %></td>
                <td><%= merchant.getAddr() %></td>
                <td><%= merchant.getEmail() %></td>
                <td><%= merchant.getInDate() %></td>
                <td><%= merchant.getOutDate() %></td>
                <td><%= merchant.getStatus() == 0 ? "정상운영" : "폐업" %></td>
                <td>
                    <button type="button" onclick="merchantManage_update('<%= merchant.getId() %>')">수정</button>
                    <button type="button" onclick="merchantManage_delete('<%= merchant.getId() %>')">삭제</button>
                </td>
            </tr>
            <%
                        }
                    } else {
            %>
            <tr>
                <td colspan="9">등록된 가맹점이 없습니다.</td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="9">시스템에서 가맹점 정보를 불러올 수 없습니다.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>