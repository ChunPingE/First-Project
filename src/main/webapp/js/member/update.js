let checkEamil = true;
let checkNickName = true;
let checkPassword = false;

function enableSubmit() {
	if (checkEamil && checkNickName && checkPassword) {
		$("#updateButton").removeAttr("disabled");
	} else {
		$("#updateButton").attr("disabled", "");
	}
}

//별명 중복체크
$("#checkNickNameBtn").click(function() {
	const userNickName = $("#nickNameInput").val();
		
	$.ajax("/member/check/nickName/" + userNickName, {
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
	$.ajax("/member/check/email/" + userEmail, {
		success: function(data) {
			if (data.available) {
				$("#availableEmailMessage").removeClass("d-none");
				$("#notAvailableEmailMessage").addClass("d-none");
				checkEamil = true;
			} else {
				$("#availableEmailMessage").addClass("d-none");
				$("#notAvailableEmailMessage").removeClass("d-none");
				checkEamil = false;
			}
		},
		//complete: enableSubmit
	});
})


$("#pwdInput, #pwdInputCheck").keyup(function() {
	const pw1 = $("#pwdInput").val();
	const pw2 = $("#pwdInputCheck").val();
	
	if (pw1 === pw2 && pw1 != '') {
		//$("#updateButton").removeClass("disabled");
		$("#pwdCheck").addClass("d-none");
		$("#pwdSuccess").removeClass("d-none");
		$("#pwdFail").addClass("d-none");
	} else {
		//$("#updateButton").addClass("disabled");
		$("#pwdCheck").addClass("d-none");
		$("#pwdSuccess").addClass("d-none");
		$("#pwdFail").removeClass("d-none");
	}
})