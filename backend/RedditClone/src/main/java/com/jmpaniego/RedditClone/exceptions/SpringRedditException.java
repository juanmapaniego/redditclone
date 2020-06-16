package com.jmpaniego.RedditClone.exceptions;

public class SpringRedditException extends RuntimeException {
  public SpringRedditException() {
    super();
  }

  public SpringRedditException(String message) {
    super(message);
  }
}
