package com.example.agenda_panaderia.Pedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda_panaderia.Menu_Principal;
import com.example.agenda_panaderia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Actualizar_Pedidos extends AppCompatActivity{
    TextView Id_pedido_A, Uid_Usuario_A, Fecha_registro_A, Estado_A, Fecha_A, Estado_nuevo;
    EditText Titulo_A,Descripcion_A, Nombre_Cliente_A;
    Button Btn_Calendario_A, Btn_Actualizar_P_A;
    RadioGroup formaRadioGroup2;
    RadioButton Local2, Domicilio2;
    Spinner Spinner_estado, Spinner_estado_2;

    String id_pedido_R, uid_usuario_R,nombre_R , fecha_registro_R, estado_R, fecha_R,estado_n_R, titulo_R,descripcion_R;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_pedidos);
        InicializarVistas();
        RecuperarDatos();
        SetearDatos();

    }
    private void InicializarVistas(){
        Id_pedido_A =findViewById(R.id.Id_pedido_A);
        Uid_Usuario_A=findViewById(R.id.Uid_Usuario_A);
        Estado_A=findViewById(R.id.Estado_A);
        Fecha_A=findViewById(R.id.Fecha_A);
        Estado_nuevo=findViewById(R.id.Estado_nuevo);
        Titulo_A=findViewById(R.id.Titulo_A);
        Descripcion_A=findViewById(R.id.Descripcion_A);
        Btn_Calendario_A=findViewById(R.id.Btn_Calendario_A);
        Nombre_Cliente_A=findViewById(R.id.Nombre_Cliente_A);
//        formaRadioGroup2=findViewById(R.id.formaRadioGroup2);
//        Local2=findViewById(R.id.Local2);
//        Domicilio2=findViewById(R.id.Domicilio2);
        Spinner_estado=findViewById(R.id.Spinner_estado);
        Spinner_estado_2=findViewById(R.id.Spinner_estado_2);
        Fecha_registro_A=findViewById(R.id.Fecha_registro_A);
        Btn_Actualizar_P_A = findViewById(R.id.Btn_Actualizar_P_A);



    }
    private void RecuperarDatos(){
        Bundle intent= getIntent().getExtras();
        id_pedido_R= intent.getString("id_pedido");
        uid_usuario_R= intent.getString("uid_usuario");
        nombre_R = intent.getString("nombre_usuario");
        fecha_registro_R= intent.getString("fecha_registro");
        estado_n_R= intent.getString("estado");
        fecha_R= intent.getString("fecha_pedido");
        titulo_R= intent.getString("titulo");
        descripcion_R= intent.getString("descripcion");


    }
    private void  SetearDatos(){
        Id_pedido_A.setText(id_pedido_R);
        Uid_Usuario_A.setText(uid_usuario_R);
        Nombre_Cliente_A.setText(nombre_R);
        Fecha_A.setText(fecha_R);
        Fecha_registro_A.setText(fecha_registro_R);
        Estado_nuevo.setText(estado_n_R);
        Fecha_A.setText(fecha_R);
        Titulo_A.setText(titulo_R);
        Descripcion_A.setText(descripcion_R);
    }

}