package fr.projet.besafe.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import fr.projet.besafe.ConnexionActivity;
import fr.projet.besafe.api.APIClient;
import fr.projet.besafe.api.IBeSafeAPI;
import fr.projet.besafe.model.User.User;
import fr.projet.besafe.modelApi.User.UserGson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserController {

    private ConnexionActivity view;
    private User user;
    //Api interface
    private IBeSafeAPI iBeSafeAPI;

    private ProgressDialog dialog;

    public UserController(ConnexionActivity view){
        this.view = view;
        this.iBeSafeAPI = APIClient.getInstance().create(IBeSafeAPI.class);
    }

    private void login(){
        // hasmap des identifiants
        Map<String, String> userLogin = new HashMap<>();
        userLogin.put("email", this.view.getEmail().getText().toString());
        userLogin.put("password", this.view.getPassword().getText().toString());

        Call<UserGson> signIn = this.iBeSafeAPI.signUser(userLogin);
        signIn.enqueue(new Callback<UserGson>() {
            @Override
            public void onResponse(@NonNull Call<UserGson> call, @NonNull Response<UserGson> response) {
                UserGson userGson = response.body();
                if(userGson != null){
                    boolean success = userGson.isSuccess();
                    getView().resultConnexion(success);
                    if(success)
                        setUser(userGson.toUser());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserGson> call, @NonNull Throwable t) {
                Log.d("api", "Api call failed");
            }
        });
    }

    public void makeConnexion(){
        try {
            Object result = new ConnectUser().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    class ConnectUser extends AsyncTask
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
            login();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            dialog.cancel();
        }
    }

    private ConnexionActivity getView(){
        return this.view;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
