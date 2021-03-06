package com.jmpaniego.RedditClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
  private Long id;
  private Long postId;
  private Instant created;
  private String text;
  private String username;
}
