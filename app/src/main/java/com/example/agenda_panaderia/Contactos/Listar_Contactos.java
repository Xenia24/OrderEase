package com.example.agenda_panaderia.Contactos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda_panaderia.Menu_Principal;
import com.example.agenda_panaderia.Objetos.Contacto;
import com.example.agenda_panaderia.R;
import com.example.agenda_panaderia.ViewHolder.ViewHolderContacto;
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


public class Listar_Contactos extends AppCompatActivity {
    ImageView ImageView, atras1;
    RecyclerView recyclerViewContactos;
    FirebaseDatabase firebaseDatabase;

    SearchView buscar;
    DatabaseReference BD_Usuarios;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseRecyclerAdapter <Contacto, ViewHolderContacto> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Contacto> firebaseRecyclerOptions;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contactos);
        ImageView = findViewById(R.id.Agregar_Contacto);
        atras1 = findViewById(R.id.regresar);
        buscar = findViewById(R.id.Buscar_contactos);
        dialog = new Dialog((Listar_Contactos.this));
        recyclerViewContactos = findViewById(R.id.recyclerViewContactos);
        recyclerViewContactos.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        BD_Usuarios = firebaseDatabase.getReference("Usuarios");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();



        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                BuscarContacto(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BuscarContacto(newText);
                return false;
            }
        });


        ImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String Uid_Recuperado = getIntent().getStringExtra("Uid");
                Intent intent = new Intent(Listar_Contactos.this, Agregar_Contactos.class);
                intent.putExtra("Uid", Uid_Recuperado);
                startActivity(intent);

                //Toast.makeText(Menu_Principal.this,"Contactos", Toast.LENGTH_SHORT).show();
            }
        });

        atras1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Listar_Contactos.this, Menu_Principal.class);
                startActivity(intent);
                //Toast.makeText(Menu_Principal.this,"Contactos", Toast.LENGTH_SHORT).show();
                //uno dos tres
            }
        });
        ListarContactos();


   }

    private void ListarContactos() {
        Query query = BD_Usuarios.child(user.getUid()).child("Contactos").orderByChild("nombres");

        firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Contacto>().setQuery(query, Contacto.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Contacto, ViewHolderContacto>(firebaseRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull ViewHolderContacto viewHolderContacto, int position, @NonNull Contacto contacto) {
                viewHolderContacto.SetearDatosContacto(
                        getApplicationContext(),
                        contacto.getId_contacto(),
                        contacto.getUid_contacto(),
                        contacto.getNombres(),
                        contacto.getApellidos(),
                        contacto.getCorreo(),
                        contacto.getTelefono(),
                        contacto.getDireccion()
                );

            }

            @NonNull
            @Override
            public ViewHolderContacto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacto, parent, false);
                ViewHolderContacto viewHolderContacto = new ViewHolderContacto(view);
                viewHolderContacto.setOnClickListener(new ViewHolderContacto.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(Listar_Contactos.this, "Mantener Precionado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        String id_c = getItem(position).getId_contacto();
                        String uid_usuario = getItem(position).getUid_contacto();
                        String Nombres_c = getItem(position).getNombres();
                        String Apellido_c = getItem(position).getApellidos();
                        String Correo_c = getItem(position).getCorreo();
                        String Telefono_c = getItem(position).getTelefono();
                        String Direccion_c = getItem(position).getDireccion();
                        //Toast.makeText(Listar_Contactos.this, "On item long click", Toast.LENGTH_SHORT).show();
                                                Button CD_Eliminar,Btn_Actualizar_C_A;

                        dialog.setContentView(R.layout.dialogo_opciones);

                        CD_Eliminar = dialog.findViewById(R.id.CD_Eliminar);
                        Btn_Actualizar_C_A= dialog.findViewById(R.id.CD_Actualizar);


                        CD_Eliminar.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(Listar_Contactos.this, "Eliminar contacto", Toast.LENGTH_SHORT).show();
                                Eliminar_contacto(id_c);
                                dialog.dismiss();
                            }

                        });
                        Btn_Actualizar_C_A.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Listar_Contactos.this, Actualizar_Contactos.class);
                                Toast.makeText(Listar_Contactos.this, "Editar contacto", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                intent.putExtra("id_c", id_c);
                                intent.putExtra("uid_usuario", uid_usuario);
                                intent.putExtra("Nombre_c", Nombres_c);
                                intent.putExtra("Apellidos_c", Apellido_c);
                                intent.putExtra("Correo_c", Correo_c);
                                intent.putExtra("Telefono_c", Telefono_c);
                                intent.putExtra("Direccion_C_A", Direccion_c);
                                startActivity(intent);

                            }
                        });


                                dialog.show();
                    }
                });
                return viewHolderContacto;

            }

        };
        recyclerViewContactos.setLayoutManager(new GridLayoutManager(Listar_Contactos.this, 2));
        {
            firebaseRecyclerAdapter.startListening();
            recyclerViewContactos.setAdapter(firebaseRecyclerAdapter);
        }
    }
    private void BuscarContacto(String Nombre_Contacto) {
        Query query = BD_Usuarios.child(user.getUid()).child("Contactos").orderByChild("nombres").startAt(Nombre_Contacto).endAt(Nombre_Contacto+"\uf8ff");

        firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Contacto>().setQuery(query, Contacto.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Contacto, ViewHolderContacto>(firebaseRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull ViewHolderContacto viewHolderContacto, int position, @NonNull Contacto contacto) {
                viewHolderContacto.SetearDatosContacto(
                        getApplicationContext(),
                        contacto.getId_contacto(),
                        contacto.getUid_contacto(),
                        contacto.getNombres(),
                        contacto.getApellidos(),
                        contacto.getCorreo(),
                        contacto.getTelefono(),
                        contacto.getDireccion()
                );

            }

            @NonNull
            @Override
            public ViewHolderContacto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacto, parent, false);
                ViewHolderContacto viewHolderContacto = new ViewHolderContacto(view);
                viewHolderContacto.setOnClickListener(new ViewHolderContacto.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(Listar_Contactos.this, "Mantener Precionado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        String id_c = getItem(position).getId_contacto();
                        String uid_usuario = getItem(position).getUid_contacto();
                        String Nombres_c = getItem(position).getNombres();
                        String Apellido_c = getItem(position).getApellidos();
                        String Correo_c = getItem(position).getCorreo();
                        String Telefono_c = getItem(position).getTelefono();
                        String Direccion_c = getItem(position).getDireccion();
                        //Toast.makeText(Listar_Contactos.this, "On item long click", Toast.LENGTH_SHORT).show();
                        Button CD_Eliminar,Btn_Actualizar_C_A;

                        dialog.setContentView(R.layout.dialogo_opciones);

                        CD_Eliminar = dialog.findViewById(R.id.CD_Eliminar);
                        Btn_Actualizar_C_A= dialog.findViewById(R.id.CD_Actualizar);


                        CD_Eliminar.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(Listar_Contactos.this, "Eliminar contacto", Toast.LENGTH_SHORT).show();
                                Eliminar_contacto(id_c);
                                dialog.dismiss();
                            }

                        });
                        Btn_Actualizar_C_A.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Listar_Contactos.this, Actualizar_Contactos.class);
                                Toast.makeText(Listar_Contactos.this, "Editar contacto", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                intent.putExtra("id_c", id_c);
                                intent.putExtra("uid_usuario", uid_usuario);
                                intent.putExtra("Nombre_c", Nombres_c);
                                intent.putExtra("Apellidos_c", Apellido_c);
                                intent.putExtra("Correo_c", Correo_c);
                                intent.putExtra("Telefono_c", Telefono_c);
                                intent.putExtra("Direccion_C_A", Direccion_c);
                                startActivity(intent);

                            }
                        });


                        dialog.show();
                    }
                });
                return viewHolderContacto;

            }

        };
        recyclerViewContactos.setLayoutManager(new GridLayoutManager(Listar_Contactos.this, 2));
        {
            firebaseRecyclerAdapter.startListening();
            recyclerViewContactos.setAdapter(firebaseRecyclerAdapter);
        }
    }

    private void Eliminar_contacto(String id_contacto) {
        AlertDialog.Builder builder= new AlertDialog.Builder(Listar_Contactos.this);
        builder.setTitle("Eliminar");
        builder.setMessage("Desea eliminar el contacto?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query query= BD_Usuarios.child(user.getUid()).child("Contactos").orderByChild("id_contacto").equalTo(id_contacto);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(Listar_Contactos.this, "Contacto elimindado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Listar_Contactos.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Listar_Contactos.this, "Evento Canonico Incompleto", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agregar_contactos, menu);
        MenuItem item = menu.findItem(R.id.Buscar_contactos);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               BuscarContacto(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BuscarContacto(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}