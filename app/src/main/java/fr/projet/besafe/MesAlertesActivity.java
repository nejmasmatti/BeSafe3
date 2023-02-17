package fr.projet.besafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.projet.besafe.controller.AlerteBeSafe.DeleteAlerteBSController;
import fr.projet.besafe.controller.AlerteBeSafe.EnvoieAlerteBSController;
import fr.projet.besafe.controller.AlerteBeSafe.RecupererAlerteBSUserController;
import fr.projet.besafe.model.AlerteBeSafe.Alerte;
import fr.projet.besafe.recycleViewAdapter.RecycleAdapterMesAlertes;

public class MesAlertesActivity extends AppCompatActivity implements RecycleAdapterMesAlertes.OnAlerteListenner {
    private ArrayList<Alerte> listAlertes;
    private List<Integer> idsAlertesDelete = new ArrayList<>();

    private RecycleAdapterMesAlertes adaptater;

    private DeleteAlerteBSController deleteAlerteBSController;
    private RecupererAlerteBSUserController recupererAlerteBSUserController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_alertes);

        this.deleteAlerteBSController = new DeleteAlerteBSController(this);
        this.recupererAlerteBSUserController = new RecupererAlerteBSUserController(this);
        this.listAlertes = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(listAlertes.size() == 0){
            try {
                recupererAlerteBSUserController.getAlerteUser();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Button delete = findViewById(R.id.btnDeleteMesAlertes);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idsAlertesDelete.size() > 0){
                    idsAlertesDelete.clear();
                }
                for(Map.Entry<Integer, Boolean> mapEntry : adaptater.getListIdAlertesCheck().entrySet()){
                    if((Boolean) mapEntry.getValue()){
                        idsAlertesDelete.add((Integer) mapEntry.getKey());
                    }
                }
                deleteAlerteBSController.deleteAlertes(idsAlertesDelete);
                finish();
                startActivity(getIntent());
            }
        });
    }


    public void setAdapter() {
        View fragment = findViewById(R.id.fragmentMesAlertes);
        RecyclerView recyclerView = fragment.findViewById(R.id.listeMesAlertes);
        TextView nombreAlerte = (TextView) fragment.findViewById(R.id.nombreAlerte);
        nombreAlerte.setText(String.valueOf(listAlertes.size()));
        adaptater = new RecycleAdapterMesAlertes(listAlertes, MesAlertesActivity.this, R.layout.recycle_view_mes_alertes, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(adaptater);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onAlerteClick(int position) {
        Intent a = new Intent(MesAlertesActivity.this, DetailAlerteBeSafeActivity.class);
        a.putExtra("idAlerte", ((Alerte) listAlertes.get(position)).getIdAlerte());
        startActivity(a);
    }

    public void resultGetAlerte(boolean success){
        this.recupererAlerteBSUserController.onPostExecute();
        if(success){
            Toast.makeText(MesAlertesActivity.this,"Voici le fil de vos alertes",Toast.LENGTH_LONG).show();
            setAdapter();
        }
        else {
            Toast.makeText(MesAlertesActivity.this,"Chargement de vos alertes échoué",Toast.LENGTH_LONG).show();
        }
    }

    public void resultDeleteAlerte(boolean success){
        this.deleteAlerteBSController.onPostExecute();
        if(success){
            Toast.makeText(MesAlertesActivity.this,"Voici le fil de vos alertes après supression",Toast.LENGTH_LONG).show();
            setAdapter();
        }
        else {
            Toast.makeText(MesAlertesActivity.this,"Suppression échoué",Toast.LENGTH_LONG).show();
        }
    }

    public ProgressDialog createDialogue(){
        return new ProgressDialog(this);
    }

    public void setListAlertes(ArrayList<Alerte> listAlertes) {
        this.listAlertes = listAlertes;
    }
}