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
		<h2>회원 정보 수정</h2>
		<form action="/member/update" method="post" name="updateForm">
			<input type="hidden" name="id" value="${member.id}">
			아이디 <input type="text" name="loginId" class="form-control-plaintext" value="${member.loginId}">
			비밀번호 <input type="password" id="input-pw" name="memberPassword" class="form-control">
			이름 <input type="text" name="memberName" class="form-control" value="${member.memberName}">
			전화번호 <input type="text" name="memberMobile" class="form-control" value="${member.memberMobile}">
			<input type="button" class="btn btn-outline-info d-grid mx-auto m-2" onclick="pwCheck()" value="정보수정">
		</form>		
	</div>
	</div>
</body>
<script>
	const pwCheck = () => {
		const pw = ${member.memberPassword};
		const inputPw = document.getElementById("input-pw").value;
		
		if (pw == inputPw) {
			updateForm.submit();
		} else {
			alert("비밀번호를 다시 확인해주세요.");
			document.getElementById("input-pw").innerHtml = "";
		}
	}
</script>
</html>