package com.store.gamestore.exceptions;

public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 4545735274606275904L;

  public NotFoundException(String msg) {
    super(msg);
  }

  public NotFoundException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
