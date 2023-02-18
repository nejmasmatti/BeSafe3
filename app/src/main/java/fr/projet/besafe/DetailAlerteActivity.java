package fr.projet.besafe;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.projet.besafe.controller.AlerteBeSafe.RecupererAlerteBSDepartementController;
import fr.projet.besafe.controller.RecupererAlerteGouvParDepartementController;
import fr.projet.besafe.model.AlerteBeSafe.Alerte;
import fr.projet.besafe.model.AlerteGouvernementale.AlerteGouvernementale;

public class DetailAlerteActivity extends AppCompatActivity {

    private List<Alerte> listAlertes = new ArrayList<>();
    private List<AlerteGouvernementale> listAlertesGouv = new ArrayList<>();

    private RecupererAlerteBSDepartementController recupererAlerteBSDepartementController;
    private RecupererAlerteGouvParDepartementController recupererAlerteGouvParDepartementController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_alerte);
        this.recupererAlerteBSDepartementController = new RecupererAlerteBSDepartementController(this);
        this.recupererAlerteGouvParDepartementController = new RecupererAlerteGouvParDepartementController(this);
        Bundle message = getIntent().getExtras();
        if (message != null) {
            String[] path = message.getString("code").split(";");
            String codeDepartement = String.valueOf(path[0]);

            selectAlerteGouv(codeDepartement);
            selectAlerte(codeDepartement);

            ListView listView1 = findViewById(R.id.listAlertExcel);
            ListView listView2 = findViewById(R.id.listAlertBeSafe);

            ArrayList<String> a1 = new ArrayList<>();
            if(listAlertesGouv.size() > 0){
                for (AlerteGouvernementale a : listAlertesGouv){
                    a1.add(String.format("Titre : %s, Type agression : %s, Département : %s",a.getLibelle(), a.getTypeAlerte(), a.getCode()));
                }
            }

            ArrayList<String> a2 = new ArrayList<>();
            if(listAlertes.size() > 0){
                for (Alerte a : listAlertes){
                    a2.add(String.format("Titre : %s, Type agression : %s",a.getLibelle(), a.getTypeAlerte()));
                }
            }

            ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,a1);
            ArrayAdapter<String> arrayAdapter2=new ArrayAdapter<>(this,android.R.layout.simple_list_item_2,a2);
            listView1.setAdapter(arrayAdapter1);
            listView1.setAdapter(arrayAdapter2);
        }
    }


    public void selectAlerte(String code){
        this.recupererAlerteBSDepartementController.getAlerteParDepartement(code);
    }

    public void selectAlerteGouv(String code){
        this.recupererAlerteGouvParDepartementController.getAlerteParDepartement(code);
    }

    public ProgressDialog createDialogue(){
        return new ProgressDialog(this);
    }

    public void resultGetAlerte(boolean success){
        recupererAlerteBSDepartementController.onPostExecute();
        if(success){
            Toast.makeText(DetailAlerteActivity.this,"Voici le fil d'actualité",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(DetailAlerteActivity.this,"No data pour ce département",Toast.LENGTH_LONG).show();
    }

    public void resultGetAlerteGouv(boolean success){
        recupererAlerteGouvParDepartementController.onPostExecute();
        if(success){
            Toast.makeText(DetailAlerteActivity.this,"Voici le fil d'actualité",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(DetailAlerteActivity.this,"No data pour ce département",Toast.LENGTH_LONG).show();
    }

    public void setListAlertes(List<Alerte> listAlertes) {
        this.listAlertes = listAlertes;
    }

    public void setListAlertesGouv(List<AlerteGouvernementale> listAlertesGouv) {
        this.listAlertesGouv = listAlertesGouv;
    }
}


