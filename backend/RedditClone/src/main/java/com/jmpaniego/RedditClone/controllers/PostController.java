package com.jmpaniego.RedditClone.controllers;

import com.jmpaniego.RedditClone.dto.PostRequest;
import com.jmpaniego.RedditClone.dto.PostResponse;
import com.jmpaniego.RedditClone.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
  @Autowired
  private PostService postService;

  @PostMapping
  public ResponseEntity createPost(@RequestBody PostRequest postRequest){
    postService.save(postRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public PostResponse getPost(@PathVariable Long id){
    return postService.getPost(id);
  }

  @GetMapping("/")
  public List<PostResponse> getAllPosts(){
    return postService.getAllPosts();
  }

  @GetMapping("/by-subreddit/{id}")
  public List<PostResponse> getPostsBySubreddit(@PathVariable Long id){
    return postService.getPostsBySubreddit(id);
  }

  @GetMapping("/by-user/{name}")
  public List<PostResponse> getPostsByUsername(@PathVariable String name){
    return postService.getPostsByUsername(name);
  }
}
