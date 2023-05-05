package com.example.appfacturasn.IO;


import com.example.appfacturasn.IO.response.FacturasVO; // Importamos la clase FacturasVO  para usarla en la respuesta

import java.util.ArrayList;

import retrofit2.Call;   //Importamos esta clase para recibir la respuesta de la API
import retrofit2.http.GET; //Importamos GET para usarlo en la solictud HTTP

public interface ApiService {

    //Obtenemos un objeto FacturasVO utilizando nua solicitud HTTP GET

    @GET("facturas")
    Call<FacturasVO> getObjetoFacturasVO();   //Nos devuelve el objeto creado


}
