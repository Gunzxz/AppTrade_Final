package com.example.apptrade2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class login extends AppCompatActivity {


    EditText edit1,edit2;


    public static String usuario;
    public static String contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit1 = findViewById(R.id.edit11);
        edit2 = findViewById(R.id.edit22);


        final ConnectSqlite admin = new ConnectSqlite(this, ConnectSqlite.DATABASE_NAME, null, ConnectSqlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd = admin.getWritableDatabase();

       ContentValues registro = new ContentValues();

        registro.put("usuario", "egarcia");
        registro.put("pass", "1234$");

        bbdd.insert("usuarios", null, registro);



        final  Button btn2 = findViewById(R.id.btn11);


        final Cursor consulta = bbdd.rawQuery("SELECT * FROM usuarios ", null);

        if (consulta.moveToFirst()) {

            usuario = consulta.getString(0);
            contraseña = consulta.getString(1);


        }

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = admin.ConsultarUsuPas(edit1.getText().toString(), edit2.getText().toString());

                if (cursor.getCount() >= 1) {

                    Intent i = new Intent(login.this, MenuPrincipalNoEditable.class);
                    startActivityForResult(i, 0);
                    finish();

                } else {

                    Toast.makeText(login.this, "El usuario/contraseña introducidos no son correctos", Toast.LENGTH_SHORT).show();

                }




            }
        });

        bbdd.close();




    }

    }

