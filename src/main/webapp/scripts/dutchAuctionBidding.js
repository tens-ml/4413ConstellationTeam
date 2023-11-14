document.addEventListener('DOMContentLoaded', () => {
	//loads fields
    loadPage();
    
    document.getElementById('BidForm').addEventListener('submit', function(e) {
		const searchQuery = getQueryParam('itemID');
        e.preventDefault(); // Prevent default form submission
        
        //go to auction ended page
        window.location.href = `/constellation-backend/auctionEnded?itemID=${searchQuery}`
  });    
    
    
 
    
       
//    document.querySelector('#redirect-to-login').addEventListener('click', e => {
//        e.preventDefault();
//        redirectToLogin();
//    });
});
const getQueryParam = (param) => {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}
function loadPage() {
	const searchQuery = getQueryParam('itemID');
    fetch(`/constellation-backend/v1/bids/${searchQuery}`)
        .then(response => {
            if (response.ok) {
                
                return response.json(); // Parse the response as JSON
            } else {
                return response.text().then(text => {
                    alert(text);
                    throw new Error('Network response was not ok');
                });
            }
        })
        .then(data => {
            // Now you can access properties of the parsed JSON data
            document.getElementById('itemID').innerText = data.itemID;
            document.getElementById('Description').innerText ="Description: "+ data.itemDescription;
            document.getElementById('Shipping_Price').innerText ="Shipping Price: "+ data.shippingPrice;
            document.getElementById('HighestPrice').innerText ="Current Price: "+ data.highestPrice;

        })
        .catch(error => console.error('Error:', error));
}

function redirectToLogin() {
    window.location.href = '/constellation-backend';
}