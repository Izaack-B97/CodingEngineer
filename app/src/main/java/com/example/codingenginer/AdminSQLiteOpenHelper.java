package com.example.codingenginer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //database.execSQL("CREATE TABLE datos_usuario(codigo integer primary key autoincrement, nombre text, apellidos text, correo text, password text, foto blob)");
        database.execSQL("CREATE TABLE usuarios(id integer primary key autoincrement, nombre text, apellidos text, correo text, password text, foto blob)");
        database.execSQL("CREATE TABLE modulos (id integer primary key autoincrement, modulo text, comienzo text, final text, tiempo text, defectos text, encargado text, descripcion text, detalles_audio_path text, correo text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if (oldVersion > newVersion){
            //db.execSQL("DROP TABLE IF EXISTS usuarios");
            //onCreate(db);
        //}
    }
}
