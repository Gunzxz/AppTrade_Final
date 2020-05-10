package com.example.apptrade2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class ListadoPartes extends AppCompatActivity {

    public  String nombre,nombre2;
    public  String buque, buque2;
    public  String partida, partida2;
    public  String subpartida,subpartida2;
    public static String hora;
    public static String fecha;
    public static String sumaHoras;
    private Transition transition;
    public static SimpleDateFormat tipoHora = new SimpleDateFormat("HH:mm", Locale.US);
    public static Date horaTotal ;
    public static Date horaTemporal;
    public static String date = "00:00";

    ArrayList<String> lista;
    TextView text;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_partes);

        final ListView list = findViewById(R.id.list1);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd = admin.getWritableDatabase();



        final Cursor consulta = bbdd.rawQuery("SELECT * FROM datosAbance ", null);




            /*horaTotal = sumarHoras(MainActivity.diferencia,horaTemporal);*/

            /*text.setText("Hoy llevas trabajadas: " + tipoHora.format(MainActivity.diferencia)+ " H");*/





        lista = llenar_lista();


                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                       Intent i = new Intent(ListadoPartes.this, MenuPrincipal.class);
                       startActivityForResult(i,0);



                    }
                });




             list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                 @Override
                 public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                     AlertDialog.Builder builder = new AlertDialog.Builder(ListadoPartes.this);
                     builder.setMessage("¿Seguro que quieres borrar este parte?")
                             .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {


                                     final SQLiteDatabase bbdd = admin.getWritableDatabase();

                                     if (consulta.moveToLast()) {

                                         nombre2 = consulta.getString(0);
                                         buque2 = consulta.getString(2);
                                         partida2 = consulta.getString(3);
                                         subpartida2 = consulta.getString(4);
                                         hora = consulta.getString(5);
                                         fecha = consulta.getString(6);
                                         sumaHoras = consulta.getString(7);



                                     }


                                     bbdd.delete("datosAbance","nombreReal = '" + nombre2 + "' AND nombreBuq = '"+ buque2 +"' AND partida = '" + partida2 + "' AND subpartida = '" + subpartida2 + "' AND horas = '" + hora + "' AND fecha = '" + fecha + "'",null );

                                     Intent i2 = new Intent(ListadoPartes.this, ListadoPartes.class);
                                     startActivityForResult(i2,0);
                                     finish();


                                 }
                             })
                             .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {

                                 }
                             });

                     builder.create();
                     builder.show();



                     return false;
                 }
             });








    }




    public ArrayList llenar_lista(){

        ArrayList <String> lista = new ArrayList<>();
        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd = admin.getWritableDatabase();

        final Cursor consulta = bbdd.rawQuery("SELECT * FROM datosAbance ", null);

        if (consulta.moveToLast()) {

            do{

                lista.add( "\n"+"Nombre: " + consulta.getString(0) + "\n" + "Buque: " + consulta.getString(2) + "\n " + "Partida: "  + consulta.getString(3) + "\n " + "Subpartida: " + consulta.getString(4) + "\n "+ "Duración: " + consulta.getString(7) + "\n" + "Fecha: " + consulta.getString(6) + "\n" );

            }while(consulta.moveToPrevious());

        }



        return lista;
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bar3, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.item1:

                Intent i = new Intent(ListadoPartes.this, login.class);
                startActivityForResult(i,0);
                finish();

            case R.id.item2:


                final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
                final SQLiteDatabase bbdd = admin.getWritableDatabase();

                bbdd.execSQL("INSERT INTO datosAbanceDef(nombreReal, nombre , nombreBuq, partida, subpartida, horas, fecha, sumaHoras) SELECT nombreReal, nombre, nombreBuq, partida, subpartida, horas, fecha, sumaHoras FROM datosAbance");
                bbdd.execSQL("DELETE FROM datosAbance");

                finish();
                Intent i2 = new Intent(ListadoPartes.this, ListadoPartes.class);
                startActivityForResult(i2,0);


        }



        return super.onOptionsItemSelected(item);
    }

   /* public static Date sumarHoras(Date dateNuevo, Date dateOriginal) {

        long milliseconds = dateOriginal.getTime() + dateNuevo.getTime() ;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();

    }*/






}






