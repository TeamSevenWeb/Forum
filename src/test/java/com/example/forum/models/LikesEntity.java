package com.example.forum.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "likes", schema = "forum", catalog = "")
public class LikesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "like_id")
    private int likeId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "post_id")
    private int postId;

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikesEntity that = (LikesEntity) o;
        return likeId == that.likeId && userId == that.userId && postId == that.postId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(likeId, userId, postId);
    }
}
