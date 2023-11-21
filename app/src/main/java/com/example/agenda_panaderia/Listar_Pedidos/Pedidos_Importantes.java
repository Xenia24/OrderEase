package com.example.agenda_panaderia.Listar_Pedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.agenda_panaderia.Detalles_Pedidos.Detalle_Pedido;
import com.example.agenda_panaderia.Objetos.Pedido;
import com.example.agenda_panaderia.R;
import com.example.agenda_panaderia.ViewHolder.ViewHolderImportante;
import com.example.agenda_panaderia.ViewHolder.ViewHolder_Pedidos;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.jetbrains.annotations.NotNull;

public class Pedidos_Importantes extends AppCompatActivity {

    RecyclerView RecyclerViewPedidosI;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference Mis_Usuarios;
    DatabaseReference Mis_Pedidos;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseRecyclerAdapter<Pedido, ViewHolderImportante> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Pedido> firebaseRecyclerOptions;

    LinearLayoutManager linearLayoutManager;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_importantes);
        RecyclerViewPedidosI = findViewById(R.id.RecyclerViewPedidosI);
        RecyclerViewPedidosI.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Mis_Usuarios = firebaseDatabase.getReference("Usuarios");
        Mis_Pedidos = firebaseDatabase.getReference("Mis pedidos importantes");

        //dialog = new Dialog(Pedidos_Importantes.this);

        comprobarUsuario();
    }

    private void comprobarUsuario() {
        if (user == null) {
            Toast.makeText(Pedidos_Importantes.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }else{
            Listar_Pedidos_Importantes();
        }
    }
    private void Listar_Pedidos_Importantes() {
        //consulta
//        Query query = BASE_DE_DATOS.orderByChild("uid_usuario").equalTo(user.getUid());
        //Query query = BASE_DE_DATOS.child(user.getUid()).child("Contactos").orderByChild("nombres");

        firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Pedido>().setQuery(Mis_Usuarios.child(user.getUid()).child("Mis pedidos importantes"), Pedido.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Pedido, ViewHolderImportante>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderImportante viewHolderImportante, int position, @NotNull Pedido pedido) {
                viewHolderImportante.SetearDatosPedidos(
                        getApplicationContext(),
                        pedido.getId_pedido(),
                        pedido.getUid_usuario(),
                        pedido.getNombre(),
                        pedido.getFecha_actual(),
                        pedido.getTitulo(),
                        pedido.getDescripcion(),
                        pedido.getFecha_pedido(),
                        pedido.getEstado()
                );
            }


            @Override
            public ViewHolderImportante onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedidos_importantes,parent,false);
                ViewHolderImportante viewHolderImportante = new ViewHolderImportante(view);
                viewHolderImportante.setOnClickListener(new ViewHolderImportante.ClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        String id_pedido = getItem(position).getId_pedido();
                        String uid_usuario = getItem(position).getUid_usuario();
                        String nombre=getItem(position).getNombre();
                        String titulo = getItem(position).getTitulo();
                        String descripcion = getItem(position).getDescripcion();
                        String fecha_registro = getItem(position).getFecha_actual();
                        String fecha_pedido = getItem(position).getFecha_pedido();
                        String forma_entrega = getItem(position).getForma_entrega();
                        String estado = getItem(position).getEstado();

                        //Enviamos los datos a la siguiente actividad
                        Intent intent = new Intent(Pedidos_Importantes.this, Detalle_Pedido.class);
                        intent.putExtra("id_pedido", id_pedido);
                        intent.putExtra("uid_usuario", uid_usuario);
                        intent.putExtra("nombre_usuario", nombre);
                        intent.putExtra("fecha_registro", fecha_registro);
                        intent.putExtra("titulo", titulo);
                        intent.putExtra("descripcion", descripcion);
                        intent.putExtra("fecha_pedido", fecha_pedido);
                        intent.putExtra("forma_entrega", forma_entrega);
                        intent.putExtra("estado", estado);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                return viewHolderImportante;
            }
        };

        linearLayoutManager = new LinearLayoutManager(Pedidos_Importantes.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        RecyclerViewPedidosI.setLayoutManager(linearLayoutManager);
        RecyclerViewPedidosI.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    protected void onStart() {
        if(firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
        super.onStart();
    }

}