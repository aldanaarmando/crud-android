package com.example.proyecto;


import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class editar extends AppCompatActivity {

    EditText nombre, apellido;
    Button editar, eliminar;
    int id;
    String nombre2, apellido2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        Bundle recibir = getIntent().getExtras();

        if(recibir!=null){
            id = recibir.getInt("ID");
            nombre2 = recibir.getString("NOMBRE");
            apellido2 = recibir.getString("APELLIDO");

        }


        nombre = (EditText) findViewById(R.id.nombre);
        apellido = (EditText) findViewById(R.id.apellido);

        nombre.setText(nombre2);
        apellido.setText(apellido2);

        editar = (Button) findViewById(R.id.editar);
        eliminar = (Button) findViewById(R.id.eliminar);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar(id);
                onBackPressed();
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id, nombre.getText().toString(), apellido.getText().toString());
                onBackPressed();
            }
        });
    }

    private void editar(int id, String nombre, String apellido){
        BaseHelper helper = new BaseHelper(this,"Demo",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "update PERSONA set NOMBRE ='"+nombre+"', APELLIDO = '"+apellido+"' where ID = "+id;
        db.execSQL(sql);
        db.close();
    }

    private void eliminar(int id){
        BaseHelper helper = new BaseHelper(this,"Demo",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "delete from PERSONA where ID="+id;
        db.execSQL(sql);
        db.close();
    }


}