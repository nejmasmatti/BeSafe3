package fr.projet.besafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.richpath.RichPath;
import com.richpath.RichPathView;

public class MainActivity extends AppCompatActivity {

    private RichPathView map;


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = (RichPathView) findViewById(R.id.mapFrance);

        map.setOnPathClickListener(new RichPath.OnPathClickListener() {
            @Override
            public void onClick(RichPath richPath) {
                Intent a = new Intent(MainActivity.this, DetailAlerteActivity.class);
                a.putExtra("code", richPath.getName());
                startActivity(a);
            }
        });

        Button boutonAlerte = (Button) findViewById(R.id.button_alerte);
        Button boutonCarte = (Button) findViewById(R.id.button_carte);
        Button boutonMesAlertes = (Button) findViewById(R.id.button_mesAlertes);

        boutonAlerte.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                Intent a = new Intent(MainActivity.this, FormCreationAlerte.class);
                startActivity(a);
            }
        });

        boutonCarte.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                Intent a = new Intent(MainActivity.this, MyMapActivity.class);
                startActivity(a);
            }
        });

        boutonMesAlertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MainActivity.this, MesAlertesActivity.class);
                startActivity(a);
            }
        });
    }



}
