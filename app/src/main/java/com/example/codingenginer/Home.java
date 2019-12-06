package com.example.codingenginer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codingenginer.Modelos.Modulo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    String correo;
    TextView tx;
    private List<Modulo> listaModulos;
    private ArrayList<String> listaInfo;
    private AdminSQLiteOpenHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        //listViewModulos = (ListView) findViewById(R.id.listViewModulos);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            correo = bundle.get("correo").toString();
            tx = (TextView) findViewById(R.id.textView4);
            tx.setText(tx.getText() + " " + correo);
        }

        listaModulos = new ArrayList<Modulo>();
        consulta();

    }

    public void go(View view) {
        Intent i = new Intent(this, AltaTarea.class);
        Bundle bundle = new Bundle();
        bundle.putString("correo", correo);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void consulta() {
        //modulo text, comienzo text, final text, tiempo text, defectos text, encargado text, descripcion text, detalles_audio_path text, correo text
        String[] campos = {"id", "modulo", "comienzo", "final", "tiempo", "defectos", "encargado", "descripcion", "detalles_audio_path", "correo"};
        String[] parametros = {correo};
        try {
            SQLiteDatabase database = admin.getReadableDatabase();
            Cursor fila = database.query("modulos", campos, "correo = ?", parametros, null, null, null);

            if (fila != null)
                Toast.makeText(this, "Hay algo", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "No hay nada", Toast.LENGTH_SHORT).show();

            while (fila.moveToNext()) {//Si encuentra el registro se posiciona en la primera fila

                int id = Integer.parseInt(fila.getString(0));
                String modulo = fila.getString(1);
                String comienzo = fila.getString(2);
                String termino = fila.getString(3);
                String tiempo = fila.getString(4);
                String defectos = fila.getString(5);
                String encargado = fila.getString(6);
                String desripcion = fila.getString(7);
                String detalles_audio_path = fila.getString(8);
                String correo = fila.getString(9);

                listaModulos.add(new Modulo(id, modulo, comienzo, termino, tiempo, defectos, encargado, desripcion, detalles_audio_path, correo));
            }

            database.close();

            listaInfo = new ArrayList<String>();
            for (Modulo modulo : listaModulos) {
                listaInfo.add(modulo.getId() + " - " + modulo.getModulo() + "\n" + modulo.getDescripcion());
            }


            generarBotonesDinamicos(listaModulos);

        } catch (Exception e) {
            //Toast.makeText(this, "El no existe", Toast.LENGTH_SHORT).show();
            Log.d("Error", e.toString());
        }
    }

    public void generarBotonesDinamicos(List<Modulo> modulos){
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_container);
        LinearLayout container = new LinearLayout(this);
        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        container.setOrientation(LinearLayout.VERTICAL);

        for (Modulo modulo : modulos) {
            Button boton = new Button(this);
            boton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            boton.setText("Modulo: "+modulo.getModulo() + " Descripcion: " + modulo.getDescripcion());
            boton.setId(modulo.getId());

            //Asignamos listener al boton
            boton.setOnClickListener(new ButtonsOnClickListener());

            container.addView(boton);
        }
        layout.addView(container);
    }

    private class ButtonsOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
//            Toast.makeText(Home.this, "boton " + v.getId(), Toast.LENGTH_LONG ).show();
            Intent i = new Intent(Home.this, InfoModulo.class);

            Bundle bundle = new Bundle();
            bundle.putString("id",String.valueOf(v.getId()));

            i.putExtras(bundle);
            startActivity(i);
        }
    }
}
