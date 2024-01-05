package com.hirokiminami.chatroom.back.dto.chatroom;

import com.hirokiminami.chatroom.back.model.ChatRoom;
import lombok.Data;

import java.util.List;
@Data
public class ChatRoomListResponse {
    List<ChatRoom> hosting;
    List<ChatRoom> participating;
    List<ChatRoom> others;
}
