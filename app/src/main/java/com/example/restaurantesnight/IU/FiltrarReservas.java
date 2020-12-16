package com.example.restaurantesnight.IU;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantesnight.CORE.SqlIO;
import com.example.restaurantesnight.R;

public class FiltrarReservas  extends AppCompatActivity {
    private ListView LV_RESERVAS;
    private SimpleCursorAdapter cursorAdapter;
    private SqlIO sqlIO;

    @Override
    public void onStart() {
        super.onStart();
        this.sqlIO = new SqlIO(this.getApplicationContext());
        LV_RESERVAS = (ListView) this.findViewById(R.id.lvMesas_reserva_total);
        //Creamos el cursor Adapter pasándole list_reservas
        this.cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.filtro_reservas,
                null,
                new String[]{SqlIO.RESERVA_TITULAR, SqlIO.RESERVA_EMAIL, SqlIO.RESERVA_MENU, SqlIO.RESERVA_HORARIO_INICIO,
                        SqlIO.RESERVA_HORARIO_FIN, SqlIO.MESAS_ID},
                new int[]{R.id.titular_lvreservas, R.id.email_lvreservas, R.id.menu_lvreservas, R.id.inicio_lvreservas,
                        R.id.fin_lvreservas, R.id.id_mesa_reserva},
                0
        );
        LV_RESERVAS.setAdapter(cursorAdapter);
        this.actualizaInicio();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_filtrar_reservas);
        Button btn_actualizar= (Button) this.findViewById(R.id.btn_Actualizar);
        LV_RESERVAS = (ListView) this.findViewById(R.id.lvMesas_reserva_total);
        //CONFIGURAMOS EL BOTON PARA QUE ACTUALICE LAS RESERVAS
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FiltrarReservas.this.sqlIO.eliminar_Fechas();
                FiltrarReservas.this.actualizaInicio();
                Toast.makeText(getApplicationContext(),"Se han actualizado las reservas de esta mesa correctamente",Toast.LENGTH_LONG).show();
            }
        });


    }
    @Override
    public void onResume(){
        //Actualizamos las reservas y eliminamos las que ya han pasado el tiempo de la mesa correspondiente
        super.onResume();
        this.sqlIO.eliminar_Fechas();
        this.actualizaInicio();
    }
    public void onPause() {

        super.onPause();
        this.sqlIO.close();
        this.cursorAdapter.getCursor().close();
    }

    //Actualiza el contenido del cursor
    private void actualizaInicio()
    {
        this.cursorAdapter.swapCursor(
                this.sqlIO.getCursorReservasTotal() );
    }
    //Registramos el menú que aparecerá en la gestión de mesas
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );

        this.getMenuInflater().inflate( R.menu.menu_reservas, menu );
        return true;
    }
    //Configuramos las distintas opciones del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // gracias a la id, sabemos que item es el que se oprime, en este caso usamos un switch
        switch (item.getItemId())
        {
            case R.id.menu_filtrar_titular:
                return true;
            case R.id.menu_filtrar_Correo:
                return true;
            case R.id.menu_filtrar_Dia:
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
