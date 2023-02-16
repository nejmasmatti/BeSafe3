package fr.projet.besafe;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.projet.besafe.controller.AlerteBeSafe.EnvoieAlerteBSController;
import fr.projet.besafe.global.UserAuth;
import fr.projet.besafe.model.Adresse;

public class FormCreationAlerte extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private EnvoieAlerteBSController controller;
    private RadioGroup typesAlertes;
    private RadioButton typeAlerteSelected;
    private SeekBar nivDanger;

    private List<Address> dataLocation = null;
    private EditText adresseEditText;

    private Button creerAlerte;

    Map<String, Object> alerte;
    private Adresse adresseAlerte;

    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_creation_alerte);

        this.controller = new EnvoieAlerteBSController(this);

        typesAlertes = (RadioGroup) findViewById(R.id.radioGroupTypesAlertes);

        adresseEditText = (EditText) findViewById(R.id.edtAdresse);

        nivDanger = (SeekBar) findViewById(R.id.seekBarNivDanger);

        creerAlerte = (Button) findViewById(R.id.btnCreerAlerte);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        alerte = new HashMap<>();
        alerte.put("id_user", UserAuth.getInstance().getUser().getId());

        typesAlertes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                typeAlerteSelected = findViewById(i);
            }
        });

        nivDanger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                alerte.put("niveau_danger", i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        creerAlerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTypeAlerteAndAdressSelected()){
                    alerte.put("type_alerte", String.valueOf(typeAlerteSelected.getText()));
                    alerte.put("adresse", adresseAlerte);
                    alerte.put("libelle", "Agression " + String.valueOf(typeAlerteSelected.getText()).toLowerCase(Locale.ROOT) + " à " + adresseAlerte.getVille());
                    controller.sendAlerte();
                }
                else {
                    Toast.makeText(FormCreationAlerte.this, "Veuillez bien pensez à cliquer sur le type de l'alerte et saisir l'adresse", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isTypeAlerteAndAdressSelected(){
        return this.typeAlerteSelected != null && this.adresseAlerte != null;
    }

    private void getLastLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>()
                    {
                        @Override
                        public void onSuccess(Location location)
                        {
                            if(location != null){
                                Geocoder geocoder = new Geocoder(FormCreationAlerte.this, Locale.getDefault());
                                try {
                                    dataLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    adresseEditText.setText(dataLocation.get(0).getAddressLine(0));
                                    adresseAlerte = new Adresse();
                                    adresseAlerte.setLibelle(String.valueOf(dataLocation.get(0).getAddressLine(0)));
                                    adresseAlerte.setVille(String.valueOf(dataLocation.get(0).getLocality()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
        else
        {
            askPermission();

        }
    }

    private void askPermission(){
        ActivityCompat.requestPermissions(FormCreationAlerte.this, new String []
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void resultConnexion(boolean success){
        this.controller.onPostExecute();
        if(success){
            Intent accueil = new Intent(FormCreationAlerte.this, MainActivity.class);
            startActivity(accueil);
        }
        else {
            Toast.makeText(FormCreationAlerte.this,"Envoie de l'alerte échoué",Toast.LENGTH_LONG).show();
        }
    }

    public ProgressDialog createDialogue(){
        return new ProgressDialog(this);
    }

    public Map<String, Object> getAlerte() {
        return alerte;
    }
}