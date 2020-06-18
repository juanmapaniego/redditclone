package com.jmpaniego.RedditClone.controllers;

import com.jmpaniego.RedditClone.dto.CommentsDto;
import com.jmpaniego.RedditClone.models.Comment;
import com.jmpaniego.RedditClone.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
public class CommentsController {

  @Autowired
  private CommentService commentService;

  @PostMapping
  public ResponseEntity<Void> createComments(@RequestBody CommentsDto commentsDto){
    commentService.save(commentsDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("by-postId/{postdId}")
  public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId ){
    return ResponseEntity.ok(
        commentService.getAllCommentsForPost(postId)
    );
  }

  @GetMapping("by-user/{username}")
  public ResponseEntity<List<CommentsDto>> getCommentsByUsername(@PathVariable String username){
    return ResponseEntity.ok(
        commentService.getCommentsByUsername(username)
    );
  }
}
