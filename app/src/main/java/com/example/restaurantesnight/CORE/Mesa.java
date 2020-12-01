package com.example.restaurantesnight.CORE;

//Clase que Mesa
public class Mesa {
    //Constructor se le pasará un número de mesa y el número de plazas
    public Mesa(int n,int p)
    {
        this.num = n;
        this.plazas = p;
    }
    public int getNumero()
    {
        return this.num;
    }
    public int getPlazas()
    {
        return this.plazas;
    }
    public String toString()
    {
        StringBuilder toret= new StringBuilder();
        toret.append("Número de Mesa : " +this.getNumero());
        toret.append("\n");
        toret.append("Número de Plazas : " +this.getPlazas());
        return toret.toString();
    }
    private int num;
    private int plazas;
}

