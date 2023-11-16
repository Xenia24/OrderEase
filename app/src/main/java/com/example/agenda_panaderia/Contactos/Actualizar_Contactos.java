package com.example.agenda_panaderia.Contactos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agenda_panaderia.R;

public class Actualizar_Contactos extends AppCompatActivity {
    TextView Id_C_A, Uid_C_A, Telefono_C_A;
    EditText Nombres_C_A, Apellidos_C_A, Correo_C_A, Direccion_C_A;
    ImageView Imagen_C_A, Actualizar_imagen_C_A, Actualizar_Telefono_C_A;
    Button Btn_Actualizar_C_A;
    String id_c , uid_usuario, nombres_c, apellidos_c, correo_c, telefono_c, edad_c, direccion_c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_contactos);

        InicializarVistas();
        RecuperarDatos();
        SetearDatosRecuperados();

    }
    private void InicializarVistas(){
        Id_C_A = findViewById(R.id.Id_C_A);
        Uid_C_A = findViewById(R.id.Uid_C_A);
        Telefono_C_A = findViewById(R.id.Telefono_C_A);
        Nombres_C_A = findViewById(R.id.Nombres_C_A);
        Apellidos_C_A = findViewById(R.id.Apellidos_C_A);
        Correo_C_A = findViewById(R.id.Correo_C_A);
        Direccion_C_A = findViewById(R.id.Direccion_C_A);
        Imagen_C_A = findViewById(R.id.Imagen_C_A);
        Actualizar_imagen_C_A = findViewById(R.id.Actualizar_imagen_C_A);
        Actualizar_Telefono_C_A = findViewById(R.id.Actualizar_Telefono_C_A);
        Btn_Actualizar_C_A = findViewById(R.id.Btn_Actualizar_C_A);

    }
    private void RecuperarDatos(){
        Bundle bundle = getIntent().getExtras();
        id_c = bundle.getString("id_c");
        uid_usuario = bundle.getString("uid_usuario");
        nombres_c = bundle.getString("Nombre_c");
        apellidos_c = bundle.getString("Apellidos_c");
        correo_c = bundle.getString("Correo_c");
        telefono_c = bundle.getString("Telefono_c");
        direccion_c = bundle.getString("Direccion_C_A");
    }
    private void SetearDatosRecuperados(){
        Id_C_A.setText(id_c);
        Uid_C_A.setText(uid_usuario);
        Nombres_C_A.setText(nombres_c);
        Apellidos_C_A.setText(apellidos_c);
        Correo_C_A.setText(correo_c);
        Telefono_C_A.setText(telefono_c);
        Direccion_C_A.setText(direccion_c);
    }
}