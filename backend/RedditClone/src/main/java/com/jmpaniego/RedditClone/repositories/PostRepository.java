package com.jmpaniego.RedditClone.repositories;

import com.jmpaniego.RedditClone.models.Post;
import com.jmpaniego.RedditClone.models.Subreddtit;
import com.jmpaniego.RedditClone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllBySubreddit(Subreddtit subreddtit);

  List<Post> findByUser(User user);
}
