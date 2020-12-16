package com.example.restaurantesnight.IU;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restaurantesnight.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Button bt_mesas=(Button) this.findViewById(R.id.btn_gestionMesas);
        Button bt_reservas=(Button) this.findViewById(R.id.btn_gestionReservas);
        Button bt_listar_reservas=(Button) this.findViewById(R.id.btn_verReservas);
        bt_mesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent (MainActivity.this, MesasActivity.class);
                startActivity(intent);

            }
        });
        bt_reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent2 = new Intent (MainActivity.this, ReservasActivity.class);
                startActivity(intent2);
            }
        });
        bt_listar_reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent3 = new Intent (MainActivity.this, FiltrarReservas.class);
                startActivity(intent3);
            }
        });
    }

}