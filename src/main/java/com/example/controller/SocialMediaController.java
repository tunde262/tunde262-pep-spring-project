package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private MessageService messageService;
    private AccountService accountService;

    @Autowired
    SocialMediaController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            Account userAccount = accountService.register(account);
            if(userAccount==null){
                return new ResponseEntity<Account>(HttpStatus.CONFLICT);
            }else{
                return ResponseEntity.status(200).body(userAccount);
            }
        } catch (Exception e) {
            return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            Account userAccount = accountService.login(account);
            if(userAccount==null){
                return new ResponseEntity<Account>(HttpStatus.UNAUTHORIZED);
            }else{
                return ResponseEntity.status(200).body(userAccount);
            }
        } catch (Exception e) {
            return new ResponseEntity<Account>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        try {
            Message messageObj = (Message) messageService.addMessage(message);

            return ResponseEntity.status(200).body(messageObj);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try {
            messages = messageService.getAllMessages();
            return ResponseEntity.status(200).body(messages);

        } catch (Exception e) {
            return ResponseEntity.status(200).body(messages);
        }
    }
    
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Message message = messageService.findMessageById(messageId);

        if(message != null) {
            return ResponseEntity.status(200).body(message);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        Message message = messageService.deleteMessage(messageId);

        if(message != null) {
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body(null);

    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId, @RequestBody Message newMessage) {
        try {
            Message message = (Message) messageService.updateMessageById(messageId, newMessage);

            if(message != null) {

                return ResponseEntity.status(200).body(1);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllUserMessages(@PathVariable int accountId) {
        List<Message> messages = new ArrayList<>();
        try {
            messages = messageService.getAllMessagesByUser(accountId);
            return ResponseEntity.status(200).body(messages);

        } catch (Exception e) {
            return ResponseEntity.status(200).body(messages);
        }
    }




}
