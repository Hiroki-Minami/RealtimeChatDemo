package com.hirokiminami.chatroom.back.repository;

import com.hirokiminami.chatroom.back.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    @Query("{'owner.id': ?0}")
    List<ChatRoom> findHostingChatRooms(String userId);

//    @Query("{'owner.id': { $ne: ?0 }, '_id': { $in: ?1 } }")
    @Query("{ 'owner.id': { $ne: ?0 }, '_id': { $in: ?1 } }")
    List<ChatRoom> findParticipatingChatRooms(String userId, List<String> participating);
}
