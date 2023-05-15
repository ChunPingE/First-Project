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
				<h1>회원가입</h1>
				<form method="post">
					<div class="mb-3">
						<label for="idInput" class="form-label">아이디</label>
						<div class="input-group">
							<input type="text" id="idInput" class="form-control" name="id" value="${member.id}" />
							<button type="button" id="checkIdBtn" class="btn btn-outline-secondary">중복확인</button>
						</div>
						<div id="availableIdMessage" class="form-text text-primary d-none">
							<i class="fa-solid fa-check"></i>
							사용가능한 ID입니다.
						</div>
						<div id="notAvailableIdMessage" class="form-text text-danger d-none">
							<i class="fa-solid fa-triangle-exclamation"></i>
							사용 불가능한 ID입니다.
						</div>
					</div>
					<div class="mb-3">
						<label for="pwdInput" class="form-label">비밀번호</label>
						<input type="password" id="pwdInput" class="form-control" name="password" />
					</div>
					<div class="mb-3">
						<label for="pwdInputCheck" class="form-label">비밀번호확인</label>
						<input type="password" id="pwdInputCheck" class="form-control" />
						<div id="pwdSuccess" class="form-text text-primary d-none">
							<i class="fa-solid fa-check"></i>
							비밀번호가 일치합니다.
						</div>
						<div id="pwdFail" class="form-text text-danger d-none">
							<i class="fa-solid fa-triangle-exclamation"></i>
							비밀번호가 일치하지 않습니다.
						</div>
					</div>
					<div class="mb-3">
						<label for="nickNameInput" class="form-label">별명</label>
						<div class="input-group">
							<input type="text" id="nickNameInput" class="form-control" name="nickName" value="${member.nickName}" />
							<button type="button" class="btn btn-outline-secondary" id="checkNickNameBtn">중복확인</button>
						</div>
						<div id="availableNickNameMessage" class="form-text text-primary d-none">
							<i class="fa-solid fa-check"></i>
							사용가능한 별명입니다.
						</div>
						<div id="notAvailableNickNameMessage" class="form-text text-danger d-none">
							<i class="fa-solid fa-triangle-exclamation"></i>
							사용 불가능한 별명입니다.
						</div>
					</div>
					<div class="mb-3">
						<label for="emailInput" class="form-label">이메일</label>
						<div class="input-group">
							<input type="email" id="emailInput" class="form-control" name="email" value="${member.email}" />
							<button type="button" id="checkEmailBtn" class="btn btn-outline-secondary">중복확인</button>
						</div>
						<div id="availableEmailMessage" class="form-text text-primary d-none">
							<i class="fa-solid fa-check"></i>
							사용가능한 이메일입니다.
						</div>
						<div id="notAvailableEmailMessage" class="form-text text-danger d-none">
							<i class="fa-solid fa-triangle-exclamation"></i>
							사용 불가능한 이메일입니다.
						</div>
					</div>
					<div class="mb-3">
						<input type="submit" id="signupSubmit" class="btn btn-primary" disabled value="가입" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

	<script src="/js/member/signup.js"></script>
</body>
</html>