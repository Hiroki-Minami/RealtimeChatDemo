package com.hirokiminami.chatroom.back.service;

import com.hirokiminami.chatroom.back.constant.chat.command.ChatCommand;
import com.hirokiminami.chatroom.back.constant.chat.type.ChatResponseType;
import com.hirokiminami.chatroom.back.dto.chat.ChatResponseMessage;
import com.hirokiminami.chatroom.back.dto.chat.KafkaRequestMessage;
import com.hirokiminami.chatroom.back.dto.chat.TimerInfo;
import com.hirokiminami.chatroom.back.factory.ChatFactory;
import com.hirokiminami.chatroom.back.model.ChatRoom;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class ChatTimerService {
//    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;
    private final Map<String, TimerInfo> timerMap = new ConcurrentHashMap<>();
    @Autowired
    private final ChatRoomService chatRoomService;

    public void startTimer(String roomId, ChatRoom chatRoom) {
        Duration duration = Duration.between(chatRoom.getStartDate(), chatRoom.getEndDate());
        long startTime = duration.getSeconds();
        // TODO: create a independent thread
        // timer start
        timerMap.put(roomId, new TimerInfo(roomId, startTime));
    }
    public void stopTimer(String roomId) {
        timerMap.remove(roomId);
    }
    @Scheduled(fixedDelay = 1000)
    public void sendTimerUpdates() {
        List<String> doneRoomIds = new ArrayList<>();
        for (Map.Entry<String, TimerInfo> entry: timerMap.entrySet()) {
            long rest = entry.getValue().getRest();
            String time = formatTime(rest);
//            ChatResponseMessage chatResponseMessage = new ChatResponseMessage(ChatResponseType.TIMER, ChatCommand.POST, null, null, time, null);
//            String path = String.format("/chat/%s/messages", entry.getKey());
//            simpMessagingTemplate.convertAndSend(path, chatResponseMessage);

            KafkaRequestMessage kafkaRequestMessage = new KafkaRequestMessage(entry.getKey(), ChatResponseType.TIMER, ChatCommand.POST, null, null, time);
            this.kafkaTemplate.send("chat", kafkaRequestMessage);

            if (rest == 0) {
                // set status done on the chatroom record
                chatRoomService.doneChatRoom(entry.getKey());
                // send done message
//                ChatResponseMessage chatResponseDoneMessage = new ChatResponseMessage(ChatResponseType.TIMER, ChatCommand.DONE, null, null, null, null);
                KafkaRequestMessage kafkaRequestDoneMessage = new KafkaRequestMessage(entry.getKey(), ChatResponseType.TIMER, ChatCommand.DONE, null, null, null);
                this.kafkaTemplate.send("chat", kafkaRequestMessage);
//                simpMessagingTemplate.convertAndSend(path, chatResponseDoneMessage);
                // delete from hashmap
                doneRoomIds.add(entry.getKey());
            } else {
                rest--;
                entry.getValue().setRest(rest);
            }
        }
        for (String id: doneRoomIds) {
            stopTimer(id);
        }
    }
    private String formatTime(long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
