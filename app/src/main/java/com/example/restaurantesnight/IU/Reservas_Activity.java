package com.example.restaurantesnight.IU;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantesnight.CORE.SqlIO;
import com.example.restaurantesnight.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reservas_Activity extends AppCompatActivity {
    private ListView LV_MESAS;
    private SimpleCursorAdapter cursorAdapter;
    private SqlIO sqlIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_reservas);
        this.sqlIO = new SqlIO( this.getApplicationContext() );
        LV_MESAS = (ListView) this.findViewById( R.id.lvMesas_reserva );

        // INICIALIZAR VISTAS

        //Creamos el cursor Adapter pasándole list_mesas
        this.cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.list_mesas,
                null,
                new String[] { SqlIO.MESAS_ID, SqlIO.MESAS_CAPACIDAD },
                new int[] { R.id.id_mesa, R.id.num_plazas },
                0
        );
        LV_MESAS.setAdapter( cursorAdapter );
        //Registramos el menu contextual
        this.registerForContextMenu(LV_MESAS);
        this.actualiza();
    }
    //Actualiza el contenido del cursor
    private void actualiza()
    {
        this.cursorAdapter.swapCursor(
                this.sqlIO.getCursorMesas() );
    }
    //Crea un dialógo para insertar una reserva en la mesa
    private void inserta()
    {
        final AlertDialog.Builder DLG = new AlertDialog.Builder( this );
        final View customLayout = getLayoutInflater().inflate(R.layout.entrada_reserva, null);
        final Button btn_fecha= (Button)  customLayout.findViewById(R.id.btn_fecha);
        final Button btn_hora= (Button)  customLayout.findViewById(R.id.btn_hora);
        final TextView txt_fecha= (TextView)  customLayout.findViewById(R.id.txt_fecha);
        final TextView txt_hora= (TextView)  customLayout.findViewById(R.id.txt_hora);
        final Button btn_fechaFin= (Button)  customLayout.findViewById(R.id.btn_fechaFin);
        final Button btn_horaFin= (Button)  customLayout.findViewById(R.id.btn_horaFin);
        final TextView txt_fechaFin= (TextView)  customLayout.findViewById(R.id.txt_fechaFin);
        final TextView txt_horaFin= (TextView)  customLayout.findViewById(R.id.txt_horaFin);
        //Definimos los eventos de los botones
        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final Calendar c= Calendar.getInstance();
                    int anho=c.get(Calendar.YEAR);
                    int mes=c.get(Calendar.MONTH);
                    int dia=c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Reservas_Activity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            txt_fecha.setText((dayOfMonth+"/"+ (month+1)+ "/"+year));
                        }
                    },anho,mes,dia);
                    datePickerDialog.show();
                }
        });
        btn_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c= Calendar.getInstance();
                int hora=c.get(Calendar.HOUR_OF_DAY);
                int minutos=c.get(Calendar.MINUTE);
                TimePickerDialog datePickerDialog = new TimePickerDialog(Reservas_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        txt_hora.setText((hour+":"+minute));
                    }
                },hora,minutos,false);
                datePickerDialog.show();
            }
        });
        btn_fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c= Calendar.getInstance();
                int anho=c.get(Calendar.YEAR);
                int mes=c.get(Calendar.MONTH);
                int dia=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Reservas_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txt_fechaFin.setText((dayOfMonth+"/"+ (month+1)+ "/"+year));
                    }
                },anho,mes,dia);
                datePickerDialog.show();
            }
        });
        btn_horaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c= Calendar.getInstance();
                int hora=c.get(Calendar.HOUR_OF_DAY);
                int minutos=c.get(Calendar.MINUTE);
                TimePickerDialog datePickerDialog = new TimePickerDialog(Reservas_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        txt_horaFin.setText((hour+":"+minute));
                    }
                },hora,minutos,false);
                datePickerDialog.show();
            }
        });
        DLG.setTitle( "Nueva Reserva" );
        DLG.setView(customLayout);
        DLG.setPositiveButton("Guarda", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {


                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        });

        DLG.create().show();
    }
    //Funcion para crear el menu contextual
    @Override
    public void onCreateContextMenu(ContextMenu contxt, View v, ContextMenu.ContextMenuInfo cmi)
    {
        super.onCreateContextMenu(contxt, v, cmi);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual_reservas, contxt);

    }
    //Funcion para realizar distintas acciones dependiendo de la opción que se escoga en el menu contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //cogemos la posicion donde salta el menu
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //cogemos el linear layout de esa posicion y accedemos a sus elementos
        LinearLayout l_mesa= (LinearLayout) LV_MESAS.getChildAt(info.position);
        final TextView ID_MESA=l_mesa.findViewById(R.id.id_mesa);
        final TextView NUM_PLAZAS=l_mesa.findViewById(R.id.num_plazas);
        int id = Integer.parseInt(ID_MESA.getText().toString());
        try {


            switch (item.getItemId()) {
                case R.id.contx_anhadir_reserva:
                    this.inserta();
                    return true;

                case R.id.contx_eliminar_reserva:
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            return true;
        }
    }
}