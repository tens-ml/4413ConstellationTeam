document.addEventListener('DOMContentLoaded', (event) => {
    document.querySelector('.login-form form').addEventListener('submit', function(e) {
        e.preventDefault(); // Prevent default form submission

        const username = document.querySelector('#username').value;
        const password = document.querySelector('#password').value;
        const requestBody = {
            username: username,
            password: password
        };

        fetch('/constellation_backend_war/v1/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (response.ok) {
                    alert("Login success!");
                    window.location.href = '/constellation_backend_war/home';
                } else {
                    response.text().then(text => alert(text));
                }
            })
            .catch(error => console.error('Error:', error));
    });
    document.querySelector('#redirect-to-signup').addEventListener('click', e => {
        e.preventDefault();
        window.location.href = '/constellation_backend_war/SignUp.html';
    });
});