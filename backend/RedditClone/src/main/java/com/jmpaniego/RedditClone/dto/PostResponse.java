package com.jmpaniego.RedditClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
  private Long postId;
  private String postName;
  private String subredditName;
  private String userName;
  private String description;
  private String url;
}
