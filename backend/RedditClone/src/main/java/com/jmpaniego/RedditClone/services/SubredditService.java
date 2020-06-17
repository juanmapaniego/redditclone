package com.jmpaniego.RedditClone.services;

import com.jmpaniego.RedditClone.dto.SubredditDto;
import com.jmpaniego.RedditClone.models.Subreddit;
import com.jmpaniego.RedditClone.repositories.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditService {

  @Autowired
  private SubredditRepository subredditRepository;

  @Transactional
  public SubredditDto save(SubredditDto subredditDto){
    Subreddit subreddit = mapSubredditDto(subredditDto);
    Subreddit save = subredditRepository.save(subreddit);
    subredditDto.setId(save.getId());
    return subredditDto;
  }

  private Subreddit mapSubredditDto(SubredditDto subredditDto) {
    return Subreddit.builder().name(subredditDto.getName())
        .description(subredditDto.getDescription())
        .build();
  }

  @Transactional(readOnly = true)
  public List<SubredditDto> getAll() {
    return subredditRepository.findAll()
        .stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());
  }

  private SubredditDto mapToDto(Subreddit subreddit) {
    return SubredditDto.builder().name(subreddit.getName())
        .description(subreddit.getDescription())
        .id(subreddit.getId())
        .numberOfPosts(subreddit.getPosts().size())
        .build();
  }
}
