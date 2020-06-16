package com.jmpaniego.RedditClone.repositories;

import com.jmpaniego.RedditClone.models.Subreddtit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddtit, Long> {
  Optional<Subreddtit> findByName(String subredditName);
}
