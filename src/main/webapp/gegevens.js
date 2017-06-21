//deze variabele wordt later gebruikt om totale waarde te berekenen
var totaal;

//vang de Cookie op die via inlog is verstuurt
function getCookie() {
    var cname = "cookie-msg-test=";
    var ca = document.cookie.split(';');
    for (var i=0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(cname) == 0) {
            return c.substring(cname.length, c.length);
        }
    }
    return null;
}

//functie voor het updaten van gegevens
function updateGegevens() {
	//#username staat in het update form onder de naam username
	var uri = "/slaapapp/restservices/update/" + $("#username").val();
	    
	    $.ajax(uri, { 
	        type: "put", 
	        data: $("#updateform").serialize(), 
	        success: function(response) {
	            alert("Gegevens zijn succesvol aangepast!\n" +
	            		"De volgende keer dat je deze pagina bezoekt is alles in orde (:");
	        },
	        error: function(response) {
	            alert("Er waren geen gegevens die matchte bij deze aanvraag!");
	        }
	    }); 
	};

//functie om gegevens in de lijst te zoeken
function zoekGegevens() {
	  // Variabelen declareren
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("zoekInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("alles");
	  tr = table.getElementsByTagName("tr");

	  // Loop door alle rijen, verberg degene die niet nodig zijn.
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[0];
	    if (td) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    } 
	  }
	}

//functie om de datum goed neer te zetten dd/mm/yyyy
function formatDate(date) {
	//Maak variabelen voor dag, maand en jaar
	var day = date.getDate();
	var month = date.getMonth() + 1; //loopt een maand achter
	var year = date.getFullYear();
	//zet ze in goede volgorde
	return day + '/' + month + '/' + year;
}

//functie voor het optellen van het totaal
function add(a, b) {
    return a + b;
}

//functie voor het weergeven van alle gegevens van de gebruiker
function showall() {
	var naam = getCookie(); //roep de cookie weer op
	var uri = "/slaapapp/restservices/alles" + "/" + naam; //zet deze achter de url voor de juiste restservice
	var lijst = []; //maak een lijst aan, deze wordt later gevuld

	$.get(uri, function(data) {
		$.each(data, function(i, gegeven) {
			var datum = formatDate(new Date(gegeven.datum)); //zet de datum in de goede volgorde: dd/mm/yyyy
			var begintijd = parseFloat(gegeven.begintijd.replace(":", ".")); //zet String tijd om naar float
			var eindtijd = parseFloat(gegeven.eindtijd.replace(":", ".")); //zet String tijd om naar float
			var verschil = 24 - begintijd; // bereken hoeveel tijd is geslapen VOOR 24u
			var gemiddelde = eindtijd + verschil; // bereken totaal aantal geslapen uren op de datum
			lijst.push(gemiddelde); // voeg dit aantal toe aan de lijst
			
			$("#alles").append( //voeg waardes toe aan tabel onder het ID alles
					"<tr><td>" + datum + "</td><td>" + gegeven.begintijd
							+ "</td><td>" + gegeven.eindtijd + "</td><td>"
							+ gemiddelde + "</td></tr>");

		});
		
		totaal = lijst.reduce(add,0); //tel alle waardes van de lijst op
		//dit staat niet in de loop omdat deze waarde niet geloopt moet worden!!
	});
}

//functie om aantal rijen op te tellen
function CountRows() {
	//eerst variabelen declareren
    var totalRowCount = 0;
    var rowCount = 0;
    var table = document.getElementById("alles"); //pak de goede tabel
    var rows = table.getElementsByTagName("tr") //duidt aan WAT geteld moet worden: table rows
    
    //voor iedere rij: count + 1
    for (var i = 0; i < rows.length; i++) {
        totalRowCount++;
        if (rows[i].getElementsByTagName("td").length > 0) {
            rowCount++;
        }
    }
    var totaalgemiddelde = Number((totaal / rowCount).toFixed(2)); //bereken het totale gemiddelde mbv totaal variabele
    var message = "In totaal heb je " + rowCount + " nachten bijgehouden.\n"
    				+ "Je hebt hierbij totaal " + totaal + " uur geslapen.\n"
    				+ "Het gemiddelde aantal uren dat je hebt geslapen is: " + totaalgemiddelde;
    alert(message); //stuur message
}

showall(); //zorgt ervoor dat de showall functie ALTIJD runt als je op de pagina komt