package com.example.appfacturasn;

import java.util.HashMap;

public class Filtro {
    private String fechaMax;
    private String fechaMin;
    private double maxValueSlider;

    private HashMap<String, Boolean> estado = new HashMap<>();

    public Filtro(String fechaMax, String fechaMin, double maxValueSlider, HashMap<String, Boolean> estado) {
        this.fechaMax = fechaMax;
        this.fechaMin = fechaMin;
        this.maxValueSlider = maxValueSlider;
        this.estado = estado;
    }

    public String getFechaMax() {
        return fechaMax;
    }

    public void setFechaMax(String fechaMax) {
        this.fechaMax = fechaMax;
    }

    public String getFechaMin() {
        return fechaMin;
    }

    public void setFechaMin(String fechaMin) {
        this.fechaMin = fechaMin;
    }

    public double getMaxValueSlider() {
        return maxValueSlider;
    }

    public void setMaxValueSlider(double maxValueSlider) {
        this.maxValueSlider = maxValueSlider;
    }

    public HashMap<String, Boolean> getEstado() {
        return estado;
    }

    public void setEstado(HashMap<String, Boolean> estado) {
        this.estado = estado;
    }
}
