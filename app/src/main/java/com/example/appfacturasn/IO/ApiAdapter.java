package com.example.appfacturasn.IO;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiAdapter {

    //Aqui almacenamos la instancia de la Interfaz APISERVICE

    private static ApiService API_SERVICE;


    //Metodo con el cual obtenemos la instancia de la interfaz
    public static ApiService getApiService() {

        //Creamos un cliente con el interceptor del reigistro

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //Estalecimiento de la URL

        String baseUrl = "https://viewnextandroid2.wiremockapi.cloud/";

        //Si la instancia es nula, creamos una nueva con Retrofit

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()

                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            // Crear una instancia de la interfaz ApiService con Retrofit

            API_SERVICE = retrofit.create(ApiService.class);
        }

        //Devolvemos la instancia

        return API_SERVICE;


    }

}
