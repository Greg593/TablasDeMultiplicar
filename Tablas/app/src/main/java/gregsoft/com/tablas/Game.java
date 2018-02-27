package gregsoft.com.tablas;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends AppCompatActivity implements View.OnClickListener {

    TextView txtEtapa, txtBase,txtPunteo;
    EditText txtRespuesta;
    Button btnRespuesta, btnRendirme, btnValidar;
    String numero;
    Boolean forma;
    Integer paso=1, punteo=0, num=-1, res=0, o=0;
    Integer PUNTOS_MAX = 10;
    int tablas[] = {1,2,3,4,5,6,7,8,9,10};
    int usados[] = new int[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle recupera = getIntent().getExtras();
        if (recupera !=null){
            numero = recupera.getString("numero");
            forma = recupera.getBoolean("forma");
        }

        txtEtapa = (TextView)findViewById(R.id.txtEtapa);
        txtBase = (TextView)findViewById(R.id.txtBase);
        txtPunteo = (TextView)findViewById(R.id.txtPuntos);
        txtRespuesta = (EditText)findViewById(R.id.txtRespuesta);
        btnRendirme = (Button)findViewById(R.id.btnRendirme);
        btnRespuesta = (Button)findViewById(R.id.btnRespuesta);
        btnValidar = (Button)findViewById(R.id.btnValidar);

        btnRendirme.setOnClickListener(this);
        btnRespuesta.setOnClickListener(this);
        btnValidar.setOnClickListener(this);

        o= 0;
        proceso();
    }

    private void proceso() {
        if (o<=9) {
            multiplica();
            txtRespuesta.setText(" ");
            txtRespuesta.setEnabled(true);
            txtPunteo.setText(punteo.toString());

            txtEtapa.setText(paso.toString() + " de 10");
            txtBase.setText(numero.toString() + " x " + tablas[num]);

            int proximoNumero = tablas[num];
            res = Integer.parseInt(numero) * proximoNumero;
        }else{
            punteo=punteo+PUNTOS_MAX;
            Thread tr = new Thread() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getApplicationContext(), EndActivity.class);
                            i.putExtra("puntuacion", txtPunteo.getText().toString());
                            startActivity(i);
                        }
                    });
                }
            };
            finish();
            tr.start();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "No se permite retroceder...", Toast.LENGTH_LONG).show();
    }

    private void multiplica() {
        if (forma == true){
            num++;
            usados[o]=num;
            o++;
        }else{
            do {
                num = (int) (Math.random() * 10);
            }while(buscaNumero(tablas[num])!=false);
            usados[o]=tablas[num];
            o++;
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.btnRendirme) {
            finish();
        }
        if (v.getId()==R.id.btnRespuesta) {
            txtRespuesta.setText(res.toString());
            txtRespuesta.setEnabled(false);
            punteo=punteo-PUNTOS_MAX;

        }
        if (v.getId()==R.id.btnValidar) {
            String texto = txtRespuesta.getText().toString();
            texto = texto.trim();
            if (texto.equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar un número valido", Toast.LENGTH_LONG).show();
            }else{
                int respuestaIngresada = Integer.parseInt(texto);
                if (respuestaIngresada == res) {
                    paso++;
                    punteo=punteo+PUNTOS_MAX;
                    Toast.makeText(getApplicationContext(), "Excelente!!!", Toast.LENGTH_LONG).show();
                    proceso();
                } else {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta, intente de nuevo", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    boolean buscaNumero(int num){
        Boolean respuesta = false;
        for (int i=0;i<usados.length;i++){
            if (usados[i]==num){
                respuesta= true;
                break;
            }
        }
        return respuesta;
    }
}
