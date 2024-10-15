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

import com.example.trabajofirebase1.Clases.Libro;


public class MainActivity extends AppCompatActivity {
    private List<Libro> ListLibro = new ArrayList<Libro>();
    private List<String> ListLibroNombre = new ArrayList();
    ArrayAdapter<Libro> arrayAdapterLibro;
    ArrayAdapter<String> arrayAdapterString;


    EditText eTNombre,eTEditorial;
    Button bTBoton, btEliminar;
    ListView lvListadoLibros;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTNombre=findViewById(R.id.eTNombre);
        eTEditorial=findViewById(R.id.eTEditorial);
        bTBoton=findViewById(R.id.bTAgregar);
        lvListadoLibros=findViewById(R.id.lvListadoLibros);
        inicializarFireBase();
        listarDatos();

        bTBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Libro libro = new Libro();
                //libro.setIdAutor("11111");
                libro.setIdAutor(UUID.randomUUID().toString());
                libro.setNombre(eTNombre.getText().toString());
                libro.setEstado(eTEditorial.getText().toString());
                databaseReference.child("Libro").child(libro.getIdAutor()).setValue(libro);


            }
        });


    }
    private void listarDatos() {
        databaseReference.child("Libro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListLibro.clear();
                for (DataSnapshot objs : snapshot.getChildren()){
                    Libro li =objs.getValue(Libro.class);
                    ListLibro.add(li);
                    ListLibroNombre.add(""+li.getNombre()+" "+li.getEstado());
                    arrayAdapterString =new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1,ListLibroNombre);
                    lvListadoLibros.setAdapter(arrayAdapterString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();
    }
}