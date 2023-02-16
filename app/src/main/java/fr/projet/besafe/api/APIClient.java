package fr.projet.besafe.api;

import java.io.IOException;

import fr.projet.besafe.BuildConfig;
import fr.projet.besafe.global.UserAuth;
import fr.projet.besafe.model.User.User;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static OkHttpClient client = null;
    private static Retrofit instance = null;
    private static final String API = BuildConfig.API_URL_BESAFE_API + "/";

    private APIClient(){
    }

    private static OkHttpClient buildClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        User user = UserAuth.getInstance().getUser();
                        String token = user != null ? user.getToken() : null;
                        Request.Builder builder = request.newBuilder()
                                .addHeader("Authorization", token !=null ? "Bearer " + token : "" );

                        request = builder.build();
                        return chain.proceed(request);
                    }
                });
        return builder.build();
    }

    public static Retrofit getInstance(){
        if(instance == null){
            client = buildClient();
            instance = new Retrofit.Builder()
                    .baseUrl(API)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
