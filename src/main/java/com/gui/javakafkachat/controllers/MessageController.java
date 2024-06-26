package com.gui.javakafkachat.controllers;

import com.gui.javakafkachat.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    @PostMapping(value = "api/send",
            consumes = "application/json",
            produces = "application/json")
    public void sendMessage(@RequestBody Message message){
        LocalDateTime dateObject = LocalDateTime.now();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        message.setTimestamp(dateObject.format(myFormat));

        try {
            kafkaTemplate.send("chat_topic", message).get();
        }catch (InterruptedException | ExecutionException e){
            throw new RuntimeException(e);
        }

    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        //Sending this message to all the subscribers
        return message;
    }

}
