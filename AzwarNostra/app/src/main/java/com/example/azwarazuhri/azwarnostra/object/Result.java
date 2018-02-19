package com.example.azwarazuhri.azwarnostra.object;

import com.google.gson.annotations.Expose;

/**
 * Created by azwarazuhri on 2/15/18.
 */

public class Result {

    @Expose
    private String message;

    @Expose
    private Object result;

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }
}
