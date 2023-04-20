package com.example.parcial3janeth.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactosDao extends DbHelper {

    Context context;

    public ContactosDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(Contactos c) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombres", c.getNombres());
            values.put("apellidos", c.getApellidos());
            values.put("telefono", c.getTelefono());
            values.put("correo_electronico", c.getCorreo_electornico());

            id = db.insert(TABLE_CONTACTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Contactos> mostrarContactos() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " ORDER BY nombres ASC", null);

        if (cursorContactos.moveToFirst()) {
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombres(cursorContactos.getString(1));
                contacto.setApellidos(cursorContactos.getString(2));
                contacto.setTelefono(cursorContactos.getString(3));
                contacto.setCorreo_electornico(cursorContactos.getString(4));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos;
    }

    public Contactos verContacto(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Contactos contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorContactos.moveToFirst()) {
            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombres(cursorContactos.getString(1));
            contacto.setApellidos(cursorContactos.getString(2));
            contacto.setTelefono(cursorContactos.getString(3));
            contacto.setCorreo_electornico(cursorContactos.getString(4));
        }

        cursorContactos.close();

        return contacto;
    }

    public boolean editarContacto(Contactos c) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CONTACTOS
                    + " SET nombres = '" + c.getNombres()
                    + "', apellidos = '" + c.getApellidos()
                    + "', telefono = '" + c.getTelefono()
                    + "', correo_electronico = '" + c.getCorreo_electornico()
                    + "' WHERE id='" + c.getId()+ "' ");

            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarContacto(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
