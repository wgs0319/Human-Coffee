<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>    
<%@ page import="com.humancoffee.common.*" %>   
<%
	Common common = new Common();
	String pathProject = common.getProjectPath(application.getRealPath("/"), request.getContextPath());

%>


 <div class="menu-header">
        <h1>COFFEE MENU</h1>
        <p>따뜻한 휴먼커피의 진한 맛을 만나보세요</p>
    </div>
 <div class="menu-section">
        <!-- 커피 -->
        <div class="category">
            <h2>☕ 커피</h2>
            <div class="menu-list">
                <div class="menu-item hot-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/HOT_americano.jpg" alt="HOT_아메리카노">
                    <h3>HOT 아메리카노</h3>
                    <p>진한 에스프레소와 깔끔한 맛의 기본 커피</p>
                </div>
                <div class="menu-item hot-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/HOT_돌체라떼.jpg" alt="HOT_돌체라떼">
                    <h3>HOT 돌체라떼</h3>
                    <p>달콤한 연유가 어우러진 부드러운 라떼</p>
                </div>
                <div class="menu-item hot-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/카푸치노.jpg" alt="카푸치노">
                    <h3>HOT 카푸치노</h3>
                    <p>풍부한 우유 거품과 진한 에스프레소의 조화</p>
                </div>
                <div class="menu-item hot-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/HOT_카페라떼.jpg" alt="라떼">
                    <h3>HOT 라떼</h3>
                    <p>부드러운 우유와 진한 커피의 크리미한 맛</p>
                </div>
                <div class="menu-item hot-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/HOT_바닐라라떼.jpg" alt="바닐라라떼">
                    <h3>HOT 바닐라라떼</h3>
                    <p>은은한 바닐라 향이 더해진 달콤한 라떼</p>
                </div>
                <div class="menu-item hot-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/HOT_카라멜마끼아또.jpg" alt="카라멜마끼아또">
                    <h3>HOT 카라멜마끼아또</h3>
                    <p>카라멜 시럽과 에스프레소가 어우러진 달콤한 맛</p>
                </div>
                <div class="menu-item hot-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/HOT_카페모카.jpg" alt="카페모카">
                    <h3>HOT 카페모카</h3>
                    <p>초콜릿과 커피가 어우러진 달콤한 음료</p>
                </div>
                <div class="menu-item hot-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/HOT_헤이즐넛라떼.jpg" alt="헤이즐넛라떼">
                    <h3>HOT 헤이즐넛라떼</h3>
                    <p>고소한 헤이즐넛 향이 풍부한 라떼</p>
                </div>
                <div class="menu-item ice-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/ICE_바닐라라떼.jpg" alt="ICE 바닐라라떼">
                    <h3>ICE 바닐라라떼</h3>
                    <p>시원하게 즐기는 바닐라 향 가득한 라떼</p>
                </div>
                <div class="menu-item ice-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/ICE_아메리카노.jpg" alt="ICE 아메리카노">
                    <h3>ICE 아메리카노</h3>
                    <p>청량하고 깔끔한 아이스 커피의 정석</p>
                </div>
                <div class="menu-item ice-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/ICE_카라멜마끼아또.jpg" alt="ICE 카라멜마끼아또">
                    <h3>ICE 카라멜마끼아또</h3>
                    <p>시원하게 즐기는 달콤한 카라멜 커피</p>
                </div>
                <div class="menu-item ice-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/ICE_카페라떼.jpg" alt="ICE 카페라떼">
                    <h3>ICE 카페라떼</h3>
                    <p>부드럽고 시원한 라떼의 기본</p>
                </div>
                <div class="menu-item ice-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/ICE_카페모카.jpg" alt="ICE 카페모카">
                    <h3>ICE 카페모카</h3>
                    <p>진한 초콜릿과 시원한 커피의 조화</p>
                </div>
                <div class="menu-item ice-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/ICE_헤이즐넛라떼.jpg" alt="ICE 헤이즐넛라떼">
                    <h3>ICE 헤이즐넛라떼</h3>
                    <p>고소한 헤이즐넛 향의 시원한 라떼</p>
                </div>
                <div class="menu-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/달고나라떼.jpg" alt="달고나라떼">
                    <h3>달고나라떼</h3>
                    <p>달콤한 거품이 매력적인 트렌디한 라떼</p>
                </div>
                <div class="menu-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/아인슈페너라떼.jpg" alt="아인슈페너라떼">
                    <h3>아인슈페너라떼</h3>
                    <p>진한 에스프레소 위에 달콤한 크림을 얹은 음료</p>
                </div>
                <div class="menu-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/에스프레소.jpg" alt="에스프레소">
                    <h3>에스프레소</h3>
                    <p>커피 본연의 진한 풍미를 담은 한 잔</p>
                </div>
                <div class="menu-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/카푸치노.jpg" alt="카푸치노">
                    <h3>카푸치노</h3>
                    <p>에스프레소와 거품 우유가 어우러진 클래식 커피</p>
                </div>
                <div class="menu-item">
                    <img src="<%= request.getContextPath() %>/images/coffee/흑당카페라떼.jpg" alt="흑당카페라떼">
                    <h3>흑당카페라떼</h3>
                    <p>달콤한 흑당 시럽이 어우러진 트렌디한 라떼</p>
                </div>
            </div>
        </div>
    </div>