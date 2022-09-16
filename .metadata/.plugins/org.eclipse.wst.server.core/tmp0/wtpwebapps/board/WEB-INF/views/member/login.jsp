<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
	.body-content {
		margin-top: 9rem;
		margin-left: 15rem;
		padding-left: 3rem;
		padding-right: 10rem;
	}
	
	h2 {
		text-align: center;
		margin-bottom: 50px;
	}
</style>
<body>
	<%@include file ="../layout/header.jsp" %>
	<div class="body-content">
	<div class="container col-3">
		<h2>로그인</h2>
		<form action="/member/login" method="post">
			<div class="form-floating mb-3">
			  <input type="text" name="loginId" class="form-control" id="floatingInput" placeholder="아이디" autofocus>
			  <label for="floatingInput">아이디</label>
			</div>
			<div class="form-floating mb-3">
			  <input type="password" name="memberPassword" class="form-control" id="floatingInput" placeholder="비밀번호">
			  <label for="floatingInput">비밀번호</label>
			</div>
			<input type="submit" class="btn btn-info d-grid mx-auto" value="로그인">
		</form>		
	</div>
	</div>
</body>
</html>