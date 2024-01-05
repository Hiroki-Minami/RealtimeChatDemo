package com.hirokiminami.chatroom.back.constant.chatroom;

import org.springframework.core.convert.converter.Converter;

public class ChatRoomStatusReadConverter implements Converter<Integer, ChatRoomStatus> {
    @Override
    public ChatRoomStatus convert(Integer source) {
        return ChatRoomStatus.fromValue(source);
    }
}
