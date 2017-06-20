function regiGebruiker() {
	 console.log("doe het!!");
	 var uri = "/slaapapp/restservices/register";

	 $.ajax(uri, {
	  type : "post",
	  data: $("#regiform").serialize(),
	  success : function(response) {
		  alert("Registratie gelukt, gefeliciteerd!");
	  },
	  error : function(response) {
		  alert("Deze username is al in gebruik, pak alsjeblieft een ander!");
	  }
	 });
	};