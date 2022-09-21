<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width", initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP 게시판 웹 사이트</title>
<style>

</style>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){ // 로그인을 한 유저라면 userID에 해당 id가 저장됨
			userID = (String) session.getAttribute("userID");
		}
	%>
		
	<nav class="navbar navbar-default">
		<div class="navar - header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expended="false">
				<span class="icon-bar"></span>	
				<span class="icon-bar"></span>	
				<span class="icon-bar"></span>	
			</button>
			<a class="navbar-brand" href="main.jsp">BBS WEBSITE</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="main.jsp">MainHome</a></li>
				<li><a href="bbs.jsp">Notice Board</a></li>
			</ul>
			<%-- 로그인이 안되어있는 유저라면 로그인을 할 수 있도록 함 --%>
			<%
				if(userID == null){
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expended="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul>
			<%
				}
				else {
			%>
			<%-- 로그인 상태라면 회원관리창과 로그아웃 링크만 표시 --%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expended="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul>
				</li>
			</ul>
			<%
				}
			%>
		</div>
	</nav>
	<div class="maincontent">
		<div class="container">
			<div class="jumbotron">
				<div class="container">
					<h1 style="font-size: 25px"> 이 웹사이트는 JSP와 Bootstrap으로 만들어졌으며, mariaDB를 이용하여 로그인 및 게시판 데이터를 처리합니다.</h1>
					<br><br>
					<p> > 비회원은 회원가입을 웹사이트의 기능을 사용할 수 있는 권한을 얻을수 있습니다. </p>
					<p> > 회원은 게시판에 글 작성 및 수정 삭제를 할 수 있습니다. </p>
					<p> > 비화원일 경우에도 게시판 작성글 열람은 가능합니다. </p>
					<p> > 사용자와 작성자의 로그인 정보가 불일치 할 경우, 열람한 글 내용을 수정 및 삭제 할 수 없습니다. </p>
					<p><a class="btn btn-primary btn-pull" href="main.jsp" role="button">홈으로 돌아가기</a></p>
				</div>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	
</body>
</html>