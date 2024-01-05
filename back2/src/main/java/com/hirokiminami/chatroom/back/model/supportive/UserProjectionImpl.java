package com.hirokiminami.chatroom.back.model.supportive;

import com.hirokiminami.chatroom.back.model.User;

import java.util.List;

public class UserProjectionImpl implements UserProjection {
    private String id;
    private String name;
    private String nickName;
    private String description;
    private List<String> followings;
    private List<String> followers;
    private List<String> participating;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<String> getFollowings() {
        return followings;
    }

    @Override
    public List<String> getFollowers() {
        return followers;
    }

    @Override
    public List<String> getParticipating() {
        return participating;
    }

    public static UserProjectionImpl fromEntity(User user) {
        UserProjectionImpl projection = new UserProjectionImpl();
        projection.id = user.getId();
        projection.name = user.getName();
        projection.nickName = user.getNickName();
        projection.description = user.getDescription();
        projection.followings = user.getFollowings();
        projection.followers = user.getFollowers();
        projection.participating = user.getParticipating();
        return projection;
    }
}
