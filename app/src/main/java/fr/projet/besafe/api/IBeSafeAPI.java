package fr.projet.besafe.api;

import java.util.Map;

import fr.projet.besafe.modelApi.User.UserGson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IBeSafeAPI {

    @POST("/api/login")
    Call<UserGson> signUser(
            @Body Map<String, String> user
    );
}
