const socket = new WebSocket("ws://localhost:8080/ws");

socket.onmessage = function(event) {
    const messageContainer = document.getElementById('message-container');
    const messageElement = document.createElement('div');
    messageElement.classList.add('message');
    messageElement.textContent = event.data;
    messageContainer.appendChild(messageElement);
    messageContainer.scrollTop = messageContainer.scrollHeight;
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