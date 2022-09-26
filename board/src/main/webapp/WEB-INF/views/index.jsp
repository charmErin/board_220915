<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
<title>Insert title here</title>
<style>
	.body-content {
		margin-top: 10rem;
		margin-left: 15rem;
		padding-left: 3rem;
		padding-right: 10rem;
	}
</style>
</head>
<body>
	<%@include file ="layout/header.jsp" %>
	<div class="body-content">
	
		<div>
			index.jsp
		</div>
		
		<a href="/file-test/save-form">파일 저장</a><br>
		<a href="/file-test/findAll">파일 불러오기</a><br>
	</div>
</body>
</html>