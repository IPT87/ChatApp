package com.trifonov.chat.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<WebSocketSession, String> sessionUsernameMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Initialize a new user with a temporary username
        sessionUsernameMap.put(session, sessionUsernameMap.get(session));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        if (payload.startsWith("USER:")) {
            // Set the username
            String username = payload.substring(5);
            sessionUsernameMap.put(session, username);
            broadcast("User " + username + " joined the chat.");
            broadcastParticipants();
        } else {
            String username = sessionUsernameMap.get(session);
            broadcast(username + ": " + payload);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        broadcast("User " + username + " left the chat.");
        broadcastParticipants();
    }

    private void broadcast(String message) throws Exception {
        for (WebSocketSession session : sessionUsernameMap.keySet()) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }

    private void broadcastParticipants() throws Exception {
        Set<String> participants = new HashSet<>(sessionUsernameMap.values());
        for (WebSocketSession session : sessionUsernameMap.keySet()) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage("PARTICIPANTS:" + participants));
            }
        }
    }
}
