package com.hirokiminami.chatroom.back.constant.chatroom;

import org.springframework.core.convert.converter.Converter;

public class ChatRoomStatusWriteConverter implements Converter<ChatRoomStatus, Integer> {

    @Override
    public Integer convert(ChatRoomStatus source) {
        return source.getIntValue();
    }
}
