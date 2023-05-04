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


    @Override
    public FacturasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_factura, parent, false);
        return new FacturasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FacturasViewHolder facturasViewHolder, int position) {
        FacturasVO.Factura facturaVa = facturaList.get(position);
        facturasViewHolder.tvFecha.setText(facturaVa.getFecha());
        facturasViewHolder.tvEstado.setText(facturaVa.getDescEstado());
        float importeOrdenacion = facturaVa.getImporteOrdenacion();
        facturasViewHolder.getTvImporteOrdenacion().setText(String.format("%.2f", importeOrdenacion));


            if(facturaList.get(position).getDescEstado().equals("Pendiente de pago")){

                facturasViewHolder.tvEstado.setTextColor(Color.RED);

            }else {
                facturasViewHolder.tvEstado.setTextColor(Color.BLUE);
            }

        }




    @Override
    public int getItemCount() {
        return facturaList.size();
    }

    public class FacturasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
            tvEstado = (TextView) itemView.findViewById(R.id.tvEstado);
            tvImporteOrdenacion = (TextView) itemView.findViewById(R.id.tvImporteOrdenacion);
            mDialog = new Dialog (itemView.getContext());


        }



        public void onClick(View v) {
            mDialog.setContentView(R.layout.mainpopup);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView mensajePopup = mDialog.findViewById(R.id.mensajePopup);
            mensajePopup.setText("Esta informacion aun no esta disponible");
            mDialog.show();
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
