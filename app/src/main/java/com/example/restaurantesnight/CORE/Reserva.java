package com.example.restaurantesnight.CORE;

import java.util.Calendar;

public class Reserva {
    private String titular;
    private String email;
    private String menu;
    private Calendar horario_inicio;
    private Calendar horario_fin;
    private int mesa;

    public Reserva(String titular, String email, String menu, Calendar horario_ini,Calendar horario_fin, int mesa) {
        this.titular = titular;
        this.email = email;
        this.menu = menu;
        this.horario_inicio = horario_ini;
        this.horario_fin = horario_fin;
        this.mesa = mesa;
    }


    public String getTitular() {
        return titular;
    }

    public String getEmail() {
        return email;
    }

    public String getMenu() {
        return menu;
    }


    public Calendar getHorario_inicio() {
        return horario_inicio;
    }

    public Calendar getHorario_fin() {
        return horario_fin;
    }

    public int getMesa() {
        return mesa;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setHorario_inicio(Calendar horario_inicio) {
        this.horario_inicio = horario_inicio;
    }

    public void setHorario_fin(Calendar horario_fin) {
        this.horario_fin = horario_fin;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }
}
