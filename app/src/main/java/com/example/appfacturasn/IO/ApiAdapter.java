package com.example.appfacturasn.IO;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiAdapter {

    private static ApiService API_SERVICE;

    public static ApiService getApiService(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient= new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        String baseUrl ="https://viewnextandroid.mocklab.io/";

        if(API_SERVICE==null){
            Retrofit retrofit = new Retrofit.Builder()

                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            API_SERVICE = retrofit.create(ApiService.class);




        }
        return API_SERVICE;


    }

}
