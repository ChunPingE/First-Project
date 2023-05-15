let checkId = false;
let checkEmail = false;
let checkNickName = false;
let checkPassword = false;

function enableSubmit() {
	if (checkId && checkEmail && checkNickName && checkPassword) {
		$("#signupSubmit").removeAttr("disabled");
	} else {
		$("#signupSubmit").attr("disabled", "");
	}
}

//id에 input에 입력시
$("#InputId").keyup(function() {
	// 아이디 중복확인 다시
	checkId = false;
	$("#availableIdMessage").addClass("d-none")
	$("#notAvailableIdMessage").addClass("d-none")

	// submit 버튼 비활성화
	enableSubmit();
});

//nickName input에 입력시
$("#nickNameInput").keyup(function() {
	//닉네임 중복확인 다시
	checkNickName = false;
	$("#availableNickNameMessage").addClass("d-none");
	$("#notAvailableNickNameMessage").addClass("d-none");
	//submit버튼 비활성화
	enableSubmit();
})

//이메일에 input에 입력시
$("#emailInput").keyup(function() {
	//이메일 중복확인 다시
	checkEmail = false;
	$("#availableEmailMessage").addClass("d-none");
	$("#notAvailableEmailMessage").addClass("d-none");
	//submit버튼 비활성화
	enableSubmit();
})

// id중복확인버튼이 클릭되면
$("#checkIdBtn").click(function() {
	//입력한 id와 ajax요청 보내서
	const userId = $("InputId").val();
	$.ajax("/member/checkid/" + userId, {
		success: function(data) {
			if (data.available) {
				//사용가능하다는 메세지 출력
				$("#availableIdMessage").removeClass("d-none");
				$("#notAvailableIdMessage").addClass("d-none");
				checkId = true;
			} else {
				// 사용가능하지 않다는 메시지 출력
				$("#availableIdMessage").addClass("d-none");
				$("#notAvailableIdMessage").removeClass("d-none");
				checkId = false;
			}
		},
		complete: enableSubmit
	});
})


//별명 중복체크
$("#checkNickNameBtn").click(function() {
	const userNickName = $("#nickNameInput").val();
	$.ajax("/member/checknickName/" + userNickName, {
		success: function(data) {
			if (data.available) {
				$("#availableNickNameMessage").removeClass("d-none");
				$("#notAvailableNickNameMessage").addClass("d-none");
				checkNickName = true;
			} else {
				$("#availableNickNameMessage").addClass("d-none");
				$("#notAvailableNickNameMessage").removeClass("d-none");
				checkNickName = false;
			}
		}
	});
})

//이메일 중복체크
$("#checkEmailBtn").click(function() {
	const userEmail = $("#emailInput").val();
	$.ajax("/member/checkemail/" + userEmail, {
		success: function(data) {
			if (data.available) {
				$("#availableEmailMessage").removeClass("d-none");
				$("#notAvailableEmailMessage").addClass("d-none");
				checkEmail = true;
			} else {
				$("#availableEmailMessage").addClass("d-none");
				$("#notAvailableEmailMessage").removeClass("d-none");
				checkEmail = false;
			}
		},
		complete: enableSubmit
	});
})

//패스워드, 패스워드 체크 인풋에 키업 이벤트 발생시
$("#pwdInput, #pwdInputCheck, #InputId, #nickNameInput").keyup(function() {
	// 패스워드에 입력한 값
	const pw1 = $("#pwdInput").val();
	// 패스워드 확인에 입력한 값
	const pw2 = $("#pwdInputCheck").val();

	// 같으면
	if (pw1 === pw2 && pw1 != '') {
		// submit버튼활성화 
		// 패스워드가 같다는 메시지 출력
		$("#pwdSuccess").removeClass("d-none");
		$("#pwdFail").addClass("d-none");
		checkPassword = true;
	} else {
		// 그렇지 않으면 비활성화
		// 패스워드가 다르다는 메세지 출력
		$("#pwdSuccess").addClass("d-none");
		$("#pwdFail").removeClass("d-none");
		checkPassword = false;
	}
	enableSubmit();
})








