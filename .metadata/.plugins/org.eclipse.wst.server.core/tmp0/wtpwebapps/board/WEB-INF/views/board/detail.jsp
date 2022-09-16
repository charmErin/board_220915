<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
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
	<%@include file="../layout/header.jsp"%>
	<div class="body-content">
		<div class="container col-7 my-4">
			<table class="table mb-5">
				<tr>
					<td colspan="2">${board.boardTitle}</td>
				</tr>
				<tr>
					<td>작성자: ${board.boardWriter}</td>
					<td>조회수: ${board.boardHits}</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2">작성일: <fmt:formatDate pattern="yyyy-MM-dd"
							value="${board.createdTime}"></fmt:formatDate></td>
				</tr>
				<tr>
					<td colspan="2"><textarea class="form-control-plaintext"
							rows="5">${board.boardContents}</textarea></td>
				</tr>
				<tr>
					<td colspan="2">첨부파일자리</td>
				</tr>
			</table>

			<span>댓글 쓰기</span>
			<hr>

			<div class="input-group mb-5">
				<textarea class="form-control" id="comment-contents"></textarea>
				<input class="btn btn-outline-dark" type="button"
					onclick="commentSave()" value="댓글 작성">
			</div>

			<span>댓글 목록</span>
			<hr>
			<div id="comment-list">
				<table class="table">
					<tr>
						<th>내용</th>
						<th>작성자</th>
						<th>작성일자</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach var="comment" items="${commentList}">
						<tr id="comment_${comment.id}">
							<td id="contents_${comment.id}">${comment.commentContents}</td>
							<td id="writer_${comment.id}">${comment.commentWriter}</td>
							<td id="createdTime_${comment.id}"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
									value="${comment.createdTime}"></fmt:formatDate></td>
							<c:choose>
								<c:when test="${sessionScope.id eq comment.memberId}">
									<td id="updateBtn_${comment.id}"><button class="btn btn-outline-info" type="button"
											onclick="commentUpdateForm('${comment.id}', '${comment.commentContents}')">수정</button></td>
									<td id="deleteBtn_${comment.id}"><button class="btn btn-outline-danger" type="button"
											onclick="commentDelete('${comment.id}')">삭제</button></td>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${sessionScope.loginId eq 'admin'}">
											<td></td>
											<td><button class="btn btn-outline-danger" type="button"
													onclick="commentDelete('${comment.id}')">삭제</button></td>
										</c:when>
										<c:otherwise>
											<td colspan="2"></td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
