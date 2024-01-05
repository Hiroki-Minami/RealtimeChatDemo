package com.hirokiminami.chatroom.back.controller.ws;

import com.hirokiminami.chatroom.back.constant.chat.type.ChatResponseType;
import com.hirokiminami.chatroom.back.constant.chatroom.ChatRoomStatus;
import com.hirokiminami.chatroom.back.dto.chat.ChatRequestMessage;
//import com.hirokiminami.chatroom.back2.dto.chat.KafkaRequestMessage;
import com.hirokiminami.chatroom.back.dto.chat.KafkaRequestMessage;
import com.hirokiminami.chatroom.back.model.ChatRoom;
import com.hirokiminami.chatroom.back.repository.ChatRoomParticipantsHolder;
import com.hirokiminami.chatroom.back.service.ActiveParticipantsService;
import com.hirokiminami.chatroom.back.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ChatWsController {
    Logger logger = LoggerFactory.getLogger(ChatWsController.class);
    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;
    @Autowired
    private ChatRoomParticipantsHolder chatRoomParticipantsHolder;
    @Autowired
    private ActiveParticipantsService activeParticipantsService;
    @Autowired
    private ChatRoomService chatRoomService;

    @MessageMapping("/{roomId}/send") // /ws-chat/{roomId}/send
//    @MessageMapping("/send")
    // TODO: kafka
//    @SendTo("/chat/{roomId}/messages") // /chat/messages
//    public ChatResponseMessage sendMessage(ChatRequestMessage chatMessage, @DestinationVariable String roomId) {
    public void sendMessage(ChatRequestMessage chatMessage, @DestinationVariable String roomId) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(roomId);
        String errorMessage;
        switch (chatRoom.getStatus()) {
            case CREATED -> {
                // TODO: Only Join if the user is owner
                // TODO: timer start
                if (chatRoom.getOwner().getId().equals(chatMessage.getPoster().getId())) {
                    chatRoom.setStatus(ChatRoomStatus.STARTED);
                    chatRoomService.updateChatRoom(chatRoom);

                    // Timer Start
                } else {
                    // Error message but just ask users to wait
                    errorMessage = "Wait for the host a while.";
                }
            }
            case STARTED -> {
                // TODO: all
                // TODO: get chat room to check the status?
                // TODO: check if the chatRoom is started
                switch (chatMessage.getCommand()) {
                    case JOIN -> {
                        // TODO: add to holder
                        Map<String, Set<String>> map = chatRoomParticipantsHolder.getParticipantsHolder();
                        Set<String> participants = map.getOrDefault(roomId, new HashSet<>());
                        participants.add(chatMessage.getPoster().getId());
                        map.put(roomId, participants);
                        // TODO: add to redis set?

                        // TODO: change status of chat room if the status is created but not started and owner
                        // TODO: timer start?
                    }
                    case POST -> {
                        // TODO: set UUID to messageID
                    }
                    case LEAVE -> {
                        // TODO: remove from holder

                        // TODO: remove from redis array?
                    }
                    case DONE -> {
                        // TODO: remove all from the holder
                        // TODO: remove all the array on redis?
                        // TODO: change status of chat room
                    }
                    case DELETE -> {}
                    // TODO: throw?
                    default -> {}
                }
            }
            default -> {
                // TODO error:
            }
        }

//        logger.info(roomId);
//        ChatResponseMessage responseMessage = new ChatResponseMessage(ChatResponseType.MESSAGE, chatMessage.command(), chatMessage.poster(), chatMessage.messageId(), chatMessage.message());
//        KafkaChatMessage kafkaChatMessage = new KafkaChatMessage(ChatResponseType.MESSAGE, chatMessage.command(), chatMessage.poster(), chatMessage.messageId(), chatMessage.message(), roomId);
//        KafkaRequestMessage kafkaRequestMessage = new KafkaRequestMessage(roomId, ChatResponseType.MESSAGE, chatMessage.command(), chatMessage.poster(), chatMessage.messageId(), chatMessage.message());
        KafkaRequestMessage kafkaRequestMessage = new KafkaRequestMessage(roomId, ChatResponseType.MESSAGE, chatMessage.getCommand(), chatMessage.getPoster(), chatMessage.getMessageId(), chatMessage.getMessage());
        this.kafkaTemplate.send("chat", kafkaRequestMessage);
//        logger.info("sent");
//        return new ChatResponseMessage(ChatResponseType.MESSAGE, chatMessage.command(), chatMessage.poster(), chatMessage.messageId(), chatMessage.message());
//        return responseMessage;
    }
    // TODO: get all users by get method
}
