package com.example.restaurantesnight.IU;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.restaurantesnight.CORE.Mesa;
import com.example.restaurantesnight.CORE.SqlIO;
import com.example.restaurantesnight.R;

import java.util.ArrayList;
import java.util.List;

public class Mesas_activity extends Activity {
   private List<Mesa> array_mesas;
    private ArrayAdapter<String> itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mesas_activity);
        // Inicializar atributos
        this.sqlIO = new SqlIO( this.getApplicationContext() );

        // Inicializar vistas
        final ListView LV_MESAS = (ListView) this.findViewById( R.id.lvMesas );
        final Button BT_INSERTA = (Button) this.findViewById( R.id.btAnhadirMesas );
        this.array_mesas= new ArrayList<>();

        BT_INSERTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Mesas_activity.this.inserta();
            }
        });

        this.cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.list_mesas,
                null,
                new String[] { SqlIO.MESAS_ID, SqlIO.MESAS_CAPACIDAD },
                new int[] { R.id.id_mesa, R.id.num_plazas },
                0
        );

        LV_MESAS.setAdapter( cursorAdapter );

        this.actualiza();
    }

    private void actualiza()
    {
        this.cursorAdapter.swapCursor(
                this.sqlIO.getCursorMesas() );
    }

    private void inserta()
    {
        final AlertDialog.Builder DLG = new AlertDialog.Builder( this );
         final View customLayout = getLayoutInflater().inflate(R.layout.entrada_mesa, null);
         DLG.setTitle( "Nueva Mesa" );
         DLG.setView(customLayout);
         DLG.setPositiveButton("Guarda", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    EditText ED_ID = customLayout.findViewById(R.id.id_mesa);
                    EditText ED_PLAZAS = customLayout.findViewById(R.id.num_plazas);
                    int id = Integer.parseInt(ED_ID.getText().toString());
                    int plazas = Integer.parseInt(ED_PLAZAS.getText().toString());
                    Mesas_activity.this.sqlIO.inserta_Mesa(id,plazas);
                    Mesas_activity.this.actualiza();
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        });

        DLG.create().show();
    }

    private SimpleCursorAdapter cursorAdapter;
    private SqlIO sqlIO;
}