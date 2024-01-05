package com.hirokiminami.chatroom.back.service;

import com.hirokiminami.chatroom.back.model.User;
import com.hirokiminami.chatroom.back.model.supportive.UserProjection;
import com.hirokiminami.chatroom.back.repository.UserRepository;
//import com.hirokiminami.chatroom.back2.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger Log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public UserProjection getProjectedUserById(String id) { return userRepository.findProjectedById(id).orElse(null); }
    public UserProjection getProjectedUserByEmail(String email) { return userRepository.findProjectedByEmail(email).orElse(null); }
    public User getUserById(String id) { return userRepository.findById(id).orElse(null); }
    public User getUserByEmail(String email) { return userRepository.findByEmail(email).orElse(null);}
    public boolean existUserByEmail(String email) { return getUserByEmail(email) != null; }
    public User createUser(User user) { return userRepository.save(user); }
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<UserProjection> getUsersByIds(List<String> ids) {
        return userRepository.findAllProjectedByIdIn(ids);
    }
    public List<UserProjection> getUsersWithSearchTexts(List<String> searchTexts) {
        return userRepository.findAllProjectedUsersByText(searchTexts);
    }

}
