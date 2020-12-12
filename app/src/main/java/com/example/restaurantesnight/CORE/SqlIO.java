package com.example.restaurantesnight.CORE;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/*Base de datos SQlite, tendrá tablas de mesas y de usuarios */
public class SqlIO  extends SQLiteOpenHelper {
    private static final String DB_NOMBRE="RestauranteNight";
    private static final int DB_VERSION=12;
    public static final String TABLA_MESAS="Mesas";
    public static final String MESAS_ID="_id";
    public static final String MESAS_CAPACIDAD="capacidad";
    public static final String TABLA_RESERVAS="Reservas";
    public static final String RESERVA_TITULAR="Titular";
    public static final String RESERVA_EMAIL="Email";
    public static final String RESERVA_MENU="Menu";
    public static final String RESERVA_HORARIO_INICIO="Horario_Inicio";
    public static final String RESERVA_HORARIO_FIN="Horario_Fin";

    public SqlIO(Context cntxt){
        super(cntxt,DB_NOMBRE,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            Log.i( DB_NOMBRE, "Creando tablas" );
            db.beginTransaction();

            db.execSQL( "CREATE TABLE IF NOT EXISTS " + TABLA_MESAS
                    + "("
                    + MESAS_ID + " INTEGER PRIMARY KEY NOT NULL ,"
                    + MESAS_CAPACIDAD + " INTEGER NOT NULL"
                    + ")"
            );

            db.execSQL( " CREATE TABLE IF NOT EXISTS " + TABLA_RESERVAS
                    + "("
                    + RESERVA_TITULAR + " TEXT PRIMARY KEY NOT NULL ,"
                    + RESERVA_EMAIL + " TEXT NOT NULL ,"
                    + RESERVA_MENU + " TEXT  NOT NULL ,"
                    + RESERVA_HORARIO_INICIO + " TEXT NOT NULL ,"
                    + RESERVA_HORARIO_FIN + " TEXT NOT NULL ,"
                    + MESAS_ID + " INTEGER NOT NULL ,"
                    +" FOREIGN KEY " + "(" + MESAS_ID + ")" +
                    " REFERENCES " + TABLA_MESAS + " (" + MESAS_ID + ")"
                    + ")"
            );

            db.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            db.endTransaction();
        }
        return;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        try {
            Log.i( DB_NOMBRE, "Actualizando base de datos" );
            db.beginTransaction();

            db.execSQL( "DROP TABLE IF EXISTS " + TABLA_MESAS );
            db.execSQL( "DROP TABLE IF EXISTS " + TABLA_RESERVAS );
            db.setTransactionSuccessful();
        } catch(SQLException error) {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            db.endTransaction();
        }
        this.onCreate( db );
    }
    public Cursor getCursorMesas()
    {
        final SQLiteDatabase DB = this.getReadableDatabase();

        return DB.query(
                TABLA_MESAS,
                null,
                null,
                null,
                null,
                null,
                MESAS_ID
        );
    }
//FUNCION PARA INSERTAR UNA MESA COMPLETA, CON SU NUMERO DE MESA Y PLAZAS
    public void inserta_Mesa(int id,int capacidad)
    {
        final SQLiteDatabase DB = this.getWritableDatabase();
        final ContentValues VALORES = new ContentValues();

        VALORES.put( MESAS_ID, id );
        VALORES.put( MESAS_CAPACIDAD, capacidad );

        try {
            DB.beginTransaction();
            DB.insert(
                    TABLA_MESAS,
                    null,
                    VALORES
            );
            DB.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            DB.endTransaction();
        }
    }
//FUNCION PARA ELIMINAR LA MESA A PARTIR DE SU ID
    public void eliminar_Mesa(int id)
    {
        final SQLiteDatabase DB = this.getWritableDatabase();
        try {
            DB.beginTransaction();
            DB.execSQL("DELETE FROM " + TABLA_MESAS

                    + " WHERE "
                    + MESAS_ID + " = "
                    + id

            );
            DB.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            DB.endTransaction();
        }
    }
    //FUNCION PARA CAMBIAR LAS PLAZAS DE UNA MESA A PARTIR DE SU ID
    public void cambiar_plazas(int id,int plazas)
    {
        final SQLiteDatabase DB = this.getWritableDatabase();
        try {
            DB.beginTransaction();
            DB.execSQL("UPDATE " + TABLA_MESAS
                    + " SET " + MESAS_CAPACIDAD
                    + " = " + plazas
                    + " WHERE "
                    + MESAS_ID + " = "
                    + id

            );
            DB.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            DB.endTransaction();
        }
    }
//FUNCION PARA ELIMINAR TODAS LAS MESAS
    public void eliminar_Todas()
    {
        final SQLiteDatabase DB = this.getWritableDatabase();

        try {
            DB.beginTransaction();
            DB.execSQL("DELETE FROM " + TABLA_MESAS

            );
            DB.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            DB.endTransaction();
        }
    }

//FUNCION PARA INSERTAR UNA MESA COMPLETA, CON SU NUMERO DE MESA Y PLAZAS
    public void inserta_Reserva(int id_mesa, String titular,String email, String menu, String horario_inicio, String horario_fin)
    {
        final SQLiteDatabase DB = this.getWritableDatabase();
        final ContentValues VALORES = new ContentValues();

        VALORES.put( MESAS_ID, id_mesa );
        VALORES.put( RESERVA_TITULAR, titular );
        VALORES.put( RESERVA_EMAIL, email );
        VALORES.put( RESERVA_MENU, menu );
        VALORES.put( RESERVA_HORARIO_INICIO, horario_inicio );
        VALORES.put( RESERVA_HORARIO_FIN, horario_fin );

        try {
            DB.beginTransaction();
            DB.insert(
                    TABLA_RESERVAS,
                    null,
                    VALORES
            );
            DB.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            DB.endTransaction();
        }
    }
    //FUNCION PARA ELIMINAR UNA RESERVA A PARTIR DEL NOMBRE DEL TITULAR
    public void eliminar_Reserva(String titular)
    {
        final SQLiteDatabase DB = this.getWritableDatabase();
        try {
            DB.beginTransaction();
            DB.execSQL("DELETE FROM " + TABLA_RESERVAS

                    + " WHERE "
                    + RESERVA_TITULAR + " = "
                    + "'"+ titular +"'"

            );

            DB.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            DB.endTransaction();
        }
    }
    //FUNCION PARA COMPROBAR SI UN TITULAR ESTA EN LA BASE DE DATOS
    public boolean comprobar_Titular(String titular)
    {
        boolean correcto= true;
        final SQLiteDatabase DB = this.getReadableDatabase();
        try {
            DB.beginTransaction();
            Cursor c= DB.rawQuery(" SELECT "+ MESAS_ID + " FROM " +TABLA_RESERVAS + " WHERE "+ RESERVA_TITULAR + " == ?",new String[] {""+titular+""});
            if( c.getCount() == 0){
                correcto=false;
            }
            DB.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            DB.endTransaction();
        }
        return  correcto;
    }
    //FUNCION PARA COMPROBAR SI UNA FECHA QUE SE VA INTRODUCIR ES VALIDA
    public boolean comprobar_Fecha(String fechaInicio, String fechaFin,int mesa)
    {
        boolean correcto= true;
        final SQLiteDatabase DB = this.getReadableDatabase();
        try {
            DB.beginTransaction();
            Cursor c= DB.rawQuery(" SELECT "+ MESAS_ID + " FROM " +TABLA_RESERVAS + "" +
                    " WHERE "+ " ? " + " BETWEEN " + RESERVA_HORARIO_INICIO + " AND " + RESERVA_HORARIO_FIN +
                    " OR "+" ? " + " BETWEEN " + RESERVA_HORARIO_INICIO + " AND " + RESERVA_HORARIO_FIN +
                    " AND " + MESAS_ID + " == ?",new String[] {""+fechaInicio+"",""+fechaFin+"",""+mesa+""});
            if( c.getCount() == 0){
                correcto=false;
            }
            DB.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            DB.endTransaction();
        }
        return  correcto;
    }
    //FUNCION PARA ELIMINAR FECHAS QUE YA PASARON
    public String eliminar_Fechas()
    {
        final Calendar c= Calendar.getInstance();
        int anho=c.get(Calendar.YEAR);
        int mes=c.get(Calendar.MONTH)+1;
        int dia=c.get(Calendar.DAY_OF_MONTH);
        int hora=c.get(Calendar.HOUR_OF_DAY);
        int minutos=c.get(Calendar.MINUTE);
        String fecha= anho+"/"+ mes+ "/"+dia+" "+hora+":"+minutos;
        final SQLiteDatabase DB = this.getWritableDatabase();
        try {
            DB.beginTransaction();
            DB.execSQL("DELETE FROM " + TABLA_RESERVAS

                    + " WHERE "
                    + RESERVA_HORARIO_FIN + " <= "
                    +  "'"+ fecha +"'" + " OR "
                    + RESERVA_HORARIO_INICIO + " <= "
                    +  "'"+ fecha +"'"

            );
            DB.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            DB.endTransaction();
        }
        return fecha;
    }


    //FUNCION LISTAR LAS RESERVAS DE UNA DETERMINADA MESA
    public Cursor getCursorReservas(int id)
    {
        final SQLiteDatabase DB = this.getReadableDatabase();
        String[] selectionArgs = {""+id+""};
        return DB.query(
                TABLA_RESERVAS,
                null,
                MESAS_ID + "=?",
                selectionArgs,
                null,
                null,
                MESAS_ID
        );
                        /*"SELECT FROM " + TABLA_RESERVAS
                        + " WHERE "
                        + RESERVA_MESA + " = "
                        + id,*/
    }
}