package com.example.forum.models;

import jakarta.persistence.*;

@Entity
@Table(name = "posts_comments", schema = "forum", catalog = "")
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "comment_id")
    private int commentId;
    @Basic
    @Column(name = "post_id")
    private int postId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "comment")
    private String comment;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment that = (Comment) o;

        if (commentId != that.commentId) return false;
        if (postId != that.postId) return false;
        if (userId != that.userId) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + postId;
        result = 31 * result + userId;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
