package fr.projet.besafe.api;

import com.google.gson.JsonObject;

import java.util.List;

import fr.projet.besafe.modelApi.AlertesBeSafe.AlerteParUserGson;
import fr.projet.besafe.modelApi.ReponseApi;
import fr.projet.besafe.modelApi.User.UserGson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("/api/alertes/user")
    Call<AlerteParUserGson> getAlerteUser(
            @Query("id_user") int idUser
    );

    @POST("/api/alertes/delete")
    Call<ReponseApi> deleteAlerte(
            @Body JsonObject alerte
    );

}
