package com.example.forum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "reactions")
public class Reaction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reaction_id")
    private int reactionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    @Column(name = "isUpVoted")
    private boolean isUpVoted;

    public int getReactionId() {
        return reactionId;
    }

    public void setReactionId(int commentId) {
        this.reactionId = commentId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public boolean getIsUpVoted() {
        return isUpVoted;
    }

    public void setIsUpVoted(boolean IsUpVoted) {
        this.isUpVoted = IsUpVoted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reaction reaction = (Reaction) o;

        return reactionId == reaction.reactionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reactionId);
    }
}
