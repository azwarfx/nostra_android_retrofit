package com.example.azwarazuhri.azwarnostra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.azwarazuhri.azwarnostra.object.PhoneBook;
import com.example.azwarazuhri.azwarnostra.object.Result;
import com.example.azwarazuhri.azwarnostra.object.ResultList;
import com.example.azwarazuhri.azwarnostra.rest.CrudKontak;
import com.example.azwarazuhri.azwarnostra.restapi.CrudKontakAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Gson gson;
    private TextView resultTextView;
    private PhoneBook tempPhoneBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Show All Data Base
        resultTextView = findViewById(R.id.result_text_view);
        gson = new Gson();
        CrudKontakAdapter retrofitAdapter = new CrudKontakAdapter();
        final CrudKontak contactPhoneRestApi = retrofitAdapter
                .getRetrofit()
                .create(CrudKontak.class);
        getContacts(contactPhoneRestApi);

        //Add
        findViewById(R.id.add_person_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addContacts(contactPhoneRestApi);
                    }
                });

        findViewById(R.id.edit_person_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editContacts(contactPhoneRestApi);
                    }
                });

        findViewById(R.id.delete_person_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteContacts(contactPhoneRestApi);
                    }
                });
    }


    //Show All Contact
    private void getContacts(CrudKontak contactPhoneRestApi) {
        resultTextView.setText("Get Contacts Started...");
        contactPhoneRestApi.getContacts()
                .enqueue(new Callback<ResultList>() {
                    @Override
                    public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getMessage().equals("OK")) {
                                JsonArray jsonArray = gson.toJsonTree(response.body().getResult()).getAsJsonArray();
                                Type phoneBookType = new TypeToken<ArrayList<PhoneBook>>() {
                                }.getType();
                                List<PhoneBook> phoneBooks = gson.fromJson(jsonArray, phoneBookType);
                                for (PhoneBook phoneBook : phoneBooks) {
                                    resultTextView.setText(resultTextView.getText().toString() + "\n\n" + phoneBook.toString());
                                }
                                tempPhoneBook = phoneBooks.get(phoneBooks.size() - 1);
                            } else {
                                resultTextView.setText(resultTextView.getText().toString() + "\n\nError : " + response.body().getResult().toString());
                            }
                        } else {
                            try {
                                resultTextView.setText(resultTextView.getText().toString() + "\n\nError : " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultList> call, Throwable t) {
                        resultTextView.setText(resultTextView.getText().toString() + "\n\nError : " + t.getMessage());
                    }
                });
    }

    //Show Contact
    private void addContacts(CrudKontak contactPhoneRestApi) {
        PhoneBook phoneBook = new PhoneBook("Azwar", "Jl. Jalan", "081234567890", "test1@test.com");
        contactPhoneRestApi.addContact(phoneBook)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            resultTextView.setText(resultTextView.getText().toString() + "\n\n" + response.body().getResult().toString());
                        } else {
                            try {
                                resultTextView.setText(resultTextView.getText().toString() + "\n\nError : " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        resultTextView.setText(resultTextView.getText().toString() + "\n\nError : " + t.getMessage());
                    }
                });
    }

    //Edit
    private void editContacts(CrudKontak contactPhoneRestApi) {
        PhoneBook phoneBook = new PhoneBook("Tes Data Azwar", "alamat testx", "08654321", "testx@test.com");
        phoneBook.setVersion(tempPhoneBook.getVersion());
        contactPhoneRestApi.updateContact(tempPhoneBook.getId(), phoneBook)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getMessage().equals("OK")) {
                                JsonObject jsonObject = gson.toJsonTree(response.body().getResult()).getAsJsonObject();
                                PhoneBook phoneBook = gson.fromJson(jsonObject.toString(), PhoneBook.class);
                                resultTextView.setText(resultTextView.getText().toString() + "\n\n" + phoneBook.toString());
                            } else {
                                resultTextView.setText(resultTextView.getText().toString() + "\n\nError : " + response.body().getResult().toString());
                            }
                        } else {
                            try {
                                resultTextView.setText(resultTextView.getText().toString() + "\n\nError : " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        resultTextView.setText(resultTextView.getText().toString() + "\n\nError : " + t.getMessage());
                    }
                });
    }

    private void deleteContacts(CrudKontak contactPhoneRestApi) {
        contactPhoneRestApi.deleteContact(tempPhoneBook.getId())
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            resultTextView.setText(resultTextView.getText().toString() + "\n\n" + response.body().getResult().toString());
                        } else {
                            try {
                                resultTextView.setText(resultTextView.getText().toString() + "\n\nError : " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        resultTextView.setText(resultTextView.getText().toString() + "\n\nError : " + t.getMessage());
                    }
                });
    }
}
