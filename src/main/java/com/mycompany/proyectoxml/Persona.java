package com.mycompany.proyectoxml;

public class Persona {
    private String nombre;
    private double altura;
    private double peso;

    public Persona(String nombre, double altura, double peso) {
        this.nombre = nombre;
        this.altura = altura;
        this.peso = peso;
    }

    public String getNombre() { return nombre; }
    public double getAltura() { return altura; }
    public double getPeso() { return peso; }
}
