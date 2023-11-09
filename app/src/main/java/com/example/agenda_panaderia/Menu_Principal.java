package com.example.agenda_panaderia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

public class Menu_Principal extends AppCompatActivity {

    Button Btn_Salir;
    FirebaseAuth firebaseAuth;
    FirebaseUser User;
    TextView NombreP,Linear_Correo;
    ProgressBar ProgresBar;
    DatabaseReference Usuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("OrderEase");
        NombreP=findViewById(R.id.NombreP);
        Linear_Correo= findViewById(R.id.CorreoP);
        ProgresBar= findViewById(R.id.ProgresBar);

        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");
        Btn_Salir=findViewById(R.id.Btn_Salir);
        firebaseAuth= FirebaseDatabase.getInstance();
        User= firebaseAuth.getCurrentUser();

        Btn_Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  SalirAplicacion();

            }
        });


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
        }
    }

    private void CargaDeDatos(){
        Usuarios.child(User.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Si el usuario existe
                if (snapshot.exists()){
                    //El progressbar se oculta
                    ProgressBar.setVisibility(View.GONE);
                    //Los TextView se muestran
                    NombreP.setVisibility(View.VISIBLE);
                    Linear_Correo.setVisibility(View.VISIBLE);

                    //Obtener los datos
                    String nombres = ""+snapshot.child("nombres").getValue();
                    String correo = ""+snapshot.child("correo").getValue();

                    //Setear los datos en los respectivos TextView
                    NombreP.setText(nombres);
                    Linear_Correo.setText(correo);

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


