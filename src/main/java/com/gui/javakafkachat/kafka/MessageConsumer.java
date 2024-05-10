package com.gui.javakafkachat.kafka;

import com.gui.javakafkachat.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private static final String KAFKA_TOPIC = "chat_topic";
    private static final String GROUP_ID = "group-chat-1";

    @Autowired
    SimpMessagingTemplate template;
    


    @KafkaListener(
            topics = KAFKA_TOPIC,
            groupId = GROUP_ID
    )
    public void listen(Message message){
        System.out.println("Consumer is part of consumer group: " + GROUP_ID);
        System.out.println("Incoming message: " + message);

       template.convertAndSend("/topic/group", message);
    }

}
