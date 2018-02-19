package com.example.azwarazuhri.azwarnostra.object;

import com.google.gson.annotations.Expose;

/**
 * Created by azwarazuhri on 2/15/18.
 */

public class ResultList extends Result {


    @Expose
    private String pages;

    @Expose
    private String elements;

    public String getPages() {
        return pages;
    }

    public String getElements() {
        return elements;
    }
}
