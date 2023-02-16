package fr.projet.besafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import fr.projet.besafe.model.AlertExcel.AlertExcel;
import fr.projet.besafe.model.AlerteBeSafe.Alerte;

public class DetailAlerteActivity extends AppCompatActivity {

    ListView listView1, listView2;
    ArrayList<AlertExcel> listAlertExcel = new ArrayList<>();
    ArrayList<Alerte> listAlertBeSafe = new ArrayList<>();

    private ProgressDialog dialog;
    private JSONParser parser = new JSONParser();
    private int cle, cle2;

    private int idDepartement;
    private String nomDepartement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_alerte);

        Bundle message = getIntent().getExtras();
        if (message != null) {
            String[] path = message.getString("code").split(";");
            idDepartement = Integer.valueOf(path[0]);
            nomDepartement = path[1];
        }
        listView1=(ListView)findViewById(R.id.listAlertExcel);
        selectAlertExcelDep();

        ArrayList<String> tmp = new ArrayList<>();
        if(listAlertExcel.size() > 0){
            for (AlertExcel a : listAlertExcel){
                tmp.add(String.format("Annee : %d, Mois : %d, Nombre crime : %d, Nom du crime : %s, Departement : %d", a.getAnnee(), a.getMois(), a.getNbCrime(), a.getLibelleAlerte(),  a.getNumDepartement()));
            }
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,tmp);

        listView1.setAdapter(arrayAdapter);
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    // GET ALERTE EXCEL

    public void selectAlertExcelDep(){
        try {
            Object result = new SelectAlertExcel().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ProgressDialog createDialogue(){
        return new ProgressDialog(this);
    }

    public void resultConnection(int cle){
        if(cle == 1){
            Toast.makeText(DetailAlerteActivity.this,"Voici le fil d'actualité",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(DetailAlerteActivity.this,"No data pour ce département",Toast.LENGTH_LONG).show();
    }

    class SelectAlertExcel extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog= createDialogue();
            dialog.setMessage("Loading");
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects)
        {
            HashMap<String, String> idDepart = new HashMap<>();
            idDepart.put("idDepartement",String.valueOf(getIdDepartement()));
            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/servicesBeSafe/selectAlertExcel.php","GET",idDepart);
            System.out.println(object);
            try
            {
                cle = Integer.parseInt(String.valueOf(object.getInt("resultat")));
                if(cle == 1)
                {
                    JSONArray alertsExcel = object.getJSONArray("listAlertGouv");
                    for(int i=0;i<alertsExcel.length();i++ )
                    {

                        JSONObject alertExcel=alertsExcel.getJSONObject(i);

                        AlertExcel a =new AlertExcel();
                        a.setIdAlert(alertExcel.getInt("idAlerteGouv"));
                        a.setAnnee(alertExcel.getInt("Annee"));
                        a.setMois(alertExcel.getInt("Mois"));
                        a.setNbCrime(alertExcel.getInt("NombreCrime"));
                        a.setLibelleAlerte(alertExcel.getString("LibeleAlerte"));
                        a.setNumDepartement(alertExcel.getInt("RefDepartement"));

                        listAlertExcel.add(a);
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            dialog.cancel();
            resultConnection(cle);
        }
    }

    // GET ALERTE BESAFE

    public void selectAlertBeSafe(){
        try {
            Object result = new SelectAlertExcel().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class SelectAlertBeSafe extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog= createDialogue();
            dialog.setMessage("Loading");
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects)
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("idDepartement",String.valueOf(getIdDepartement()));
            map.put("idUser", "1");
            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/servicesBeSafe/selectAlertExcel.php","GET",map);
            try
            {
                cle2 = Integer.parseInt(String.valueOf(object.getInt("resultat")));
                if(cle2 == 1)
                {
                    JSONArray alertsBeSafe = object.getJSONArray("listAlertBeSafe");
                    for(int i=0;i<alertsBeSafe.length();i++ )
                    {

                        JSONObject alertBeSafe=alertsBeSafe.getJSONObject(i);

                        Alerte a =new Alerte();
                        listAlertBeSafe.add(a);
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            dialog.cancel();
        }
    }

}


