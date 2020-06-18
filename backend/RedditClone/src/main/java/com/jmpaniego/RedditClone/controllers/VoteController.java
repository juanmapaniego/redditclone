package com.jmpaniego.RedditClone.controllers;

import com.jmpaniego.RedditClone.dto.VoteDto;
import com.jmpaniego.RedditClone.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes/")
public class VoteController {
  @Autowired
  private VoteService voteService;

  @PostMapping
  public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto){
    voteService.vote(voteDto);
    return ResponseEntity.ok().build();
  }


}
