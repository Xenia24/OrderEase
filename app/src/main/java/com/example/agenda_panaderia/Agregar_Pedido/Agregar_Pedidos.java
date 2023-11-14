package com.example.agenda_panaderia.Agregar_Pedido;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.agenda_panaderia.R;

public class Agregar_Pedidos extends AppCompatActivity {
TextView id_Usuario, Correo_usuario, Fecha_Actual, Fecha, Estado;
EditText Descripcion, Titulo, Nombre_Cliente;
Button Btn_Calendario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pedidos);

        InicializarVariables();
        Obtenerdatos();
    }
    private void InicializarVariables() {
        id_Usuario = findViewById(R.id.Uid_Usuario);
        Correo_usuario=findViewById(R.id.Correo_usuario);
        Fecha_Actual=findViewById(R.id.Fecha_Actual);
        Fecha=findViewById(R.id.Fecha);
        Estado=findViewById(R.id.Estado);
        Descripcion=findViewById(R.id.Descripcion);
        Titulo=findViewById(R.id.Titulo);
        Nombre_Cliente=findViewById(R.id.Nombre_Cliente);
        Btn_Calendario=findViewById(R.id.Btn_Calendario);
    }


    protected void Obtenerdatos() {

        String uid_recuperado= getIntent().getStringExtra("Uid");
        String email_recuperado= getIntent().getStringExtra("Correo");

        id_Usuario.setText(uid_recuperado);
        Correo_usuario.setText(email_recuperado);

    }
}