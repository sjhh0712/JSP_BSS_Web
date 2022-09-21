<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO" %> 
<%@ page import="bbs.Bbs" %>
<%@ page import="java.io.PrintWriter" %> <%-- 자바스크립트 사용하기위함 --%>
<% request.setCharacterEncoding("utf-8"); %> <%-- 전달받은 데이터를 UTF-8로 변환 --%>
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
		if(userID == null){ // id가 null이라면(세션에 아이디가 존재하지않는다면) 로그인 페이지로 이동
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href =  'login.jsp'");
			script.println("</script>");
		}
		int bbsID = 0;
		if (request.getParameter("bbsID") != null) {
			bbsID = Integer.parseInt(request.getParameter("bbsID"));
		}
		Bbs bbs = new BbsDAO().getBbs(bbsID);
		if(!userID.equals(bbs.getUserID())){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href = 'bbs.jsp'");
			script.println("</script>");
		}
		else {	
			BbsDAO bbsDAO = new BbsDAO();
			int result = bbsDAO.delete(bbsID);
			if(result == -1){ 
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('삭제 실패')");
				script.println("history.back()");
				script.println("</script>");
			}
			else
			{
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('삭제 완료')");
				script.println("location.href = 'bbs.jsp'");
				script.println("</script>");
			}			
		}
		
	%>
</body>
</html>