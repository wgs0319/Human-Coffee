<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="login-container">
    <div class="login-form">
        <h2>Human Coffee 로그인</h2>
        
        <!-- 로그인 폼 -->
        <form action="<%= request.getContextPath() %>/loginService/login.jsp" method="post">
            <div>
                <label>아이디 입력</label>
                <input type="text" name="id" placeholder="아이디를 입력해주세요.">
            </div>
            <div>
                <label>비밀번호 입력</label>
                <input type="password" name="pw" placeholder="비밀번호를 입력해주세요.">
            </div>
            <button type="submit" class="login-btn">로그인</button>
        </form>
        
        <!-- 버튼들 컨테이너 -->
        <div class="button-container">
            <!-- 취소 버튼 -->
            <form action="<%= request.getContextPath() %>/" method="post" style="display: inline;">
                <input type="hidden" name="next_page" value="./home.jsp" />
                <button type="submit" class="btn cancel-btn">취소</button>
            </form>
            
            <!-- 회원가입 버튼 -->
            <form action="<%= request.getContextPath() %>/" method="post" style="display: inline;">
                <input type="hidden" name="next_page" value="./loginService/signup_form.jsp" />
                <button type="submit" class="btn signup-btn">회원가입</button>
            </form>
        </div>
    </div>
</div>