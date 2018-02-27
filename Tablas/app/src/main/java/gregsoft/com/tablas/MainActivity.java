package gregsoft.com.tablas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtNumero;
    Button btnAceptar, btnSalir;
    RadioButton rbNormal, rbAleatorio;
    Boolean sForma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNumero = (EditText) findViewById(R.id.txtNumero);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        rbAleatorio = (RadioButton)findViewById(R.id.rbAleatorio);
        rbNormal = (RadioButton)findViewById(R.id.rbNormal);

        btnAceptar.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        //Nothing
    }

    @Override
    public void onClick(View v) {

        if (rbNormal.isChecked()==true) {
            sForma = true;
        }else {
            sForma = false;
        }

        if (v.getId()==R.id.btnAceptar) {
            String texto = txtNumero.getText().toString();
            texto = texto.trim();
            if (texto.equals("")){
                Toast.makeText(getApplicationContext(), "Debe ingresar un número", Toast.LENGTH_LONG).show();
            }else{
            Thread tr = new Thread() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getApplicationContext(), Game.class);
                            i.putExtra("numero", txtNumero.getText().toString());
                            i.putExtra("forma",sForma.booleanValue());
                            startActivity(i);
                        }
                    });
                }
            };
            //finish();
            tr.start();
            } /*else {
                Toast.makeText(getApplicationContext(), "Debe ingresar un número", Toast.LENGTH_LONG).show();
            }*/
        }

        if (v.getId()==R.id.btnSalir) {
            finish();
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }
}