package com.hirokiminami.chatroom.back.repository;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class ChatRoomParticipantsHolder {
    private final Map<String, Set<String>> participantsHolder = new ConcurrentHashMap<>();
}
