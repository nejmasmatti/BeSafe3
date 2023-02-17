package fr.projet.besafe.controller.AlerteBeSafe;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;

import java.util.ArrayList;

import fr.projet.besafe.MesAlertesActivity;
import fr.projet.besafe.api.APIClient;
import fr.projet.besafe.api.IBeSafeAPI;
import fr.projet.besafe.global.UserAuth;
import fr.projet.besafe.model.AlerteBeSafe.Alerte;
import fr.projet.besafe.modelApi.AlertesBeSafe.AlerteParUserGson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecupererAlerteBSUserController {
    private MesAlertesActivity view;
    private IBeSafeAPI iBeSafeAPI;

    private ProgressDialog dialog;

    public RecupererAlerteBSUserController(MesAlertesActivity view){
        this.view = view;
        this.iBeSafeAPI = APIClient.getInstance().create(IBeSafeAPI.class);
    }

    public void getAlerteUser() throws JSONException {
        int idUser = UserAuth.getInstance().getUser().getId();

        Call<AlerteParUserGson> getAlerteUser = this.iBeSafeAPI.getAlerteUser(idUser);
        this.onPreExecute();
        getAlerteUser.enqueue(new Callback<AlerteParUserGson>() {
            @Override
            public void onResponse(@NonNull Call<AlerteParUserGson> call, @NonNull Response<AlerteParUserGson> response) {
                AlerteParUserGson alerteParUserGson = response.body();
                if(alerteParUserGson != null){
                    boolean success = alerteParUserGson.isSuccess();
                    if(success){
                        ArrayList<Alerte> alertes = alerteParUserGson.getAlertes();
                        view.setListAlertes(alertes);
                    }
                    view.resultGetAlerte(success);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AlerteParUserGson> call, @NonNull Throwable t) {
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
