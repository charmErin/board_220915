<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
		<h2>회원가입</h2>
		<form action="/member/save" method="post" name="saveForm">
			<div class="form-floating mb-3">
			  <input type="text" name="loginId" class="form-control" id="loginId" onblur="idCheck()" placeholder="아이디" autofocus>
			  <label for="loginId">아이디</label>
			</div>
			<p id="id-check-msg"></p>
			<div class="form-floating mb-3">
			  <input type="password" name="memberPassword" class="form-control" id="floatingInput" placeholder="비밀번호">
			  <label for="floatingInput">비밀번호</label>
			</div>
			<div class="form-floating mb-3">
			  <input type="text" name="memberName" class="form-control" id="floatingInput" placeholder="이름">
			  <label for="floatingInput">이름</label>
			</div>
			<div class="form-floating mb-3">
			  <input type="text" name="memberMobile" class="form-control" id="floatingInput" placeholder="전화번호">
			  <label for="floatingInput">전화번호</label>
			</div>
			<input type="button" class="btn btn-outline-info d-grid mx-auto" onclick="memberSave()" value="회원가입">
		</form>		
	</div>
	</div>
</body>
<script>
	let dupCheck = false;
	
	const idCheck = () => {
		const loginId = document.getElementById("loginId").value;
		const idCheckMsg = document.getElementById("id-check-msg");
		
		if (loginId.legnth != 0) {
			
			$.ajax({
				type: "post",
				url: "/member/dup-check",
				data: {"loginId": loginId},
				dataType: "text",
				success: function(result) {
					if (result == "ok") {
						dupCheck = true;
						idCheckMsg.innerHTML = '사용가능한 아이디 입니다.';
						idCheckMsg.style.color = 'green';
					} else {
						dupCheck = false;
						idCheckMsg.innerHTML = '이미 사용중인 아이디입니다.';
						idCheckMsg.style.color = 'red';
					}
				}
			});
		} else {
			idCheckMsg.innerHTML = "필수 입력 항목입니다.";
			idCheckMsg.style.color = "red";
		}
	}
	
	const memberSave = () => {
		if (dupCheck) {
			saveForm.submit();
		} else {
			document.getElementById("loginId").focus();
			alert("아이디를 확인해 주세요.");
		}
	}
	
</script>
</html>