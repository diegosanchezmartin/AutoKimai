package com.firstProject.scheduleX.model;

public class OwnExceptions {
    public static class RegisteredSchedulesException extends RuntimeException {
        public RegisteredSchedulesException(String errorMessage) {
            super(errorMessage);
        }
    }
    public static class RegisteredSchedulesDiscoveredException extends RuntimeException {
        public RegisteredSchedulesDiscoveredException(String errorMessage) {
            super(errorMessage);
        }
    }
}
