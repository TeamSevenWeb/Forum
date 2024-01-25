package com.example.forum.helpers;

import com.example.forum.models.Comment;
import com.example.forum.models.dtos.CommentDto;
import com.example.forum.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private final CommentService commentService;

    @Autowired
    public CommentMapper(CommentService commentService) {
        this.commentService = commentService;
    }
    public Comment fromDto(int id, CommentDto dto) {
        Comment comment = fromDto(dto);
        comment.setCommentId(id);
        comment.setPost(commentService.getById(id).getPost());
        comment.setCreatedBy(commentService.getById(id).getCreatedBy());
        return comment;
    }

    public Comment fromDto(CommentDto dto) {
        Comment comment = new Comment();
        comment.setComment(dto.getComment());
        return comment;
    }
}