<script>
	const commentSave = () => {
		const boardId = '${board.id}';
		const memberId = '${sessionScope.id}';
		const commentWriter = '${sessionScope.memberName}';
		const commentContents = document.getElementById("comment-contents").value;
		const loginId = '${sessionScope.loginId}';
		const commentList = document.getElementById("comment-list");
		
		$.ajax({
			type: "post",
			url: "/comment/save",
			data: {"boardId": boardId, "memberId": memberId, "commentWriter": commentWriter, "commentContents": commentContents},
			dataType: "json",
			success: function(result) {
				let output = '<table class="table">'
							+ '	<tr>'
							+ '		<th>내용</th>'
							+ '		<th>작성자</th>'
							+ '		<th>작성일자</th>'
							+ '		<th></th>'
							+ '		<th></th>'
							+ '	</tr>';

				for (let i in result) {
					output += '	<tr>'
							+ '		<td>' + result[i].commentContents + '</td>'
							+ '		<td>' + result[i].commentWriter + '</td>'
							+ '		<td>' + moment(result[i].createdTime).format("YYYY-MM-DD HH:mm:ss") + '</td>';
							
					if (memberId == result[i].memberId) {
						output += '<td><button class="btn btn-outline-info" type="button" onclick="commentUpdateForm((' + result[i].id + ')">수정</button></td>'
								+ '<td><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
						
					} else {
						if (loginId == 'admin') {
							output += '<td></td>'
									+ '<td><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
						} else {
							output += '	<td></td>'
									+ '	<td></td>';
						}
					}
					output += '	</tr>';
					
				}
				output += '</table>';
				
				commentList.innerHTML = output;
				document.getElementById("comment-contents").innerHTML = "";
			}
		});
	}
	
	const commentDelete = (id) => {
		const boardId = '${board.id}';
		const memberId = '${sessionScope.id}';
		const loginId = '${sessionScope.loginId}';
		const commentList = document.getElementById("comment-list");
		
		$.ajax({
			type: "post",
			url: "/comment",
			data: {"id": id, "boardId": boardId},
			dataType: "json",
			success: function(result) {
				let output = '<table class="table">'
							+ '	<tr>'
							+ '		<th>내용</th>'
							+ '		<th>작성자</th>'
							+ '		<th>작성일자</th>'
							+ '		<th></th>'
							+ '		<th></th>'
							+ '	</tr>';

				for (let i in result) {
					output += '	<tr>'
							+ '		<td>' + result[i].commentContents + '</td>'
							+ '		<td>' + result[i].commentWriter + '</td>'
							+ '		<td>' + moment(result[i].createdTime).format("YYYY-MM-DD HH:mm:ss") + '</td>';
							
					if (memberId == result[i].memberId) {
						output += '<td><button class="btn btn-outline-info" type="button" onclick="commentUpdateForm((' + result[i].id + ')">수정</button></td>'
								+ '<td><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
						
					} else {
						if (loginId == 'admin') {
							output += '<td></td>'
									+ '<td><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
						} else {
							output += '	<td></td>'
									+ '	<td></td>';
						}
					}
					output += '	</tr>';
					
				}
				output += '</table>';
				
				commentList.innerHTML = output;
			}
		});
	}
	
	
	let rawComment = null;
	
	const commentUpdateForm = (id, commentContents) => {
		const comment = document.getElementById("comment_" + id);
		rawComment = document.getElementById("comment_" + id).innerHTML;
		
		let output = '<td colspan="3"><textarea class="form-control" id="new-contents">' + commentContents + '</textarea></td>'
					+ '<td><button type="button" class="btn btn-dark" onclick="commentUpdate(' + "'" + id + "')" + '">수정</button></td>'
					+ '<td><button type="button" class="btn btn-outline-danger" onclick="commentCancel(' + id + ')">취소</button></td>';
		
		comment.innerHTML = output;
	}
	
	const commentUpdate = (id) => {
		const newContents = document.getElementById("new-contents").value;
		const boardId = '${board.id}';
		const memberId = '${sessionScope.id}';
		const loginId = '${sessionScope.loginId}';
		const commentList = document.getElementById("comment-list");
		
		$.ajax({
			type: "post",
			url: "/comment/update",
			data: {"id": id, "boardId": boardId, "commentContents": newContents},
			dataType: "json",
			success: function(result) {
				let output = '<table class="table">'
					+ '	<tr>'
					+ '		<th>내용</th>'
					+ '		<th>작성자</th>'
					+ '		<th>작성일자</th>'
					+ '		<th></th>'
					+ '		<th></th>'
					+ '	</tr>';

			for (let i in result) {
				output += '	<tr>'
						+ '		<td>' + result[i].commentContents + '</td>'
						+ '		<td>' + result[i].commentWriter + '</td>'
						+ '		<td>' + moment(result[i].createdTime).format("YYYY-MM-DD HH:mm:ss") + '</td>';
						
				if (memberId == result[i].memberId) {
					output += '<td><button class="btn btn-outline-info" type="button" onclick="commentUpdateForm((' + result[i].id + ')">수정</button></td>'
							+ '<td><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
					
				} else {
					if (loginId == 'admin') {
						output += '<td></td>'
								+ '<td><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
					} else {
						output += '	<td></td>'
								+ '	<td></td>';
					}
				}
				output += '	</tr>';
				
			}
			output += '</table>';
			
			commentList.innerHTML = output;
			}
		});
	}
	
	const commentCancel = (id) => {
		document.getElementById("comment_" + id).innerHTML = rawComment;
	}
</script>
</html>