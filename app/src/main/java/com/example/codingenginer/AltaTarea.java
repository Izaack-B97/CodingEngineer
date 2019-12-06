package com.example.codingenginer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AltaTarea extends AppCompatActivity {

    EditText txtModulo, txtComienzo, txtFinal, txtDefectos, txtTiempo, txtEncargado, txtDescripcion;
    MediaRecorder grabacion;
    String archivoSalida = null;
    Button btnGrabacion;
    TextView textView;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_tarea);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) correo = bundle.get("correo").toString();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AltaTarea.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }

        btnGrabacion = (Button) findViewById(R.id.btnGrabacion);
        txtModulo = (EditText) findViewById(R.id.txtModulo);
        txtComienzo = (EditText) findViewById(R.id.txtComienzo);
        txtFinal = (EditText) findViewById(R.id.txtFinal);
        txtDefectos = (EditText) findViewById(R.id.txtDefectos);
        txtTiempo = (EditText) findViewById(R.id.txtTiempo);
        txtEncargado = (EditText) findViewById(R.id.txtEncargado);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
    }

    public void grabar(View view) {
        if (grabacion == null) {
            archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion" + System.currentTimeMillis() + ".mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setOutputFile(archivoSalida);

            try {
                grabacion.prepare();
                grabacion.start();

            } catch (Exception e) {
            }

            btnGrabacion.setText("Pausa");
            Toast.makeText(this, "Grabando", Toast.LENGTH_LONG).show();
        } else if (grabacion != null) {
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            btnGrabacion.setText("Grabar detalles");
            Toast.makeText(this, "Grabacion finalizada", Toast.LENGTH_LONG).show();
        }
    }

    public void reproducir(View view) {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(archivoSalida);
            mp.prepare();
        } catch (Exception ex) {
        }

        mp.start();
        Toast.makeText(this, "Reproduciendo grabacion", Toast.LENGTH_LONG).show();
    }

    public void registrar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        String modulo = txtModulo.getText().toString();
        String comienzo = txtComienzo.getText().toString();
        String termino = txtFinal.getText().toString();
        String defectos = txtDefectos.getText().toString();
        String tiempo = txtTiempo.getText().toString();
        String encargado = txtEncargado.getText().toString();
        String descripcion = txtDescripcion.getText().toString();

        String detalles_audio_path = archivoSalida;

        if (!modulo.isEmpty() && !comienzo.isEmpty() && !termino.isEmpty() && !defectos.isEmpty() && !tiempo.isEmpty() && !encargado.isEmpty() & !descripcion.isEmpty()) {
            try {

                //modulo text, comienzo text, final text, tiempo text, defectos text, encargado text, descripcion text, detalles_audio_path text, correo text
                ContentValues registro = new ContentValues();
                registro.put("modulo", modulo);
                registro.put("comienzo", comienzo);
                registro.put("final", termino);
                registro.put("tiempo", tiempo);
                registro.put("defectos", defectos);
                registro.put("encargado", encargado);
                registro.put("descripcion", descripcion);
                registro.put("detalles_audio_path", detalles_audio_path);
                registro.put("correo",correo);

                int a = (int) database.insert("modulos", null, registro);

                database.close();

                limpiar();// Limpiamos los campos de texto

                Toast.makeText(this, "Registro exitoso " + a, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d("Error: ", e.toString());
            }
        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiar() {

        btnGrabacion.setText("");
        txtModulo.setText("");
        txtComienzo.setText("");
        txtFinal.setText("");
        txtDefectos.setText("");
        txtTiempo.setText("");
        txtEncargado.setText("");
        txtDescripcion.setText("");
    }
}
