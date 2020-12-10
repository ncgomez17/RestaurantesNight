package com.example.restaurantesnight.IU;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantesnight.CORE.SqlIO;
import com.example.restaurantesnight.R;

public class ListarReservasActivity extends AppCompatActivity {
    private SimpleCursorAdapter cursorAdapter;
    private ListView LV_RESERVAS;
    private SqlIO sqlIO;

    @Override
    public void onStart() {
        super.onStart();
        this.sqlIO = new SqlIO(this.getApplicationContext());
        LV_RESERVAS = (ListView) this.findViewById(R.id.lvreservas_Mesa);
        final int id_mesa= Integer.parseInt(getIntent().getStringExtra("Mesa"));
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

        LV_RESERVAS = (ListView) this.findViewById(R.id.lvreservas_Mesa);
        int id_m=Integer.parseInt(getIntent().getStringExtra("Mesa"));
        LV_RESERVAS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(ListarReservasActivity.this);
                LinearLayout l1= (LinearLayout) LV_RESERVAS.getChildAt(pos);
                TextView titular= l1.findViewById(R.id.titular_lvreservas);
                adb.setTitle("Eliminar Reserva");
                adb.setMessage("¿Estas seguro de eliminar esta reserva?");
                adb.setNegativeButton("Cancelar", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ListarReservasActivity.this.sqlIO.eliminar_Reserva((titular.getText().toString()));;
                        ListarReservasActivity.this.actualiza(id_m);

                    }});
                adb.show();
                return true;
            }
        });


    }
    public void onPause() {

        super.onPause();
        this.sqlIO.close();
        this.cursorAdapter.getCursor().close();
    }

    //Actualiza el contenido del cursor
    private void actualiza(int id_mesa)
    {
        this.cursorAdapter.swapCursor(
                this.sqlIO.getCursorReservas(id_mesa) );
    }



}
