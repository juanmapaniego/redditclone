package com.jmpaniego.RedditClone.repositories;

import com.jmpaniego.RedditClone.models.Subreddtit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddtit, Long> {
  Optional<Subreddtit> findByName(String subredditName);
}
