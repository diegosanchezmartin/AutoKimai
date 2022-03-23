package com.firstProject.scheduleX.model;

public class OwnExceptions {
    public static class RegisteredSchedulesException extends RuntimeException {
        public RegisteredSchedulesException(String errorMessage) {
            super(errorMessage);
        }
    }
}
