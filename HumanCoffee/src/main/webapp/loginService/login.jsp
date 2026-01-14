<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.humancoffee.manager.*" %>
<%@ page import = "com.humancoffee.common.*" %>
<%@ page import = "com.humancoffee.model.*" %>
<%@ page import = "com.humancoffee.*" %>

<%
	// out 내장객체로 웹 페이지 출력
	out.println("<h1>로그인 처리중입니다</h1>");

	// post로 요청한 파라미터 조회 - 파라미터 이름 수정
	String Id = request.getParameter("id");
	String password = request.getParameter("pw");
	int roll;
	out.println("Id: " + Id + ", Pwd: " + password);
	
	// ServletContext에서 HumanCoffee 객체를 가져옴
	HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
	// HumanCoffee 객체로부터 ManageLogin 객체를 가져옴
	ManageLogin mLogin = hcInstance.mLogin;
	
	out.println("ManageLogin : " + mLogin);
	try {
		roll = mLogin.login(Id, password);
		if( roll >= 0 ) {
			System.out.println("success roll : " + roll);
			
			// tomcat 내장 session객체에 작성자를 저장
			session.setAttribute("id", Id);
			session.setAttribute("roll", roll);
			ManageCustomers mCustomer = hcInstance.mCustomers;
			Customer customer = new Customer();
			customer.setId(Id);
			System.out.println("id: " + Id);
			Customer chk_customer = mCustomer.searchCustomerById(customer);
			if(chk_customer != null){
				session.setAttribute("name", chk_customer.getName());
				session.setAttribute("div", "customer");
				session.setAttribute("point", chk_customer.getPoint());
				session.setAttribute("cupon", chk_customer.getCupon());
				System.out.println("name: " + chk_customer.getName());
				System.out.println("point: " + chk_customer.getPoint());
				System.out.println("cupon: " + chk_customer.getCupon());
			}else{
				ManageComMembers mComMems = hcInstance.mComMems;
				Com_Member com_member = new Com_Member();
				com_member.setId(Id);
				Com_Member chk_com_member = mComMems.searchComMemberById(com_member);
				session.setAttribute("name", chk_com_member.getName());
				session.setAttribute("div", "member");
				session.setAttribute("point", 0);
				session.setAttribute("cupon", 0);
			}		
%>
<script>
    alert('로그인에 성공했습니다.');
    var form = document.createElement('form');
    form.method = 'POST';
    form.action = '<%= request.getContextPath() %>/';
    document.body.appendChild(form);
    form.submit();
</script>
<%
		} else {
%>
<script>
    alert('아이디 또는 비밀번호가 올바르지 않습니다.');
    var form = document.createElement('form');
    form.method = 'POST';
    form.action = '<%= request.getContextPath() %>/';
    
 	// hidden 파라미터로 page 값 전달
    var hidden = document.createElement('input');
    hidden.type = 'hidden';
    hidden.name = 'page';
    hidden.value = 'login_form.jsp';
    form.appendChild(hidden);
    
    // Create an input for the error parameter
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'error';
    input.value = 'invalid';
    form.appendChild(input);
    
    document.body.appendChild(form);
    form.submit();
</script>
<%
		}
	} catch (Exception e) {
		Common common = new Common();
		common.getStackTraceAsString(e);
	}
%>