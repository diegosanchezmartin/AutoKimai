package com.firstProject.scheduleX.model;

import lombok.Getter;

public class OwnExceptions {
    public static class RegisteredSchedulesException extends RuntimeException {
        @Getter
        TimeSheetGet error;
        public RegisteredSchedulesException(TimeSheetGet errorMessage) {
            super();
            this.error = errorMessage;
        }
    }
    public static class RegisteredSchedulesDiscoveredException extends RuntimeException {
        @Getter
        TimeSheetGet error;
        public RegisteredSchedulesDiscoveredException(TimeSheetGet errorMessage) {
            super();
            this.error = errorMessage;
        }
    }

    public static class RegisteredSchedulesDiscoveredButMustContinueException extends RuntimeException {
        @Getter
        TimeSheetGet error;
        public RegisteredSchedulesDiscoveredButMustContinueException(TimeSheetGet errorMessage) {
            super();
            this.error = errorMessage;
        }
    }

}
