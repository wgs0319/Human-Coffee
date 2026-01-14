<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<link rel="stylesheet" href="./css/menu-dikapein.css" />


<div class="menu-header">
        <h1>DECAF MENU</h1>
        <p>편안한 휴식을 위한 디카페인 커피</p>
    </div>

    <!-- 메뉴 카테고리 -->
    <div class="menu-section">
        <!-- 디카페인 -->
        <div class="category">
            <h2>🌿 디카페인</h2>
            <div class="menu-list">
                <div class="menu-item">
                    <div class="decaf-badge">Decaf</div>
                    <img src="<%= request.getContextPath() %>/images/coffee/HOT_아메리카노.jpg" alt="아메리카노">
                    <h3>디카페인 아메리카노</h3>
                    <p>카페인을 제거한 부드럽고 은은한 맛의 아메리카노로, 언제든지 편안하게 즐길 수 있습니다.</p>
                </div>
                <div class="menu-item">
                    <div class="decaf-badge">Decaf</div>
                    <img src="<%= request.getContextPath() %>/images/coffee/HOT_카페라떼.jpg" alt="라떼">
                    <h3>디카페인 라떼</h3>
                    <p>부드러운 우유와 디카페인 에스프레소가 어우러진 편안한 커피의 여유를 선사합니다.</p>
                </div>
            </div>
        </div>
    </div>

    <!-- 디카페인 정보 섹션 -->
    <div class="info-section">
        <h3>디카페인 커피란?</h3>
        <p>
            디카페인 커피는 카페인 함량을 97% 이상 제거한 커피로, 카페인에 민감하거나 
            저녁 시간에도 커피를 즐기고 싶은 분들에게 완벽한 선택입니다. 
            커피 본연의 풍미는 그대로 유지하면서 편안한 휴식을 제공합니다.
        </p>
</div>
