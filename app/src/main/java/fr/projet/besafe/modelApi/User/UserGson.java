package fr.projet.besafe.modelApi.User;

import fr.projet.besafe.model.User.User;
import fr.projet.besafe.modelApi.AReponseApi;

public class UserGson extends AReponseApi {

    private String token;
    private int id;
    private String name;
    private String email;


    public UserGson(boolean success, String message, String token, int id, String name, String email){
        super(success, message);
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User toUser(){
        return new User(
                this.token,
                this.id,
                this.name,
                this.email
        );
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
