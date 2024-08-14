package com.quizguru.auth.utils;

public final class Constant {

    public static final String VERIFY_SUBJECT = "Confirm your verification code";
    public static final String PASSWORD_RESET_SUBJECT = "Reset your password";
    public static final String PASSWORD_RESET_SUCCESS_SUBJECT = "Password reset successfully";

    public final class ERROR_CODE {
        public static final String UNAUTHORIZED_USERNAME_NOT_EXIST = "UNAUTHORIZED_USERNAME_NOT_EXIST";
        public static final String UNAUTHORIZED_ID_NOT_EXIST = "UNAUTHORIZED_ID_NOT_EXIST";
        public static final String INVALID_TOKEN = "INVALID_TOKEN";
        public static final String UNAUTHORIZED_INTERNAL_ERROR = "UNAUTHORIZED_INTERNAL_ERROR";
        public static final String INVALID_REFRESH_TOKEN = "INVALID_REFRESH_TOKEN";
        public static final String REFRESH_TOKEN_NOT_FOUND = "REFRESH_TOKEN_NOT_FOUND";
        public static final String REFRESH_TOKEN_EXPIRED = "REFRESH_TOKEN_EXPIRED";
        public static final String INVALID_VERIFY_TOKEN = "INVALID_VERIFY_TOKEN";
        public static final String VERIFY_TOKEN_NOT_FOUND = "VERIFY_TOKEN_NOT_FOUND";
        public static final String VERIFY_TOKEN_EXPIRED = "VERIFY_TOKEN_EXPIRED";
        public static final String USER_NOT_ENABLED = "USER_NOT_ENABLED";
        public static final String INVALID_PASSWORD_FORMAT = "INVALID_PASSWORD_FORMAT";
        public static final String PASSWORD_REST_TOKEN_NOT_FOUND = "PASSWORD_REST_TOKEN_NOT_FOUND";
        public static final String PASSWORD_RESET_TOKEN_EXPIRED = "PASSWORD_RESET_TOKEN_EXPIRED";
        public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
        public static final String SOMETHING_WENT_WRONG = "SOMETHING_WENT_WRONG";
    }
}
