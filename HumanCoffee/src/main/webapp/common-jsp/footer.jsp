<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<footer class="footer_root">
    <div class="footer_container">
        <div class="footer_com_intro">
            <ul class="footer_menu">회사소개
                <form action="<%= request.getContextPath() %>/" method="post" class="footer_tree">
					<input type="hidden" name="next_page" value="/about/company.jsp" />
					<button type="submit" class="link-button">휴먼커피에 대하여</button>
				</form>
                <form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/about/comhistory.jsp" />
					<button type="submit" class="link-button">연혁</button>
				</form>
                <form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/about/map.jsp" />
					<button type="submit" class="link-button">오시는 길</button>
				</form>
            </ul>
        </div>
        <div class="footer_product">
            <ul class="footer_menu">제품
                <form action="<%= request.getContextPath() %>/" method="post" class="footer_tree">
					<input type="hidden" name="next_page" value="/menu/menu-coffee.jsp" />
					<button type="submit" class="link-button">커피</button>
				</form>
				<form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/menu/menu-dikapein.jsp" />
					<button type="submit" class="link-button">디카페인</button>
				</form> 
                <form action="<%= request.getContextPath() %>/" method="post">
					<input type="hidden" name="next_page" value="/menu/menu-juice.jsp" />
					<button type="submit" class="link-button">주스</button>
				</form> 
            </ul>
        </div>
        <div class="footer_franchise">
            <ul class="footer_menu">가맹점
                <form action="<%= request.getContextPath() %>/" method="post" class="footer_tree">
					<input type="hidden" name="next_page" value="/merchant/merchantList.jsp" />
					<button type="submit" class="link-button">가맹점 리스트</button>
				  </form> 
            </ul>
        </div>
        <div class="footer_info">
            <div class="footer_infotext">
                <div>사업자등록번호 201-81-21515</div>
                <div>(주)휴먼 커피 대표: 홍길동</div>
                <div>TEL : (02) 3015-1100 / FAX : (02) 3015-1106</div>
                <div>개인정보 책임자 : 이순신</div>
            </div>
            <button class="footer_button">Privacy Statement +</button>
            <p class="footer_copyright">
                &copy; <span class="footer_year"></span> Human Coffee Company. All Rights Reserved.
            </p>
        </div>
    </div>
</footer>
