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
	
	h2 {
		text-align: center;
		margin: 50px;
	}
</style>
</head>
<body>
	<%@include file ="../layout/header.jsp" %>
	<div class="body-content">
	<div class="container col-4">
		<h2>회원 정보</h2>
		<table class="table">
			<tr>
				<th scope="row">회원번호</th>
				<td>${member.id}</td>
			</tr>
			<tr>
				<th scope="row">아이디</th>
				<td>${member.loginId}</td>
			</tr>
			<tr>
				<th scope="row">이름</th>
				<td>${member.memberName}</td>
			</tr>
			<tr>
				<th scope="row">전화번호</th>
				<td>${member.memberMobile}</td>
			</tr>
		</table>
		<c:if test="${sessionScope.id == member.id}">
			<c:choose>
				<c:when test="${sessionScope.memberName == '관리자'}">
					<div class="d-grid gap-2 d-md-flex justify-content-md-end">
						<button type="button" class="btn btn-outline-dark" onclick="memberList()">회원목록</button>
					</div>
				</c:when>
				<c:otherwise>
					<div class="d-grid gap-2 d-md-flex justify-content-md-end">
				<button type="button" class="btn btn-outline-primary" onclick="memberUpdate()">정보수정</button>
				<button type="button" class="btn btn-outline-dark" onclick="memberDelete()">회원탈퇴</button>			
			</div>
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
	</div>
</body>
<script>
	const memberUpdate = () => {
		location.href = "/member/update-form/" + ${sessionScope.id};
	}
	
	const memberDelete = () => {
		location.href = "/member/delete/" + ${sessionScope.id};
	}
	
	const memberList = () => {
		location.href = "/member/findAll";
	}
</script>
</html>