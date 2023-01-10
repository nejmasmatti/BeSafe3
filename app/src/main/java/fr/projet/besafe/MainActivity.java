package fr.projet.besafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.io.File;

import fr.projet.besafe.Services.ImportDataServices.Download;

public class MainActivity extends AppCompatActivity {

    private RichPathView map;

    private Button bouton_alerte_vol;
    private Button bouton_alerte_physique;
    private Button bouton_alerte_verbale;
    private Button bouton_carte;


    private static final String DATA_URL = "https://static.data.gouv.fr/resources/chiffres-departementaux-mensuels-relatifs-aux-crimes-et-delits-enregistres-par-les-services-de-police-et-de-gendarmerie-depuis-janvier-1996/20221031-102847/tableaux-4001-ts.xlsx";

    @Override
    protected void onStart() {
        super.onStart();
        //downloadData();
    }

    public void downloadData(){
        File path = getApplicationContext().getCacheDir();
        String link = DATA_URL;
        System.out.println(path.getAbsolutePath());
        File out = new File(path, "/BeSafe.xlsx");
        new Thread(new Download(link, out)).start();
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



        Button button_alerte_vol = (Button) findViewById(R.id.button_alerte_vol);
        Button button_alerte_physique = (Button) findViewById(R.id.button_alerte_physique);
        Button button_alerte_verbale = (Button) findViewById(R.id.button_alerte_verbale);
        Button button_carte = (Button) findViewById(R.id.button_carte);

        button_alerte_vol.setOnClickListener(new View.OnClickListener()

        {
            public void onClick (View v){
                Intent button_alerte_vol= new Intent(MainActivity.this, AlerteVolActivity.class);
                startActivity(button_alerte_vol);            }
        });

        button_alerte_physique.setOnClickListener(new View.OnClickListener()

        {
            public void onClick (View v){
                Intent button_alerte_physique= new Intent(MainActivity.this, AlertePhysiqueActivity.class);
                startActivity(button_alerte_physique);
            }
        });

        button_alerte_verbale.setOnClickListener(new View.OnClickListener()

        {
            public void onClick (View v){
                Intent button_alerte_verbale= new Intent(MainActivity.this, AlerteVerbaleActivity.class);
                startActivity(button_alerte_verbale);
            }
        });

        button_carte.setOnClickListener(new View.OnClickListener()

        {
            public void onClick (View v){
                Intent button_carte= new Intent(MainActivity.this, MyMapActivity.class);
                startActivity(button_carte);
            }
        });
        //SendDataExcelBD s = SendDataExcelBD.setPath(this, "test");
        //s.setListAlertExel();
        //s.sendAlert();


    }



}
