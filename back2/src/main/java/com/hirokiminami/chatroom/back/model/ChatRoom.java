package com.hirokiminami.chatroom.back.model;

import com.hirokiminami.chatroom.back.constant.chatroom.ChatRoomStatus;
import com.hirokiminami.chatroom.back.model.supportive.Owner;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "chatRoom")
public class ChatRoom {
    @Id
    private String id;
    private String roomName;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private ChatRoomStatus status;
    private Owner owner;
    private List<String> participants;
}
