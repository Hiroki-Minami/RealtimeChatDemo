package com.hirokiminami.chatroom.back.config;

import com.hirokiminami.chatroom.back.dto.chat.ChatResponseMessage;
import com.hirokiminami.chatroom.back.dto.chat.KafkaResponseMessage;
import com.hirokiminami.chatroom.back.factory.ChatFactory;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {
    private final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
    private final TaskExecutor executor = new SimpleAsyncTaskExecutor();
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2)
        );
    }
    @Bean
    public RecordMessageConverter converter() {
//        JsonMessageConverter converter = new JsonMessageConverter();
//        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
//        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
//        typeMapper.addTrustedPackages("*");
//        Map<String, Class<?>> mappings = new HashMap<>();
//        mappings.put("chat", KafkaResponseMessage.class);
//        typeMapper.setIdClassMapping(mappings);
//        converter.setTypeMapper(typeMapper);
        return new JsonMessageConverter();
    }
    @KafkaListener(groupId = "chatGroup", topics = "chat")
//    public void listen(KafkaChatMessage kafkaChatMessage) {
    public void listen(KafkaResponseMessage kafkaChatMessage) {
        logger.info("kafka received");
        logger.info(kafkaChatMessage.getMessage());
        String path = String.format("/chat/%s/messages", kafkaChatMessage.getRoomId());
        ChatResponseMessage chatResponseMessage = ChatFactory.convertFromKafkaChatMessage(kafkaChatMessage);
        simpMessagingTemplate.convertAndSend(path, chatResponseMessage);
//        simpMessagingTemplate.send("");
        // TODO: send to ws topic
    }

    @KafkaListener(id = "dltGroup", topics = "chat.DLT")
    public void dltListen(byte[] in) {
        logger.info("DLT received");
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("chat", 1, (short) 1);
    }

    @Bean
    public NewTopic dlt() {
        return new NewTopic("chat.DLT", 1, (short) 1);
    }
}
