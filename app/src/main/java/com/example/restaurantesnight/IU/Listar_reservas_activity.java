package com.example.restaurantesnight.IU;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantesnight.CORE.SqlIO;
import com.example.restaurantesnight.R;

public class Listar_reservas_activity extends AppCompatActivity {
    private SimpleCursorAdapter cursorAdapter;
    private SqlIO sqlIO;

    @Override
    public void onStart() {
        super.onStart();
        this.sqlIO = new SqlIO(this.getApplicationContext());
        final ListView LV_RESERVAS = (ListView) this.findViewById(R.id.lvreservas_Mesa);
        final String id_mesa= getIntent().getStringExtra("Mesa");
        //Creamos el cursor Adapter pasándole list_reservas
        this.cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.reservas_out,
                null,
                new String[]{SqlIO.RESERVA_TITULAR, SqlIO.RESERVA_EMAIL, SqlIO.RESERVA_MENU, SqlIO.RESERVA_HORARIO_INICIO,
                        SqlIO.RESERVA_HORARIO_FIN},
                new int[]{R.id.titular_lvreservas, R.id.email_lvreservas, R.id.menu_lvreservas, R.id.inicio_lvreservas,
                        R.id.fin_lvreservas},
                0
        );
        LV_RESERVAS.setAdapter(cursorAdapter);
        this.actualiza(id_mesa);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.list_reservas);

    }
    public void onPause() {

        super.onPause();
    }

    //Actualiza el contenido del cursor
    private void actualiza(String id_mesa)
    {
        this.cursorAdapter.swapCursor(
                this.sqlIO.getCursorReservas(id_mesa) );
    }
}
