package com.moonrover;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private String error;

    public ApiResponse() {
        this.success = true;
    }

    public ApiResponse(T data) {
        this.success = true;
        this.data = data;
    }

    public ApiResponse(String message) {
        this.success = true;
        this.message = message;
    }

    public ApiResponse(String message, String error) {
        this.success = false;
        this.message = message;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}