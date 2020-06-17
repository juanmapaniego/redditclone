package com.jmpaniego.RedditClone.mapper;

import com.jmpaniego.RedditClone.dto.PostRequest;
import com.jmpaniego.RedditClone.dto.PostResponse;
import com.jmpaniego.RedditClone.models.Post;
import com.jmpaniego.RedditClone.models.Subreddit;
import com.jmpaniego.RedditClone.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
  @Mapping(target = "created", expression = "java(java.time.Instant.now())")
  @Mapping(target = "description", source = "postRequest.description")
  Post map(PostRequest postRequest, Subreddit subreddit, User user);

  @Mapping(target = "subredditName", source = "subreddit.name")
  @Mapping(target = "userName", source = "user.username")
  PostResponse mapToDto(Post post);

}
