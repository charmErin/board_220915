<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
<title>Insert title here</title>
<style>
	header {
		background: #CDCDCD;
	}
	
	.side-bar {
		width: 14rem;
		height: 100%;
		margin-top: 1rem;
		position: fixed;
		display: inline !important;
	}
</style>
</head>
<body>
	<header class="py-3 border-bottom header-fix fixed-top">
		<div class="container d-flex flex-wrap justify-content-center">
			<a href="/" class="d-flex align-items-center mb-3 mb-lg-0 me-lg-auto text-dark text-decoration-none">
				<img src="../../../resources/svg/mailbox.svg" width="40" height="40">
				<span class="fs-4 m-2">
					MemberB
				</span>
			</a>
			
			<div>
				<c:choose>
					<c:when test="${sessionScope.id == null}">
						<ul class="nav">
							<li class="nav-item">
								<a href="/member/login-form" class="nav-link link-dark px-2">로그인</a>
							</li>
							<li class="nav-item">
								<a href="/member/save-form" class="nav-link link-dark px-2">회원가입</a>
							</li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="nav">
							<li class="nav-item">
								<a href="/member/detail/${sessionScope.id}" class="nav-link link-dark px-2">${sessionScope.memberName}</a>
							</li>
							<c:if test="${sessionScope.loginId eq 'admin'}">
								<li class="nav-item">
									<a href="/member/findAll" class="nav-link link-dark px-2">회원목록</a>
								</li>
							</c:if>
							<li class="nav-item">
								<a href="/member/logout" class="nav-link link-dark px-2">로그아웃</a>
							</li>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="d-flex flex-column flex-shrink-0 p-3 text-bg-dark side-bar">
			<span class="fs-4">카테고리</span>
			<hr>
			<ul class="nav nav-pills flex-column mb-auto">
				<li class="nav-item">
					<a href="/board/1" class="nav-link text-white">공지사항</a>
				</li>
				<li>
					<a href="/board/2" class="nav-link text-white">가입인사</a>
				</li>
				<li>
					<a href="/board/3" class="nav-link text-white">질문하기</a>
				</li>
				<li>
					<a href="/board/4" class="nav-link text-white">자유게시판</a>
				</li>
			</ul>
		</div>
	</header>
</body>
</html>