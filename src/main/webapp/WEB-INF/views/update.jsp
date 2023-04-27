<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
	<my:navBar />

	<c:if test="${fail eq 'modifyFail'}">
		<my:alert />
	</c:if>

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>${board.id }게시물수정</h1>
				<form method="post">
					<input type="hidden" name="id" value="${board.id }" />
					<div class="mb-3">
						<label for="titleInput" class="form-label">제목</label>
						<input type="text" id="titleInput" name="title" value="${board.title }" />
					</div>
					<div class="mb-3">
						<label for="bodyInput" class="form-label">본문</label>
						<textarea rows="10" name="body" id="bodyInput" class="form-control">${board.body }</textarea>
					</div>
					<div class="mb-3">
						<label for="writerInput" class="form-label">작성자</label>
						<input type="text" id="writerInput" name="writer" class="form-control" value="${board.writer}" />
					</div>
					<div class="mb-3">
						<label for="writerInput" class="form-label">작성일시</label>
						<input type="text" name="inserted" value="${board.inserted }" readonly />
					</div>
					<div class="mb-3">
						<input class="btn btn-secondary" type="submit" value="수정" />
						<a class="btn btn-secondary" href="/list">목록으로가기</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>


</body>
</html>