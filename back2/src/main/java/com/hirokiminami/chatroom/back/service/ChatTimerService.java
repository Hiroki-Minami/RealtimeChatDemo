package com.hirokiminami.chatroom.back.service;

import com.hirokiminami.chatroom.back.constant.chat.type.ChatResponseType;
import com.hirokiminami.chatroom.back.dto.chat.ChatResponseMessage;
import com.hirokiminami.chatroom.back.dto.chat.TimerInfo;
import com.hirokiminami.chatroom.back.model.ChatRoom;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class ChatTimerService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Map<String, TimerInfo> timerMap = new ConcurrentHashMap<>();
    public void startTimer(String roomId, ChatRoom chatRoom) {
        Duration duration = Duration.between(chatRoom.getStartDate(), chatRoom.getEndDate());
        long startTime = duration.getSeconds();
        timerMap.put(roomId, new TimerInfo(roomId, startTime));
    }
    public void stopTimer(String roomId) {
        timerMap.remove(roomId);
    }
    @Scheduled(fixedDelay = 1000)
    public void sendTimerUpdates() {
        for (Map.Entry<String, TimerInfo> entry: timerMap.entrySet()) {
            ChatResponseMessage chatResponseMessage = new ChatResponseMessage(ChatResponseType.TIMER, ChatCo)
        }
    }
    private String formatTime(long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
