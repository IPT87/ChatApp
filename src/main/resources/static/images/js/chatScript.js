const socket = new WebSocket("ws://localhost:8080/ws");

let username = localStorage.getItem('username');

socket.onopen = function() {
    socket.send("USER:" + username);
};

socket.onmessage = function(event) {
    const messageContainer = document.getElementById('message-container');
    const messageElement = document.createElement('div');
    if (event.data.startsWith("PARTICIPANTS:")) {
        let participants = event.data.substring(13);
        let usersList = document.getElementById('users-list');
        let listElement = '';
        participants = participants.replace('[', ' ').replace(']', '').split(',');
        participants.forEach(function (user) {
            listElement += '<li>' + user + '</li>';
        })
        usersList.innerHTML = listElement;
    } else {
    messageElement.classList.add('message');
    messageElement.textContent = event.data;
    messageContainer.appendChild(messageElement);
    messageContainer.scrollTop = messageContainer.scrollHeight;
    }
};

const form = document.getElementById('message-form');
form.addEventListener('submit', function(event) {
    event.preventDefault();
    const input = document.getElementById('message-input');
    const message = input.value;
    let time = new Date().toLocaleTimeString();
    socket.send(message);
    socket.send(time);
    input.value = '';
});