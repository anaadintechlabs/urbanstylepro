package com.urbanstyle.inventory.util;

import org.springframework.stereotype.Component;



@Component
public class CustomException extends Exception {

	private static final long serialVersionUID = 6798031143363755641L;
	private static final String MESSAGE = "message";

	public CustomException() {

	}

	public CustomException(String message) {
		super(message);
	}





}