package com.hirokiminami.chatroom.back.constant.chat.command;

import lombok.Getter;

@Getter
public enum ChatCommand {
    JOIN("join"),
    LEAVE("leave"),
    POST("post"),
    DELETE("delete"),
    DONE("done");

    private final String stringValue;
    ChatCommand(String stringValue) {
        this.stringValue = stringValue;
    }

    public static ChatCommand fromValue(String stringValue) {
        for (ChatCommand command: values()) {
            if (command.getStringValue().equals(stringValue)) {
                return command;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + stringValue);
    }
}

