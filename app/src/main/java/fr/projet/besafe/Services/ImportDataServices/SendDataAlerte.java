package fr.projet.besafe.Services.ImportDataServices;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import fr.projet.besafe.JSONParser;
import fr.projet.besafe.MainActivity;

public class SendDataAlerte {

    //traitement + l'envoie des données de l'appli en BD
    MainActivity main;
    //public static String path;
    //private static SendDataAlerte INSTANCE;

    JSONParser parser;
    private int cle;

    //ArrayList<AlertExcel> listAlertExel;

    private SendDataAlerte(MainActivity main){
        this.main = main;
        parser = new JSONParser();
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
            Toast.makeText(this.main,"Fichier pas envoyé en BD",Toast.LENGTH_LONG).show();
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
            HashMap<String, String> mapDataAlertUser = new HashMap<>();
            mapDataAlertUser.put("listeAlerteUser", "25-11-2022");
            mapDataAlertUser.put("listeAlerteUser", "3");
            mapDataAlertUser.put("listeAlerteUser", "1");
            mapDataAlertUser.put("listeAlerteUser", "1");
            mapDataAlertUser.put("listeAlerteUser", "34");
            mapDataAlertUser.put("listeAlerteUser", "12");
            mapDataAlertUser.put("listeAlerteUser", "7");
            mapDataAlertUser.put("listeAlerteUser", "avenue du général leclerc");
            mapDataAlertUser.put("listeAlerteUser", "Nanterre");


            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/servicesBeSafe/SendDataExcleServices/InsertAlert.php","GET",mapDataAlertUser);
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
