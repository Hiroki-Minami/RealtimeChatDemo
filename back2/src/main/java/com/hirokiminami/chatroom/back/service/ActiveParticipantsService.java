package com.hirokiminami.chatroom.back.service;

import com.hirokiminami.chatroom.back.repository.ChatRoomParticipantsHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActiveParticipantsService {
    private static final String PREFIX = "chatRoom:participants:";
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    ChatRoomParticipantsHolder chatRoomParticipantsHolder;

    public List<String> getParticipants(String roomId) {
        String redisKey = PREFIX + roomId;

        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        Set<String> participantsSet = setOperations.members(redisKey);
        return participantsSet != null ? List.copyOf(participantsSet): new ArrayList<>();
    }

    public void join(String roomId, String userId) {
        String redisKey = PREFIX + roomId;

        Map<String, Set<String>> map = chatRoomParticipantsHolder.getParticipantsHolder();
        Set<String> participants = map.getOrDefault(roomId, new HashSet<>());
        participants.add(userId);
        map.put(roomId, participants);

        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        setOperations.add(redisKey, userId);
    }

    public void leave(String roomId, String userId) {
        String redisKey = PREFIX + roomId;

        Map<String, Set<String>> map = chatRoomParticipantsHolder.getParticipantsHolder();
        Set<String> participants = map.getOrDefault(roomId, new HashSet<>());
        participants.remove(userId);
        map.put(roomId, participants);

        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        setOperations.remove(redisKey, userId);
    }

    public void done(String roomId) {
        String redisKey = PREFIX + roomId;

        Map<String, Set<String>> map = chatRoomParticipantsHolder.getParticipantsHolder();
        map.remove(roomId);

        redisTemplate.delete(roomId);
    }
}
