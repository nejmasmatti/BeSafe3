package fr.projet.besafe.controller.AlerteBeSafe;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import fr.projet.besafe.MesAlertesActivity;
import fr.projet.besafe.api.APIClient;
import fr.projet.besafe.api.IBeSafeAPI;
import fr.projet.besafe.global.UserAuth;
import fr.projet.besafe.modelApi.ReponseApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteAlerteBSController {
    private MesAlertesActivity view;
    private IBeSafeAPI iBeSafeAPI;

    private ProgressDialog dialog;

    public DeleteAlerteBSController(MesAlertesActivity view){
        this.view = view;
        this.iBeSafeAPI = APIClient.getInstance().create(IBeSafeAPI.class);
    }

    public void deleteAlertes(List<Integer> idsAlertesDelete){
        JsonObject deleteAlertes = new JsonObject();
        deleteAlertes.addProperty("id_user", UserAuth.getInstance().getUser().getId());
        JsonArray idsAlertes = new JsonArray();
        for(Integer id : idsAlertesDelete){
            idsAlertes.add(id);
        }
        deleteAlertes.add("ids_alertes", idsAlertes);

        System.out.println(deleteAlertes);
        Call<ReponseApi> deleteAlerte = this.iBeSafeAPI.deleteAlerte(deleteAlertes);
        this.onPreExecute();
        deleteAlerte.enqueue(new Callback<ReponseApi>() {
            @Override
            public void onResponse(@NonNull Call<ReponseApi> call, @NonNull Response<ReponseApi> response) {
                ReponseApi reponseApi = response.body();
                System.out.println(response);
                if(reponseApi != null){
                    boolean success = reponseApi.isSuccess();
                    view.resultDeleteAlerte(success);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReponseApi> call, @NonNull Throwable t) {
                Log.d("api", "Api call failed");
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
