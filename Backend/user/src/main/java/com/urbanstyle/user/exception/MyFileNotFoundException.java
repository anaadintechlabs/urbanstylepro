package com.urbanstyle.user.exception;

public class MyFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1559573751368589132L;

	public MyFileNotFoundException(String message) {
        super(message);
    }

    public MyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}