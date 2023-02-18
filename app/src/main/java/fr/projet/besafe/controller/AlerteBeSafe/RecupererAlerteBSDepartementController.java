package fr.projet.besafe.controller.AlerteBeSafe;

import android.app.ProgressDialog;
import android.util.Log;

import java.util.ArrayList;

import fr.projet.besafe.DetailAlerteActivity;
import fr.projet.besafe.api.APIClient;
import fr.projet.besafe.api.IBeSafeAPI;
import fr.projet.besafe.model.AlerteBeSafe.Alerte;
import fr.projet.besafe.model.AlerteGouvernementale.AlerteGouvernementale;
import fr.projet.besafe.modelApi.AlerteGouvParDepartementGson;
import fr.projet.besafe.modelApi.AlertesBeSafe.AlerteParDepartementGson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecupererAlerteBSDepartementController {
    private DetailAlerteActivity view;
    private IBeSafeAPI iBeSafeAPI;

    private ProgressDialog dialog;

    public RecupererAlerteBSDepartementController(DetailAlerteActivity view){
        this.view = view;
        this.iBeSafeAPI = APIClient.getInstance().create(IBeSafeAPI.class);
    }

    public void getAlerteParDepartement(String code) {
        Call<AlerteParDepartementGson> getAlerteGouvParDepartement = this.iBeSafeAPI.getAlerteParDepartement(code);
        this.onPreExecute();
        getAlerteGouvParDepartement.enqueue(new Callback<AlerteParDepartementGson>() {
            @Override
            public void onResponse(Call<AlerteParDepartementGson> call, Response<AlerteParDepartementGson> response) {
                System.out.println(response);
                AlerteParDepartementGson alerteParDepartementGson = response.body();
                if(alerteParDepartementGson != null){
                    boolean success = alerteParDepartementGson.isSuccess();
                    if(success){
                        ArrayList<Alerte> alertes = alerteParDepartementGson.getAlertes();
                        view.setListAlertes(alertes);
                    }
                    view.resultGetAlerte(success);
                }
            }

            @Override
            public void onFailure(Call<AlerteParDepartementGson> call, Throwable t) {
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
