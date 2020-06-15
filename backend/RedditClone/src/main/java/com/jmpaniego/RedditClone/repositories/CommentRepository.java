package com.jmpaniego.RedditClone.repositories;

import com.jmpaniego.RedditClone.models.Comment;
import com.jmpaniego.RedditClone.models.Post;
import com.jmpaniego.RedditClone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPost(Post post);

  List<Comment> findAllByUser(User user);
}
