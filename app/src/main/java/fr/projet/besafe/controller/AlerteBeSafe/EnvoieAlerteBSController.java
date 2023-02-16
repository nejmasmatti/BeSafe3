package fr.projet.besafe.controller.AlerteBeSafe;

import android.app.ProgressDialog;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.Map;

import fr.projet.besafe.FormCreationAlerte;
import fr.projet.besafe.api.APIClient;
import fr.projet.besafe.api.IBeSafeAPI;
import fr.projet.besafe.model.Adresse;
import fr.projet.besafe.model.AlerteBeSafe.Alerte;
import fr.projet.besafe.modelApi.ReponseApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnvoieAlerteBSController {


    private FormCreationAlerte view;
    private Alerte alerteVol;

    private IBeSafeAPI iBeSafeAPI;

    private ProgressDialog dialog;

    public EnvoieAlerteBSController(FormCreationAlerte view){
        this.view = view;
        this.iBeSafeAPI = APIClient.getInstance().create(IBeSafeAPI.class);
    }

    public void sendAlerte(){
        JsonObject alerte = new JsonObject();
        for (Map.Entry<String, Object> entry : this.view.getAlerte().entrySet()){
            String key = entry.getKey();
            Object obj = entry.getValue();
            if(obj instanceof Adresse){
                JsonObject adresse = new JsonObject();
                adresse.addProperty("libelle", ((Adresse) obj).getLibelle());
                adresse.addProperty("ville", ((Adresse) obj).getVille());
                alerte.add(key, adresse);
            }
            else
                alerte.addProperty(key, obj.toString());
        }

        Call<ReponseApi> sendAlerte = this.iBeSafeAPI.sendAlerte(alerte);
        onPreExecute();
        sendAlerte.enqueue(new Callback<ReponseApi>() {
            @Override
            public void onResponse(Call<ReponseApi> call, Response<ReponseApi> response) {
                ReponseApi reponseApi = response.body();
                if(reponseApi != null){
                    boolean success = reponseApi.isSuccess();
                    view.resultConnexion(success);
                }
            }

            @Override
            public void onFailure(Call<ReponseApi> call, Throwable t) {
                Log.d("api", "Api call failed");
                System.out.println(t.getMessage());
            }
        });
    }

    private void onPreExecute() {
        dialog= this.view.createDialogue();
        dialog.setMessage("Loading");
        dialog.show();
    }

    public void onPostExecute() {
        dialog.cancel();
    }
}
