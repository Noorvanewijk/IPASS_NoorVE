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

function updateGegevens() {
	console.log("Zo werkt hij");    
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

function zoekGegevens() {
	  // Declare variables 
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("zoekInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("alles");
	  tr = table.getElementsByTagName("tr");

	  // Loop through all table rows, and hide those who don't match the search query
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

function formatDate(date) {
	var day = date.getDate();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();

	return day + '/' + month + '/' + year;
}

function showall() {
	var naam = getCookie();
	var uri = "/slaapapp/restservices/alles" + "/" + naam;
	console.log(uri);

	$.get(uri, function(data) {
		$.each(data, function(i, gegeven) {
			var datum = formatDate(new Date(gegeven.datum));
			var begintijd = parseFloat(gegeven.begintijd.replace(":", "."));
			var eindtijd = parseFloat(gegeven.eindtijd.replace(":", "."));
			var verschil = 24 - begintijd;
			var gemiddelde = eindtijd + verschil;
			
			$("#alles").append(
					"<tr><td>" + datum + "</td><td>" + gegeven.begintijd
							+ "</td><td>" + gegeven.eindtijd + "</td><td>"
							+ gemiddelde + "</td></tr>");

		});
	});
}

function CountRows() {
    var totalRowCount = 0;
    var rowCount = 0;
    var table = document.getElementById("alles");
    var rows = table.getElementsByTagName("tr")
    for (var i = 0; i < rows.length; i++) {
        totalRowCount++;
        if (rows[i].getElementsByTagName("td").length > 0) {
            rowCount++;
        }
    }
    var message = "Er zijn " + rowCount + " rijen!!";
    alert(message);
}

showall();