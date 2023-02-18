package fr.projet.besafe.controller;

import android.app.ProgressDialog;
import android.util.Log;

import java.util.ArrayList;

import fr.projet.besafe.DetailAlerteActivity;
import fr.projet.besafe.api.APIClient;
import fr.projet.besafe.api.IBeSafeAPI;
import fr.projet.besafe.model.AlerteGouvernementale.AlerteGouvernementale;
import fr.projet.besafe.modelApi.AlerteGouvParDepartementGson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecupererAlerteGouvParDepartementController {
    private DetailAlerteActivity view;
    private IBeSafeAPI iBeSafeAPI;

    private ProgressDialog dialog;

    public RecupererAlerteGouvParDepartementController(DetailAlerteActivity view){
        this.view = view;
        this.iBeSafeAPI = APIClient.getInstance().create(IBeSafeAPI.class);
    }
    public void getAlerteParDepartement(String code) {
        Call<AlerteGouvParDepartementGson> getAlerteGouvParDepartement = this.iBeSafeAPI.getAlerteGouvParDepartement(code);
        this.onPreExecute();
        getAlerteGouvParDepartement.enqueue(new Callback<AlerteGouvParDepartementGson>() {
            @Override
            public void onResponse(Call<AlerteGouvParDepartementGson> call, Response<AlerteGouvParDepartementGson> response) {
                AlerteGouvParDepartementGson alerteGouvParDepartementGson = response.body();
                if(alerteGouvParDepartementGson != null){
                    boolean success = alerteGouvParDepartementGson.isSuccess();
                    if(success){
                        ArrayList<AlerteGouvernementale> alertes = alerteGouvParDepartementGson.getAlertes();
                        view.setListAlertesGouv(alertes);
                    }
                    view.resultGetAlerteGouv(success);
                }
            }

            @Override
            public void onFailure(Call<AlerteGouvParDepartementGson> call, Throwable t) {
                Log.d("api", "Api call failed");
                onPostExecute();
            }
        });

    }

    private void onPreExecute(){
        dialog= this.view.createDialogue();
        dialog.setMessage("Loading");
        dialog.show();
    }

    public void onPostExecute(){
        dialog.cancel();
    }
}
