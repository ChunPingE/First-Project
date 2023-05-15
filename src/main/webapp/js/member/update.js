let checkEmail = true;
let checkNickName = true;
let checkPassword = true;

function enableSubmit() {
	if (checkEmail && checkNickName && checkPassword) {
		$("#updateButton").removeAttr("disabled");
	} else {
		$("#updateButton").attr("disabled", "");
	}
}

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

// 닉네임 중복확인버튼 클릭시
$("#checkNickNameBtn").click(function() {
	const nickName = $("#nickNameInput").val();

	$.ajax("/member/checkNickName/" + nickName, {
		success: function(data) {
			// `{"available" : true}`
			if (data.available) {
				// 사용가능하다는 메세지 출력
				$("#availableNickNameMessage").removeClass("d-none");
				$("#notAvailableNickNameMessage").addClass("d-none");
				// 중복확인 되었다는 표시 
				checkNickName = true;
				/*if ($("#notAvailableEmailMessage").hasClass("d-none")) {
					checkEmail = true;
				}*/
			} else {
				// 사용 불가능하다는 메세지 출력
				$("#availableNickNameMessage").addClass("d-none");
				$("#notAvailableNickNameMessage").removeClass("d-none");
				// 중복확인 안되었다는 표시
				checkNickName = false
			}
		},
		complete: enableSubmit
	});
});

// 이메일 중복확인 버튼이 클릭되면
$("#checkEmailBtn").click(function() {
	const email = $("#emailInput").val();
	$.ajax("/member/checkEmail/" + email, {
		success: function(data) {
			if (data.available) {
				$("#availableEmailMessage").removeClass("d-none");
				$("#notAvailableEmailMessage").addClass("d-none");
				checkEmail = true;
				/*if ($("#notAvailableNickNameMessage").hasClass("d-none")) {
					checkNickName = true;
				}*/
			} else {
				$("#availableEmailMessage").addClass("d-none");
				$("#notAvailableEmailMessage").removeClass("d-none");
				checkEmail = false;
			}
		},
		complete: enableSubmit
	});
});


$("#pwdInput, #pwdInputCheck").keyup(function() {
	const pw1 = $("#pwdInput").val();
	const pw2 = $("#pwdInputCheck").val();

	if (pw1 === pw2 || pw1 == '') {
		//$("#updateButton").removeClass("disabled");
		$("#pwdCheck").addClass("d-none");
		$("#pwdSuccess").removeClass("d-none");
		$("#pwdFail").addClass("d-none");
		/*if ($("#notAvailableEmailMessage").hasClass("d-none")) {
			checkEmail = true;
		}
		if ($("#notAvailableNickNameMessage").hasClass("d-none")) {
			checkNickName = true;
		}*/
		checkPassword = true;
	} else {
		//$("#updateButton").addClass("disabled");
		$("#pwdCheck").addClass("d-none");
		$("#pwdSuccess").addClass("d-none");
		$("#pwdFail").removeClass("d-none");
		checkPassword = false;
	}
	enableSubmit();
})






