package fr.projet.besafe.Services.ImportDataServices;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import fr.projet.besafe.Arrondissement;
import fr.projet.besafe.JSONParser;
import fr.projet.besafe.MainActivity;
import fr.projet.besafe.model.AlertExcel;

public class SendDataExcelBD {
    //traitement + l'envoie des données de l'excel en BD
    MainActivity main;
    public static String path;
    private static SendDataExcelBD INSTANCE;

    JSONParser parser;
    private int cle;

    ArrayList<AlertExcel> listAlertExel;

    private SendDataExcelBD(MainActivity main, String nameFile){
        this.main = main;
        path = main.getApplicationContext().getCacheDir().getAbsolutePath().toString() + "/" + nameFile;
        parser = new JSONParser();
        listAlertExel = new ArrayList<>();
    }

    public static SendDataExcelBD setPath(MainActivity main, String nameFile){
        if(nameFile != null){
            INSTANCE = new SendDataExcelBD(main, nameFile);
        }
        return INSTANCE;
    }

    public void setListAlertExel(){
        listAlertExel.add(new AlertExcel("vol", 1, 1500, 5, 2022));
        listAlertExel.add(new AlertExcel("agression physique", 2, 15000, 6, 2022));
    }

    public void sendAlert(){
        try {
            Object result = new SendAlert().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resultConnection(int cle){
        if(cle == 1){
            Toast.makeText(this.main,"Fichier envoyé en BD",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this.main,"Fichié pas envoyé en BD",Toast.LENGTH_LONG).show();
    }

    class SendAlert extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects)
        {
            HashMap<String, ArrayList<AlertExcel>> mapDataAlertExel = new HashMap<>();
            mapDataAlertExel.put("listeAlerteExcel", listAlertExel);


            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/servicesBeSafe/SendDataExcleServices/SendAlertExcel.php","GET",mapDataAlertExel);
            try
            {
                cle = Integer.parseInt(String.valueOf(object.getInt("resultat")));
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
            resultConnection(cle);
        }
    }
}
