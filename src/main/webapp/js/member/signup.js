//패스워드, 패스워드 체크 인풋에 키업 이벤트 발생시
$("#pwdInput, #pwdInputCheck, #idInput, #nickNameInput").keyup(function() {
	// 패스워드에 입력한 값
	const pw1 = $("#pwdInput").val();
	// 패스워드 확인에 입력한 값
	const pw2 = $("#pwdInputCheck").val();
	const id = $("#idInput").val();
	const nickName = $("#nickNameInput").val();

	// 같으면
	if (pw1 === pw2 && pw1 != '') {
		// submit버튼활성화 
		// 패스워드가 같다는 메시지 출력
		$("#pwdSuccess").removeClass("d-none");
		$("#pwdFail").addClass("d-none");
		if (id != '' && nickName != '') {
			$("#signupSubmit").removeClass("disabled");
		} else {
			$("#signupSubmit").addClass("disabled");
		}
	} else {
		// 그렇지 않으면 비활성화
		// 패스워드가 다르다는 메세지 출력
		$("#signupSubmit").addClass("disabled");
		$("#pwdSuccess").addClass("d-none");
		$("#pwdFail").removeClass("d-none");
	}
})