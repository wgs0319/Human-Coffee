# ☕ HumanCoffee: 실시간 주문 커피 쇼핑몰 시스템

## 📌 프로젝트 소개
사용자의 주문 편의성과 관리자의 운영 효율성을 극대화한 Java 기반 웹 애플리케이션입니다. 
WebSocket 기술을 도입하여 관리자가 주문 현황을 실시간으로 모니터링할 수 있는 대시보드를 구축했습니다.

## 🛠 기술 스택
- **Backend:** Java, Jakarta Servlet, Jakarta WebSocket
- **Frontend:** HTML5, CSS3, JavaScript, JSP
- **Database:** Oracle Database, JDBC
- **Library:** Jackson (JSON Parsing), SHA-256 (Security)
- **Server:** Apache Tomcat 10.1

## ✨ 주요 기능
1. **실시간 주문 동기화:** WebSocket을 이용해 주문 발생 시 관리자 페이지에 실시간 브로드캐스팅
2. **보안 로그인:** SHA-256 해시 알고리즘을 적용한 안전한 사용자 인증 및 세션 관리
3. **관리자 권한 시스템:** RBAC(Role-Based Access Control)를 통한 제품 등록 및 주문 관리 권한 차등 부여
4. **통합 관리:** 제품 이미지 업로드(Blob), 쿠폰 발행, 고객 관리 기능을 포함한 통합 ERP 형태 구현
5. **공통 레이아웃:** Header/Footer 등 재사용 가능한 UI 컴포넌트 설계

## 💻 핵심 구현 포인트
- **실시간 데이터 처리:** `OrderWebSocket` 클래스를 통한 서버-클라이언트 간 JSON 데이터 양방향 통신
- **서버 생명주기 관리:** `ServletContextListener`를 활용해 서버 시작 시 DB 연결 및 스레드 자동 초기화
- **DB 최적화:** `OraConnect` 공통 클래스를 설계하여 DB 커넥션 및 쿼리 실행 로직의 코드 중복 최소화
