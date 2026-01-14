<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
<link rel="stylesheet" href="../../css/productForm.css" />

</head>
<body>
<h2>상품 등록</h2>
<form action="./product_insert.jsp" method="post" id="form-product-insert" enctype="multipart/form-data">
상품명: <input type="text" name="name" required><br>
가격: <input type="number" name="price" required><br>
카테고리: 
<select name="category" required>
    <option value="">카테고리를 선택하세요</option>
    <option value="커피">커피</option>
    <option value="디카페인">디카페인</option>
    <option value="주스">주스</option>
    <option value="기타">기타</option>
</select><br>
이미지: <input type="file" name="product_image" accept="image/*" required><br>
<button type="submit" class="btn">업로드</button>
</form>
</body>
<script>
	function updateSubmit(form){
	alert(form);
	document.getElementById("form-product-insert").submit();
	}
</script>
</html>