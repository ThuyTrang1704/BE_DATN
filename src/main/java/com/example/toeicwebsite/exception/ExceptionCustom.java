package com.example.toeicwebsite.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ExceptionCustom extends RuntimeException {
	private final Object errors;
	
	public ExceptionCustom(String msg, Object errors) {
		super(msg);
		this.errors = errors;
	}
}
