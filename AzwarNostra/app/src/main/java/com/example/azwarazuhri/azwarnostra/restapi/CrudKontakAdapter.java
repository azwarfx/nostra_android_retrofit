package com.example.azwarazuhri.azwarnostra.restapi;

import com.example.azwarazuhri.azwarnostra.rest.CrudKontak;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by azwarazuhri on 2/15/18.
 */

public class CrudKontakAdapter {

    public Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dev.nostratech.com:10093/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
