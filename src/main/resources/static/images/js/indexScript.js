let socket = new WebSocket("ws://localhost:8080/ws");
const form = document.getElementById('userForm');

form.addEventListener('submit', function(event) {
    event.preventDefault();
    username = document.getElementById('username').value;
    let password = document.getElementById('password');
    localStorage.setItem('username', username);
    username.value = '';
    password.value = '';
    window.location.href = 'chat.html';
});
