package com.example.dbexample;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataBase database;

    EditText ingresarTareas, editarTareas;

    Button btnAgregar, btnEditar, btnEliminar;

    ListView listarTareas;

    ArrayList<String> tareas;

    ArrayAdapter<String> adapter;

    String seleccionarTareas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        database = new DataBase(this);
        ingresarTareas = findViewById(R.id.editTextTask);
        editarTareas = findViewById(R.id.editTextEditTask);
        btnAgregar = findViewById(R.id.buttonAdd);
        btnEditar = findViewById(R.id.buttonEdit);
        btnEliminar = findViewById(R.id.buttonDelete);
        listarTareas = findViewById(R.id.listViewTasks);

        tareas = database.getAllTareas();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tareas);
        listarTareas.setAdapter(adapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tarea = ingresarTareas.getText().toString();
                if (!tarea.isEmpty()) {
                    database.insertTarea(tarea);
                    actualizarListadoTareas();
                    ingresarTareas.setText(" ");
                }
            }
        });

        listarTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                seleccionarTareas = tareas.get(position);
                editarTareas.setText(seleccionarTareas);
                btnEditar.setVisibility(View.VISIBLE);
                btnEliminar.setVisibility(View.VISIBLE);
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevaTarea = editarTareas.getText().toString();
                if(!nuevaTarea.isEmpty()){
                    database.updateTarea(seleccionarTareas,nuevaTarea);
                    actualizarListadoTareas();
                    LimpiarCampos();
                }
            }
        });
    btnEliminar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            database.deleteTarea(seleccionarTareas);
            actualizarListadoTareas();
            LimpiarCampos();
        }
    });


    }
    public void actualizarListadoTareas(){
        tareas.clear();
        tareas.addAll(database.getAllTareas());
        adapter.notifyDataSetChanged();
    }
    public void LimpiarCampos(){
        editarTareas.setText("");
        editarTareas.setVisibility(View.GONE);
        btnEditar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);
    }


}
