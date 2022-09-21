<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %> <%--  --%>
<%@ page import="java.io.PrintWriter" %> <%-- 자바스크립트 사용하기위함 --%>
<% request.setCharacterEncoding("utf-8"); %> <%-- 전달받은 데이터를 UTF-8로 변환 --%>
<jsp:useBean id="user" class="user.User" scope="page"/> <%-- Beans 사용 --%>
<jsp:setProperty name="user" property="userID"/>
<jsp:setProperty name="user" property="userPassword"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){ // 세션에 해당id가 설정되있다면
			userID = (String) session.getAttribute("userID"); // 해당 id값을 userID에 저장
		}
		if(userID != null){ // id가 null이 아니라면(세션에 아이디가 존재한다면) 메인 페이지로 이동
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 로그인이 되어있습니다.')");
			script.println("location.href =  'main.jsp'");
			script.println("</script>");
		}
		UserDAO userDAO = new UserDAO();
		int result = userDAO.login(user.getUserID(), user.getUserPassword()); // 전달받은 id,password를 login함수에 삽입
		if(result == 1){
			session.setAttribute("userID", user.getUserID());  // 로그인에 성공시 세션에 userID 설정
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href =  'main.jsp'");
			script.println("</script>");
		}else if(result == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('비밀번호가 틀립니다.')");
			script.println("history.back()");
			script.println("</script>");
		}else if(result == -1){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('존재하지 않는 아이디입니다.')");
			script.println("history.back()");
			script.println("</script>");
		}else if(result == -2){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류가 발생했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
	%>
</body>
</html>