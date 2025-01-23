const apiUrl = 'http://localhost:8080/api';

let loggedInUser = '';

function showLogin() {
    document.getElementById('loginForm').style.display = 'block';
    document.getElementById('registerForm').style.display = 'none';
}

function showChatSection(username) {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('registerForm').style.display = 'none';
    document.getElementById('chatSection').style.display = 'block';
    document.getElementById('chatUsername').innerText = username;
    loggedInUser = username;
}

function registerUser() {
    const username = document.getElementById('registerUsername').value;
    const password = document.getElementById('registerPassword').value;

    const user = { username, password };
    fetch(`${apiUrl}/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
    .then(response => response.json())
    .then(data => {
        alert('Registration successful!');
        showLogin();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error during registration!');
    });
}

function loginUser() {
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    // Create user object
    const user = {
        username: username,
        password: password
    };

    // Send POST request with the user object as JSON body
    fetch(`${apiUrl}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'  // Set the request content type to JSON
        },
        body: JSON.stringify(user)  // Convert user object to JSON string
    })
    .then(response => response.text())
    .then(data => {
        if (data.includes('Login successful')) {
            showChatSection(username);
            getMessages();
        } else {
            alert('Invalid credentials!');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error during login!');
    });
}


function sendMessage() {
    const recipient = document.getElementById('recipient').value;
    const content = document.getElementById('messageContent').value;

    fetch(`${apiUrl}/${loggedInUser}/send?recipient=${recipient}&content=${content}`, {
        method: 'POST'
    })
    .then(response => response.text())
    .then(data => {
        alert('Message sent!');
        getMessages();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error sending message!');
    });
}

function getMessages() {
    fetch(`${apiUrl}/${loggedInUser}/messages`)
    .then(response => response.json())
    .then(messages => {
        const messagesDiv = document.getElementById('messages');
        messagesDiv.innerHTML = '';
        messages.forEach(msg => {
            const messageElement = document.createElement('div');
            messageElement.textContent = `${msg.sender}: ${msg.content}`;
            messagesDiv.appendChild(messageElement);
        });
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error fetching messages!');
    });
}

showLogin(); // Start with the login form
