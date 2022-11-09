package fr.projet.besafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import fr.projet.besafe.JSONParser;
import fr.projet.besafe.Arrondissement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Arrondissement> listArrondissements = new ArrayList<>();

    private ProgressDialog dialog;
    private JSONParser parser = new JSONParser();;
    private int cle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listview);

        selectAnnonces();

        ArrayList<String> tmp = new ArrayList<>();
        if(listArrondissements.size() > 0){
            for (Arrondissement a : listArrondissements){
                tmp.add(String.format("Ville : %s, Arr : %d", a.getName(), a.getArrondissement()));
            }
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,tmp);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"Voici le degré d'incident dans cet arrondissement : " + listArrondissements.get(i).getDegreIncident(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void selectAnnonces(){
        try {
            Object result = new SelectItems().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ProgressDialog createDialogue(){
        return new ProgressDialog(MainActivity.this);
    }

    public void resultConnection(int cle){
        if(cle == 1){
            Toast.makeText(MainActivity.this,"Voici le fil d'actualité",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(MainActivity.this,"Connexion échoué, login ou mode de passe invalide",Toast.LENGTH_LONG).show();
    }

    class SelectItems extends AsyncTask
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
            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/servicesBeSafe/arrondissement.php","GET",null);
            System.out.println("objj");
            System.out.println(object);
            try
            {
                cle = Integer.parseInt(String.valueOf(object.getInt("resultat")));
                if(cle == 1)
                {
                    JSONArray arrondissements=object.getJSONArray("arrondissement");
                    for(int i=0;i<arrondissements.length();i++ )
                    {

                        JSONObject arrondissement=arrondissements.getJSONObject(i);


                        Arrondissement a =new Arrondissement();
                        a.setArrondissement(arrondissement.getInt("idArrondissement"));
                        a.setDegreIncident(arrondissement.getInt("degreIncident"));

                        listArrondissements.add(a);
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
}