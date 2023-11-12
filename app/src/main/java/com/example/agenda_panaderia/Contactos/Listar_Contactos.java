package com.example.agenda_panaderia.Contactos;

import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contactos);


    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_agregar_contactos, menu);
//
//
//        return super.onCreateOptionsMenu(menu);
//
//
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.imageView9) {
//            Intent intent=new Intent(Listar_Contactos.this,Agregar_Contactos.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }



}
