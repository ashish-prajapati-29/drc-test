package com.drc.test.exception;

import org.springframework.http.HttpStatus;

public class GlobalError extends RuntimeException {

    private  String message;
    private int applicationStatusCode;
    private  int httpStatus;

    public GlobalError(String message){
        this.message=message;
        this.applicationStatusCode=0;
        this.httpStatus= HttpStatus.BAD_REQUEST.value();
    }
    public GlobalError(String message, int applicationStatusCode, int httpStatus) {
        this.message = message;
        this.applicationStatusCode = applicationStatusCode;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
    public int getApplicationStatusCode() { return applicationStatusCode; }
    public int getHttpStatus() {
        return httpStatus;
    }

    protected void setMessage(String message){this.message=message;}
    protected void setApplicationStatusCode(int applicationStatusCode){this.applicationStatusCode=applicationStatusCode;}
    protected void setHttpStatus(int httpStatus){this.httpStatus=httpStatus;}
}

