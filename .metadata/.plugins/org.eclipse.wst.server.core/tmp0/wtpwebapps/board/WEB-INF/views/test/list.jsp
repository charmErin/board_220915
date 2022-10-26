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
		<button onclick="download('${file.id}','${file.fileName}')">다운로드</button><br>
	</c:forEach>
	
	
</body>
<script>
	const download = (id, fileName) => {
		var xhr = new XMLHttpRequest();
		
		$.ajax({
			type: "get",
			url: "/file-test/download3/" + id,
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
</script>
</html>