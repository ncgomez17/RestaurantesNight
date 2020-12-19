package com.example.restaurantesnight.IU;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantesnight.CORE.SqlIO;
import com.example.restaurantesnight.R;

import java.util.Calendar;

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
                Toast.makeText(getApplicationContext(),"Se han actualizado todas las reservas correctamente",Toast.LENGTH_LONG).show();
            }
        });

        //CONFIGURAMOS BOTON PARA ELIMINAR LAS RESERVAS
        LV_RESERVAS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(FiltrarReservas.this);
                LinearLayout l1 = (LinearLayout) LV_RESERVAS.getChildAt(pos);
                TextView titular = l1.findViewById(R.id.titular_lvreservas);
                adb.setTitle("Eliminar Reserva");
                adb.setMessage("¿Estas seguro de eliminar esta reserva?");
                adb.setNegativeButton("Cancelar", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FiltrarReservas.this.sqlIO.eliminar_Reserva((titular.getText().toString()));
                        FiltrarReservas.this.actualizaInicio();

                    }
                });
                adb.show();
                return true;
            }
        });
    }

    //Cuando quiere filtrar las reservas por titular usamos este método
    public void onTitularMenuClick(){
        final AlertDialog.Builder DLG = new AlertDialog.Builder( this );
        final View customLayout = getLayoutInflater().inflate(R.layout.entrada_titular, null);
        DLG.setTitle( "Filtrar por titular" );
        DLG.setView(customLayout);

        DLG.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    EditText ED_TITULAR = customLayout.findViewById(R.id.titular_filtro);
                    String titular = ED_TITULAR.getText().toString();
                    FiltrarReservas.this.actualizaTitular(titular);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        });

        DLG.create().show();
    }


    //Cuando quiere filtrar las reservas por email usamos este método
    public void onCorreoMenuClick(){
        final AlertDialog.Builder DLG = new AlertDialog.Builder( this );
        final View customLayout = getLayoutInflater().inflate(R.layout.entrada_correo, null);
        DLG.setTitle( "Filtrar por correo" );
        DLG.setView(customLayout);

        DLG.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    EditText CORREO_TITULAR = customLayout.findViewById(R.id.correo_filtro);
                    String correo = CORREO_TITULAR.getText().toString();
                    FiltrarReservas.this.actualizaCorreo(correo);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        });

        DLG.create().show();
    }

    //Cuando quiere filtrar las reservas por dia usamos este método
    public void onDiaMenuClick(){
        final AlertDialog.Builder DLG = new AlertDialog.Builder( this );
        final View customLayout = getLayoutInflater().inflate(R.layout.entrada_dia, null);
        DLG.setTitle( "Filtrar por dia" );
        DLG.setView(customLayout);

        final Button btn_fecha= (Button)  customLayout.findViewById(R.id.btn_fecha_filtro);
        TextView DIA_RESERVA = customLayout.findViewById(R.id.txt_fecha_filtro);

        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c= Calendar.getInstance();
                int anho=c.get(Calendar.YEAR);
                int mes=c.get(Calendar.MONTH);
                int dia=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FiltrarReservas.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DIA_RESERVA.setText((year+"/"+ (month+1)+ "/"+dayOfMonth));
                    }
                },anho,mes,dia);
                //configuramos para que no se puedan seleccionar dias anteriores al actual y lo mostramos
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });



        DLG.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    FiltrarReservas.this.actualizaFecha(DIA_RESERVA.getText().toString());
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        });

        DLG.create().show();
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

    //Actualiza el contenido del cursor en la búsqueda por titular
    private void actualizaTitular(String titular)
    {
        this.cursorAdapter.swapCursor(
                this.sqlIO.getCursorReservasTitular(titular) );
    }

    //Actualiza el contenido del cursor en la búsqueda por email
    private void actualizaCorreo(String email)
    {
        this.cursorAdapter.swapCursor(
                this.sqlIO.getCursorReservasEmail(email) );
    }

    //Actualiza el contenido del cursor en la búsqueda por fecha
    private void actualizaFecha(String fecha)
    {
        this.cursorAdapter.swapCursor(
                this.sqlIO.getCursorReservasDia(fecha) );
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
                onTitularMenuClick();
                return true;
            case R.id.menu_filtrar_Correo:
                onCorreoMenuClick();
                return true;
            case R.id.menu_filtrar_Dia:
                onDiaMenuClick();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
