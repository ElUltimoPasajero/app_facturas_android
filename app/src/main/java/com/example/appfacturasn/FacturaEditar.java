package com.example.appfacturasn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FacturaEditar extends AppCompatActivity implements View.OnClickListener{

    private String fecha;
    private String importe;
    private String estado;

   Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura_editar);

        // Obtiene la referencia del botón de volver

        volver =(Button)findViewById(R.id.button2);

        // Establecer un listener para el botón de volver

        volver.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:

                // Crear un intent para volver a la actividad principal (MainActivity)


                Intent intent2 = new Intent(FacturaEditar.this, MainActivity.class);

                // Iniciar la actividad MainActivity

                startActivity(intent2);
                break;
        }
    }

}