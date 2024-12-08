package com.example.toeicwebsite.exception;

import java.util.Map;

public class ConflictException extends ExceptionCustom {

    public ConflictException(Map<String, Object> errors) {
        super("DATA EXISTING", errors);
    }

}
