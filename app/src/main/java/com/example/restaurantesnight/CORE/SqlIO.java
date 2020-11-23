package com.example.restaurantesnight.CORE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/*Base de datos SQlite, tendrá tablas de mesas y de usuarios */
public class SqlIO  extends SQLiteOpenHelper {
    private static final String DB_NOMBRE="RestauranteNight";
    private static final int DB_VERSION=1;
    public static final String TABLA_MESAS="Mesas";
    public static final String MESAS_ID="_id";
    public static final String MESAS_CAPACIDAD="capacidad";

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
    public void eliminar_Mesa(int id)
    {
        final SQLiteDatabase DB = this.getWritableDatabase();
        final ContentValues VALORES = new ContentValues();

        VALORES.put( MESAS_ID, id );

        try {
            DB.beginTransaction();
            DB.execSQL("DELETE FROM " + TABLA_MESAS
                    + "("
                    + " WHERE "
                    + MESAS_ID + " = "
                    + id
                    + ")"
            );
            DB.setTransactionSuccessful();
        } catch(SQLException error)
        {
            Log.e( DB_NOMBRE, error.getMessage() );
        } finally {
            DB.endTransaction();
        }
    }
}
