package com.example.appfacturasn.IO.response;


import java.util.List;

public class FacturasVO {

    private int numFacturas;

    private List<Factura> facturas;

    public FacturasVO(int numFacturas, List<Factura> facturas) {
        this.numFacturas = numFacturas;
        this.facturas = facturas;
    }


    public void setNumFacturas(int numFacturas) {
        this.numFacturas = numFacturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public int getNumFacturas() {
        return numFacturas;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public class Factura {

        private String descEstado;
        private float importeOrdenacion;
        private String fecha;

        public String getDescEstado() {
            return descEstado;
        }

        public void setDescEstado(String descEstado) {
            this.descEstado = descEstado;
        }

        public float getImporteOrdenacion() {
            return importeOrdenacion;
        }

        public void setImporteOrdenacion(float importeOrdenacion) {
            this.importeOrdenacion = importeOrdenacion;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
    }
}

