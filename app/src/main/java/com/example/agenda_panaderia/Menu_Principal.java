package com.example.agenda_panaderia;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.agenda_panaderia.Pedidos.Agregar_Pedidos;

import com.example.agenda_panaderia.Contactos.Listar_Contactos;
import com.example.agenda_panaderia.Pedidos.Listar_Pedidos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Menu_Principal extends AppCompatActivity {



    Button Btn_Salir,Contactos, Verificacion, AgregarPedidos, ListaPedidos;

    FirebaseAuth firebaseAuth;
    FirebaseUser User;
    LinearLayoutCompat Linear_Nombres, Linear_Verificacion, Linear_Correo;
    TextView NombreP,Linear, IdMenu;
    ProgressBar ProgresBar;
    ProgressDialog progressDialog;
    DatabaseReference Usuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

//            ActionBar actionBar = getSupportActionBar();
//            actionBar.setTitle("OrderEase");
//            actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);

        NombreP=findViewById(R.id.NombreP);
        Linear= findViewById(R.id.CorreoP);

        IdMenu= findViewById(R.id.IdMenu);
        Linear_Nombres = findViewById(R.id.Linear_Nombres);
        Linear_Verificacion = findViewById(R.id.Linear_Verificacion);
        Linear_Correo = findViewById(R.id.Linear_Correo);
        Verificacion = findViewById(R.id.Verificacion);
        AgregarPedidos=findViewById(R.id.Btn_Agregar);
        ListaPedidos= findViewById(R.id.Btn_Lista);

        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");
        Btn_Salir=findViewById(R.id.Btn_Salir);
        firebaseAuth = FirebaseAuth.getInstance();
        User= firebaseAuth.getCurrentUser();
        Contactos= findViewById(R.id.Btn_Contactos);

        ProgresBar= findViewById(R.id.ProgresBar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor...");
        progressDialog.setCanceledOnTouchOutside(false);



        Verificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(User.isEmailVerified()){
                    Toast.makeText(Menu_Principal.this, "Cuenta ya verificada", Toast.LENGTH_SHORT).show();
                }else {
                    VerificarCuentaCorreo();
                }
            }
        });
        AgregarPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid_usuario = IdMenu.getText().toString();
                String correo_usuario = Linear.getText().toString();
                Intent intent= new Intent(Menu_Principal.this, Agregar_Pedidos.class);
                intent.putExtra("Uid",uid_usuario);
                intent.putExtra("Correo", correo_usuario);
                startActivity(intent);
            }
        });

        Contactos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String uid_usuario = IdMenu.getText().toString();
                Intent intent= new Intent(Menu_Principal.this, Listar_Contactos.class);
                intent.putExtra("Uid", uid_usuario);
                startActivity(intent);
                //Toast.makeText(Menu_Principal.this,"Contactos", Toast.LENGTH_SHORT).show();

            }
        });

        ListaPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Menu_Principal.this, Listar_Pedidos.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Menu_Principal", "Error al intentar iniciar Listar_Pedidos", e);

                }
            }
        });

        Btn_Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  SalirAplicacion();

            }
        });

    }


    private void VerificarCuentaCorreo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Verificar Cuenta").setMessage("Estas seguro(a) de enviar instrucciones de verificacion a su correo electronico? "
                        + User.getEmail()).setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EnviarCorreoAVerificacion();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Menu_Principal.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void EnviarCorreoAVerificacion() {
        progressDialog.setMessage("Enviando mensaje de verificacion a su correo electronico "+User.getEmail());
        progressDialog.show();

        User.sendEmailVerification()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(Menu_Principal.this, "Instrucciones enviadas, revise su bandeja "+ User.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Menu_Principal.this, "Fallo debido a: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
//h
    private void VerificacionEstadoDeCuenta(){
        String Verificado = "Verificado";
        String No_Verificado = "No Verificado";
        if(User.isEmailVerified()){
            Verificacion.setText(Verificado);
        }else {
            Verificacion.setText(No_Verificado);
        }
    }


    @Override
    protected void onStart() {
        ComprobarInicioSesion();
        super.onStart();
    }

    private void ComprobarInicioSesion(){
        if (User!=null){
            //El usuario ha iniciado sesión
            CargaDeDatos();
        }else {
            //Lo dirigirá al MainActivity
            startActivity(new Intent(Menu_Principal.this,MainActivity.class));
            finish();
            //uno dos tres
        }
    }

    private void CargaDeDatos(){
        VerificacionEstadoDeCuenta();
        Usuarios.child(User.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Si el usuario existe
                if (snapshot.exists()){
                    //El progressbar se oculta
                    ProgresBar.setVisibility(View.GONE);
                    //Los TextView se muestran
                    NombreP.setVisibility(View.VISIBLE);
                    Linear.setVisibility(View.VISIBLE);


                    IdMenu.setVisibility(View.VISIBLE);
                    Linear_Nombres.setVisibility(View.VISIBLE);
                    Linear_Verificacion.setVisibility(View.VISIBLE);
                    Linear_Correo.setVisibility(View.VISIBLE);
                    Verificacion.setVisibility(View.VISIBLE);



                    //Obtener los datos
                    String nombre = ""+snapshot.child("nombre").getValue();
                    String correo = ""+snapshot.child("correo").getValue();
                    String uid = ""+snapshot.child("uid").getValue();

                    //Setear los datos en los respectivos TextView
                    NombreP.setText(nombre);
                    Linear.setText(correo);
                    IdMenu.setText(uid);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    private void SalirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(Menu_Principal.this, MainActivity.class));
        Toast.makeText(this, "Cerraste sesión exitosamente", Toast.LENGTH_SHORT).show();
    }
}