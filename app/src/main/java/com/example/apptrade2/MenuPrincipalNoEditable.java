package com.example.apptrade2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.w3c.dom.Text;

public class MenuPrincipalNoEditable extends AppCompatActivity {

    FloatingActionMenu btn;
    FloatingActionButton btn2,btn1;
    private static String nombre;
    private static String buque;
    private static String partida;
    private static String subpartida;
    private static String hora;
    private static String fecha;
    TextView text1,text2,text3,text4,text5,text6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_no_editable);
        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd = admin.getWritableDatabase();

        btn = findViewById(R.id.btn111);
        btn1 = findViewById(R.id.btn222);
        btn2 = findViewById(R.id.btn333);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MenuPrincipalNoEditable.this, MainActivity.class);
                startActivityForResult(i,0);
                finish();



            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MenuPrincipalNoEditable.this, ListadoPartes.class);
                startActivityForResult(i,0);
                finish();



            }
        });

        btn.setClosedOnTouchOutside(true);

        final Cursor consulta = bbdd.rawQuery("SELECT * FROM datosAbance ", null);

        if (consulta.moveToLast()) {

            nombre = consulta.getString(0);
            buque = consulta.getString(2);
            partida = consulta.getString(3);
            subpartida = consulta.getString(4);
            hora = consulta.getString(5);
            fecha = consulta.getString(6);

            text1.setText(nombre);
            text2.setText(buque);
            text3.setText(partida);
            text4.setText(subpartida);
            text5.setText(hora);
            text6.setText(fecha);


        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bar2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.item1:

                Intent i = new Intent(MenuPrincipalNoEditable.this, login.class);
                startActivityForResult(i,0);
                finish();



        }



        return super.onOptionsItemSelected(item);
    }

}
