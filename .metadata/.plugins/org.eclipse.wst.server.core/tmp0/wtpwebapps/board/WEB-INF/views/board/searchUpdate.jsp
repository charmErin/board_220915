<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
</style>
</head>
<body>
	<%@include file ="../layout/header.jsp" %>
	<div class="body-content">
	<div class="container col-5 my-4">
		<form action="/board/search/update" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${board.id}">
			<input type="hidden" name="searchType" value="${searchType}">
			<input type="hidden" name="q" value="${q}">
			<input type="hidden" name="page" value="${page}">
			<select class="form-select mt-3 mb-3" aria-label="Default select example" name="categoryId">
				<c:choose>
					<c:when test="${board.categoryId eq 1}">
						<option value="1" selected>공지사항</option>
						<option value="2">가입인사</option>
						<option value="3">질문하기</option>
						<option value="4">자유게시판</option>
					</c:when>
					<c:when test="${board.categoryId eq 2}">
						<option value="1">공지사항</option>
						<option value="2" selected>가입인사</option>
						<option value="3">질문하기</option>
						<option value="4">자유게시판</option>
					</c:when>
					<c:when test="${board.categoryId eq 3}">
						<option value="1">공지사항</option>
						<option value="2">가입인사</option>
						<option value="3" selected>질문하기</option>
						<option value="4">자유게시판</option>
					</c:when>
					<c:otherwise>
						<option value="1">공지사항</option>
						<option value="2">가입인사</option>
						<option value="3">질문하기</option>
						<option value="4" selected>자유게시판</option>
					</c:otherwise>
				</c:choose>
			</select>
			<input type="hidden" name="memberId" value="${board.memberId}">
			<input class="form-control mb-3" type="text" name="boardTitle" value="${board.boardTitle}">
			<input type="hidden" name="boardWriter" value="${board.boardWriter}">
			<textarea class="form-control mb-3" name="boardContents" rows="5">${board.boardContents}</textarea>
<!-- 			<div class="mb-3"> -->
<!-- 			  <label for="formFile" class="form-label">현재 파일</label> -->
<%-- 			  <input class="form-control" type="file" name="boardFileName" value="${board.boardFileName}"> --%>
<!-- 			</div> -->
<!-- 			<input type="button" onclick="fileDelete()" value="이전 첨부파일 삭제"> -->
			<div class="mb-3">
			  <label for="formFile" class="form-label">변경할 파일 첨부</label>
			  <input class="form-control" type="file" name="boardFile" id="formFile">
			</div>
			<input class="btn btn-outline-info d-grid mx-auto" type="submit" value="글수정">
		</form>
	</div>
	</div>
</body>
</html>