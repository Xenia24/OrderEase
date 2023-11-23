package com.example.agenda_panaderia.Contactos;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda_panaderia.Objetos.Contacto;
import com.example.agenda_panaderia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class Agregar_Contactos extends AppCompatActivity {
TextView Uid_Usuario,Telefono_c;
EditText Nombre_c, Apellido_c, Correo_c, Direccion_c;
ImageView Editar_Telefono_C,  atras1;
Button Btn_guardar_contacto;
Dialog  dialog_establecer_telefono;
DatabaseReference BD_Contactos;
FirebaseAuth firebaseAuth;
FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contactos);
        atras1 = findViewById(R.id.regresar2);
        InicializarVariables();
        ObtenerUidUsuario();

        Editar_Telefono_C.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Establecer_telefono_contacto();
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
        BD_Contactos= FirebaseDatabase.getInstance().getReference("Usuarios");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();



    }
    private void ObtenerUidUsuario() {

        String UidRecuperado= getIntent().getStringExtra("Uid");
        Uid_Usuario.setText(UidRecuperado);

    }
//h

    private void AgregarContacto() {
        String uid= Uid_Usuario.getText().toString();
        String nombres= Nombre_c.getText().toString();
        String apellidos= Apellido_c.getText().toString();
        String correo= Correo_c.getText().toString();
        String telefono= Telefono_c.getText().toString();
        String direccion= Direccion_c.getText().toString();

        String id_contacto = BD_Contactos.push().getKey();

        if (!uid.equals("") && !nombres.equals("")){
            Contacto contacto = new Contacto(id_contacto,uid,nombres,apellidos, correo,telefono,direccion);

            String Nombre_BD = "Contactos";
            assert id_contacto != null;
            BD_Contactos.child(user.getUid()).child(Nombre_BD).child(id_contacto).setValue(contacto);
            Toast.makeText(this, "Contacto agregado", Toast.LENGTH_SHORT).show();
            onBackPressed();

        }
        else{
            Toast.makeText(this, "Por favor, al menos complete el nombre del contacto", Toast.LENGTH_SHORT).show();
        }
    }


    private void Establecer_telefono_contacto(){
        CountryCodePicker ccp;
        EditText Establecer_Telefono;
        Button Btn_Aceptar_Telefono;

        dialog_establecer_telefono.setContentView(R.layout.cuadro_dialogo_establecer_telefono);

        ccp = dialog_establecer_telefono.findViewById(R.id.ccp);
        Establecer_Telefono = dialog_establecer_telefono.findViewById(R.id.Establecer_Telefono);
        Btn_Aceptar_Telefono = dialog_establecer_telefono.findViewById(R.id.Btn_Aceptar_Telefono);

        Btn_Aceptar_Telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo_pais = ccp.getSelectedCountryCodeWithPlus();
                String telefono = Establecer_Telefono.getText().toString();
                String codigo_pais_telefono = codigo_pais+telefono;

                if (!telefono.equals("")){
                    Telefono_c.setText(codigo_pais_telefono);
                    dialog_establecer_telefono.dismiss();
                }else {
                    Toast.makeText(Agregar_Contactos.this, "Ingrese un número telefónico", Toast.LENGTH_SHORT).show();
                    dialog_establecer_telefono.dismiss();
                }
            }
        });

        dialog_establecer_telefono.show();
        dialog_establecer_telefono.setCanceledOnTouchOutside(true);
    }

}