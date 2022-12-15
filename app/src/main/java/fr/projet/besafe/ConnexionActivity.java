package fr.projet.besafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.projet.besafe.controller.UserController;

public class ConnexionActivity extends AppCompatActivity {

    private UserController userController;

    private EditText email;
    private EditText password;

    //private RequestQueue queue;

    private String token;

    private ProgressDialog dialog;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        this.userController = new UserController(this);

        email = (EditText) findViewById(R.id.edtlogin);
        password = (EditText) findViewById(R.id.edtpass);
        Button connexion = (Button) findViewById(R.id.btnconn);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                makeConnexion();
                connexion();
            }
        });
    }

    public EditText getEmail() {
        return email;
    }

    public EditText getPassword() {
        return password;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private void connexion(){
        this.userController.makeConnexion();
    }

    public void resultConnexion(boolean success){
        if(success){
            Intent accueil = new Intent(ConnexionActivity.this, MainActivity.class);
            startActivity(accueil);
        }
        else {
            Toast.makeText(ConnexionActivity.this,"Connexion échoué, email ou mot de passe invalides",Toast.LENGTH_LONG).show();
        }
    }

    public ProgressDialog createDialogue(){
        return new ProgressDialog(this);
    }
}