<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/file-test/save2" method="post" enctype="multipart/form-data">
		<input type="file" name="blobFile">
		<input type="submit" value="파일 올리기">
	</form>
</body>
<script>

	//file을 Blob으로 변환
	function getfileToBlob(){
		let reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = function(event){
			let dataURL = event.target.result;
			let base64 = dataURL.substr(dataURL.indexOf('base64,')+7);
			let file_etc = dataURL.substring(dataURL.indexOf(':')+1,dataURL.indexOf(';'));
	        				
			let blob = b64toBlob(base64,file_etc);
			let blobUrl = URL.createObjectURL(blob);// blobUrl을 src에 넣으면 5초이상 재생가능
		}
	}
	
	//base64를 Blob으로 변환
	function b64toBlob(b64Data,contentType){
   		let sliceSize=512;
		const byteCharacters = atob(b64Data);
		const byteArrays = [];
		
		for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
		  const slice = byteCharacters.slice(offset, offset + sliceSize);
		
		  const byteNumbers = new Array(slice.length);
		  for (let i = 0; i < slice.length; i++) {
		    byteNumbers[i] = slice.charCodeAt(i);
		  }
		
		  const byteArray = new Uint8Array(byteNumbers);
		  byteArrays.push(byteArray);
		}
		
		const blob = new Blob(byteArrays, {type: contentType});
		return blob;
	}
	
	function send(){
	    let formData = new FormData();
	    formData.append('blob', blob, fileName); // (key, value, 파일명)
	    formData.append('file_etc', file_etc);
		
	    $.ajax({
			method : "POST",
			url : "/file-test/save",
			processData: false, 
	  		contentType: false, 
			data : formdata,
			success : function (result) {
			
	        }
	    });
	}
</script>
</html>