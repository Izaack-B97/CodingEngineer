package com.example.codingenginer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InfoModulo extends AppCompatActivity {

    TextView txTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_modulo);

        txTitle = (TextView) findViewById(R.id.textViewTitulo);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            txTitle.setText(txTitle.getText()+ " " + bundle.getString("id"));
        } else {
            txTitle.setText("No llego nada");
        }
    }
}
