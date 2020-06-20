package com.jmpaniego.RedditClone.services;

import com.jmpaniego.RedditClone.dto.CommentsDto;
import com.jmpaniego.RedditClone.exceptions.SpringRedditException;
import com.jmpaniego.RedditClone.mapper.CommentsMapper;
import com.jmpaniego.RedditClone.models.Comment;
import com.jmpaniego.RedditClone.models.NotificationEmail;
import com.jmpaniego.RedditClone.models.Post;
import com.jmpaniego.RedditClone.models.User;
import com.jmpaniego.RedditClone.repositories.CommentRepository;
import com.jmpaniego.RedditClone.repositories.PostRepository;
import com.jmpaniego.RedditClone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthService authService;
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private CommentsMapper commentsMapper;

  @Autowired
  private MailContentBuilder mailContentBuilder;
  @Autowired
  private MailService mailService;

  public void save(CommentsDto commentsDto){
    Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(
        ()->new SpringRedditException("")
    );
    System.out.println(commentsDto.getPostId());
    System.out.println(post);
    User user = authService.getCurrentUser();
    Comment comment = commentRepository.save(commentsMapper.map(commentsDto, post, user));

    String message = mailContentBuilder.build(
        post.getUser().getUsername() + " posted a comment on your post " + post.getUrl()
    );
    sendCommentNotification(message, post.getUser());
  }

  private void sendCommentNotification(String message, User user) {
    mailService.sendMail(
        new NotificationEmail(
            user.getUsername() + " commented on your post",
            user.getEmail(),
            message
        )
    );
  }

  public List<CommentsDto> getAllCommentsForPost(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new SpringRedditException(String.format("Post $d not found", postId))
    );
    return commentRepository.findByPost(post).stream()
        .map(commentsMapper::mapToDto)
        .collect(Collectors.toList());
  }

  public List<CommentsDto> getCommentsByUsername(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        ()->new SpringRedditException(String.format("Username %s not found",username))
    );
    return commentRepository.findAllByUser(user).stream()
        .map(commentsMapper::mapToDto)
        .collect(Collectors.toList());
  }
}
