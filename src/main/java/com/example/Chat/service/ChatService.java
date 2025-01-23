package com.example.Chat.service;

import com.example.Chat.UserNotFoundException;
import com.example.Chat.model.Message;
import com.example.Chat.model.User;
import com.example.Chat.repository.MessageRepository;
import com.example.Chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ChatService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    // Register a new user
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // Validate login
    public boolean validateUserLogin(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    // Send a message
    public Message sendMessage(String sender, String recipient, String content) {
    	
    	 User recipientuser = userRepository.findByUsername(recipient).orElseThrow(() ->
         new UserNotFoundException("Recipient not found: " + recipient));
			/*
			 * if (userRepository.findByUsername(recipient).isEmpty()) { throw new
			 * IllegalArgumentException("Recipient does not exist"); }
			 */
        Message message = new Message(sender, recipient, content);
        return messageRepository.save(message);
    }

    // Get messages for a user
    public List<Message> getMessagesForUser(String username) {
        return messageRepository.findByRecipient(username);
    }
}
