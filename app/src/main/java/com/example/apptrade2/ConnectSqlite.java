package com.example.apptrade2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConnectSqlite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DatosAbance2.db";
    public static final int DATABASE_VERSION = 1;


    public ConnectSqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS datosAbance (nombreReal VARCHAR(40), nombre VARCHAR(40), nombreBuq VARCHAR(20) , partida VARCHAR(20), subpartida VARCHAR(20), horas INTEGER(6), fecha INTEGER(15), sumaHoras VARCHAR(20)); ");
        db.execSQL("CREATE TABLE IF NOT EXISTS datosAbanceDef (nombreReal VARCHAR(40), nombre VARCHAR(40), nombreBuq VARCHAR(20) , partida VARCHAR(20), subpartida VARCHAR(20), horas INTEGER(6), fecha INTEGER(15), sumaHoras VARCHAR(20)); ");
        db.execSQL("CREATE TABLE IF NOT EXISTS nombresPartidas (nombre VARCHAR(40))");
        db.execSQL("CREATE TABLE IF NOT EXISTS nombresPersonas (nombre VARCHAR(50))");
        db.execSQL("CREATE TABLE IF NOT EXISTS nombresBuques (nombre VARCHAR(50))");
        db.execSQL("CREATE TABLE IF NOT EXISTS nombresSubpartidas (nombre VARCHAR(50))");
        db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (usuario VARCHAR(20), pass VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {




    }


    public Cursor ConsultarUsuPas (String usu, String pass){

        Cursor mCursor  = this.getReadableDatabase().query("usuarios", new String[] {"usuario", "pass"},"usuario LIKE '" + usu +"' AND pass LIKE '" + pass +"'", null,null,null,null);

        return mCursor;
    }

    public Cursor ConsultarPartida (String partida){


        Cursor mCursor = this.getReadableDatabase().query("nombresPartidas", new String[] {"nombre"}, "nombre LIKE '" + partida + "'",null,null,null,null);

        return mCursor;
    }



}
