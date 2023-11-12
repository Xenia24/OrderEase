package com.example.agenda_panaderia.Contactos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda_panaderia.Menu_Principal;
import com.example.agenda_panaderia.R;


public class Listar_Contactos extends AppCompatActivity {
    ImageView ImageView, atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contactos);
        ImageView = findViewById(R.id.Agregar_Contacto);
        atras = findViewById(R.id.regresar);
        ImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String Uid_Recuperado = getIntent().getStringExtra("Uid");
                Intent intent = new Intent(Listar_Contactos.this, Agregar_Contactos.class);
                intent.putExtra("Uid", Uid_Recuperado);
                startActivity(intent);

                //Toast.makeText(Menu_Principal.this,"Contactos", Toast.LENGTH_SHORT).show();
            }
            });

        atras.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Listar_Contactos.this, Menu_Principal.class);
                startActivity(intent);
                //Toast.makeText(Menu_Principal.this,"Contactos", Toast.LENGTH_SHORT).show();

            }
        });


    }

}
