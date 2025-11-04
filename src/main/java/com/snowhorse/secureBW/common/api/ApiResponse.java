package com.snowhorse.secureBW.common.api;

import java.time.OffsetDateTime;

    public class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        private OffsetDateTime timestamp = OffsetDateTime.now();

        public ApiResponse() {}
        public ApiResponse(boolean success, String message, T data) {
            this.success = success; this.message = message; this.data = data;
        }
        public static <T> ApiResponse<T> ok(String msg, T data){ return new ApiResponse<>(true, msg, data); }
        public static <T> ApiResponse<T> fail(String msg){ return new ApiResponse<>(false, msg, null); }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }
        public OffsetDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
    }

