package com.hirokiminami.chatroom.back.controller;

import com.hirokiminami.chatroom.back.model.User;
import com.hirokiminami.chatroom.back.model.supportive.UserProjection;
import com.hirokiminami.chatroom.back.model.supportive.UserProjectionImpl;
import com.hirokiminami.chatroom.back.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserProjection> detail(@PathVariable String id) {
        UserProjection user = userService.getProjectedUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = userService.createUser(user);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody User user) {
        User updated = userService.updateUser(user);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<UserProjection>> searchUsers(@RequestParam String searchText) {
        List<String> searchTexts = List.of(searchText.split(" "));
        List<UserProjection> searched = userService.getUsersWithSearchTexts(searchTexts);
        return ResponseEntity.ok(searched);
    }

    @GetMapping("/followings")
    public ResponseEntity<List<UserProjection>> followings(Principal principal) {
        UserProjection user = userService.getProjectedUserByEmail(principal.getName());
        List<UserProjection> followings = userService.getUsersByIds(user.getFollowings());
        return ResponseEntity.ok(followings);
    }
    @GetMapping("/followers")
    public ResponseEntity<List<UserProjection>> followers(Principal principal) {
        UserProjection user = userService.getProjectedUserByEmail(principal.getName());
        List<UserProjection> followers = userService.getUsersByIds(user.getFollowers());
        return ResponseEntity.ok(followers);
    }
    @PutMapping("/followings/{id}")
    public ResponseEntity<UserProjection> follow(@PathVariable String id, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        // Add to followings
        List<String> followings = user.getFollowings();
        if (followings == null) {
            followings = new ArrayList<>();
            followings.add(id);
        } else if (!followings.contains(id)){
            followings.add(id);
        }
        user.setFollowings(followings);
        User updated = userService.updateUser(user);
        // Add to followers
        User followed = userService.getUserById(id);
        List<String> followers = followed.getFollowers();
        if (followers == null) {
            followers = new ArrayList<>();
            followers.add(updated.getId());
        } else if (!followers.contains(updated.getId())){
            followers.add(updated.getId());
        }
        userService.updateUser(followed);

        UserProjection updatedProjection = UserProjectionImpl.fromEntity(user);
        return ResponseEntity.ok(updatedProjection);
    }
    @DeleteMapping("/followings/{id}")
    public ResponseEntity<UserProjection> unfollow(@PathVariable String id, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        user.getFollowings().remove(id);
        User updated = userService.updateUser(user);
        UserProjection userProjection = UserProjectionImpl.fromEntity(updated);
        return ResponseEntity.ok(userProjection);
    }
}
