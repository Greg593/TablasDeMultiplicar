package gregsoft.com.tablas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class EndActivity extends AppCompatActivity implements View.OnClickListener{

    TextView txtPuntuacion;
    Button btnMenu;
    String puntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Bundle recupera = getIntent().getExtras();
        if (recupera !=null){
            puntos = recupera.getString("puntuacion");
        }

        txtPuntuacion = (TextView)findViewById(R.id.txtPuntuacion);
        btnMenu = (Button)findViewById(R.id.btnMenu);

        txtPuntuacion.setText(puntos);
        btnMenu.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "No se permite retroceder...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        Thread tr = new Thread() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                });
            }
        };
        finish();
        tr.start();
    }
}
