package com.gui.javakafkachat.controllers;

import com.gui.javakafkachat.models.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private KafkaTemplate<String, Message> kafkaTemplate;
}
