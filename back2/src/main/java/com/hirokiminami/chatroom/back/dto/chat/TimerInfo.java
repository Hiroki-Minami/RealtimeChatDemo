package com.hirokiminami.chatroom.back.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class TimerInfo {
    private String roomId;
    private long rest;
}
