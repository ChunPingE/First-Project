$("#pwdInput, #pwdInputCheck").keyup(function() {
	const pw1 = $("#pwdInput").val();
	const pw2 = $("#pwdInputCheck").val();

	if (pw1 === pw2 && pw1 != '') {
		$("#updateButton").removeClass("disabled");
		$("#pwdCheck").addClass("d-none");
		$("#pwdSuccess").removeClass("d-none");
		$("#pwdFail").addClass("d-none");
	} else {
		$("#updateButton").addClass("disabled");
		$("#pwdCheck").addClass("d-none");
		$("#pwdSuccess").addClass("d-none");
		$("#pwdFail").removeClass("d-none");
	}
})