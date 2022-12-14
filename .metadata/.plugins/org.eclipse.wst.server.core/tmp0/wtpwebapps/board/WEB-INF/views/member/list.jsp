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
	
	a {
		text-decoration: none !important;
		color: #000 !important;
	}
	
	a:hover  {
		font-weight: boarder;
		cursor: pointer;
	}
	
	th td {
		text-align: center !important;
	}
	
	td:not(:last-child) {
		cursor: pointer;
	}
</style>
</head>
<body>
	<%@include file ="../layout/header.jsp" %>
	<div class="body-content">
	<div class="container">
		<h2>회원목록</h2>
		<table class="table table-hover">
			<tr>
				<th scope="row">회원번호</th>
				<th scope="row">아이디</th>
				<th scope="row">이름</th>
				<th scope="row">전화번호</th>
				<th></th>
			</tr>
			<c:forEach var="member" items="${memberList}">
				<tr>
					<td onclick="detailMember('${member.id}')">${member.id}</td>
					<td onclick="detailMember('${member.id}')">${member.loginId}</td>
					<td onclick="detailMember('${member.id}')">${member.memberName}</td>
					<td onclick="detailMember('${member.id}')">${member.memberMobile}</td>
					<c:choose>
						<c:when test="${member.loginId ne 'admin'}">
							<td><button class="btn btn-danger" onclick="deleteMember('${member.id}')">삭제</button></td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
	</div>
	</div>
</body>
<script>
	const detailMember = (id) => {
		location.href = "/member/detail/" + id;
	}
	const deleteMember = (id) => {
		location.href = "/member/delete/" + id;
	}
</script>
</html>