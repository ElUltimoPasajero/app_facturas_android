package com.example.appfacturasn;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfacturasn.IO.response.FacturasVO;

import java.util.List;

public class FacturasAdaptador extends RecyclerView.Adapter<FacturasAdaptador.FacturasViewHolder> {


    Dialog mDialog;

    private List<FacturasVO.Factura> facturaList;

    public FacturasAdaptador(List<FacturasVO.Factura> facturas) {

        this.facturaList = facturas;
    }

    //Metodo para crear el ViewHolder

    @Override
    public FacturasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_factura, parent, false);
        return new FacturasViewHolder(v);
    }

    // Método para vincular los datos a cada elemento de la lista


    @Override
    public void onBindViewHolder(@NonNull FacturasViewHolder facturasViewHolder, int position) {
        FacturasVO.Factura facturaVa = facturaList.get(position);
        facturasViewHolder.tvFecha.setText(facturaVa.getFecha());
        facturasViewHolder.tvEstado.setText(facturaVa.getDescEstado());
        float importeOrdenacion = facturaVa.getImporteOrdenacion();
        facturasViewHolder.getTvImporteOrdenacion().setText(String.format("%.2f", importeOrdenacion));

        //Aqui cambiamos elc color del texto segun el estado de la factura


            if(facturaList.get(position).getDescEstado().equals("Pendiente de pago")){

                facturasViewHolder.tvEstado.setTextColor(Color.RED);

            }else {
                facturasViewHolder.tvEstado.setTextColor(Color.BLUE);
            }

        }





        //Obtenemos la cantidad de elementos de la factura
    @Override
    public int getItemCount() {
        return facturaList.size();
    }

    // Clase ViewHolder que representa cada elemento de la lista


    public class FacturasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Obtener las referencias a los elementos de la vista


        private TextView tvEstado;
        private TextView tvFecha;
        private TextView tvImporteOrdenacion;

        Dialog mDialog;

        public TextView getTvEstado() {
            return tvEstado;
        }

        public void setTvEstado(TextView tvEstado) {
            this.tvEstado = tvEstado;
        }

        public TextView getTvFecha() {
            return tvFecha;
        }

        public void setTvFecha(TextView tvFecha) {
            this.tvFecha = tvFecha;
        }

        public TextView getTvImporteOrdenacion() {
            return tvImporteOrdenacion;
        }

        public void setTvImporteOrdenacion(TextView tvImporteOrdenacion) {
            this.tvImporteOrdenacion = tvImporteOrdenacion;
        }

        public FacturasViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            // Obtiene las referencias a los TextView en la vista del elemento

            tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
            tvEstado = (TextView) itemView.findViewById(R.id.tvEstado);
            tvImporteOrdenacion = (TextView) itemView.findViewById(R.id.tvImporteOrdenacion);

            // Crea una nueva instancia de Dialog y establece su vista

            mDialog = new Dialog (itemView.getContext());


        }


        // Establece la vista de contenido del diálogo con el diseño personalizado

        public void onClick(View v) {
            mDialog.setContentView(R.layout.mainpopup);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // Encuentra el TextView en el diseño del diálogo y establece su texto

            TextView mensajePopup = mDialog.findViewById(R.id.mensajePopup);
            mensajePopup.setText("Esta informacion aun no esta disponible");

            // Muestra el diálogo

            mDialog.show();

            // Encuentra el botón "Cerrar" en el diseño del diálogo y establece su listener de clic

            Button cerrarButton = mDialog.findViewById(R.id.buttonCerrar);
            cerrarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss(); // Cierra el diálogo al pulsar el botón "Cerrar"
                }
            });

        }

    }


}
