package com.jmpaniego.RedditClone.repositories;

import com.jmpaniego.RedditClone.models.Comment;
import com.jmpaniego.RedditClone.models.Post;
import com.jmpaniego.RedditClone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPost(Post post);

  List<Comment> findAllByUser(User user);
}
