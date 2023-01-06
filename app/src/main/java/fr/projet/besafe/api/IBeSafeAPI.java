package fr.projet.besafe.api;

import java.util.Map;

import fr.projet.besafe.modelApi.AlertesBeSafe.AlerteVolGson;
import fr.projet.besafe.modelApi.User.UserGson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IBeSafeAPI {

    @POST("/api/login")
    Call<UserGson> signUser(
            @Body Map<String, String> user
    );

    @POST("/api/aleretev/create")
    Call<AlerteVolGson> sendAlerteV(
            @Body Map<String, String> alertes
    );
    @POST("/api/aleretevv/create")
    Call<AlerteVolGson> sendAlerteVV(
            @Body Map<String, String> alertes
    );
    @POST("/api/aleretevp/create")
    Call<AlerteVolGson> sendAlerteVP(
            @Body Map<String, String> alertes
    );
}
