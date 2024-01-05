package com.hirokiminami.chatroom.back.service;

import com.hirokiminami.chatroom.back.constant.chatroom.ChatRoomStatus;
import com.hirokiminami.chatroom.back.dto.chatroom.ChatRoomRequest;
import com.hirokiminami.chatroom.back.model.ChatRoom;
import com.hirokiminami.chatroom.back.model.User;
import com.hirokiminami.chatroom.back.model.supportive.Owner;
import com.hirokiminami.chatroom.back.model.supportive.UserProjection;
import com.hirokiminami.chatroom.back.model.supportive.UserProjectionImpl;
import com.hirokiminami.chatroom.back.repository.ChatRoomRepository;
import com.hirokiminami.chatroom.back.repository.UserRepository;
import com.hirokiminami.chatroom.back.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomService {

    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    UserRepository userRepository;

    public List<ChatRoom> getAllChatRoom() { return chatRoomRepository.findAll(); }

    public List<ChatRoom> getHostingChatRooms(String userId) { return chatRoomRepository.findHostingChatRooms(userId); }

    public List<ChatRoom> getParticipatingChatRooms(String userId, List<String> participating) {
        return chatRoomRepository.findParticipatingChatRooms(userId, participating != null ? participating: new ArrayList<>());
    }

    public ChatRoom getChatRoom(String id) { return chatRoomRepository.findById(id).orElse(null); }

    public ChatRoom createChatRoom(ChatRoomRequest request, Owner owner) throws ParseException {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(request.getRoomName());
        chatRoom.setDescription(request.getDescription());
        Instant startDate = DateUtil.convertDateStr(request.getStartDate());
        Instant endDate = DateUtil.convertDateStr(request.getEndDate());
        chatRoom.setStartDate(startDate);
        chatRoom.setEndDate(endDate);
        chatRoom.setOwner(owner);
        chatRoom.setStatus(ChatRoomStatus.CREATED);
        // TODO: validation
        // If bad throw exception
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom updateChatRoom(String id, ChatRoomRequest request) throws ParseException {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow();
        chatRoom.setRoomName(request.getRoomName());
        chatRoom.setDescription(request.getDescription());
        Instant startDate = DateUtil.convertDateStr(request.getStartDate());
        Instant endDate = DateUtil.convertDateStr(request.getEndDate());
        chatRoom.setStartDate(startDate);
        chatRoom.setEndDate(endDate);

        // validation
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom updateChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    public void delete(String id) { chatRoomRepository.deleteById(id); }

    @Transactional
    public UserProjection attendChatRoom(String id, User user) {
        // add the user into participants
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow();
        List<String> participants = chatRoom.getParticipants();
        if (participants == null) {
            participants = new ArrayList<>();
            participants.add(user.getId());
            chatRoom.setParticipants(participants);
        } else {
            participants.add(user.getId());
        }
        chatRoomRepository.save(chatRoom);

        // add the roomId into participating
        List<String> participating = user.getParticipating();
        if (participating == null) {
            participating = new ArrayList<>();
            participating.add(id);
            user.setParticipating(participating);
        } else {
            participating.add(id);
        }
        userRepository.save(user);
        return UserProjectionImpl.fromEntity(user);
    }

    @Transactional
    public UserProjection cancel(String id, User user) {
        // remove the user into participants
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow();
        List<String> participants = chatRoom.getParticipants();
        participants.remove(user.getId());
        chatRoomRepository.save(chatRoom);

        // add the roomId into participating
        List<String> participating = user.getParticipating();
        participating.remove(id);

        userRepository.save(user);
        return UserProjectionImpl.fromEntity(user);
    }
}
