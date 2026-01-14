<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%@ page import="com.humancoffee.common.*" %>
<%@ page import="com.humancoffee.model.*" %>
<%@ page import="com.humancoffee.manager.*" %>
<%@ page import="com.humancoffee.*" %>

<%
    // POST 방식으로만 처리하도록 제한
    if (!"POST".equals(request.getMethod())) {
        response.sendRedirect(request.getContextPath() + "/loginService/signup_form.jsp");
        return;
    }

	//post로 요청한 파라미터 조회 - 파라미터 이름 수정
	String Id = request.getParameter("id");
	String password = request.getParameter("pw");
	String name = request.getParameter("name");
	String tel = request.getParameter("tel");

	// 입력값 검증
    if (Id == null || password == null || name == null || tel == null ||
        Id.trim().isEmpty() || password.trim().isEmpty() || 
        name.trim().isEmpty() || tel.trim().isEmpty()) {
%>
        <script>
            alert('모든 정보를 입력해주세요.');
            setTimeout(function() {
                history.back();
            }, 100);
        </script>
<%
        return;
    }

    try {
        // ServletContext에서 HumanCoffee 객체를 가져옴
        HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
        if (hcInstance == null) {
%>
            <script>
                alert('시스템 초기화 오류입니다. 관리자에게 문의하세요.');
                setTimeout(function() {
                    history.back();
                }, 100);
            </script>
<%
            return;
        }
        
        ManageCustomers mCustomers = hcInstance.mCustomers;
        ManageComMembers mMembers = hcInstance.mComMems;
        Customer customer = new Customer();
        customer.setId(Id);
        Com_Member com_member = new Com_Member();
        com_member.setId(Id);
        if (mCustomers == null) {
%>
            <script>
                alert('고객 관리 시스템 오류입니다. 관리자에게 문의하세요.');
                setTimeout(function() {
                    history.back();
                }, 100);
            </script>
<%
            return;
        }
        
        
        Customer chkCustomer = mCustomers.searchCustomerById(customer);
        // Customer 중복 검사
        if(chkCustomer != null){
%>
            <script>
                alert('이미 존재하는 아이디입니다.');
                setTimeout(function() {
                    history.back();
                }, 100);
            </script>
<%
            return; // 중요: 여기서 실행을 중단해야 함
        }
        
        // Com_Member 중복 검사
        System.out.println("mMembers:" + mMembers);
        System.out.println("mMembers size:" + mMembers.com_members[mMembers.memory_pos].size());
        if (mMembers == null) {
%>
            <script>
                alert('멤버 관리 시스템 오류입니다. 관리자에게 문의하세요.');
                setTimeout(function() {
                    history.back();
                }, 100);
            </script>
<%
            return;
        }
        
        Com_Member chk_com_member = mMembers.searchComMemberById(com_member);
        
        if(chk_com_member != null){
%>
            <script>
                alert('이미 존재하는 아이디입니다.');
                setTimeout(function() {
                    history.back();
                }, 100);
            </script>
<%
            return; // 중요: 여기서 실행을 중단해야 함
        }
        
        // 중복이 없는 경우에만 회원가입 진행
        // Customer 객체 설정
        customer.setPwd(password);
        customer.setName(name);
        customer.setTel(tel);
        System.out.println("테스트");
        
        // 회원가입 실행
        try {
            mCustomers.insertCustomer(customer);
            // 회원가입 성공 - JavaScript로 alert 후 리다이렉트
            session.setAttribute("signupSuccess", "true");
%>
            <script>
                alert('회원가입에 성공했습니다.');
                setTimeout(function() {
                	var form = document.createElement('form');
                    form.method = 'POST';
                    form.action = '<%= request.getContextPath() %>/loginService/login_form.jsp';
                    document.body.appendChild(form);
                    form.submit();
                }, 100);
            </script>
<%
            return;
        } catch (Exception insertException) {
            // 회원가입 실패
%>
            <script>
                alert('회원가입에 실패했습니다. 다시 시도해주세요. 오류: <%= insertException.getMessage() %>');
                setTimeout(function() {
                    history.back();
                }, 100);
            </script>
<%
            return;
        }
        
    } catch (Exception e) {
        // 예외 발생시 상세 오류 메시지 표시
        String errorMessage = "알 수 없는 오류";
        if (e.getMessage() != null) {
            errorMessage = e.getMessage();
        }
%>
        <script>
            alert('회원가입 처리 중 오류가 발생하였습니다: <%= errorMessage %>');
            console.log('Full error: <%= e.toString() %>');
            setTimeout(function() {
                history.back();
            }, 100);
        </script>
<%
        // 서버 로그에도 출력
        System.out.println("Signup error: " + e.toString());
        e.printStackTrace();
        return;
    }
%>