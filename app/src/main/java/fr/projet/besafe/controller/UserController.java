package fr.projet.besafe.controller;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;

import fr.projet.besafe.ConnexionActivity;
import fr.projet.besafe.api.APIClient;
import fr.projet.besafe.api.IBeSafeAPI;
import fr.projet.besafe.global.UserAuth;
import fr.projet.besafe.model.User.User;
import fr.projet.besafe.modelApi.User.UserGson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserController {

    private ConnexionActivity view;
    private IBeSafeAPI iBeSafeAPI;

    private ProgressDialog dialog;

    public UserController(ConnexionActivity view){
        this.view = view;
        this.iBeSafeAPI = APIClient.getInstance().create(IBeSafeAPI.class);
    }

    public void login() throws JSONException {
        String email = this.view.getEmail().getText().toString();
        String password = this.view.getPassword().getText().toString();

        Call<UserGson> signIn = this.iBeSafeAPI.signUser(email, password);
        this.onPreExecute();
        signIn.enqueue(new Callback<UserGson>() {
            @Override
            public void onResponse(@NonNull Call<UserGson> call, @NonNull Response<UserGson> response) {
                UserGson userGson = response.body();
                System.out.println(response);
                if(userGson != null){
                    boolean success = userGson.isSuccess();
                    if(success){
                        User user = userGson.toUser();
                        UserAuth.getInstance().setUser(user);
                    }
                    view.resultConnexion(success);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserGson> call, @NonNull Throwable t) {
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
