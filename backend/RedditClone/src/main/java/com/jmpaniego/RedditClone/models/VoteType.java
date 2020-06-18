package com.jmpaniego.RedditClone.models;

import com.jmpaniego.RedditClone.exceptions.SpringRedditException;

import java.util.Arrays;

public enum VoteType {
  UPVOTE(1),
  DOWNVOTE(-1);

  private int direction;

  VoteType(int direction){}

  public static VoteType lookuo(Integer direction){
    return Arrays.stream(VoteType.values())
        .filter(value -> value.getDirection().equals(direction))
        .findAny()
        .orElseThrow(
            ()->new SpringRedditException("Vote not found.")
        );
  }

  private Integer getDirection() {
    return direction;
  }
}
