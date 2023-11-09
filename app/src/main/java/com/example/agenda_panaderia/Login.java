package com.example.agenda_panaderia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    EditText CorreoL,PasswordL;
    Button Btn_LoginI;
    TextView UsuarioNuevo;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String correo = "", password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CorreoL = findViewById(R.id.CorreoL);
        PasswordL = findViewById(R.id.PasswordL);
        Btn_LoginI = findViewById(R.id.Btn_LoginI);
        UsuarioNuevo = findViewById(R.id.UsuarioNuevo);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Espere Por Favor");
        progressDialog.setCanceledOnTouchOutside(false);

        Btn_LoginI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarDatos();
            }
        });

        UsuarioNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });
    }

    private void ValidarDatos() {
        correo = CorreoL.getText().toString();
        password = PasswordL.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Correo Invalido", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese Contraseña", Toast.LENGTH_SHORT).show();
        }
        else{
            LoginUsuario();
        }
    }

    private void LoginUsuario() {
        progressDialog.setMessage("Iniciando Sesion....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    startActivity(new Intent(Login.this, Menu_Principal.class));
                    Toast.makeText(Login.this, "Bienvenido(a)" + user.getEmail(),Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Verifique si la Contraseña y Correo son Correctos", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}