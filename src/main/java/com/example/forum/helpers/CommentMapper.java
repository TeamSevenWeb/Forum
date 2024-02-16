package com.example.forum.helpers;

import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.dtos.CommentDto;
import com.example.forum.models.dtos.PostDto;
import com.example.forum.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
        comment.setComment(dto.getComment());
        comment.setCreatedBy(commentService.getById(id).getCreatedBy());
        return comment;
    }

    public Comment fromDto(CommentDto dto) {
        Comment comment = new Comment();
        comment.setComment(dto.getComment());
        comment.setDateAndTimeOfCreation(LocalDateTime.now());
        return comment;
    }


    public CommentDto toDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setComment(comment.getComment());
        return commentDto;
    }


}
