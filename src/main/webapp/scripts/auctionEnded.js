var final_price;
document.addEventListener('DOMContentLoaded', () => {
	
	//loads fields
    loadPage();
    
    document.getElementById('BidForm').addEventListener('submit', function(e) {
        e.preventDefault(); // Prevent default form submission
        loadPage();
        // Create the request body as per BidRequest class structure
        const requestBody = {
            itemID: document.getElementById('itemID').innerText,
 			newBid : 0,
            itemDescription : " ",
			highestPrice : final_price,
			shippingPrice: 0,
			expeditedShippingPrice : 0,
			highestBidder : 0
        };

        fetch('/constellation-backend/v1/user/auction_ended/pay', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (response.ok) {
					loadPage();
                    window.location.href = '/constellation-backend/Payment'
                } else {
                    response.text().then(text => alert(text));
                }
            })
            .catch(error => console.error('Error:', error));
    }); 
    
   document.getElementById('Expedited_Shipping_Radio').addEventListener('change', loadPage );  
    
    
 
    
       
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
            // Convert values to integers
            const itemID = parseInt(data.itemID);
            const shippingPrice = parseInt(data.shippingPrice);
            const expeditedShippingPrice = parseInt(data.expeditedShippingPrice);
            const highestBidder = parseInt(data.highestBidder);
            const highestPrice = parseInt(data.highestPrice);

            // Now you can access properties of the parsed JSON data as integers
            document.getElementById('itemID').innerText = itemID;
            document.getElementById('Description').innerText = "Description: " + data.itemDescription;
            document.getElementById('Shipping_Price').innerText = "Shipping Price: $" + shippingPrice;
            document.getElementById('Expedited_Shipping_Price').innerText = "Expedited Shipping Price: $" + expeditedShippingPrice;
            document.getElementById('Highest_Bidder').innerText = "Highest Bidder: " + highestBidder;

            if (document.getElementById('Expedited_Shipping_Radio').checked) {
				final_price = (highestPrice + expeditedShippingPrice);
                document.getElementById('Winning_Price').innerText = "Winning Price: $" + (highestPrice + expeditedShippingPrice);
            } else {
				final_price = (highestPrice + shippingPrice);
                document.getElementById('Winning_Price').innerText = "Winning Price: $" + (highestPrice + shippingPrice);
            }
            

        })
        .catch(error => console.error('Error:', error));
}

function redirectToLogin() {
    window.location.href = '/constellation-backend';
}