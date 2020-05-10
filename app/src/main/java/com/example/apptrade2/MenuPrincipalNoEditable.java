package com.example.apptrade2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.datatype.Duration;

public class MenuPrincipalNoEditable extends AppCompatActivity {

    FloatingActionMenu btn;
    FloatingActionButton btn2,btn1;
    private static String nombre;
    private static String buque;
    private static String partida;
    private static String subpartida;
    private static String hora;
    private static String fecha;
    private static String sumaHoras;
    TextView text1,text2,text3,text4,text5,text6,text7;

    ArrayList<String> lista;
    TextView text;

    ListView list;


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
        list = findViewById(R.id.list2);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MenuPrincipalNoEditable.this, MainActivity.class);
                startActivityForResult(i,0);




            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MenuPrincipalNoEditable.this, ListadoPartes.class);
                startActivityForResult(i,0);




            }
        });

        btn.setClosedOnTouchOutside(true);





        final Cursor consulta = bbdd.rawQuery("SELECT * FROM datosAbanceDef ", null);




        /*horaTotal = sumarHoras(MainActivity.diferencia,horaTemporal);*/

        /*text.setText("Hoy llevas trabajadas: " + tipoHora.format(MainActivity.diferencia)+ " H");*/





        lista = llenar_lista();


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        list.setAdapter(adapter);

    }




    public ArrayList llenar_lista(){

        ArrayList <String> lista = new ArrayList<>();
        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd = admin.getWritableDatabase();

        final Cursor consulta = bbdd.rawQuery("SELECT * FROM datosAbanceDef ", null);

        if (consulta.moveToLast()) {

            do{

                lista.add( "\n"+"Nombre: " + consulta.getString(0) + "\n" + "Buque: " + consulta.getString(2) + "\n " + "Partida: "  + consulta.getString(3) + "\n " + "Subpartida: " + consulta.getString(4) + "\n "+ "Duración: " + consulta.getString(7) + "\n" + "Fecha: " + consulta.getString(6) + "\n" );

            }while(consulta.moveToPrevious());

        }



        return lista;
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
