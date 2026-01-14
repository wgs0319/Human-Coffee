<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.humancoffee.model.Sub_Com" %>
<%@ page import="com.humancoffee.manager.ManageSubComs" %>
<%@ page import="com.humancoffee.HumanCoffee" %>
<%@ page import="java.util.List" %>

<div class="merchantUpdate_container">
    <div class="merchantUpdate_title">가맹점 수정</div>

    <%
        String id = request.getParameter("id");
        Sub_Com merchant = null;

        if (id != null) {
            HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
            if (hcInstance != null) {
                ManageSubComs mSubComs = hcInstance.mSubComs;
                List<Sub_Com> merchantList = mSubComs.sub_coms[mSubComs.memory_pos];

                if (merchantList != null) {
                    for (Sub_Com m : merchantList) {
                        if (id.equals(m.getId())) {
                            merchant = m;
                            break;
                        }
                    }
                }
            }
        }
    %>

    <%
        if (merchant != null) {
    %>
    <form class="merchantUpdate_form" method="post" action="<%= request.getContextPath() %>/admin/merchant/merchantUpdateAction.jsp">
        <input type="hidden" name="id" value="<%= merchant.getId() %>">
        <input type="hidden" name="com_id" value="<%= merchant.getComId() %>">

        <div class="merchantUpdate_row">
            <label class="merchantUpdate_label">가맹점명</label>
            <input type="text" class="merchantUpdate_input" name="name" value="<%= merchant.getName() %>">
        </div>

        <div class="merchantUpdate_row">
            <label class="merchantUpdate_label">대표전화</label>
            <input type="text" class="merchantUpdate_input" name="tel" value="<%= merchant.getTel() %>">
        </div>

        <div class="merchantUpdate_row">
            <label class="merchantUpdate_label">FAX</label>
            <input type="text" class="merchantUpdate_input" name="fax" value="<%= merchant.getFax() %>">
        </div>

        <div class="merchantUpdate_row">
            <label class="merchantUpdate_label">주소</label>
            <input type="text" class="merchantUpdate_input" name="addr" value="<%= merchant.getAddr() %>">
        </div>

        <div class="merchantUpdate_row">
            <label class="merchantUpdate_label">대표 이메일</label>
            <input type="email" class="merchantUpdate_input" name="email" value="<%= merchant.getEmail() %>">
        </div>

        <div class="merchantUpdate_row">
            <label class="merchantUpdate_label">창립일</label>
            <input type="date" class="merchantUpdate_input" name="indate" value="<%= merchant.getInDate() %>">
        </div>

        <div class="merchantUpdate_row">
            <label class="merchantUpdate_label">폐업일</label>
            <input type="date" class="merchantUpdate_input" name="outdate" value="<%= merchant.getOutDate() %>">
        </div>

        <div class="merchantUpdate_row">
            <label class="merchantUpdate_label">상태</label>
            <select class="merchantUpdate_input" name="status">
                <option value="0" <%= merchant.getStatus() == 0 ? "selected" : "" %>>정상운영</option>
                <option value="1" <%= merchant.getStatus() == 1 ? "selected" : "" %>>폐업</option>
            </select>
        </div>

        <div class="merchantUpdate_actions">
            <button type="submit" class="merchantUpdate_btn merchantUpdate_btn_save">저장</button>
            <button type="button" class="merchantUpdate_btn merchantUpdate_btn_cancel" onclick="history.back()">취소</button>
        </div>
    </form>
    <%
        } else {
    %>
    <div class="merchantUpdate_error">해당 가맹점을 불러올 수 없습니다.</div>
    <%
        }
    %>
</div>
