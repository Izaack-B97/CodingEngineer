package com.example.codingenginer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Registro extends AppCompatActivity {

    EditText txtNombre, txtApellidos, txtCorreo, txtConfirmCorreo, txtContrasenia, txtConfirmContrasenia;
    ImageView ivPerfil;
    final static int REQUEST_IMAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNombre = (EditText) findViewById(R.id.txtNombreRegistro);
        txtApellidos = (EditText) findViewById(R.id.txtApellidosRegistro);
        txtCorreo = (EditText) findViewById(R.id.txtCorreoRegistro);
        txtConfirmCorreo = (EditText) findViewById(R.id.txtConfirmarCorreoRegistro);
        txtContrasenia = (EditText) findViewById(R.id.txtContraseniaRegistro);
        txtConfirmContrasenia = (EditText) findViewById(R.id.txtConfirmarContraseniaRegistro);
        ivPerfil = (ImageView) findViewById(R.id.ivPerfil);
    }

    public void tomarFoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPerfil.setImageBitmap(imageBitmap);
        }
    }

    public void Rigistrarse(View view) {

        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase database = admin.getWritableDatabase();

            String nombre = txtNombre.getText().toString();
            String apellidos = txtApellidos.getText().toString();
            String correo = txtCorreo.getText().toString();
            String confirmCorreo = txtConfirmCorreo.getText().toString();
            String password = txtContrasenia.getText().toString();
            String confirmPassword = txtConfirmContrasenia.getText().toString();

            Bitmap bitmap = ((BitmapDrawable) ivPerfil.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytesPhoto = stream.toByteArray();


            if (!nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty() && !confirmCorreo.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {

                if (bytesPhoto != null) {

                    if (correo.compareTo(confirmCorreo) == 0 || password.compareTo(confirmPassword) == 0) {
                        //byte[] bitmap = null;
                        ContentValues registro = new ContentValues();
                        registro.put("nombre", nombre);
                        registro.put("apellidos", apellidos);
                        registro.put("correo", correo);
                        registro.put("password", password);
                        registro.put("foto", bytesPhoto);

                        int a = (int) database.insert("usuarios", null, registro);

                        database.close();

                        limpiar();// Limpiamos los campos de texto

                        Toast.makeText(this, "Registro exitoso" + a, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "El usuario o la contraseña no coinciden", Toast.LENGTH_SHORT).show();
                    }

                } else Toast.makeText(this,"Debes tomar la foto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            Log.d("Error: ", e.toString());
        }


    }

    private void limpiar() {

        txtNombre.setText("");
        txtApellidos.setText("");
        txtContrasenia.setText("");
        txtConfirmContrasenia.setText("");
        txtCorreo.setText("");
        txtConfirmCorreo.setText("");

    }

}
