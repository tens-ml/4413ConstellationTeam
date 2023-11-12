/**
 * 
 */

//we might get some text on the fly as well, when loading the page, e.g the legend

const lgContent = document.querySelector('#lg');
var url="http://localhost:8080/constellation-backend/PaymentServlet?type=info";

    var request = new XMLHttpRequest();
    request.open('GET', url);//open a http connection
    request.send();//send the http request
    
    request.onload = function() {//when the response tome...
      var data = request.responseText;//responseText is a field of request, check with debugger
      lgContent.textContent=data; //populate the legend
    }


function validate() {
    // here we declare two variables, one boolean, one string
	var ok = true; 
	var errorMessage="";
	
	//find and assign the places in the document were the elements with the ids err, cn and fn are
	//note that the ids are referenced with "#" tags with "tagname", classes with "."
	const err = document.querySelector('#err');
	const userName = document.querySelector('#un');
	const cardNo = document.querySelector('#cno');
	const cardNa = document.querySelector('#cna');
	const expM = document.querySelector('#exm');
	const expY = document.querySelector('#exy');
	const ccv = document.querySelector('#ccv');
	
	//test if the student name is empty; the value of an element is retrieved with el.value
	// ==== is used to test if two variables are identical
	
	if (userName.value===""){
	    ok=false;
		errorMessage = " Username should not be empty; ";
	
	}
	
	if (cardNa.value===""){
	    ok=false;
		errorMessage = " Cardholder name should not be empty; ";
	
	}
	
	if (isNaN(cardNo.value) || cardNo.value < 100000000000000 || cardNo.value > 9999999999999999) {
		ok = false;
		errorMessage+=" Card number should be a 15 or 16 digit integer;";
	}
	
	if (isNaN(expM.value) || expM.value < 0 || expM.value > 12) {
		ok = false;
		errorMessage+=" Enter a valid month;";
	}
	
	if (isNaN(expY.value) || expY.value < 2023 || expY.value > 2099) {
		ok = false;
		errorMessage+=" Enter a valid year;";
	}

	if (isNaN(ccv.value) || ccv.value < 100 || ccv.value > 9999) {
		ok = false;
		errorMessage+=" CCV should be 3 or 4 digits;";
	}
	
	if(ok){
		err.innerHTML = "";//empty the error paragraph
		alert("Payment Method Successfully Added!");
	}else{
		err.innerHTML = "";
		  var myPara1 = document.createElement('p');
          myPara1.textContent = errorMessage;
		  err.appendChild(myPara1); 
		  err.style.color = "red";
	}
	
	return ok;
}

