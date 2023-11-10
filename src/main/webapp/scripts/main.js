/**
 * 
 */

//we might get some text on the fly as well, when loading the page, e.g the legend

const lgContent = document.querySelector('#lg');
var url="http://localhost:8080/RegistrationJavaScript/RegistrationServlet?type=info";

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
	const course = document.querySelector('#cn');
	const fname = document.querySelector('#fn');
	
	//test if the student name is empty; the value of an element is retrieved with el.value
	// ==== is used to test if two variables are identical
	
	if (fname.value===""){
	    ok=false;
		errorMessage = "Student Name should not be empty; ";
	
	}

	//test if the cn is a number within the range; note how you test isNaN

	if (isNaN(course.value) || course.value <= 0 || course.value >= 9000) {
		ok = false;
		errorMessage+="Course number should be an integer > 0 and <9000";
	}
	
	if(ok){
		err.innerHTML = "";//empty the error paragraph
	}else{
		  var myPara1 = document.createElement('p');
          myPara1.textContent = errorMessage;
		  err.appendChild(myPara1); 
		  err.style.color = "red";

	}
	
	return ok;
}

