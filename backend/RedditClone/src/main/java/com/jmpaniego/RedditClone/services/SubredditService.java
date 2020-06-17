package com.jmpaniego.RedditClone.services;

import com.jmpaniego.RedditClone.dto.SubredditDto;
import com.jmpaniego.RedditClone.exceptions.SpringRedditException;
import com.jmpaniego.RedditClone.mapper.SubredditMapper;
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
  @Autowired
  private SubredditMapper subredditMapper;

  @Transactional
  public SubredditDto save(SubredditDto subredditDto){
    Subreddit subreddit = subredditMapper.mapDtoToSubreddit(subredditDto);
    Subreddit save = subredditRepository.save(subreddit);
    subredditDto.setId(save.getId());
    return subredditDto;
  }

  @Transactional(readOnly = true)
  public List<SubredditDto> getAll() {
    return subredditRepository.findAll()
        .stream()
        .map(subredditMapper::mapSubredditToDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public SubredditDto getSubreddit(Long id) {
    Subreddit subreddit = subredditRepository.findById(id).orElseThrow(
        () -> new SpringRedditException("Not Subreddit found with id " + id)
    );
    return subredditMapper.mapSubredditToDto(subreddit);
  }
}
