package com.jmpaniego.RedditClone.models;

import jdk.internal.jline.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long postId;
  @NotBlank(message = "Post Name cannot be empty or Null")
  private String postName;
  @Nullable
  private String url;
  @Nullable
  @Lob
  private String description;
  private Integer voteCount;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "userId")
  private User user;
  private Instant created;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id",referencedColumnName = "id")
  private Subreddtit subreddtit;
}
