<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

	h2 {
		text-align: center;
	}
	
	a {
		text-decoration: none !important;
		color: #000 !important;
	}
	
	td a:hover {
		color: dodgerblue !important;
		cursor: pointer;
	}
	
	th {
		text-align: center !important;
	}
	
	td:not(:nth-child(2)) {
		text-align: center !important;
	}
</style>
</head>
<body>
	<%@include file ="../layout/header.jsp" %>
	<div class="body-content">
	<div class="container my-4">
		<div>
		<c:choose>
			<c:when test="${category eq 1}">
				<h2>공지사항</h2>
			</c:when>
			<c:when test="${category eq 2}">
				<h2>가입인사</h2>
			</c:when>
			<c:when test="${category eq 3}">
				<h2>질문하기</h2>
			</c:when>
			<c:otherwise>
				<h2>자유게시판</h2>
			</c:otherwise>
		</c:choose>
		</div>
		
		<div>
	    	<form class="col-4" action="/board/search" method="get">
				<div class="input-group">
					<select class="form-select col-1" name="searchType">
						<option value="boardTitle">제목</option>
						<option value="boardWriter">작성자</option>
						<option value="boardContents">내용</option>
					</select>
					<input class="form-control" type="text" name="q" placeholder="검색" aria-label="Search">
				</div>
			</form>
	    </div>
		
		<div class="d-grid gap-2 d-md-flex justify-content-md-end mb-4">
			<c:if test="${sessionScope.id ne null}">
				<button onclick="boardSave()" class="btn btn-dark">글작성</button>
			</c:if>
		</div>
		
		
		<table class="table table-hover">
			<tr>
				<th scope="row">번호</th>
				<th scope="row" class="col-4">제목</th>
				<th scope="row">작성자</th>
				<th scope="row">조회수</th>
				<th scope="row">작성날짜</th>
				<th></th>
			</tr>
			<c:forEach var="board" items="${boardList}">
				<tr>
					<td>${board.id}</td>
					<td>
						<a href="/board/detail/${categoryId}?page=${pageDTO.page}&id=${board.id}">${board.boardTitle}
						<c:if test="${board.boardFileName ne null}"><img src="../../../resources/svg/image.svg" alt=""/></c:if></a>
					</td>
					<td>${board.boardWriter}</td>
					<td>${board.boardHits}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.createdTime}"></fmt:formatDate></td>
					<td></td>
				</tr>
			</c:forEach>
		</table>
	</div>
    
	
	<div class="container">
        <ul class="pagination justify-content-center">
            <c:choose>
                <c:when test="${pageDTO.page<=1}">
                    <li class="page-item disabled">
                        <a class="page-link">이전</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="/board/${categoryId}?page=${pageDTO.page-1}">이전</a>
                    </li>
                </c:otherwise>
            </c:choose>
            <c:forEach begin="${pageDTO.startPage}" end="${pageDTO.endPage}" var="i" step="1">
                <c:choose>
                    <c:when test="${i eq pageDTO.page}">
                        <li class="page-item active">
                            <a class="page-link text-white">${i}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="/board/${categoryId}?page=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${pageDTO.page>=pageDTO.maxPage}">
                    <li class="page-item disabled">
                        <a class="page-link">다음</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="/board/${categoryId}?page=${pageDTO.page+1}">다음</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    </div>
</body>
<script>
	const boardSave = () => {
		location.href = "/board/save-form";
	}
</script>
</html>