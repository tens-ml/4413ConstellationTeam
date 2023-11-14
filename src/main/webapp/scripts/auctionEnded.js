var final_price;
document.addEventListener('DOMContentLoaded', () => {
	
	//loads fields
    loadPage();
    
    document.getElementById('BidForm').addEventListener('submit', function(e) {
        e.preventDefault(); // Prevent default form submission
        loadPage();
        // Create the request body as per BidRequest class structure
        const requestBody = {
            itemId: document.getElementById('itemID').innerText,
 			price : final_price
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
                    window.location.href = '/constellation-backend/payment'
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
			document.getElementById('Winning_Price').innerText = "Winning Price: $" + highestPrice;
            if (document.getElementById('Expedited_Shipping_Radio').checked) {
				final_price = (highestPrice + expeditedShippingPrice);
                //document.getElementById('Winning_Price').innerText = "Winning Price: $" + (highestPrice + expeditedShippingPrice);
            } else {
				final_price = (highestPrice + shippingPrice);
                //document.getElementById('Winning_Price').innerText = "Winning Price: $" + (highestPrice + shippingPrice);
            }
            

        })
        .catch(error => console.error('Error:', error));
}

function redirectToLogin() {
    window.location.href = '/constellation-backend';
}