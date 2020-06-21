package com.jmpaniego.RedditClone.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.jmpaniego.RedditClone.dto.PostRequest;
import com.jmpaniego.RedditClone.dto.PostResponse;
import com.jmpaniego.RedditClone.models.*;
import com.jmpaniego.RedditClone.repositories.CommentRepository;
import com.jmpaniego.RedditClone.repositories.VoteRepository;
import com.jmpaniego.RedditClone.services.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private VoteRepository voteRepository;
  @Autowired
  private AuthService authService;

  @Mapping(target = "created", expression = "java(java.time.Instant.now())")
  @Mapping(target = "description", source = "postRequest.description")
  @Mapping(target = "voteCount", constant = "0")
  @Mapping(target = "user", source = "user")
  @Mapping(target = "subreddit", source = "subreddit")
  public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

  @Mapping(target = "subredditName", source = "subreddit.name")
  @Mapping(target = "userName", source = "user.username")
  //Newly
  @Mapping(target = "commentCount", expression = "java(commentCount(post))")
  @Mapping(target = "duration", expression = "java(getDuration(post))")
  @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
  @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
  public abstract PostResponse mapToDto(Post post);


  Integer commentCount(Post post) {
    return commentRepository.findByPost(post).size();
  }

  String getDuration(Post post) {
    return TimeAgo.using(post.getCreated().toEpochMilli());
  }

  boolean isPostUpVoted(Post post) {
    return checkVoteType(post, VoteType.UPVOTE);
  }

  boolean isPostDownVoted(Post post) {
    return checkVoteType(post, VoteType.DOWNVOTE);
  }

  private boolean checkVoteType(Post post, VoteType voteType) {
    if (authService.isLoggedIn()) {
      Optional<Vote> voteForPostByUser =
          voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
              authService.getCurrentUser());
      return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
          .isPresent();
    }
    return false;
  }
}
