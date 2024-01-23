package com.example.forum.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "post_id")
    private int postId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "likes")
    private Integer likes;
    @Basic
    @Column(name = "date_and_time_of_creation")
    private Timestamp dateAndTimeOfCreation;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Timestamp getDateAndTimeOfCreation() {
        return dateAndTimeOfCreation;
    }

    public void setDateAndTimeOfCreation(Timestamp dateAndTimeOfCreation) {
        this.dateAndTimeOfCreation = dateAndTimeOfCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post posts = (Post) o;

        if (postId != posts.postId) return false;
        if (userId != posts.userId) return false;
        if (title != null ? !title.equals(posts.title) : posts.title != null) return false;
        if (content != null ? !content.equals(posts.content) : posts.content != null) return false;
        if (likes != null ? !likes.equals(posts.likes) : posts.likes != null) return false;
        if (dateAndTimeOfCreation != null ? !dateAndTimeOfCreation.equals(posts.dateAndTimeOfCreation) : posts.dateAndTimeOfCreation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = postId;
        result = 31 * result + userId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        result = 31 * result + (dateAndTimeOfCreation != null ? dateAndTimeOfCreation.hashCode() : 0);
        return result;
    }
}
