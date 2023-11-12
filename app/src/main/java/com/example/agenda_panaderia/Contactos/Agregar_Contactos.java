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

import com.example.agenda_panaderia.Objetos.Contacto;
import com.example.agenda_panaderia.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Agregar_Contactos extends AppCompatActivity {
TextView Uid_Usuario,Telefono_c;
EditText Nombre_c, Apellido_c, Correo_c, Direccion_c;
ImageView Editar_Telefono_C;
Button Btn_guardar_contacto;
Dialog  dialog_establecer_telefono;
DatabaseReference DB_Contactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contactos);

        InicializarVariables();
        ObtenerUidUsuario();
        Editar_Telefono_C.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Establecer_telefono_C();
            }
        });
        Btn_guardar_contacto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AgregarContacto();
            }
        });

    }
    private void InicializarVariables(){
        //uno dos tres
        Uid_Usuario= findViewById(R.id.Uid_Usuario);
        Nombre_c=findViewById(R.id.Nombre_c);
        Apellido_c=findViewById(R.id.Apellido_c);
        Correo_c=findViewById(R.id.Correo_c);
        Telefono_c=findViewById(R.id.Telefono_c);
        Direccion_c=findViewById(R.id.Direccion_c);
        Editar_Telefono_C=findViewById(R.id.Editar_Telefono_C);
        Btn_guardar_contacto=findViewById(R.id.Btn_guardar_contacto);

        dialog_establecer_telefono= new Dialog(Agregar_Contactos.this);
        DB_Contactos= FirebaseDatabase.getInstance().getReference();


    }
    private void ObtenerUidUsuario() {

        String UidRecuperado= getIntent().getStringExtra("Uid");
        Uid_Usuario.setText(UidRecuperado);

    }
//h

    private void AgregarContacto() {
        String uid= Uid_Usuario.getText().toString();
        String nombre= Nombre_c.getText().toString();
        String apellido= Apellido_c.getText().toString();
        String correo= Correo_c.getText().toString();
        String telefono= Telefono_c.getText().toString();
        String direccion= Direccion_c.getText().toString();

        String id_contacto = DB_Contactos.push().getKey();

        if (!uid.equals("") && !nombre.equals("")){
            Contacto contacto = new Contacto(id_contacto,uid,nombre,apellido, correo,telefono,direccion);

            String Nombre_BD = "Contactos";
            DB_Contactos.child(Nombre_BD).child(id_contacto).setValue(contacto);
            Toast.makeText(this, "Contacto agregado", Toast.LENGTH_SHORT).show();
            onBackPressed();

        }
        else{
            Toast.makeText(this, "Por favor, al menos complete el nombre del contacto", Toast.LENGTH_SHORT).show();
        }

    }

}