$(document).ready(function(){
    $(".btn1").click(function(){
        $("p").slideUp();
    });
    $(".btn2").click(function(){
        $("p").slideDown();
    });
});

function gebruikerLogin() {
	console.log("doe het!!");
	var uri = "/slaapapp/restservices/authentication";

	$.ajax(uri, {
		type : "post",
		data : $("#inlog").serialize(),
		success : function(response) {
			alert("Gegevens toegevoegd!");
		},
		error : function(response) {
			alert("Er is geen gebruiker met deze naam!");
		}
	});
};

function voegToe() {
	console.log("doe het!!");
	var uri = "/slaapapp/restservices/voegtoe/";

	$.ajax(uri, {
		type : "post",
		data : $("#voegtoe").serialize() + '&String=' + String,
		success : function(response) {
			alert("Gegevens toegevoegd!");
		},
		error : function(response) {
			alert("Er is geen gebruiker met deze naam!");
		}
	});
};

function regiGebruiker() {
	console.log("doe het!!");
	var uri = "/slaapapp/restservices/register";

	$
			.ajax(
					uri,
					{
						type : "post",
						data : $("#regiform").serialize(),
						success : function(response) {
							alert("Registratie gelukt, gefeliciteerd!");
						},
						error : function(response) {
							alert("Deze username is al in gebruik, pak alsjeblieft een ander!");
						}
					});
};

function deleteGebruiker() {
	console.log("doe het!!");
	var uri = "/slaapapp/restservices/deletegebruiker/" + $("#username").val();

	$
			.ajax(
					uri,
					{
						type : "delete",
						data : $("#deleteform").serialize(),
						success : function(response) {
							alert("Gebruiker succesvol verwijderd!");
						},
						error : function(response) {
							alert("Kon gebruiker niet verwijderen!/n"
									+ "Check of de gebruikersnaam en het wachtwoord wel overeenkomen.");
						}
					});
};

function loginGebruiker() {
	console.log("doe het!!");
	var uri = "/slaapapp/restservices/login/"

	$.ajax(uri, {
		type : "post",
		data : $("#loginform").serialize(),
		dataType: String,
		success : function(response) {
			if (data == null)
				alert("yay");
		},
		error : function(response) {
			alert("Er is iets mis met de server, check alsjeblieft je internetverbinding!");
		}
	});
};