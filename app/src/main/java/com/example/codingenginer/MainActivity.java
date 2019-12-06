package com.example.codingenginer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtCorreoElectronico, txtContrasenia;
    private AdminSQLiteOpenHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        txtCorreoElectronico = (EditText) findViewById(R.id.txtCorreoLogin);
        txtContrasenia = (EditText) findViewById(R.id.txtContreaseniaLogin);
    }

    public void Login(View view) {
        Intent i = new Intent(this, Terminos.class);
        String correo = txtCorreoElectronico.getText().toString();
        String password = txtContrasenia.getText().toString();
        String[] campos = {"nombre","correo", "password","foto"};
        String[] parametros = {correo};

        if (!correo.isEmpty() && !password.isEmpty()) {
            try {
                SQLiteDatabase database = admin.getReadableDatabase();

                Cursor fila = database.query("usuarios", campos, "correo = ?", parametros, null, null, null);

                if (fila.moveToNext()) {//Si encuentra el registro se posiciona en la primera fila

                    if (correo.compareTo(fila.getString(1)) == 0 && password.compareTo(fila.getString(2)) == 0) {
                        Toast.makeText(this, "Bienvenido " + fila.getString(0), Toast.LENGTH_LONG).show();

                        Bundle bundle = new Bundle();
                        bundle.putString("nombre", fila.getString(0));
                        bundle.putString("correo", fila.getString(1));
                        bundle.putByteArray("bytePhoto", fila.getBlob(3));

                        i.putExtras(bundle);

                        startActivity(i);
                        database.close();
                    } else {
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                //Toast.makeText(this, "El no existe", Toast.LENGTH_SHORT).show();
                Log.d("Error", e.toString());
            }
        } else {
            Toast.makeText(this, "Se necesitan correo y contraseña", Toast.LENGTH_SHORT);
        }
    }

    public void goRegistro(View view) {
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }


}
