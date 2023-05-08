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
	<my:navBar current="signup" />

	<my:alert />

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-10 col-lg-8">
				<h1>회원수정</h1>
				<form id="modifyForm" action="/member/update" method="post">
					<div class="mb-3">
						<label for="idInput" class="form-label">아이디</label>
						<input type="text" id="idInput" class="form-control" name="id" value="${member.id}" readonly />
					</div>
					<div class="mb-3">
						<label for="pwdInput" class="form-label">비밀번호</label>
						<input type="text" id="pwdInput" class="form-control" name="password" value="" />
					</div>
					<div class="mb-3">
						<label for="nickNameInput" class="form-label">별명</label>
						<input type="text" id="nickNameInput" class="form-control" name="nickName" value="${member.nickName}" />
					</div>
					<div class="mb-3">
						<label for="emailInput" class="form-label">이메일</label>
						<input type="email" id="emailInput" class="form-control" name="email" value="${member.email}" />
					</div>
					<div class="mb-3">
						<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmModal">수정</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- 수정 확인 Modal -->
	<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">수정 확인</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<label for="inputOldPassword" class="form-label">이전 암호</label>
					<input form="modifyForm" id="inputOldPassword" class="form-control" type="text" name="inputPassword" />
				</div>
				<div class="modal-footer">
					<button type="submit" form="modifyForm" class="btn btn-primary">확인</button>
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				</div>
			</div>
			
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>