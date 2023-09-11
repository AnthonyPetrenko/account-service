package com.example.acccounts_crud.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProjectIdProducer {

    private final KafkaTemplate<String, Long> kafkaTemplate;

    @Autowired
    public ProjectIdProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProjectId(String topicName, long projectId) {
        kafkaTemplate.send(topicName, projectId);
    }

}