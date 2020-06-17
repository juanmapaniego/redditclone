package com.jmpaniego.RedditClone.controllers;

import com.jmpaniego.RedditClone.dto.SubredditDto;
import com.jmpaniego.RedditClone.services.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {

  @Autowired
  private SubredditService subredditService;

  @PostMapping
  public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subreddit){
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(subredditService.save(subreddit));
  }

  @GetMapping
  public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(subredditService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id){
    return ResponseEntity.ok(subredditService.getSubreddit(id));
  }

}
