package com.jmpaniego.RedditClone.repositories;

import com.jmpaniego.RedditClone.models.Post;
import com.jmpaniego.RedditClone.models.Subreddtit;
import com.jmpaniego.RedditClone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllBySubreddit(Subreddtit subreddtit);

  List<Post> findByUser(User user);
}
