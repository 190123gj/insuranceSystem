package com.born.insurance.ws.info.sms;

/**
 * 短信接口
 *
 *
 * @author hehao
 *
 */
public class SMSInfo {

    private String error;
    private String message;
    private boolean success;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
