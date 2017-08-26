package com.vatebra.eirsagentpoc.domain.entity;

import java.util.List;

/**
 * Created by David Eti on 26/08/2017.
 */

public class ApiSingleResponse<T> {


    private String Status;
    private String Message;
    private T Data;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}

