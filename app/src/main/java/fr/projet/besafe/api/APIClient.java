package fr.projet.besafe.api;

import fr.projet.besafe.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit instance = null;
    private static final String API = BuildConfig.API_URL_BESAFE_API + "/";

    private APIClient(){

    }

    public static Retrofit getInstance(){
        if(instance == null){
            instance = new Retrofit.Builder()
                    .baseUrl(API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
