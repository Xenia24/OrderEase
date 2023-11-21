package com.example.agenda_panaderia.Detalles_Pedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda_panaderia.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Detalle_Pedido extends AppCompatActivity {

    String id_pedido, uid_usuario, nombre_usuario,fecha_registro, titulo, descripcion, fecha_pedido, forma_entrega,estado;
    TextView Id_Pedido_Detalle, Id_Usuario_Detalle, Nombre_Detalle, Titulo_Detalle, Descripcion_Detalle, Fecha_Registro_Detalle, Fecha_Pedido_Detalle, Estado_Detalle;

    FirebaseAuth firebaseAuth;
    Button Importante;
    boolean comprobar = false;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        Iniciar_vista();
        RecuperarDatos();
        SetDatosR();
        Verificarimportante();

        Importante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comprobar){
                    EliminarPedidoIm();
                }else{
                    pedidos_importantes();
                }
            }
        });
//r
    }
    private void  Iniciar_vista(){
        Id_Pedido_Detalle=findViewById(R.id.Id_Pedido_Detalle);
        Id_Usuario_Detalle=findViewById(R.id.Id_Usuario_Detalle);
        Nombre_Detalle=findViewById(R.id.Nombre_Detalle);
        Titulo_Detalle=findViewById(R.id.Titulo_Detalle);
        Descripcion_Detalle=findViewById(R.id.Descripcion_Detalle);
        Fecha_Registro_Detalle=findViewById(R.id.Fecha_Registro_Detalle);
        Fecha_Pedido_Detalle=findViewById(R.id.Fecha_Pedido_Detalle);
        Estado_Detalle=findViewById(R.id.Estado_Detalle);
        Importante = findViewById(R.id.Importantes);



        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

    }
    private  void RecuperarDatos(){
        Bundle intent = getIntent().getExtras();

        id_pedido=intent.getString("id_pedido");
        uid_usuario=intent.getString("uid_usuario");
        nombre_usuario=intent.getString("nombre_usuario");
        titulo=intent.getString("titulo");
        descripcion=intent.getString("descripcion");
        fecha_registro=intent.getString("fecha_registro");
        fecha_pedido=intent.getString("fecha_pedido");
        forma_entrega=intent.getString("forma_entrega");
        estado=intent.getString("estado");
    }
    private void SetDatosR(){
        Id_Pedido_Detalle.setText(id_pedido);
        Id_Usuario_Detalle.setText(uid_usuario);
        Nombre_Detalle.setText(nombre_usuario);
        Titulo_Detalle.setText(titulo);
        Descripcion_Detalle.setText(descripcion);
        Fecha_Registro_Detalle.setText(fecha_registro);
        Fecha_Pedido_Detalle.setText(fecha_pedido);
        Estado_Detalle.setText(estado);
    }

    private void pedidos_importantes() {
        if (user == null) {
            Toast.makeText(Detalle_Pedido.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        } else {

            //Obtener datos
            Bundle intent = getIntent().getExtras();

            id_pedido=intent.getString("id_pedido");
            uid_usuario=intent.getString("uid_usuario");
            nombre_usuario=intent.getString("nombre");
            titulo=intent.getString("titulo");
            descripcion=intent.getString("descripcion");
            fecha_registro=intent.getString("fecha_registro");
            fecha_pedido=intent.getString("fecha_pedido");
            forma_entrega=intent.getString("forma_entrega");
            estado=intent.getString("estado");

            String idenpedido_importante = uid_usuario + titulo;

            HashMap<String, String> Pedido_importante = new HashMap<>();
            Pedido_importante.put("id_pedido", id_pedido);
            Pedido_importante.put("uid_usuario",uid_usuario);
            Pedido_importante.put("nombre",  nombre_usuario);
            Pedido_importante.put("fecha_registro", fecha_registro);
            Pedido_importante.put("titulo", titulo);
            Pedido_importante.put("descripcion",descripcion);
            Pedido_importante.put("fecha_pedido",fecha_pedido);
            Pedido_importante.put("forma_entrega", forma_entrega);
            Pedido_importante.put("estado", estado);
            Pedido_importante.put("id_pedido_importante", idenpedido_importante);

            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Usuarios");
            reference.child(firebaseAuth.getUid()).child("Mis pedidos importantes").child(idenpedido_importante).setValue(Pedido_importante).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(Detalle_Pedido.this, "Agregado a pedidos Importantes", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Detalle_Pedido.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    private void EliminarPedidoIm() {
        if (user==null) {
            Toast.makeText(Detalle_Pedido.this,"Ha ocurrido un error", Toast.LENGTH_SHORT).show();;
        }else {
            Bundle intent = getIntent().getExtras();
            uid_usuario = intent.getString("uid_usuario");
            titulo = intent.getString("titulo");

            String ident_pedido_importante= uid_usuario+titulo;

            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Usuarios");
            reference.child(firebaseAuth.getUid()).child("Mis pedidos importantes").child(ident_pedido_importante).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(Detalle_Pedido.this, "La nota dejo de ser importantes", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Detalle_Pedido.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    private void Verificarimportante(){
        if(user==null){
            Toast.makeText(Detalle_Pedido.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }else{
            Bundle intent = getIntent().getExtras();
            uid_usuario = intent.getString("uid_usuario");
            titulo = intent.getString("titulo");
            String ident_pedido_importante= uid_usuario+titulo;

            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Usuarios");
            databaseReference.child(firebaseAuth.getUid()).child("Mis pedidos importantes").child(ident_pedido_importante).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    comprobar = snapshot.exists();

                    if (comprobar) {
                        String importante = "Importante";
                        Importante.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.importante, 0, 0);
                        Importante.setText(importante);
                    } else {
                        String no_importante = "No importante";
                        Importante.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.importante2, 0, 0);
                        Importante.setText(no_importante);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}