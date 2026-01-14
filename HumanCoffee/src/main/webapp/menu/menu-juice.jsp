<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 메뉴 타이틀 -->
<div class="menu-header">
    <h1>JUICE MENU</h1>
    <p>신선하고 상큼한 과일 주스로 건강한 하루를 시작하세요</p>
</div>

<!-- 메뉴 카테고리 -->
<div class="menu-section">
    <!-- 주스 -->
    <div class="category">
        <h2>🍹 주스 & 에이드</h2>
        <div class="menu-list">
            <div class="menu-item strawberry">
              <img src="<%= request.getContextPath() %>/images/juice/국내산딸기주스.png" alt="딸기주스">
                <h3>국내산딸기주스</h3>
                <p>달콤하고 상큼한 국내산 딸기의 풍미가 가득한 프리미엄 주스</p>
            </div>
            <div class="menu-item lemon">
                <img src="<%= request.getContextPath() %>/images/juice/레몬에이드.jpg" alt="레몬에이드">
                <h3>레몬에이드</h3>
                <p>상큼한 레몬과 달콤함이 조화로운 시원한 음료</p>
            </div>
            <div class="menu-item mango">
                <img src="<%= request.getContextPath() %>/images/juice/망고에이드.jpg" alt="망고에이드">
                <h3>망고에이드</h3>
                <p>달콤하고 시원한 열대 망고의 진한 맛</p>
            </div>
            <div class="menu-item peach">
                <img src="<%= request.getContextPath() %>/images/juice/복숭아주스.jpg" alt="복숭아주스">
                <h3>복숭아주스</h3>
                <p>부드럽고 달콤한 복숭아 향이 가득한 주스</p>
            </div>
            <div class="menu-item lemon">
                <img src="<%= request.getContextPath() %>/images/juice/블루레몬스페셜에이드.jpg" alt="블루레몬스페셜에이드">
                <h3>블루레몬스페셜에이드</h3>
                <p>상큼한 레몬과 블루베리가 어우러진 스페셜 에이드</p>
            </div>
            <div class="menu-item grape">
                <img src="<%= request.getContextPath() %>/images/juice/샤인머스캣케일주스.jpg" alt="샤인머스캣케일주스">
                <h3>샤인머스캣케일주스</h3>
                <p>달콤한 샤인머스캣과 건강한 케일의 완벽한 조화</p>
            </div>
            <div class="menu-item orange">
                <img src="<%= request.getContextPath() %>/images/juice/오렌지당근주스.jpg" alt="오렌지당근주스">
                <h3>오렌지당근주스</h3>
                <p>비타민 가득, 상큼한 오렌지와 당근이 어우러진 건강 주스</p>
            </div>
        </div>
    </div>
</div>
