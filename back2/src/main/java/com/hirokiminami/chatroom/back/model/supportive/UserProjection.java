package com.hirokiminami.chatroom.back.model.supportive;

import java.util.List;

public interface UserProjection {
    String getId();
    String getName();
    String getNickName();
    String getDescription();
    List<String> getFollowings();
    List<String> getFollowers();
    List<String> getParticipating();
}
