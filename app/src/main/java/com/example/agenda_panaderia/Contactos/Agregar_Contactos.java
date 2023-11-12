package com.example.agenda_panaderia.Contactos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda_panaderia.R;

public class Agregar_Contactos extends AppCompatActivity {
TextView Uid_Usuario,Telefono_c;
EditText Nombre_c, Apellido_c, Correo_c, Direccion_c;
ImageView Editar_Telefono_C;
Button Btn_guardar_contacto;
Dialog  dialog_establecer_telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contactos);

        InicializarVariables();
        ObtenerUidUsuario();

    }
    private void InicializarVariables(){
        Uid_Usuario= findViewById(R.id.Uid_Usuario);
        Nombre_c=findViewById(R.id.Nombre_c);
        Apellido_c=findViewById(R.id.Apellido_c);
        Correo_c=findViewById(R.id.Correo_c);
        Telefono_c=findViewById(R.id.Telefono_c);
        Direccion_c=findViewById(R.id.Direccion_c);
        Editar_Telefono_C=findViewById(R.id.Editar_Telefono_C);
        Btn_guardar_contacto=findViewById(R.id.Btn_guardar_contacto);

    }
    private void ObtenerUidUsuario() {

        String UidRecuperado= getIntent().getStringExtra("Uid");
        Uid_Usuario.setText(UidRecuperado);

    }

}