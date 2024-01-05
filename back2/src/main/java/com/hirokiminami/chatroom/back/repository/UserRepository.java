package com.hirokiminami.chatroom.back.repository;

import com.hirokiminami.chatroom.back.model.User;
import com.hirokiminami.chatroom.back.model.supportive.UserProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<UserProjection> findProjectedByEmail(String email);
    Optional<UserProjection> findProjectedById(String id);
    List<UserProjection> findAllProjectedByIdIn(List<String> ids);

    @Query(value = "{ $or: [ { 'name': { $in: ?0 } }, { 'nickName' : { $in: ?0 } } ] }",
            fields = "{ 'id' : 1, 'name': 1, 'nickName': 1 }")
    List<UserProjection> findAllProjectedUsersByText(List<String> searchTexts);
}
