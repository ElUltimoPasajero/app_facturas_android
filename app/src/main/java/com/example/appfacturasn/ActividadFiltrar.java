package com.example.appfacturasn;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;


public class ActividadFiltrar extends AppCompatActivity {

    private ActividadFiltrar instance = this;
    private SeekBar importeSeekBar;
    Context context = this;
    DatePickerDialog datePickerDialog;
    private int valorActualSeekbar = 0;

    MenuHost menu = this;
    private Activity ActividadFiltrar = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_filtrar);

        menu.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.volver, menu);
            }


            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.vuelta:
                        Intent intent = new Intent(ActividadFiltrar, MainActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

         TextView valorSeekBar = (TextView) findViewById(R.id.cifraSlider);

        int valorRealMax = MainActivity.maxImporte.intValue() + 1;

        importeSeekBar = findViewById(R.id.sliderImporte);
        importeSeekBar.setMax(valorRealMax);
        Log.d("Valor Seekbar", "" + valorRealMax);
        importeSeekBar.setProgress(valorRealMax);
        valorSeekBar.setText(String.valueOf(valorRealMax));
        valorActualSeekbar = valorRealMax;


        importeSeekBar.setMax((valorRealMax));

        importeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                TextView importeTextview = findViewById(R.id.cifraSlider);

                importeTextview.setText(String.valueOf(progress));
                valorActualSeekbar = progress;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button botonFiltrar = findViewById(R.id.buttonAplicar);

        CheckBox checkBoxPagada = (CheckBox) findViewById(R.id.checkBoxPagada);
        CheckBox checkBoxAnulada = (CheckBox) findViewById(R.id.checkBoxAnulada);
        CheckBox checkBoxCuotaFija = (CheckBox) findViewById(R.id.checkBoxCuotaFija);
        CheckBox checkBoxPendienteDePago = (CheckBox) findViewById(R.id.checkBoxPendienteDePago);
        CheckBox checkBoxPlanDePago = (CheckBox) findViewById(R.id.checkBoxPLanDePago);

        Button fechaInicio = (Button) findViewById(R.id.buttonDesde);
        Button fechaFin = (Button) findViewById(R.id.buttonHasta);


        botonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Intent intent = new Intent(instance, MainActivity.class);
                double maxValueSlider = Double.parseDouble(valorSeekBar.getText().toString());
                HashMap<String, Boolean> estado = new HashMap<>();
                estado.put("pagada", checkBoxPagada.isChecked());
                estado.put("anulada", checkBoxAnulada.isChecked());
                estado.put("cuotaFija", checkBoxCuotaFija.isChecked());
                estado.put("pendienteDePago", checkBoxPlanDePago.isChecked());
                estado.put("planDePago", checkBoxPlanDePago.isChecked());
                String fechaMin = fechaInicio.getText().toString();
                String fechaMax = fechaFin.getText().toString();
                Filtro miFiltro = new Filtro(fechaMax, fechaMin, maxValueSlider, estado);
                intent.putExtra("filtro", gson.toJson(miFiltro));
                Log.d("myIntent", intent.toString());
                startActivity(intent);
            }

        });

        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(ActividadFiltrar.this, (view, year1, monthofyear, dayofmonth) ->
                        fechaInicio.setText(dayofmonth + "/" + (monthofyear + 1) + "/" + year1), year, month, day);
                dpd.show();
            }
        });
        fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(ActividadFiltrar.this, (view, year1, monthofyear, dayofmonth) ->
                        fechaFin.setText(dayofmonth + "/" + (monthofyear + 1) + "/" + year1), year, month, day);
                dpd.show();
            }
        });

        Button resetFiltrosButton = findViewById(R.id.reinicio);
        resetFiltrosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFiltros();


            }
        });
    }

    private void resetFiltros() {
        // Restablecer valores de fecha

        Button fechaDesde = findViewById(R.id.buttonDesde);
        fechaDesde.setText("dia/mes/año");
        Button fechaHasta = findViewById(R.id.buttonHasta);
        fechaHasta.setText("dia/mes/año");

// Restablecer valor de seekBar
        SeekBar seekBar = findViewById(R.id.sliderImporte);
        int maxImporte = MainActivity.maxImporte.intValue() + 1;
        seekBar.setMax(maxImporte);
        seekBar.setProgress(maxImporte);
        TextView tvValorImporte = findViewById(R.id.cifraSlider);
        tvValorImporte.setText(String.valueOf(maxImporte));

// Restablecer valores de checkboxes
        CheckBox pagadas = findViewById(R.id.checkBoxPagada);
        pagadas.setChecked(false);
        CheckBox anuladas = findViewById(R.id.checkBoxAnulada);
        anuladas.setChecked(false);
        CheckBox cuotaFija = findViewById(R.id.checkBoxCuotaFija);
        cuotaFija.setChecked(false);
        CheckBox pendientesPago = findViewById(R.id.checkBoxPendienteDePago);
        pendientesPago.setChecked(false);
        CheckBox planPago = findViewById(R.id.checkBoxPLanDePago);
        planPago.setChecked(false);
    }
}
