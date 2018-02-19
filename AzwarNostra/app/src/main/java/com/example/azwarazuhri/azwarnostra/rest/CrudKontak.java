package com.example.azwarazuhri.azwarnostra.rest;

import com.example.azwarazuhri.azwarnostra.object.PhoneBook;
import com.example.azwarazuhri.azwarnostra.object.Result;
import com.example.azwarazuhri.azwarnostra.object.ResultList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by azwarazuhri on 2/15/18.
 */

public interface CrudKontak {

    @GET("api/v1/person?limit=100")
    Call<ResultList> getContacts();

    @Headers("Content-Type: application/json")
    @POST("api/v1/person")
    Call<Result> addContact(@Body PhoneBook phoneBook);

    @Headers("Content-Type: application/json")
    @PUT("api/v1/person/{secure_id}")
    Call<Result> updateContact(@Path("secure_id") String contact_id, @Body PhoneBook phoneBook);

    @DELETE("api/v1/person/{secure_id}")
    Call<Result> deleteContact(@Path("secure_id") String contact_id);
}
