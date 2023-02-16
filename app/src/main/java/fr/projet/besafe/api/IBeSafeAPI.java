package fr.projet.besafe.api;

import com.google.gson.JsonObject;

import fr.projet.besafe.modelApi.ReponseApi;
import fr.projet.besafe.modelApi.User.UserGson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IBeSafeAPI {

    @GET("/api/login")
    Call<UserGson> signUser(
        @Query("email") String email,
        @Query("password") String password
    );

    @POST("/api/alerte/create")
    Call<ReponseApi> sendAlerte(
            @Body JsonObject alerte
    );

}
