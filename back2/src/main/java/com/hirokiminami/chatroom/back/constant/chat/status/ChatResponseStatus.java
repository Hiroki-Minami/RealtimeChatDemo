package com.hirokiminami.chatroom.back.constant.chat.status;

import lombok.Getter;

@Getter
public enum ChatResponseStatus {
    OK("ok"),
    ERROR("error");
    private final String stringValue;
    ChatResponseStatus(String stringValue) { this.stringValue = stringValue; }

    public static ChatResponseStatus fromValue(String stringValue) {
        for (ChatResponseStatus chatResponseStatus: values()) {
            if (chatResponseStatus.getStringValue().equals(stringValue)) {
                return chatResponseStatus;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + stringValue);
    }
}
