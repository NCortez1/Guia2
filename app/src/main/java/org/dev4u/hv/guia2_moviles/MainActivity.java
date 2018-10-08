package org.dev4u.hv.guia2_moviles;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtURL;
    private TextView lblEstado;
    private Button btnDescargar;
    private ProgressBar progressBar;
    private RadioGroup rgNombre;
    private RadioButton rbnCambiar;
    private RadioButton rbnNoCambiar;
    private EditText txtNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inicializar
        txtURL       = (EditText) findViewById(R.id.txtURL);
        lblEstado    = (TextView) findViewById(R.id.lblEstado);
        btnDescargar = (Button)   findViewById(R.id.btnDescargar);
        rgNombre     = (RadioGroup) findViewById(R.id.rgNombre);
        rbnCambiar   = (RadioButton) findViewById(R.id.rbnCambiar);
        rbnNoCambiar   = (RadioButton) findViewById(R.id.rbnNoCambiar);
        txtNombre    = (EditText) findViewById(R.id.txtNombre);

        rgNombre.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rbnCambiar.isChecked()){
                    txtNombre.setEnabled(true);
                }
                if(rbnNoCambiar.isChecked()){
                    txtNombre.setEnabled(false);
                }
            }
        });

        //evento onClick
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbnCambiar.isChecked()){
                    new Descargar(
                            MainActivity.this,
                            lblEstado,
                            progressBar,
                            btnDescargar,
                            txtNombre.getText().toString()
                    ).execute(txtURL.getText().toString());
                }else {
                    new Descargar(
                            MainActivity.this,
                            lblEstado,
                            progressBar,
                            btnDescargar
                    ).execute(txtURL.getText().toString());
                }

            }
        });

        verifyStoragePermissions(this);
    }

    //esto es para activar perimiso de escritura y lectura en versiones de android 6 en adelante
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
