document.addEventListener('DOMContentLoaded', (event) => {
    // Extract search query from URL
    const searchQuery = getQueryParam('search');

    // Set the search input value and load items
    const searchInput = document.getElementById('search');
    if (searchQuery) {
        searchInput.value = searchQuery;
        loadItems(searchQuery);
    } else {
        loadItems();
    }

    // Add event listener for the search form
    const searchForm = document.getElementById('search-form');
    searchForm.addEventListener('submit', (event) => {
        event.preventDefault();
        loadItems(searchInput.value);
    });

    const sellItemForm = document.getElementById('sell-item-form');
    sellItemForm.addEventListener('submit', (event) => {
        event.preventDefault();
        submitItem();
    });

    document.getElementById('bid-button').addEventListener('click', function(e) {
        // Find the selected radio button
        const selectedRadio = document.querySelector('input[name="itemSelection"]:checked');

        if (selectedRadio) {
            const selectedItemID = selectedRadio.value;
            const itemIsDutch = selectedRadio.getAttribute("data-is-dutch");
            const itemIsAvailable = selectedRadio.getAttribute("data-is-available");

            console.log("printing itemIsAvailable: " + itemIsAvailable);
            console.log("printing itemIsDutch: " + itemIsDutch);
            console.log("printing selectedItemID: " + selectedItemID);

            if (!itemIsAvailable) {
                window.location.href = `/constellation-backend/auctionEnded?itemID=${selectedItemID}`;
            } else if (itemIsDutch) {
                window.location.href = `/constellation-backend/forwardAuction?itemID=${selectedItemID}`;
            } else {
                window.location.href = `/constellation-backend/dutchAuction?itemID=${selectedItemID}`;
            }

        } else {
            alert("Please select an item first.");
        }
    });
});
const getQueryParam = (param) => {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}
const loadItems = (searchFilter = '') => {
    const content = document.querySelector('#content');
    content.innerHTML = ''; // Clear existing content

    let apiUrl = '/constellation-backend/v1/catalog';
    if (searchFilter) {
        apiUrl += `?filter=${encodeURIComponent(searchFilter)}`;
    }

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            data.forEach(item => {
                const DOMRow = content.insertRow();
                const radioInput = document.createElement("input");
                radioInput.type = "radio";
                radioInput.name = "itemSelection"; // All radios have the same name to allow only one selection
                radioInput.value = item.id; // You can set value to item's ID or other identifier
                radioInput.setAttribute("data-is-dutch", item.isDutch); // Set data attribute to store auction type
                radioInput.setAttribute("data-is-available", item.available);
                // Create a cell for the radio button and append it
                const radioCell = DOMRow.insertCell(0);
                radioCell.classList.add("radio-cell");
                radioCell.appendChild(radioInput);

                DOMRow.insertCell(1).textContent = item.id;
                DOMRow.insertCell(2).textContent = item.sellerName;
                DOMRow.insertCell(3).textContent = item.itemName;
                DOMRow.insertCell(4).textContent = item.itemDescription;
                DOMRow.insertCell(5).textContent = item.isDutch ? "Dutch" : "Forward";
                DOMRow.insertCell(6).textContent = item.daysToShip;
                DOMRow.insertCell(7).textContent = item.initialPrice;
                DOMRow.insertCell(8).textContent = (item.highestBid <= item.initialPrice) ? item.initialPrice : item.highestBid

                const auctionEndTime = new Date(item.auctionEnd);
                const now = new Date();
                const timeLeftInMinutes = Math.round((auctionEndTime - now) / (1000 * 60)) ;
                const rtf = new Intl.RelativeTimeFormat('en', { numeric: 'auto' });

                DOMRow.insertCell(9).textContent = (item.isDutch) ? "N/a" : timeLeftInMinutes + " minutes";
                DOMRow.insertCell(10).textContent = item.available ? 'Yes' : 'No';
            });
        })
        .catch(error => {
            alert("Error fetching data");
            console.error('Error fetching items:', error);
        });
};

const submitItem = () => {
    // Collect the form data
    const itemName = document.getElementById('itemName').value;
    const itemDescription = document.getElementById('itemDescription').value;
    const auctionType = document.getElementById('auctionType').value;
    const daysToShip = document.getElementById('daysToShip').value;
    const initialPrice = document.getElementById('initialPrice').value;
    const auctionEnd = document.getElementById('auctionEnd').value;

    // Create an object with the form data
    const itemData = {
        itemName,
        itemDescription,
        auctionType,
        daysToShip: parseInt(daysToShip),
        initialPrice: parseFloat(initialPrice),
        auctionEnd
    };

    // Send the data to the server
    // Replace '/api/add-item' with your actual API endpoint
    fetch('/constellation-backend/v1/catalog', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(itemData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            alert("Item added for sale!");
            window.location.reload();
        })
        .catch(error => {
            console.error('Error adding item:', error);
        });
};