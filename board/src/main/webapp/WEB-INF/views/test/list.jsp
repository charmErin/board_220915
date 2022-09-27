<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<c:forEach var="file" items="${fileList}">
		${file.fileName}<br>
		<button onclick="download('${file.id}')">다운로드</button><br>
	</c:forEach>
	
	
</body>
<script>
	const download = (id) => {
		$.ajax({
			type: "get",
			url: "/file-test/download3/" + id,
			success: function(result) {
				alert("다운로드 성공");
			}
		});

	}
</script>
</html>