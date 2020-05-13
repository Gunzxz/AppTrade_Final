package com.example.apptrade2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MenuPrincipal extends AppCompatActivity {


    public  String nombre,nombre2;
    public  String buque, buque2;
    public  String partida, partida2;
    public  String subpartida,subpartida2;
    public  String hora;
    public  String fecha;
    private int dia,mes,ano;
    DatePicker calendario;
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    public final Calendar c = Calendar.getInstance();
    final int horas = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    public static String hora2;
    public static String hora3;
    public static String horass = "";
    public static String minutoss = "";
    public static SimpleDateFormat tipoHora = new SimpleDateFormat("HH:mm", Locale.US);
    public static Date diferencia;

    Button btn,btn2,btn3,btn4;
    Spinner spinner1,spinner2,spinner3,spinner4;
    ArrayList<String> listaNombres;
    ArrayList<String> listaBuques;
    ArrayList<String> listaPartidas;
    ArrayList<String> listaSubpartidas;
    ArrayList<MenuPrincipal> personasList;
    ArrayList<MenuPrincipal> partidasList;
    ArrayList<MenuPrincipal> buquesList;
    ArrayList<MenuPrincipal> subpartidasList;

    public MenuPrincipal(String nombre, String buque, String partida, String subpartida, String hora, String fecha) {
        this.nombre = nombre;
        this.buque = buque;
        this.partida = partida;
        this.subpartida = subpartida;
        this.hora = hora;
        this.fecha = fecha;
    }

    public MenuPrincipal() {


    }

    public String getNombre() {
        return nombre;
    }

    public String getBuque() {
        return buque;
    }

    public String getPartida() {
        return partida;
    }

    public String getSubpartida() {
        return subpartida;
    }

    public String getHora() {
        return hora;
    }

    public String getFecha() {
        return fecha;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setBuque(String buque) {
        this.buque = buque;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public void setSubpartida(String subpartida) {
        this.subpartida = subpartida;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd = admin.getWritableDatabase();
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        btn = findViewById(R.id.btn);
        spinner1 = findViewById(R.id.text1);
        spinner2 = findViewById(R.id.text2);
        spinner3 = findViewById(R.id.text3);
        spinner4 = findViewById(R.id.text4);
        btn2 = findViewById(R.id.btn5);
        btn3 = findViewById(R.id.btn6);
        btn4 = findViewById(R.id.btn7);



       /*ContentValues cv1 = new ContentValues();

        cv1.put("nombre", "Eugenio Garcia");
        cv1.put("nombre","Javier García");
        bbdd.insert("nombresPersonas", null, cv1);

        ContentValues cv2 = new ContentValues();

        cv2.put("nombre", "Partida1");
        bbdd.insert("nombresPartidas", null, cv2);

        ContentValues cv3 = new ContentValues();

        cv3.put("nombre", "Buque1");
        bbdd.insert("nombresBuques", null, cv3);

        ContentValues cv4 = new ContentValues();

        cv4.put("nombre", "Subpartidas1");
        bbdd.insert("nombresSubpartidas", null, cv4);*/

        final Cursor consulta2 = bbdd.rawQuery("SELECT * FROM datosAbance ", null);






        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                consultarListaPartidas();
                ArrayAdapter<String> adaptador1 = new ArrayAdapter(MenuPrincipal.this, R.layout.negrita_spinner, listaPartidas);
                spinner3.setAdapter(adaptador1);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }

        });



        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                consultarListaSubapartidas();
                ArrayAdapter<String> adaptador4 = new ArrayAdapter(MenuPrincipal.this, R.layout.negrita_spinner,listaSubpartidas );
                spinner4.setAdapter(adaptador4);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        consultarListaNombres();
        ArrayAdapter<String> adaptador2 = new ArrayAdapter(this, R.layout.negrita_spinner,listaNombres );
        spinner1.setAdapter(adaptador2);

        consultarListaBuques();
        ArrayAdapter<String> adaptador3 = new ArrayAdapter(this, R.layout.negrita_spinner,listaBuques );
        spinner2.setAdapter(adaptador3);



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerHora(btn2);


            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(btn3);


            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerHora2(btn4);

            }
        });













        final Cursor consulta = bbdd.rawQuery("SELECT * FROM datosAbance ", null);

        if (consulta.moveToLast()) {

       nombre2 = consulta.getString(0);
       buque2 = consulta.getString(2);
       partida2 = consulta.getString(3);
       subpartida2 = consulta.getString(4);
       hora = consulta.getString(5);
       fecha = consulta.getString(6);




        }


        final SharedPreferences sharprefs = getSharedPreferences("ArchivoSP", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharprefs.edit();

        editor.putString("fecha", " ");
        editor.putString("hora", " ");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinner1.getSelectedItem().equals("Selecciona una opción...") || spinner2.getSelectedItem().equals("Selecciona una opción...") || spinner3.getSelectedItem().equals("Selecciona una opción...") || spinner4.getSelectedItem().equals("Selecciona una opción...") || sharprefs.getString("hora", "sin datos").equals(" ")  || sharprefs.getString("fecha", "sin datos").equals(" ") ){

                    Toast.makeText(MenuPrincipal.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }else {


                    ContentValues registro2 = new ContentValues();

                    registro2.put("nombreReal", spinner1.getSelectedItem().toString());
                    bbdd.update("datosAbance", registro2, null, null);

                    registro2.put("nombreBuq", spinner2.getSelectedItem().toString());
                    bbdd.update("datosAbance", registro2, null, null);

                    registro2.put("partida", spinner3.getSelectedItem().toString());
                    bbdd.update("datosAbance", registro2, null, null);

                    registro2.put("subpartida", spinner4.getSelectedItem().toString());
                    bbdd.update("datosAbance", registro2, null, null);

                    registro2.put("horas", sharprefs.getString("hora", "sin datos"));
                    bbdd.update("datosAbance", registro2, null, null);

                    registro2.put("fecha", sharprefs.getString("fecha", "sin datos"));
                    bbdd.update("datosAbance", registro2, null, null);

                    try {

                        diferencia = calcularDiferencia(tipoHora.parse(hora2),tipoHora.parse(hora3));
                        registro2.put("sumaHoras", String.valueOf(tipoHora.format(diferencia)));
                        bbdd.update("datosAbance",registro2,null,null);

                    } catch (ParseException e) {

                        e.printStackTrace();
                    }





                    Toast.makeText(MenuPrincipal.this, "Los datos se han actualizado correctamente", Toast.LENGTH_SHORT).show();



                    Intent i6 = new Intent(MenuPrincipal.this, ListadoPartes.class);
                    startActivityForResult(i6, 0);


                }

            }
        });



    }



    public void consultarListaPartidas() {

        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();

        MenuPrincipal persona = null;
        partidasList = new ArrayList<MenuPrincipal>();

        //Select


        Cursor cursor = bbdd2.rawQuery("SELECT * FROM nombresPartidas WHERE FK_Buque = '" + spinner2.getSelectedItem().toString() + "'",null);


        if(cursor.moveToFirst() && cursor != null) {


            do {

                persona = new MenuPrincipal();
                persona.setPartida(cursor.getString(0));


                Log.i("partida", "" + persona.getPartida());


                partidasList.add(persona);

            }

            while (cursor.moveToNext());

            cursor.close();
            bbdd2.close();

        }

        obtenerListaPartidas();

    }

    public void consultarListaSubapartidas(){

        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();

        MenuPrincipal persona3 = new MenuPrincipal();
        subpartidasList = new ArrayList<MenuPrincipal>();





        Cursor cursor = bbdd2.rawQuery("SELECT * FROM nombresSubpartidas WHERE FK_Buque = '" + spinner2.getSelectedItem().toString() + "' AND FK_Partida = '" + spinner3.getSelectedItem().toString() + "'" ,null);

        if(cursor.moveToFirst()) {


            do {

                persona3 = new MenuPrincipal();
                persona3.setSubpartida(cursor.getString(0));


                Log.i("subpartidas", "" + persona3.getSubpartida());


                subpartidasList.add(persona3);

            }

            while (cursor.moveToNext());

        }

        obtenerListasSubpartidas();

    }

    public void consultarListaBuques(){

        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();

        MenuPrincipal persona2 = new MenuPrincipal();
        buquesList = new ArrayList<MenuPrincipal>();

        //Select

        Cursor cursor = bbdd2.rawQuery("SELECT * FROM nombresBuques",null);

        if(cursor.moveToFirst()) {


            do {

                persona2 = new MenuPrincipal();
                persona2.setBuque(cursor.getString(0));


                Log.i("buque", "" + persona2.getBuque());


                buquesList.add(persona2);

            }

            while (cursor.moveToNext());

        }

        obtenerListaBuques();

    }

    public void consultarListaNombres(){

        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();

        MenuPrincipal persona1 = new MenuPrincipal();
        personasList = new ArrayList<MenuPrincipal>();

        //Select

        Cursor cursor = bbdd2.rawQuery("SELECT * FROM nombresPersonas",null);



        persona1 = new MenuPrincipal();
        persona1.setNombre(login.usuario);


        Log.i("nombre", "" + persona1.getNombre());


        personasList.add(persona1);





        obtenerListaNombres();

    }



    public void obtenerListaPartidas(){

        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();
        //final Cursor consulta = bbdd2.rawQuery("SELECT * FROM nombresPartidas ", null);

        listaPartidas = new ArrayList<String>();
        listaPartidas.add("Selecciona una opción...");

        for (int i = 0; i < partidasList.size(); i++){

            listaPartidas.add(partidasList.get(i).getPartida());



        }



    }

    public void obtenerListaNombres(){

        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();
        final Cursor consulta = bbdd2.rawQuery("SELECT * FROM nombresPersonas ", null);

        listaNombres = new ArrayList<String>();
        listaNombres.add("Selecciona una opción...");

        for (int i = 0; i < personasList.size(); i++){

            listaNombres.add(personasList.get(i).getNombre());



        }



    }




    public void obtenerListaBuques(){

        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();
        final Cursor consulta = bbdd2.rawQuery("SELECT * FROM nombresBuques ", null);
        final Cursor consulta2 = bbdd2.rawQuery("SELECT * FROM nombresBuques WHERE nombre = 'ESG' ", null);

        listaBuques = new ArrayList<String>();
        listaBuques.add("Selecciona una opción...");

        for (int i = 0; i < buquesList.size(); i++){

            listaBuques.add(buquesList.get(i).getBuque());



        }



    }







    public void obtenerListasSubpartidas(){

        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();
        final Cursor consulta = bbdd2.rawQuery("SELECT * FROM nombresSubpartidas ", null);

        listaSubpartidas = new ArrayList<String>();
        listaSubpartidas.add("Selecciona una opción...");

        for (int i = 0; i < subpartidasList.size(); i++){

            listaSubpartidas.add(subpartidasList.get(i).getSubpartida());



        }



    }

    private void obtenerHora(View view){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario

                //Muestro la hora con el formato deseado
                hora2 = (horaFormateada + DOS_PUNTOS + minutoFormateado);
                btn2.setText(hora2);


                SharedPreferences sharprefs = getSharedPreferences("ArchivoSP", getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharprefs.edit();
                editor.putString("hora", hora2);
                editor.commit();


            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, horas, minuto, true);

        recogerHora.show();
    }

    private void obtenerHora2(View view){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada2 =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado2 = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario



                //Muestro la hora con el formato deseado
                hora3 = (horaFormateada2 + DOS_PUNTOS + minutoFormateado2);
                btn4.setText(hora3);



            }



        }, horas, minuto, true);

        recogerHora.show();
    }

    public static Date calcularDiferencia(Date dateInicio, Date dateFinal) {
        long milliseconds = dateFinal.getTime() - dateInicio.getTime();
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();

    }






    public void obtenerFecha(View v) {
        if(v==btn3){
            final Calendar c= Calendar.getInstance();

            //Almacenamos en variables la información del dia, el mes y el año

            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            // Despues llamamos al AlertDialog del selector de fecha.

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    SharedPreferences sharprefs = getSharedPreferences("ArchivoSP", getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharprefs.edit();
                    editor.putString("fecha", dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    editor.commit();

                    btn3.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);

                }
            }
                    ,ano,mes,dia);
            datePickerDialog.show();
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

                Intent i4 = new Intent(MenuPrincipal.this, login.class);
                startActivityForResult(i4,0);
                finish();




        }



        return super.onOptionsItemSelected(item);
    }

}
