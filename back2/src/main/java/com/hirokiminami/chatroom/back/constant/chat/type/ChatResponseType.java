package com.hirokiminami.chatroom.back.constant.chat.type;

import lombok.Getter;

@Getter
public enum ChatResponseType {
//    MESSAGE,
//    TIME;
    MESSAGE("message"),
    TIMER("timer");

    private final String stringValue;
    ChatResponseType(String stringValue) {
        this.stringValue = stringValue;
    }
    public static ChatResponseType fromValue(String stringValue) {
        for (ChatResponseType chatResponseType: values()) {
            if (chatResponseType.getStringValue().equals(stringValue)) {
                return chatResponseType;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + stringValue);
    }
}

