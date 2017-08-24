package com.vatebra.eirsagentpoc.domain.entity;

import java.util.List;

/**
 * Created by David Eti on 22/08/2017.
 */

public class ApiResponse<T> {

    private String Status;
    private String Message;
    private List<T> Data;

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

    public List<T> getData() {
        return Data;
    }

    public void setData(List<T> data) {
        Data = data;
    }
}
