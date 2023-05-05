package com.example.appfacturasn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appfacturasn.IO.ApiAdapter;
import com.example.appfacturasn.IO.response.FacturasVO;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements Callback<FacturasVO> {
    private List<FacturasVO.Factura> facturaList;
    private RecyclerView rvList;
    private FacturasAdaptador facturasAdaptador;
    public static Double maxImporte = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MenuHost menu = this;

        menu.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()) {

                    case R.id.menuFiltros:
                        Intent intent = new Intent(MainActivity.this, ActividadFiltrar.class);
                        startActivity(intent);
                        return true;
                }

                return false;
            }
        });

        rvList = findViewById(R.id.rvLista);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(linearLayoutManager);

        facturaList = new ArrayList<>();
        facturasAdaptador = new FacturasAdaptador(facturaList);
        rvList.setAdapter(facturasAdaptador);

        Call<FacturasVO> call = ApiAdapter.getApiService().getObjetoFacturasVO();
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<FacturasVO> call, Response<FacturasVO> response) {
        if (response.isSuccessful()) {

            FacturasVO facturas = response.body();
            facturaList = facturas.getFacturas();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                maxImporte = Double.valueOf(facturaList.stream().max(Comparator.comparing(FacturasVO.Factura::getImporteOrdenacion)).get().getImporteOrdenacion());
            }

            String datosRecibidos = getIntent().getStringExtra("filtro");

            if (datosRecibidos != null) {
                FiltroFacturas filtro = new Gson().fromJson(datosRecibidos, FiltroFacturas.class);

                List<FacturasVO.Factura> listFiltro = facturaList;

                listFiltro = chequearFiltroEstados(filtro.getEstado(), listFiltro);
                listFiltro = chequearFiltroFechas(filtro.getFechaMax(), filtro.getFechaMin(), listFiltro);
                listFiltro = chekearBarraImporte(filtro.getMaxValueSlider(), listFiltro);

                if (listFiltro.isEmpty()) {

                    mostrarMensajeFiltroVacio();

                }
                facturaList = listFiltro;
            }

            facturasAdaptador = new FacturasAdaptador(facturaList);
            rvList.setAdapter(facturasAdaptador);

        }

    }


    @Override
    public void onFailure(Call<FacturasVO> call, Throwable t) {

    }

    private List<FacturasVO.Factura> chequearFiltroEstados(HashMap<String, Boolean> estado, List<FacturasVO.Factura> listFiltro) {
        boolean pagada = estado.get("pagada");
        boolean anulada = estado.get("anulada");
        boolean cuotaFija = estado.get("cuotaFija");
        boolean pendienteDePago = estado.get("pendienteDePago");
        boolean planDePago = estado.get("planDePago");
        List<FacturasVO.Factura> listFiltro2 = new ArrayList<>();

        if (pagada || anulada || cuotaFija || pendienteDePago || planDePago) {
            for (FacturasVO.Factura factura : listFiltro) {
                if (factura.getDescEstado().equals("Pagada") && pagada) {
                    listFiltro2.add(factura);
                }
                if (factura.getDescEstado().equals("Pendiente de pago") && pendienteDePago) {
                    listFiltro2.add(factura);
                }
                if (factura.getDescEstado().equals("Anulada") && anulada) {
                    listFiltro2.add(factura);
                }
                if (factura.getDescEstado().equals("Cuota fija") && cuotaFija) {
                    listFiltro2.add(factura);
                }
                if (factura.getDescEstado().equals("Plan de pago") && planDePago) {
                    listFiltro2.add(factura);
                }
            }
            listFiltro = listFiltro2;
        }
        return listFiltro;
    }

    private List<FacturasVO.Factura> chequearFiltroFechas(String fechaMax, String fechaMin, List<FacturasVO.Factura> listFiltro) {

        List<FacturasVO.Factura> facturasFiltradas = new ArrayList<>();

        if (!fechaMin.equals("dia/mes/año") && !fechaMax.equals("dia/mes/año")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyyy");
            Date fechaMinDate = null;
            Date fechaMaxDate = null;

            try {
                fechaMinDate = sdf.parse(fechaMin);
                fechaMaxDate = sdf.parse(fechaMax);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            for (FacturasVO.Factura factura : listFiltro) {
                Date fechaFactura = null;
                try {
                    fechaFactura = sdf.parse(factura.getFecha());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (fechaFactura.after(fechaMinDate) && fechaFactura.before(fechaMaxDate)) {
                    facturasFiltradas.add(factura);
                }
            }
            listFiltro = facturasFiltradas;
        }
        return listFiltro;
    }

    private List<FacturasVO.Factura> chekearBarraImporte(Double importeFiltro, List<FacturasVO.Factura> listFiltro) {

        List<FacturasVO.Factura> listFiltroSeekBar = new ArrayList<>();

        for (FacturasVO.Factura factura : listFiltro) {

            if (factura.getImporteOrdenacion() < importeFiltro) {
                listFiltroSeekBar.add(factura);
            }
            listFiltro = listFiltroSeekBar;

        }
        return listFiltro;
    }

    private void mostrarMensajeFiltroVacio() {

        TextView textView = new TextView(MainActivity.this);
        textView.setText("Aqui no hay nada");
        textView.setTextSize(30);
        textView.setVisibility(View.INVISIBLE);

        textView.setVisibility(View.VISIBLE);
        RelativeLayout layout = new RelativeLayout(MainActivity.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(textView, params);
        setContentView(layout);


    }
}