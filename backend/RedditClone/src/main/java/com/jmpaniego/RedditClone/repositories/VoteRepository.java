package com.jmpaniego.RedditClone.repositories;

import com.jmpaniego.RedditClone.models.Post;
import com.jmpaniego.RedditClone.models.User;
import com.jmpaniego.RedditClone.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
  Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
