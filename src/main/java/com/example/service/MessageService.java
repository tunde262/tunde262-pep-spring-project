package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    MessageRepository messageRepository;

    @Autowired
    MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    public Message addMessage(Message message) {
        String message_text = message.getMessageText();

        if(message_text != null && message_text.length() > 0 && message_text.length() <= 255){
            return (Message) messageRepository.save(message);
        }

        throw new IllegalArgumentException("");
    }

    public Message findMessageById(int messageId) {
        try {
            Optional<Message> message = messageRepository.findById(messageId);
        
            if(message.isPresent()) {
                return message.get();
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Message deleteMessage(int messageId) {
        try {
            Optional<Message> message = messageRepository.findById(messageId);
        
            if(message.isPresent()) {
                messageRepository.deleteById(messageId);
                return message.get();
            }

            return null;
        } catch (Exception e) {
            return null;
        }
        
    }

    public Message updateMessageById(int messageId, Message newMessage) throws AuthenticationException{
        Optional<Message> message = messageRepository.findById(messageId);
        
        if(message.isPresent()) {
            String message_text = newMessage.getMessageText();

            if(message_text == null || message_text.length() == 0 || message_text.length() > 255){
                throw new AuthenticationException("");
            }

            Message updatedMessage = message.get();

            updatedMessage.setMessageText(message_text);
            return (Message) messageRepository.save(updatedMessage);
        }

        throw new AuthenticationException("");

    }

    public List<Message> getAllMessagesByUser(int account_id) {
        return (List<Message>) messageRepository.findByPostedBy(account_id);
    }
}
