<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="merchantCreate_container">
    <div class="merchantCreate_title">가맹점 등록</div>

    <form class="merchantCreate_form" method="post" action="<%= request.getContextPath() %>/admin/merchant/merchantCreateAction.jsp">
        <div class="merchantCreate_row">
            <label class="merchantCreate_label">가맹점명</label>
            <input type="text" class="merchantCreate_input" name="name" value="">
        </div>

        <div class="merchantCreate_row">
            <label class="merchantCreate_label">대표전화</label>
            <input type="text" class="merchantCreate_input" name="tel" value="">
        </div>

        <div class="merchantCreate_row">
            <label class="merchantCreate_label">FAX</label>
            <input type="text" class="merchantCreate_input" name="fax" value="">
        </div>

        <div class="merchantCreate_row">
            <label class="merchantCreate_label">주소</label>
            <input type="text" class="merchantCreate_input" name="addr" value="">
        </div>

        <div class="merchantCreate_row">
            <label class="merchantCreate_label">대표 이메일</label>
            <input type="email" class="merchantCreate_input" name="email" value="">
        </div>

        <div class="merchantCreate_row">
            <label class="merchantCreate_label">창립일</label>
            <input type="date" class="merchantCreate_input" name="indate" value="">
        </div>
        <div class="merchantCreate_actions">
            <button type="submit" class="merchantCreate_btn merchantCreate_btn_save">저장</button>
            <button type="button" class="merchantCreate_btn merchantCreate_btn_cancel" onclick="history.back()">취소</button>
        </div>
    </form>
</div>
