package fr.projet.besafe.global;

import fr.projet.besafe.model.User.User;

public class UserAuth {
    private static UserAuth INSTANCE = null;

    private User user;

    private UserAuth(){

    }

    public static synchronized UserAuth getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserAuth();
        }

        return INSTANCE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
