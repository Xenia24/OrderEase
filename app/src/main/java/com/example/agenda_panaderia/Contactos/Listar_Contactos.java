package com.example.agenda_panaderia.Contactos;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Listar_Contactos extends AppCompatActivity {
    ImageView ImageView, atras;
    RecyclerView recyclerViewContactos;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference BD_Usuarios;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseRecyclerAdapter <Contacto, ViewHolderContacto> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Contacto> firebaseRecyclerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contactos);
        ImageView = findViewById(R.id.Agregar_Contacto);
        atras = findViewById(R.id.regresar);

        recyclerViewContactos = findViewById(R.id.recyclerViewContactos);
        recyclerViewContactos.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        BD_Usuarios = firebaseDatabase.getReference("Usuarios");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();



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

        atras.setOnClickListener(new View.OnClickListener() {

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
                        Toast.makeText(Listar_Contactos.this, "On item click", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(Listar_Contactos.this, "On item long click", Toast.LENGTH_SHORT).show();
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
    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }
}