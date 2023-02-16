package fr.projet.besafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.projet.besafe.controller.AlerteBeSafe.DeleteAlerteBSController;
import fr.projet.besafe.controller.AlerteBeSafe.EnvoieAlerteBSController;
import fr.projet.besafe.model.AlerteBeSafe.Alerte;
import fr.projet.besafe.recycleViewAdapter.RecycleAdapterMesAlertes;

public class MesAlertesActivity extends AppCompatActivity implements RecycleAdapterMesAlertes.OnAlerteListenner {
    private ArrayList<Alerte> listAlertes;
    private List<Integer> idsAlertesDelete = new ArrayList<>();

    private View fragment;
    private RecycleAdapterMesAlertes adaptater;
    private RecyclerView recyclerView;

    private DeleteAlerteBSController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_alertes);

        this.controller = new DeleteAlerteBSController(this);
        this.listAlertes = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(listAlertes.size() == 0){
//            controller.selectAlertes();
        }
        setAdapter();

        Button delete = findViewById(R.id.btnDeleteMesAlertes);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idsAlertesDelete.size() > 0){
                    idsAlertesDelete.clear();
                }
                for(Map.Entry mapEntry : adaptater.getListIdAlertesCheck().entrySet()){
                    if((Boolean) mapEntry.getValue()){
                        idsAlertesDelete.add((Integer) mapEntry.getKey());
                    }
                }
                controller.deleteAlertes(idsAlertesDelete);
                finish();
                startActivity(getIntent());
            }
        });
    }


    public void setAdapter() {
        fragment = findViewById(R.id.fragmentMesAlertes);
        recyclerView = fragment.findViewById(R.id.listeMesAlertes);
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
}