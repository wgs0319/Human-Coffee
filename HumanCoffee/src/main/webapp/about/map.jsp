<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import = "com.humancoffee.*" %>
<%@ page import="com.humancoffee.model.Company" %>
<%@ page import="com.humancoffee.manager.ManageCompanys" %>

<%
    String companyName = "";
    String companyTel = "";
    String companyFax = "";
    String companyAddr = "";
    String companyId = "2025081901"; // 본사 ID
    
    try {
        // ServletContext에서 HumanCoffee 객체를 가져옴
        HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
        
        if (hcInstance != null && hcInstance.mCompanys != null) {
            // ManageCompanys 객체를 가져옴
            ManageCompanys mCompanys = hcInstance.mCompanys;
            
            // 검색할 Company 객체 생성
            Company searchCompany = new Company();
            searchCompany.setId(companyId);
            
            // ID로 회사 정보 검색
            Company foundCompany = mCompanys.searchCompanyById(searchCompany);
            
            if (foundCompany != null && foundCompany.getStatus() == 0) { // status 0 = 활성
                companyName = foundCompany.getName() != null ? foundCompany.getName() : companyName;
                companyTel = foundCompany.getTel() != null ? foundCompany.getTel() : companyTel;
                companyFax = foundCompany.getFax() != null ? foundCompany.getFax() : companyFax;
                companyAddr = foundCompany.getAddr() != null ? foundCompany.getAddr() : companyAddr;
                
                System.out.println("회사 정보 조회 성공: " + companyName);
            } else {
                System.out.println("회사 정보를 찾을 수 없거나 비활성 상태입니다. ID: " + companyId);
            }
        } else {
            System.out.println("HumanCoffee 인스턴스 또는 ManageCompanys가 null입니다.");
        }
        
    } catch (Exception e) {
        // 오류 시 기본값 사용
        System.out.println("회사 정보 조회 오류: " + e.getMessage());
        e.printStackTrace();
    }
%>

<div class="map">
    <div class="title">
        <h1>오시는 길</h1>
        <p><%= companyName %> 본사로 오시는 길을 안내드립니다</p>
    </div>

    <div class="container">
        <div class="content-wrapper">
            <div class="left-container">
                <div class="company-info">
                    <h3>본사 위치</h3>
                    
                    <div class="info-item">
                        <div class="info-label">회사명</div>
                        <div class="info-value"><%= companyName %></div>
                    </div>
                    
                    <div class="info-item">
                        <div class="info-label">주소</div>
                        <div class="info-value"><%= companyAddr %></div>
                    </div>
                    
                    <div class="info-item">
                        <div class="info-label">전화번호</div>
                        <div class="info-value">Tel. <%= companyTel %></div>
                    </div>
                    
                    <div class="info-item">
                        <div class="info-label">팩스</div>
                        <div class="info-value">Fax. <%= companyFax %></div>
                    </div>
                </div>
                
                <div class="navigation-buttons">
                    <a href="#" class="nav-btn naver" onclick="openNaverMap()">
                        네이버 지도
                    </a>
                    <a href="#" class="nav-btn kakao" onclick="openKakaoMap()">
                        카카오 지도
                    </a>
                </div>
            </div>
            
            <div class="right-container">
                <div class="map-container">
                    <!-- Google Maps 임베드 -->
                    <iframe class="embedded-map" 
                        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3174.7628025087824!2d127.02498751151211!3d37.27705144055839!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357b434db893bab1%3A0x9aa2101d832e4a89!2z6rK96riw64-EIOyImOybkOyLnCDtjJTri6zqtawg7KSR67aA64yA66GcIDEwMA!5e0!3m2!1sko!2skr!4v1755741288181!5m2!1sko!2skr" 
                        width="600" 
                        height="450" 
                        style="border:0;" 
                        allowfullscreen="" 
                        loading="lazy" 
                        referrerpolicy="no-referrer-when-downgrade">
                    </iframe>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // 네이버 지도 열기
    function openNaverMap() {
        const address = encodeURIComponent('<%= companyAddr %>');
        const url = `https://naver.me/57QrU6RF`;
        window.open(url, '_blank');
    }
    
    // 카카오 지도 열기
    function openKakaoMap() {
        const address = encodeURIComponent('<%= companyAddr %>');
        const url = `https://kko.kakao.com/xQRrrpFEzO`;
        window.open(url, '_blank');
    }
</script>