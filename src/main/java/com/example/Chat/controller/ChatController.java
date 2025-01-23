package com.example.Chat.controller;

import com.example.Chat.model.Message;
import com.example.Chat.model.User;
import com.example.Chat.service.ChatService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // Register a user
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return chatService.registerUser(user);
    }

    // Login a user
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        if (chatService.validateUserLogin(user.getUsername(), user.getPassword())) {
            return "Login successful for user: " + user.getUsername();
        }
        return "Invalid credentials";
    }

    // Send a message (user-specific endpoint)
    @PostMapping("/{username}/send")
    public String sendMessage(
            @PathVariable String username,
            @RequestParam String recipient,
            @RequestParam String content) {

        chatService.sendMessage(username, recipient, content);
        return "Message sent successfully from " + username + " to " + recipient;
    }

    // Get messages for the logged-in user (user-specific endpoint)
    @GetMapping("/{username}/messages")
    public List<Message> getMessages(@PathVariable String username) {
        return chatService.getMessagesForUser(username);
    }
 // Logout a user
    
}
