package com.example.codingenginer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Terminos extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);

        Bundle bundle = this.getIntent().getExtras();

        textView = (TextView) findViewById(R.id.textView2);
        imageView = (ImageView) findViewById(R.id.imageView2);

        if (bundle != null){
            String nombre = bundle.get("nombre").toString();
            Bitmap ivPerfil = BitmapFactory.decodeByteArray(bundle.getByteArray("bytePhoto"),0,bundle.getByteArray("bytePhoto").length);
            correo = bundle.get("correo").toString();
            RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), ivPerfil);
            roundedDrawable.setCornerRadius(ivPerfil.getHeight() * 5);

            textView.setText(textView.getText() + " " + nombre);
            imageView.setImageDrawable(roundedDrawable);
        } else {
            textView.setText("No llego nada");
        }
    }

    public void goHome(View view){
        Intent i = new Intent(this, Home.class);
        Bundle bundle = new Bundle();
        bundle.putString("correo", correo);
        i.putExtras(bundle);
        startActivity(i);
    }
}
