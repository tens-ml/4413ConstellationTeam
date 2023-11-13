document.addEventListener('DOMContentLoaded', () => {
	//loads fields
    loadPage();
    
    document.getElementById('BidForm').addEventListener('submit', function(e) {
        e.preventDefault(); // Prevent default form submission
        // Create the request body as per BidRequest class structure
        const requestBody = {
            itemID: document.getElementById('itemID').innerText,
 			newBid : 0,
            itemDescription : " ",
			highestPrice : 0,
			shippingPrice: 0,
			expeditedShippingPrice : 0,
			highestBidder : 0
        };

        fetch('/constellation-backend/v1/user/dutch_auction_bidding/pay', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (response.ok) {
					loadPage();
                    alert("Bid Created");
                } else {
                    response.text().then(text => alert(text));
                }
            })
            .catch(error => console.error('Error:', error));
    });    
    
    
 
    
       
//    document.querySelector('#redirect-to-login').addEventListener('click', e => {
//        e.preventDefault();
//        redirectToLogin();
//    });
});

function loadPage() {
    fetch('/constellation-backend/v1/user/dutch_auction_bidding/1', {
        method: 'GET'
    })
        .then(response => {
            if (response.ok) {
                alert("load succesful");
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