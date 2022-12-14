<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.body-content {
		margin-top: 9rem;
		margin-left: 15rem;
		padding-left: 3rem;
		padding-right: 10rem;
	}
	
	input {
		display: block;
	}
</style>
</head>
<body>
	<%@include file ="../layout/header.jsp" %>
	<div class="body-content">
	<div class="container col-5 my-4">
		<form action="/board/save" method="post" enctype="multipart/form-data">
			<select class="form-select mt-3 mb-3" aria-label="Default select example" name="categoryId">
				<option selected>게시판 선택</option>
				<option value="1">공지사항</option>
				<option value="2">가입인사</option>
				<option value="3">질문하기</option>
				<option value="4">자유게시판</option>
			</select>
			<input type="hidden" name="memberId" value="${sessionScope.id}">
			<input class="form-control mb-3" type="text" name="boardTitle" placeholder="제목">
			<input type="hidden" name="boardWriter" value="${sessionScope.memberName}">
			<textarea class="form-control mb-3" name="boardContents" rows="5" placeholder="내용"></textarea>
			<input class="form-control mb-3" type="file" name="boardFile" multiple>
			<input class="btn btn-outline-dark d-grid mx-auto" type="submit" value="글작성">
		</form>
	</div>
	</div>
</body>
</html>