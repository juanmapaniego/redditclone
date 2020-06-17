package com.jmpaniego.RedditClone.services;

import com.jmpaniego.RedditClone.dto.PostRequest;
import com.jmpaniego.RedditClone.dto.PostResponse;
import com.jmpaniego.RedditClone.exceptions.SpringRedditException;
import com.jmpaniego.RedditClone.mapper.PostMapper;
import com.jmpaniego.RedditClone.models.Post;
import com.jmpaniego.RedditClone.models.Subreddit;
import com.jmpaniego.RedditClone.models.User;
import com.jmpaniego.RedditClone.repositories.PostRepository;
import com.jmpaniego.RedditClone.repositories.SubredditRepository;
import com.jmpaniego.RedditClone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
  @Autowired
  private SubredditRepository subredditRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthService authService;
  @Autowired
  private PostRepository postRepository;
  @Autowired
  private PostMapper postMapper;

  public Post save(PostRequest postRequest){
    Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(
        () -> new SpringRedditException(
            String.format("Not subreddit with name %s exit", postRequest.getSubredditName())
        )
    );
    User user = authService.getCurrentUser();

    return postRepository.save(
        postMapper.map(postRequest, subreddit, user)
    );
  }

  @Transactional(readOnly = true)
  public PostResponse getPost(Long id) {
    Post post = postRepository.findById(id).orElseThrow(
        () -> new SpringRedditException(String.format("Post with id %d not exist", id))
    );
    return postMapper.mapToDto(post);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts() {
    List<Post> posts = postRepository.findAll();
    return posts.stream()
        .map(postMapper::mapToDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsBySubreddit(Long id) {
    Subreddit subreddit = subredditRepository.findById(id).orElseThrow(
        () -> new SpringRedditException(
            String.format("Not subreddit with id %d exist", id)
        )
    );
    List<Post> posts = postRepository.findAllBySubreddit(subreddit);
    return posts.stream()
        .map(postMapper::mapToDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByUsername(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new SpringRedditException(String.format("User with name %s not found", username))
    );
    List<Post> posts = postRepository.findByUser(user);
    return posts.stream()
        .map(postMapper::mapToDto)
        .collect(Collectors.toList());
  }
}
