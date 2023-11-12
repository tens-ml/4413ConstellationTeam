document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('signup-form').addEventListener('submit', function(e) {
        e.preventDefault(); // Prevent default form submission

        const user = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            streetAddress: document.getElementById('streetAddress').value,
            streetNumber: document.getElementById('streetNumber').value,
            postalCode: document.getElementById('postalCode').value,
            city: document.getElementById('city').value,
            country: document.getElementById('country').value,
            username: document.getElementById('username').value
        };
        const password = document.getElementById('password').value;

        // Create the request body as per SignupRequest class structure
        const requestBody = {
            user: user,
            password: password
        };

        fetch('/constellation-backend/v1/user/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (response.ok) {
                    alert("Signup success!");
                    redirectToLogin();
                } else {
                    response.text().then(text => alert(text));
                }
            })
            .catch(error => console.error('Error:', error));
    });
    document.querySelector('#redirect-to-login').addEventListener('click', e => {
        e.preventDefault();
        redirectToLogin();
    });
});

function redirectToLogin() {
    window.location.href = '/constellation-backend';
}