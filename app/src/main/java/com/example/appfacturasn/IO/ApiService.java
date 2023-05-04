package com.example.appfacturasn.IO;


import com.example.appfacturasn.IO.response.FacturasVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("facturas")
    Call<FacturasVO> getObjetoFacturasVO();


}
