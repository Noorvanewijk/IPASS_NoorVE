//Functie voor het toevoegen van tijden aan de database
function voegToe() {
	var uri = "/slaapapp/restservices/voegtoe/";

	$.ajax(uri, {
		type : "post",
		data : $("#voegtoe").serialize() + '&String=' + String, //zet alle data die uit het form is gehaald om tot een String
		success : function(response) {
			alert("Gegevens toegevoegd!");
		},
		error : function(response) {
			alert("Er is geen gebruiker met deze naam!");
		}
	});
};

//functie voor registratie van een gebruiker: gebruiker toevoegen aan db
function regiGebruiker() {
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


//functie voor het deleten van data uit de lijst
function deleteGebruiker() {
	//het specifiek op username, dus haal die op en stop die in de uri
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