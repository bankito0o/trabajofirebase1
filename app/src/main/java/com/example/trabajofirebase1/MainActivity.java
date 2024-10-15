package com.example.trabajofirebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.trabajofirebase1.Clases.Producto;

public class MainActivity extends AppCompatActivity {
    private List<Producto> ListProducto = new ArrayList<>();
    private List<String> ListProductoNombre = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterString;

    private EditText eTNombre, eTCodigo;
    private Button bTAgregar;
    private ListView lvListadoProductos;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTNombre = findViewById(R.id.eTNombre);
        eTCodigo = findViewById(R.id.eTEditorial);
        bTAgregar = findViewById(R.id.bTAgregar);
        lvListadoProductos = findViewById(R.id.lvListadoLibros);

        inicializarFireBase();
        listarDatos();

        bTAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = eTNombre.getText().toString();
                String codigo = eTCodigo.getText().toString();
                if (!nombre.isEmpty() && !codigo.isEmpty()) {
                    Producto producto = new Producto();
                    producto.setIdProducto(UUID.randomUUID().toString());
                    producto.setNombre(nombre);
                    producto.setCodigo(codigo);
                    databaseReference.child("Producto").child(producto.getIdProducto()).setValue(producto);
                    eTNombre.setText("");
                    eTCodigo.setText("");
                }
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListProducto.clear();
                ListProductoNombre.clear();
                for (DataSnapshot objs : snapshot.getChildren()) {
                    Producto p = objs.getValue(Producto.class);
                    if (p != null) {
                        ListProducto.add(p);
                        ListProductoNombre.add(p.getNombre() + " - " + p.getCodigo());
                    }
                }
                arrayAdapterString = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, ListProductoNombre);
                lvListadoProductos.setAdapter(arrayAdapterString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void inicializarFireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}

