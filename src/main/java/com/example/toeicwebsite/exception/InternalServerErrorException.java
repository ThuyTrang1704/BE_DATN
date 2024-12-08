package com.example.toeicwebsite.exception;

public class InternalServerErrorException extends ExceptionCustom {

    public InternalServerErrorException(Object errors) {
        super("UNEXPECTED ERROR OCCURRED", errors);
    }

}
