package com.hirokiminami.chatroom.back.controller;

import com.hirokiminami.chatroom.back.dto.chatroom.ChatRoomListResponse;
import com.hirokiminami.chatroom.back.dto.chatroom.ChatRoomRequest;
import com.hirokiminami.chatroom.back.factory.UserFactory;
import com.hirokiminami.chatroom.back.model.ChatRoom;
import com.hirokiminami.chatroom.back.model.User;
import com.hirokiminami.chatroom.back.model.supportive.Owner;
import com.hirokiminami.chatroom.back.model.supportive.UserProjection;
import com.hirokiminami.chatroom.back.service.ChatRoomService;
import com.hirokiminami.chatroom.back.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/chatRoom")
public class ChatRoomController {

    private static Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    UserService userService;
    @Autowired
    private SecurityContextRepository securityContextRepository;

    @GetMapping
    public ChatRoomListResponse chatRoomList(Principal principal, HttpServletRequest request) {
//        logger.info(Boolean.valueOf(securityContextRepository.containsContext(request)).toString());
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication authentication = context.getAuthentication();
//        logger.info(authentication.getName());
        ChatRoomListResponse response = new ChatRoomListResponse();
        response.setOthers(chatRoomService.getAllChatRoom());
        if (principal != null) {
            UserProjection user = userService.getProjectedUserByEmail(principal.getName());
            response.setHosting(chatRoomService.getHostingChatRooms(user.getId()));
            response.setParticipating(chatRoomService.getParticipatingChatRooms(user.getId(), user.getParticipating()));
            logger.info(principal.getName());
        } else {
            response.setHosting(new ArrayList<>());
            response.setParticipating(new ArrayList<>());
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatRoom> detail(@PathVariable String id) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(id);
        if (chatRoom == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chatRoom);
    }

    @PostMapping
    public ResponseEntity<ChatRoom> create(@Valid @RequestBody ChatRoomRequest chatRoomRequest, Principal principal) {
        // TODO: Transaction
        // insert into chatRoom and update user collection
        UserProjection user = userService.getProjectedUserByEmail(principal.getName());
        Owner owner = UserFactory.convertUser(user);
        ChatRoom room;
        try {
            room = chatRoomService.createChatRoom(chatRoomRequest, owner);
//            logger.info("created");
            // TODO: catch another exception when startDate and endDate are not appropriate
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
//        if (room == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
        return ResponseEntity.ok(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatRoom> update(@PathVariable String id, @Valid @RequestBody ChatRoomRequest chatRoomRequest) {
        ChatRoom updated;
        try {
            updated = chatRoomService.updateChatRoom(id, chatRoomRequest);
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        chatRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/participate/{id}")
    public  ResponseEntity<UserProjection> attend(@PathVariable String id, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        UserProjection updated = chatRoomService.attendChatRoom(id, user);

        return ResponseEntity.ok(updated);
        // TODO: add myself into the participant list in chatroom collection
        // add chatroom id into participating
    }

    @DeleteMapping("/participate/{id}")
    public ResponseEntity<UserProjection> cancel(@PathVariable String id, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        UserProjection updated = chatRoomService.cancel(id, user);

        return ResponseEntity.ok(updated);
    }
}
