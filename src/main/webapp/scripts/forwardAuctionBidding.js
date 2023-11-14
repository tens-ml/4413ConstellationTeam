
document.addEventListener('DOMContentLoaded', () => {
	//loads fields
    loadPage();
 
    document.getElementById('BidForm').addEventListener('submit', function(e) {
        e.preventDefault();
        // Create the request body as per BidRequest class structure
        const requestBody = {
            itemId: parseInt(document.getElementById('itemID').innerText),
            price: parseFloat(document.getElementById('NewBid').value),
        };

        fetch('/constellation-backend/v1/bids', {
            method: 'POST',
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
                return response.json();
            } else {
                return response.text().then(text => {
                    alert(text);
                    throw new Error('Network response was not ok');
                });
            }
        })
        .then(data => {
            document.getElementById('itemID').innerText = data.itemID;
            document.getElementById('description').innerText = data.itemDescription;
            document.getElementById('shippingPrice').innerText = "$ " + data.shippingPrice;
            document.getElementById('highestPrice').innerText = "$ " + data.highestPrice;
            document.getElementById('highestBidder').innerText = data.highestBidder;

            const minBid = data.highestPrice + 0.01;
            document.getElementById('NewBid').value = minBid;
            document.getElementById('NewBid').min = minBid;
        })
        .catch(error => console.error('Error:', error));
}

function redirectToLogin() {
    window.location.href = '/constellation-backend';
}