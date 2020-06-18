package com.jmpaniego.RedditClone.services;

import com.jmpaniego.RedditClone.dto.VoteDto;
import com.jmpaniego.RedditClone.exceptions.SpringRedditException;
import com.jmpaniego.RedditClone.models.Post;
import com.jmpaniego.RedditClone.models.User;
import com.jmpaniego.RedditClone.models.Vote;
import com.jmpaniego.RedditClone.models.VoteType;
import com.jmpaniego.RedditClone.repositories.PostRepository;
import com.jmpaniego.RedditClone.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

  @Autowired
  private AuthService authService;
  @Autowired
  PostRepository postRepository;
  @Autowired
  VoteRepository voteRepository;

  public void vote(VoteDto voteDto) {
    Post post = postRepository.findById(voteDto.getPostId())
        .orElseThrow(() -> new SpringRedditException(
            String.format("Post with id %d doe not exist",voteDto.getPostId())
        ));
    User user = authService.getCurrentUser();
    Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user);
    if(voteByPostAndUser.isPresent() &&
        voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
      throw new SpringRedditException("You have already vote "+voteDto.getVoteType() + " on " + post.getPostName());
    }
    if(VoteType.UPVOTE.equals(voteDto.getVoteType())){
      post.setVoteCount(post.getVoteCount()+1);
    }else{
      post.setVoteCount(post.getVoteCount()-1);
    }
    voteRepository.save(mapToVote(voteDto,post));
    postRepository.save(post);
  }

  private Vote mapToVote(VoteDto voteDto, Post post) {
    return Vote.builder()
        .post(post)
        .voteType(voteDto.getVoteType())
        .user(authService.getCurrentUser())
        .build();
  }
}
