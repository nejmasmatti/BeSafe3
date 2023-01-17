package fr.projet.besafe.controller.AlerteBeSafe;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import fr.projet.besafe.FormCreationAlerte;
import fr.projet.besafe.api.APIClient;
import fr.projet.besafe.api.IBeSafeAPI;
import fr.projet.besafe.global.UserAuth;
import fr.projet.besafe.model.AlerteBeSafe.AlerteVol;
import fr.projet.besafe.modelApi.AlertesBeSafe.AlerteVolGson;
import fr.projet.besafe.modelApi.User.UserGson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlerteBSController {


    private FormCreationAlerte view;
    private AlerteVol alerteVol;
    //Api interface
    private IBeSafeAPI iBeSafeAPI;

    private ProgressDialog dialog;
    private String typeAlerte = "";

    public AlerteBSController(FormCreationAlerte view){
        this.view = view;
        this.iBeSafeAPI = APIClient.getInstance().create(IBeSafeAPI.class);
    }

    public String getTypeAlerte() {
        return typeAlerte;
    }

    public void setTypeAlerte(String typeAlerte) {
        this.typeAlerte = typeAlerte;
    }

    private void sendAlerte(String typeAlerte){

        Map<String, String> alerte = new HashMap<>();
        alerte.put("RefPosition", "2");
        alerte.put("nivDanger", String.valueOf(this.view.getNivDanger().getProgress()));


        if("alerteV".equals(typeAlerte)){
            Call<AlerteVolGson> sendAlerteV = this.iBeSafeAPI.sendAlerteV(alerte);
            sendAlerteV.enqueue(new Callback<AlerteVolGson>() {
                @Override
                public void onResponse(Call<AlerteVolGson> call, Response<AlerteVolGson> response) {
                    AlerteVolGson alerteVolGson = response.body();
                    if(alerteVolGson != null){
                        boolean success = alerteVolGson.isSuccess();
                        getView().resultConnexion(success);
                    }
                }

                @Override
                public void onFailure(Call<AlerteVolGson> call, Throwable t) {
                    Log.d("api", "Api call failed");
                    System.out.println("api a planté");
                }
            });
        }
        else if("alerteVP".equals(typeAlerte)){
            Call<AlerteVolGson> sendAlerteVP = this.iBeSafeAPI.sendAlerteVP(alerte);
            sendAlerteVP.enqueue(new Callback<AlerteVolGson>() {
                @Override
                public void onResponse(Call<AlerteVolGson> call, Response<AlerteVolGson> response) {

                }

                @Override
                public void onFailure(Call<AlerteVolGson> call, Throwable t) {

                }
            });
        }
        else if("alerteVV".equals(typeAlerte)){
            Call<AlerteVolGson> sendAlerteVV = this.iBeSafeAPI.sendAlerteVV(alerte);
            sendAlerteVV.enqueue(new Callback<AlerteVolGson>() {
                @Override
                public void onResponse(Call<AlerteVolGson> call, Response<AlerteVolGson> response) {

                }

                @Override
                public void onFailure(Call<AlerteVolGson> call, Throwable t) {

                }
            });
        }
        else {
            System.out.println("type alerte incorrect");
        }
    }

    public void sendAlerteAsync(){
        try {
            Object result = new SendAlerte().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    class SendAlerte extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog= getView().createDialogue();
            dialog.setMessage("Loading");
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects)
        {
            sendAlerte(getTypeAlerte());
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            dialog.cancel();
        }
    }

    public FormCreationAlerte getView() {
        return view;
    }
}
