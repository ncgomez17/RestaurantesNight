package com.example.restaurantesnight.IU;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantesnight.CORE.SqlIO;
import com.example.restaurantesnight.R;

import java.util.ArrayList;
import java.util.List;

public class Mesas_activity extends AppCompatActivity {
    private ListView LV_MESAS;
    private SimpleCursorAdapter cursorAdapter;
    private SqlIO sqlIO;

    @Override
    public void onStart() {
        super.onStart();
        this.sqlIO = new SqlIO( this.getApplicationContext() );
        final ListView LV_MESAS = (ListView) this.findViewById( R.id.lvMesas );
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
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mesas_activity);
        // INICIALIZAR ATRIBUTOS

        LV_MESAS = (ListView) this.findViewById( R.id.lvMesas );
        final Button BT_INSERTA = (Button) this.findViewById( R.id.btAnhadirMesas );
        // INICIALIZAR VISTAS
        BT_INSERTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mesas_activity.this.inserta();
            }
        });

    }
    @Override
    public void onPause() {

        super.onPause();
        this.sqlIO.close();
        this.cursorAdapter.getCursor().close();
    }
//Actualiza el contenido del cursor
    private void actualiza()
    {
        this.cursorAdapter.swapCursor(
                this.sqlIO.getCursorMesas() );
    }
//Crea un dialógo para insertar una mensaje con sus dos atributos
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
    //Crea un Diálogo para eliminar una mesa indicandole el id de esta
    private void elimina_mesa()
    {
        final AlertDialog.Builder DLG = new AlertDialog.Builder( this );
        final EditText id_mesa = new EditText( this );
        DLG.setTitle( "Eliminar Mesa" );
        DLG.setMessage("Indica el numero de mesa");
        DLG.setView(id_mesa);
        DLG.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    int id = Integer.parseInt(id_mesa.getText().toString());
                    Mesas_activity.this.sqlIO.eliminar_Mesa(id);
                    Mesas_activity.this.actualiza();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
                    DLG.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

        DLG.create().show();
    }
    //Crea un diálogo en el que se pide el numero de plazas para modificar mesas, se le pasa como parametro el id de la mesa
    private void cambiar_plazas(int id)
    {
        final AlertDialog.Builder DLG = new AlertDialog.Builder( this );
        final EditText plazas = new EditText( this );
        DLG.setTitle( "Cambiar Plazas" );
        DLG.setMessage("Indica el numero de plazas que tendrá");
        DLG.setView(plazas);
        DLG.setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    int num_plazas = Integer.parseInt(plazas.getText().toString());
                    Mesas_activity.this.sqlIO.cambiar_plazas(id,num_plazas);
                    Mesas_activity.this.actualiza();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        DLG.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        DLG.create().show();
    }
//Crea un diálogo que da la opción de eliminar todas las mesas
    private void elimina_todas()
    {
        final AlertDialog.Builder DLG = new AlertDialog.Builder( this );
        final EditText id_mesa = new EditText( this );
        DLG.setTitle( "Eliminar Todas las mesas" );
        DLG.setMessage("¿Estás seguro de eliminar todas las mesas?");
        DLG.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Mesas_activity.this.sqlIO.eliminar_Todas();
                    Mesas_activity.this.actualiza();
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        });
        DLG.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        DLG.create().show();
    }
    //Registramos el menú que aparecerá en la gestión de mesas
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );

        this.getMenuInflater().inflate( R.menu.menu_mesas, menu );
        return true;
    }
    //Configuramos las distintas opciones del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // gracias a la id, sabemos que item es el que se oprime, en este caso usamos un switch
        switch (item.getItemId())
        {
            case R.id.menu_anhadir_mesa:
                this.inserta();
                return true;
            case R.id.menu_eliminar_mesa:
                this.elimina_mesa();
                return true;
            case R.id.menu_eliminar_mesas:
                this.elimina_todas();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
    //Funcion para crear el menu contextual
    @Override
    public void onCreateContextMenu(ContextMenu contxt, View v, ContextMenu.ContextMenuInfo cmi)
    {
            super.onCreateContextMenu(contxt, v, cmi);
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_contextual_mesas, contxt);

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
                case R.id.contx_eliminar_mesa:
                    Mesas_activity.this.sqlIO.eliminar_Mesa(id);
                    this.actualiza();
                    return true;

                case R.id.contx_cambiar_plazas:
                    this.cambiar_plazas(id);
                    this.actualiza();
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