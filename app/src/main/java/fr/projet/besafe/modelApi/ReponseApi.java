package fr.projet.besafe.modelApi;

public class ReponseApi {
    private boolean success = false;
    private String message;

    public ReponseApi(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
