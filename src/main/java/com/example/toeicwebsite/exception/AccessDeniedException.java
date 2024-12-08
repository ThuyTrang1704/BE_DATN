package com.example.toeicwebsite.exception;

import java.util.Map;

public class AccessDeniedException extends ExceptionCustom {

    public AccessDeniedException() {
        super("ACCESS DENIED", "NOT ENOUGH PERMISSION");
    }

    public AccessDeniedException(Map<String, Object> errors) {
        super("ACCESS DENIED - NOT ENOUGH PERMISSION", errors);
    }

}
