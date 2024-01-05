package com.hirokiminami.chatroom.back.constant.chatroom;

import lombok.Getter;

@Getter
public enum ChatRoomStatus {
    CREATED(1),
    STARTED(2),
    DONE(3),
    CANCELLED(4);

    private final int intValue;
    ChatRoomStatus(int intValue) {
        this.intValue = intValue;
    }

    public static ChatRoomStatus fromValue(int intValue) {
        for (ChatRoomStatus status: ChatRoomStatus.values()) {
            if (status.getIntValue() == intValue) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + intValue);
    }
}
