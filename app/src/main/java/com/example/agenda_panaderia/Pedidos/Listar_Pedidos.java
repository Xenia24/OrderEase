package com.example.agenda_panaderia.Pedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.agenda_panaderia.Contactos.Actualizar_Contactos;
import com.example.agenda_panaderia.Contactos.Listar_Contactos;
import com.example.agenda_panaderia.Objetos.Contacto;
import com.example.agenda_panaderia.Objetos.Pedido;
import com.example.agenda_panaderia.R;
import com.example.agenda_panaderia.ViewHolder.ViewHolderContacto;
import com.example.agenda_panaderia.ViewHolder.ViewHolder_Pedidos;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Listar_Pedidos extends AppCompatActivity {

    RecyclerView recyclerviewPedidos;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference BASE_DE_DATOS;

    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerAdapter<Pedido, ViewHolder_Pedidos> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Pedido> options;

    Dialog dialog;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pedidos);

        recyclerviewPedidos = findViewById(R.id.recyclerviewPedidos);
        recyclerviewPedidos.setHasFixedSize(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

//        dialog = new Dialog(Listar_Pedidos.this);
//        dialog_filtrar = new Dialog(Listar_Pedidos.this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        BASE_DE_DATOS = firebaseDatabase.getReference("Pedidos_Realizados");
//        dialog = new Dialog(Listar_Pedidos.this);
//        dialog = new Dialog(Listar_Pedidos.this);
        ListarNotasUsuarios();
        //Estado_Filtro();
    }

    private void ListarNotasUsuarios(){
        //consulta
        Query query = BASE_DE_DATOS.orderByChild("uid_usuario").equalTo(user.getUid());
        //Query query = BASE_DE_DATOS.child(user.getUid()).child("Contactos").orderByChild("nombres");

        options = new FirebaseRecyclerOptions.Builder<Pedido>().setQuery(query, Pedido.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Pedido, ViewHolder_Pedidos>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder_Pedidos viewHolder_pedidos, int position, @NotNull Pedido pedido) {
                viewHolder_pedidos.SetearDatosPedidos(
                        getApplicationContext(),
                        pedido.getId_pedido(),
                        pedido.getUid_usuario(),
                        pedido.getNombre(),
                        pedido.getFecha_actual(),
                        pedido.getTitulo(),
                        pedido.getDescripcion(),
                        pedido.getFecha_pedido(),
                        pedido.getForma_entrega(),
                        pedido.getEstado()
                );
            }


            @Override
            public ViewHolder_Pedidos onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido,parent,false);
                ViewHolder_Pedidos viewHolder_pedidos = new ViewHolder_Pedidos(view);
                viewHolder_pedidos.setOnClickListener(new ViewHolder_Pedidos.ClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        String id_pedido = getItem(position).getId_pedido();
                        String uid_usuario = getItem(position).getUid_usuario();
                        String nombre=getItem(position).getNombre();
                        String titulo = getItem(position).getTitulo();
                        String descripcion = getItem(position).getDescripcion();
                        String fecha_registro = getItem(position).getFecha_actual();
                        String fecha_nota = getItem(position).getFecha_pedido();
                        String forma_entrega = getItem(position).getForma_entrega();
                        String estado = getItem(position).getEstado();

                        //Enviamos los datos a la siguiente actividad
                        Intent intent = new Intent(Listar_Pedidos.this, Detalle_Pedido.class);
                        intent.putExtra("id_nota", id_pedido);
                        intent.putExtra("uid_usuario", uid_usuario);
                        intent.putExtra("nombre_usuario", nombre);
                        intent.putExtra("fecha_registro", fecha_registro);
                        intent.putExtra("titulo", titulo);
                        intent.putExtra("descripcion", descripcion);
                        intent.putExtra("fecha_nota", fecha_nota);
                        intent.putExtra("forma_entrega", forma_entrega);
                        intent.putExtra("estado", estado);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(Listar_Pedidos.this, "click ", Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder_pedidos;
            }
        };

        linearLayoutManager = new LinearLayoutManager(Listar_Pedidos.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerviewPedidos.setLayoutManager(linearLayoutManager);
        recyclerviewPedidos.setAdapter(firebaseRecyclerAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.startListening();
        }
    }


}