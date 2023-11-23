package com.example.agenda_panaderia.Pedidos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda_panaderia.Menu_Principal;
import com.example.agenda_panaderia.Objetos.Pedido;
import com.example.agenda_panaderia.R;
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
    android.widget.ImageView  atras;
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
        atras = findViewById(R.id.regresar2);
        recyclerviewPedidos = findViewById(R.id.recyclerviewPedidos);
        recyclerviewPedidos.setHasFixedSize(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        dialog = new Dialog(Listar_Pedidos.this);
//        dialog_filtrar = new Dialog(Listar_Pedidos.this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        BASE_DE_DATOS = firebaseDatabase.getReference("Pedidos_Realizados");
//        dialog = new Dialog(Listar_Pedidos.this);
//        dialog = new Dialog(Listar_Pedidos.this);
        ListarNotasUsuarios();
        atras.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Listar_Pedidos.this, Menu_Principal.class);
                startActivity(intent);
            }
        });
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
                        intent.putExtra("id_pedido", id_pedido);
                        intent.putExtra("uid_usuario", uid_usuario);
                        intent.putExtra("nombre_usuario", nombre);
                        intent.putExtra("fecha_registro", fecha_registro);
                        intent.putExtra("titulo", titulo);
                        intent.putExtra("descripcion", descripcion);
                        intent.putExtra("fecha_pedido", fecha_nota);
                        intent.putExtra("forma_entrega", forma_entrega);
                        intent.putExtra("estado", estado);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(Listar_Pedidos.this, "Mantener precionado ", Toast.LENGTH_SHORT).show();


                        String id_pedido = getItem(position).getId_pedido();
                        String uid_usuario = getItem(position).getUid_usuario();
                        String nombre=getItem(position).getNombre();
                        String titulo = getItem(position).getTitulo();
                        String descripcion = getItem(position).getDescripcion();
                        String fecha_registro = getItem(position).getFecha_actual();
                        String fecha_nota = getItem(position).getFecha_pedido();
                        String forma_entrega = getItem(position).getForma_entrega();
                        String estado = getItem(position).getEstado();

                        Button CD_Eliminar, CD_Actualizar;

                        dialog.setContentView(R.layout.dialogo_opciones);

                        CD_Eliminar = dialog.findViewById(R.id.CD_Eliminar);
                        CD_Actualizar=dialog.findViewById(R.id.CD_Actualizar);

                        CD_Eliminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EliminarPedido(id_pedido);
                                dialog.dismiss();
                            }


                        });

                        CD_Actualizar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(Listar_Pedidos.this, Actualizar_Pedidos.class);
                                intent.putExtra("id_pedido", id_pedido);
                                intent.putExtra("uid_usuario", uid_usuario);
                                intent.putExtra("nombre_usuario",nombre);
                                intent.putExtra("titulo", titulo);
                                intent.putExtra("descripcion", descripcion);
                                intent.putExtra("fecha_registro", fecha_registro);
                                intent.putExtra("fecha_pedido",fecha_nota);
                                intent.putExtra("forma_entrega", forma_entrega);
                                intent.putExtra("estado", estado);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
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
    private void EliminarPedido(String id_pedido) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Listar_Pedidos.this);
        builder.setTitle("Eliminar pedido");
        builder.setMessage("¿Desea eliminar el pedido?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //ELIMINAR PEDIDO EN BD
                Query query = BASE_DE_DATOS.orderByChild("id_pedido").equalTo(id_pedido);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(Listar_Pedidos.this, "Pedido eliminado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Toast.makeText(Listar_Pedidos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Listar_Pedidos.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }



}
