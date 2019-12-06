package com.example.codingenginer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.codingenginer.Modelos.Modulo;

import java.util.ArrayList;
import java.util.Date;

public class InfoModulo extends AppCompatActivity {

    EditText txtModulo, txtComienzo, txtFinal, txtDefectos, txtTiempo, txtEncargado, txtDescripcion;
    TextView txTitle;
    ToggleButton reproductor;
    Switch switc;
    Button btnCompartir;
    private AdminSQLiteOpenHelper admin;
    String id, detallesPath, email;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_modulo);
        admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        txTitle = (TextView) findViewById(R.id.textViewTitulo);
        txtModulo = (EditText) findViewById(R.id.txtModuloInfo);
        txtComienzo = (EditText) findViewById(R.id.txtComienzoInfo);
        txtFinal = (EditText) findViewById(R.id.txtFinalInfo);
        txtDefectos = (EditText) findViewById(R.id.txtDefectosInfo);
        txtTiempo = (EditText) findViewById(R.id.txtTiempoInfo);
        txtEncargado = (EditText) findViewById(R.id.txtEncInfo);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcionInfo);
        reproductor = (ToggleButton) findViewById(R.id.toggleButton3);
        switc = (Switch) findViewById(R.id.switch1);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id").toString();
            txTitle.setText(txTitle.getText() + " " + bundle.getString("id"));
            consultaInfo();
        } else {
            txTitle.setText("No llego nada");
        }


        reproductor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MediaPlayer mp = new MediaPlayer();
                if (isChecked) {
                    try {
                        mp.setDataSource(detallesPath);
                        mp.prepare();
                    } catch (Exception ex) {
                    }

                    mp.start();
                    Toast.makeText(InfoModulo.this, "Reproduciendo grabacion", Toast.LENGTH_LONG).show();
                } else {
                    //if(mp.isPlaying()){
                    mp.stop();
                    Toast.makeText(InfoModulo.this, "Grabacion detenida", Toast.LENGTH_LONG).show();
                    //}
                }
            }
        });

        switc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    txTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                else
                    txTitle.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });


        initSeekBar();

    }

    public void consultaInfo() {
        //Toast.makeText(InfoModulo.this, "Hola", Toast.LENGTH_LONG).show();

        //modulo text, comienzo text, final text, tiempo text, defectos text, encargado text, descripcion text, detalles_audio_path text, correo text
        String[] campos = {"id", "modulo", "comienzo", "final", "tiempo", "defectos", "encargado", "descripcion", "detalles_audio_path", "correo"};
        String[] parametros = {id};
        try {
            SQLiteDatabase database = admin.getReadableDatabase();
            Cursor fila = database.query("modulos", campos, "id = ?", parametros, null, null, null);

            if (fila != null)
                Toast.makeText(this, "Hay algo", Toast.LENGTH_SHORT).show();
            if (fila.moveToFirst()) {
                //int id = Integer.parseInt(fila.getString(0));
                String modulo = fila.getString(1);
                String comienzo = fila.getString(2);
                String termino = fila.getString(3);
                String tiempo = fila.getString(4);
                String defectos = fila.getString(5);
                String encargado = fila.getString(6);
                String desripcion = fila.getString(7);
                String detalles_audio_path = fila.getString(8);
                String correo = fila.getString(9);

                detallesPath = detalles_audio_path;
                email = correo;

                txtModulo.setText("Modulo: " + modulo);
                txtComienzo.setText("Comenzo: " + comienzo);
                txtFinal.setText("Termino: " + termino);
                txtTiempo.setText("Duro: " + tiempo);
                txtDefectos.setText("Defectos: " + defectos);
                txtEncargado.setText("Encargado: " + encargado);
                txtDescripcion.setText("Descripcion: " + desripcion);
            } else
                Toast.makeText(this, "No hay nada", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            //Toast.makeText(this, "El no existe", Toast.LENGTH_SHORT).show();
            Log.d("Error", e.toString());
        }
    }

    public void initSeekBar() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        try {
            volumeSeekbar = (SeekBar) findViewById(R.id.seekBar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void compartir(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        Date date = new Date();

        String message = "Modulo: " + txtModulo.getText() + "\n"
                + "Comenzo: " + txtComienzo.getText() + "\n"
                + "Finalizo: " + txtFinal.getText() + "\n"
                + "Defectos: " + txtDefectos.getText() + "\n"
                + "Duracion: " + txtTiempo.getText() + "\n"
                + "Encargado: " + txtEncargado.getText() + "\n"
                + "Descripcion: " + txtDescripcion.getText() + "\n"
                + "Cotacto: " + email + "\n"
                + "Fecha: " + date.getDay() + "/" + date.getMonth() + "/2019";

        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, message);
        Intent chooser = Intent.createChooser(i,"Message");
        startActivity(chooser);
    }

}
