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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />

</head>
<body>
	<my:navBar />

	<c:if test="${success eq 'modifySuccess'}">
		<my:alert />
	</c:if>

	<c:if test="${success eq 'insertScucess'}">
		<my:alert />
	</c:if>

	<c:if test="${fail eq 'removeFail'}">
		<script>
			alert("게시물이 삭제되지 않았습니다.")
		</script>
	</c:if>

	<div class="container-lg">
		<!-- .row.justify-content-center>.col-12.col-md-8.col-lg-6 -->
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>${board.id }번게시물</h1>
				<div>
					<div class="mb-3">
						<label for="" class="form-label">제목</label>
						<input type="text" class="form-control" value="${board.title }" readonly />
					</div>
					<div class="mb-3">
						<label for="" class="form-label">본문</label>
						<textarea rows="10" name="body" class="form-control">${board.body}</textarea>
					</div>
					<div class="mb-3">
						<label for="" class="form-label">작성자</label>
						<input type="text" class="form-control" value="${board.writer }" readonly />
					</div>
					<div class="mb-3">
						<label for="" class="form-label">작성일시</label>
						<input type="text" readonly class="form-control" value="${board.inserted }" />
					</div>
					<div>
						<a class="btn btn-secondary" href="/update/${board.id}">수정</a>
						<button id="removeButton" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal" >삭제</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="d-none">
		<form action="/remove" method="post" id="removeForm">
			<input type="text" name="id" value="${board.id }" />
		</form>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">삭제 확인</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        삭제 하시겠습니까?
	      </div>
	      <div class="modal-footer">
	        <button type="submit" class="btn btn-danger" form="removeForm">삭제</button>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>

<!-- <script>
	$("#removeButton").click(function(e) {
		// submit 진행 이벤트 막기
		e.preventDefault();

		const res = confirm("삭제 하시겠습니까?");
		if (res) {
			// submit 실행
			$("#removeForm").submit();
		}
	});
</script> -->
