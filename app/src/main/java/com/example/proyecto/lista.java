package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class lista extends AppCompatActivity {

    ListView listado;
    ArrayList<String> vectorlista;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        cargar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        listado = (ListView) findViewById(R.id.listado);
        cargar();
        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(lista.this,vectorlista.get(position), Toast.LENGTH_SHORT).show();
                int clave = Integer.parseInt(vectorlista.get(position).split(" ")[0]);
                String nombre = vectorlista.get(position).split(" ")[1];
                String apellido = vectorlista.get(position).split(" ")[2];
                Intent intent = new Intent(lista.this, editar.class);
                intent.putExtra("ID", clave);
                intent.putExtra("NOMBRE", nombre);
                intent.putExtra("APELLIDO", apellido);
                startActivity(intent);
            }
        });

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargar(){
        vectorlista = ListaPersona();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vectorlista);
        listado.setAdapter(adapter);
    }

    private ArrayList<String> ListaPersona(){
        ArrayList<String> datos = new ArrayList<String>();
        BaseHelper helper = new BaseHelper(this,"Demo",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select ID, NOMBRE, APELLIDO FROM PERSONA";
        Cursor c = db.rawQuery(sql,null);
        if (c.moveToFirst()){
            do{
                String linea = c.getInt(0)+" "+ c.getString(1)+" "+ c.getString(2);
                datos.add(linea);
            }while (c.moveToNext());
        }
        db.close();
        return datos;
    }
}