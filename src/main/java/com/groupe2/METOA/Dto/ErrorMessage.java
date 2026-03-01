package com.groupe2.METOA.Dto;

import java.time.LocalDateTime;

public class ErrorMessage {
    private String message;
    private Integer statutsCode;
    private String error;
    private LocalDateTime timestamp;

    public ErrorMessage() {
    }

    public ErrorMessage(String message, Integer statutsCode, String error, LocalDateTime timestamp) {
        this.message = message;
        this.statutsCode = statutsCode;
        this.error = error;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatutsCode() {
        return statutsCode;
    }

    public void setStatutsCode(Integer statutsCode) {
        this.statutsCode = statutsCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
