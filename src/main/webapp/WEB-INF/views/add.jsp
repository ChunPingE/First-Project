<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
	<my:navBar current="add" />

	<my:alert />

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-10 col-lg-8">
				<h1>게시물 작성</h1>
				<form method="post" enctype="multipart/form-data">
					<div class="mb-3">
						<label for="titleInput" class="form-label">제목</label>
						<input type="text" id="titleInput" class="form-control" name="title" value="${board.title}" />
					</div>
					<div class="mb-3">
						<label for="bodyInput" class="form-label">내용</label>
						<textarea rows="10" name="body" id="bodyInput" class="form-control">${board.body}</textarea>
					</div>
					<%--
					<div class="mb-3">
						<label for="writerInput" class="form-label">작성자</label>
						<input type="text" id="writerInput" name="writer" class="form-control" value="${username}" readonly />
						<input type="text" id="writerInput" name="writer" class="form-control" value="<sec:authentication property="name"/>" readonly/>
					</div>
					--%>
					<div class="mb-3">
						<label for="formFile" class="form-label">첨부 파일</label>
						<input class="form-control" name="files" type="file" id="formFile" accept="image/*" multiple>
						<div class="form-text">총 10MB, 하나의 파일을 1MB를 초과할 수 없습니다.</div>
					</div>
					<div class="mb-3">
						<input class="btn btn-primary" type="submit" value="등록" />
					</div>
				</form>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>