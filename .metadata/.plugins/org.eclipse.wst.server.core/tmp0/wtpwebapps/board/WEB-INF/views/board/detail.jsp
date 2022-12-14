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
			<table class="table mb-3">
				<tr>
					<td colspan="2">${board.boardTitle}</td>
				</tr>
				<tr>
					<td>작성자: ${board.boardWriter}</td>
					<td>조회수: ${board.boardHits}</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2">작성일: <fmt:formatDate pattern="yyyy-MM-dd hh:MM:ss"
							value="${board.createdTime}"></fmt:formatDate></td>
				</tr>
				<tr>
					<td colspan="2"><textarea class="form-control-plaintext"
							rows="5" readonly>${board.boardContents}</textarea></td>
				</tr>
				<c:if test="${!board.boardFileList.isEmpty()}">
					<tr>
						<td colspan="2">
								첨부파일<br>
								<c:forEach var="fileList" items="${board.boardFileList}">
									${fileList.boardFileName}
									<button class="btn btn-sm btn-outline-dark" onclick="downloadFile('${fileList.id}','${fileList.boardFileName}')">다운로드</button><br>
								</c:forEach>
						</td>
					</tr>
				</c:if>
<%-- 				<c:if test="${board.boardFileName ne null}"> --%>
<!-- 					<tr> -->
<%-- 						<td colspan="2"><img src="${pageContext.request.contextPath}/upload/${board.boardFileName}" alt="" height="100" width="100"></td> --%>
<!-- 					</tr> -->
<%-- 				</c:if> --%>
			</table>
			
			<div class="d-grid gap-2 d-md-flex justify-content-md-end mb-5">
				<c:choose>
					<c:when test="${sessionScope.id eq board.memberId}">
						<button class="btn btn-warning me-md-2" type="button" onclick="updateBoard()">수정</button>
						<button class="btn btn-secondary me-md-2" type="button" onclick="deleteBoard()">삭제</button>
						<button class="btn btn-dark" type="button" onclick="goBack()">글목록</button>
					</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.loginId eq 'admin'}">
							<button class="btn btn-secondary me-md-2" type="button" onclick="deleteBoard()">삭제</button>
							<button class="btn btn-dark" type="button" onclick="goBack()">글목록</button>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
			

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
											<td><button id="deleteBtn_${comment.id}" class="btn btn-outline-danger" type="button"
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
	const updateBoard = () => {
		location.href = "/board/update-form?page=" + ${page} + "&id=" + ${board.id};
	}
	
	const deleteBoard = () => {
		if (confirm("삭제하시겠습니까?")) {
			location.href = "/board/delete/" + ${board.id};
		}
	}
	
	const downloadFile = (id, fileName) => {
		var xhr = new XMLHttpRequest();
		
		$.ajax({
			type: "get",
			url: "/board/download/" + id,
			cache: false,
			xhrFields: {  //response 데이터를 바이너리로 처리한다.
				responseType: 'blob'
			},
			success: function(result) {
				console.log("완료");
				var blob = new Blob([result]);

				var link = document.createElement('a');
				link.href = window.URL.createObjectURL(blob);
				link.download = fileName;
				link.click();
			}
		});
	}


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
					output += '	<tr id="comment_' + result[i].id + '">'
							+ '		<td id="contents_' + result[i].id + '">' + result[i].commentContents + '</td>'
							+ '		<td id="writer_' + result[i].id + '">' + result[i].commentWriter + '</td>'
							+ '		<td id="createdTime_' + result[i].id + '">' + moment(result[i].createdTime).format("YYYY-MM-DD HH:mm:ss") + '</td>';
							
					if (memberId == result[i].memberId) {
						output += '<td id="updateBtn_' + result[i].id + '"><button class="btn btn-outline-info" type="button" onclick="commentUpdateForm(' + result[i].id + ",'" + result[i].commentContents + "'"  + ')">수정</button></td>'
								+ '<td id="deleteBtn_' + result[i].id + '"><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
						
					} else {
						if (loginId == 'admin') {
							output += '<td></td>'
									+ '<td id="deleteBtn_' + result[i].id + '"><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
						} else {
							output += '	<td></td>'
									+ '	<td></td>';
						}
					}
					output += '	</tr>';
					
				}
				output += '</table>';
				
				commentList.innerHTML = output;
				document.getElementById("comment-contents").value = null;
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
					output += '	<tr id="comment_' + result[i].id + '">'
							+ '		<td id="contents_' + result[i].id + '">' + result[i].commentContents + '</td>'
							+ '		<td id="writer_' + result[i].id + '">' + result[i].commentWriter + '</td>'
							+ '		<td id="createdTime_' + result[i].id + '">' + moment(result[i].createdTime).format("YYYY-MM-DD HH:mm:ss") + '</td>';
							
					if (memberId == result[i].memberId) {
						output += '<td id="updateBtn_' + result[i].id + '"><button class="btn btn-outline-info" type="button" onclick="commentUpdateForm(' + result[i].id + ",'" + result[i].commentContents + "'"  + ')">수정</button></td>'
								+ '<td id="deleteBtn_' + result[i].id + '"><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
						
					} else {
						if (loginId == 'admin') {
							output += '<td></td>'
									+ '<td id="deleteBtn_' + result[i].id + '"><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
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
					output += '	<tr id="comment_' + result[i].id + '">'
							+ '		<td id="contents_' + result[i].id + '">' + result[i].commentContents + '</td>'
							+ '		<td id="writer_' + result[i].id + '">' + result[i].commentWriter + '</td>'
							+ '		<td id="createdTime_' + result[i].id + '">' + moment(result[i].createdTime).format("YYYY-MM-DD HH:mm:ss") + '</td>';
							
					if (memberId == result[i].memberId) {
						output += '<td id="updateBtn_' + result[i].id + '"><button class="btn btn-outline-info" type="button" onclick="commentUpdateForm(' + result[i].id + ",'" + result[i].commentContents + "'"  + ')">수정</button></td>'
								+ '<td id="deleteBtn_' + result[i].id + '"><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
						
					} else {
						if (loginId == 'admin') {
							output += '<td></td>'
									+ '<td id="deleteBtn_' + result[i].id + '"><button class="btn btn-outline-danger" type="button" onclick="commentDelete(' + result[i].id + ')">삭제</button></td>';
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
	
	const goBack = () => {
		location.href = "/board/" + ${categoryId} + "?page=" + ${page} + "&id=" + ${board.id};
	}
</script>
</html>