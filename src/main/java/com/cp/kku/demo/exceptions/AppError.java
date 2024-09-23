package com.cp.kku.demo.exceptions;

import lombok.Data;
import java.util.Date;

@Data
public class AppError {
    private int status;
    private String messege;
    private Date timestamp;

    public AppError(int status, String message){
        this.status = status;
        this.messege = message;
        this.timestamp = new Date();
    }
}
