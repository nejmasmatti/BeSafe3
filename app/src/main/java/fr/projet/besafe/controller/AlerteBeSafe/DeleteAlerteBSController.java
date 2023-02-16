package fr.projet.besafe.controller.AlerteBeSafe;

import android.app.ProgressDialog;

import java.util.List;

import fr.projet.besafe.FormCreationAlerte;
import fr.projet.besafe.MesAlertesActivity;
import fr.projet.besafe.api.APIClient;
import fr.projet.besafe.api.IBeSafeAPI;
import fr.projet.besafe.model.AlerteBeSafe.Alerte;

public class DeleteAlerteBSController {
    private MesAlertesActivity view;
    private Alerte alerteVol;
    //Api interface
    private IBeSafeAPI iBeSafeAPI;

    private ProgressDialog dialog;

    public DeleteAlerteBSController(MesAlertesActivity view){
        this.view = view;
        this.iBeSafeAPI = APIClient.getInstance().create(IBeSafeAPI.class);
    }

    public void deleteAlertes(List<Integer> idsAlertesDelete){

    }
}
