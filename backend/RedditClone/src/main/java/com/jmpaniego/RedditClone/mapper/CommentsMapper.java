package com.jmpaniego.RedditClone.mapper;

import com.jmpaniego.RedditClone.dto.CommentsDto;
import com.jmpaniego.RedditClone.models.Comment;
import com.jmpaniego.RedditClone.models.Post;
import com.jmpaniego.RedditClone.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "text", source = "commentsDto.text")
  @Mapping(target = "created", expression = "java(java.time.Instant.now())")
  @Mapping(target = "post" ,source = "post")
  Comment map(CommentsDto commentsDto, Post post, User user);

  @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
  @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
  CommentsDto mapToDto(Comment comment);
}
