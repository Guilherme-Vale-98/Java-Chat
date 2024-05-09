package com.gui.javakafkachat.controllers;

import com.gui.javakafkachat.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

@RestController
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
            kafkaTemplate.send(KAFKA_CHAT_TOPIC, message).get();
        }catch (InterruptedException | ExecutionException e){
            throw new RuntimeException(e);
        }

    }

}
