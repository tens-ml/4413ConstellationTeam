document.addEventListener('DOMContentLoaded', () => {
	//loads fields
    loadPage();
    
    document.getElementById('BidForm').addEventListener('submit', function(e) {
        e.preventDefault(); // Prevent default form submission
        
        //go to auction ended page
        window.location.href = '/constellation-backend/AuctionEnded'
  });    
    
    
 
    
       
//    document.querySelector('#redirect-to-login').addEventListener('click', e => {
//        e.preventDefault();
//        redirectToLogin();
//    });
});

function loadPage() {
    fetch('/constellation-backend/v1/user/auction_bidding/1', {
        method: 'GET'
    })
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