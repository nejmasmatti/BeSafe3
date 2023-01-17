package fr.projet.besafe.global;

public class UserAuth {
    private static UserAuth INSTANCE = null;

    private String token;

    private UserAuth(){

    }

    public static synchronized UserAuth getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserAuth();
        }

        return INSTANCE;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
