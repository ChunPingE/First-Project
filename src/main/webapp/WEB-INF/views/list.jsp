<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
	<div class="container-lg">
		<div>
			<h1>게시물 목록 보기</h1>
			<div align="right">
				<a class="btn btn-secondary" href="/add">글쓰기</a>
			</div>
		</div>
		<!-- table.table>thead>tr>th*4^^tbody -->
		<table class="table">
			<thead>
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일시</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${boardList}" var="board">
					<tr>
						<td>${board.id}</td>
						<td>
							<a href="/detail/${board.id}">${board.title}</a>
						</td>
						<td>${board.writer}</td>
						<td>
							<fmt:parseDate value="${board.inserted}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
							<fmt:formatDate value="${parsedDateTime}" pattern="yyyy/MM/dd"/> 
						</td> 
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
		
	<c:if test="${success eq 'removeScucess'}" >
		<script>
			alert("게시물이 삭제되었습니다.")
		</script>
	</c:if>
	
	<c:if test="${success eq 'insertScucess'}" >
		<script>
			alert("게시물이 등록되었습니다.")
		</script>
	</c:if>
	
	<c:if test="${fail eq 'insertFail'}" >
		<script>
			alert("게시물 등록에 실패했습니다.")
		</script>
	</c:if>
	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>