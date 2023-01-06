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
import java.util.List;
import java.util.Locale;

public class FormCreationAlerte extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;

    private RadioGroup typesAlertes;
    private static final int TYPE_ALERTE_AGRESSION_VOL = 0;
    private static final int TYPE_ALERTE_AGRESSION_PHYSIQUE = 1;
    private static final int TYPE_ALERTE_AGRESSION_VERBALE = 2;

    private List<Address> dataLocation = null;
    private EditText adresse;

    private SeekBar nivDanger;
    private Button creerAlerte;


    private String adresseAlerte, latitudeAlerte, longitudeAlerte, villeAlerte, nivDangerAlerte;

    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_creation_alerte);

        typesAlertes = (RadioGroup) findViewById(R.id.radioGroupTypesAlertes);

        adresse = (EditText) findViewById(R.id.edtAdresse);

        nivDanger = (SeekBar) findViewById(R.id.seekBarNivDanger);

        creerAlerte = (Button) findViewById(R.id.btnCreerAlerte);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        typesAlertes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton typeAlerte = findViewById(i);
                System.out.println(typeAlerte.getText());
            }
        });

        creerAlerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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

                                    latitudeAlerte = String.valueOf(dataLocation.get(0).getLatitude());
                                    longitudeAlerte = String.valueOf(dataLocation.get(0).getLongitude());
                                    adresseAlerte = String.valueOf(dataLocation.get(0).getAddressLine(0));
                                    adresse.setText(dataLocation.get(0).getAddressLine(0));
                                    villeAlerte = String.valueOf(dataLocation.get(0).getLocality());
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
    // faire une classe pour récupérer la position et demander la permission S SOLID
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

    public SeekBar getNivDanger() {
        return nivDanger;
    }

    public void resultConnexion(boolean success){
        if(success){
            Intent accueil = new Intent(FormCreationAlerte.this, MainActivity.class);
            startActivity(accueil);
        }
        else {
            Toast.makeText(FormCreationAlerte.this,"Connexion échoué, email ou mot de passe invalides",Toast.LENGTH_LONG).show();
        }
    }

    public ProgressDialog createDialogue(){
        return new ProgressDialog(this);
    }
}