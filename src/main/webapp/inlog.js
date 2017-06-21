//setter van de cookie, value wordt via logingebruiker functie gehaald
function setCookie(value) {
	document.cookie = "cookie-msg-test=" + value + "; path=/";
	return true;
}

$(document).ready(function() {
	$("form#loginform").submit(function(e) { //wordt geactiveerd wanneer de submit wordt geactiveerd bij het form met het id loginform
		e.preventDefault(); //om te voorkomen dat hij naar return pagina gaat
		// update message voor cookie
		var t = document.forms['sender'].elements['username']; //pak het form met het id sender en de input met de waarde username
		setCookie(t.value); //zet de value

		// check de inlog gegevens
		var uri = "/slaapapp/restservices/login/"

		$.ajax({
			url : uri,
			type : "post",
			data : $("#loginform").serialize(),
			error : function(response) {
				alert("Ongeldige gebruiker!");
			}
		}).done(function(data)
				{
			if (data == "success") //check de data die eruit komt!
				window.location ="http://localhost:8083/slaapapp/gegevens.html"; //verwijs door naar nieuwe pagina
			else
				alert(data); //geef alert met melding
			});
	});
});