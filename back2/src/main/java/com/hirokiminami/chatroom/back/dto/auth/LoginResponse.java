package com.hirokiminami.chatroom.back.dto.auth;

import com.hirokiminami.chatroom.back.model.supportive.UserProjection;

public record LoginResponse(boolean loggedIn, UserProjection user) {
}
