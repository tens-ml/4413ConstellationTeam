document.addEventListener('DOMContentLoaded', (event) => {
    document.querySelector('#login-form').addEventListener('submit', function(e) {
        e.preventDefault(); // Prevent default form submission

        const username = document.querySelector('#username').value;
        const password = document.querySelector('#password').value;
        const requestBody = {
            username: username,
            password: password
        };

        fetch('/constellation-backend/v1/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (response.ok) {
                    alert("Login success!");
                    window.location.href = '/constellation-backend/catalog';
                } else {
                    response.text().then(text => alert(text));
                }
            })
            .catch(error => console.error('Error:', error));
    });
    document.querySelector('#redirect-to-signup').addEventListener('click', e => {
        e.preventDefault();
        window.location.href = '/constellation-backend/signup';
    });
    document.querySelector('#forgot-password-form').addEventListener('submit', e => {
        e.preventDefault();
        const username = document.querySelector('#fp-username').value;
        const password = document.querySelector('#fp-password').value;

        fetch('/constellation-backend/v1/user/forgotPassword', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username, password})
        })
            .then(response => {
                if (response.ok) {
                    alert("Password reset!");
                    toggleForgotPassword(false);
                } else {
                    response.text().then(text => alert(text));
                }
            })
            .catch(error => console.error('Error:', error));
    });
    document.querySelector("#forgot-password-button").addEventListener('click', e => {
        e.preventDefault();
        toggleForgotPassword(true);
    });
    document.querySelector("#forgot-password-back").addEventListener('click', e => {
        e.preventDefault();
        toggleForgotPassword(false);
    });
});

const toggleForgotPassword = (show) => {
    if (show) {
        document.querySelector("#forgot-password-back").style.display = "block";
        document.querySelector("#forgot-password-form").style.display = "block";
        document.querySelector("#forgot-password-button").style.display = "none";
        document.querySelector("#login-form").style.display = "none";
    } else {
        document.querySelector("#forgot-password-back").style.display = "none";
        document.querySelector("#forgot-password-form").style.display = "none";
        document.querySelector("#forgot-password-button").style.display = "block";
        document.querySelector("#login-form").style.display = "block";
    }
}