package com.hirokiminami.chatroom.back.factory;

import com.hirokiminami.chatroom.back.model.supportive.Owner;
import com.hirokiminami.chatroom.back.model.supportive.UserProjection;

public class UserFactory {
    public static Owner convertUser(UserProjection user) {
        Owner owner = new Owner();
        owner.setId(user.getId());
        owner.setName(user.getName());
        owner.setNickName(user.getNickName());
        return owner;
    }
}
