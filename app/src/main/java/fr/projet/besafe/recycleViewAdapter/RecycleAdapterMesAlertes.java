package fr.projet.besafe.recycleViewAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import fr.projet.besafe.R;
import fr.projet.besafe.model.AlerteBeSafe.Alerte;

public class RecycleAdapterMesAlertes extends RecyclerView.Adapter<RecycleAdapterMesAlertes.MyViewHolder> {
    private ArrayList<Alerte> listAlertes;
    private int ressource;
    private Context context;
    private LayoutInflater inflater;

    private OnAlerteListenner onAlerteListenner;

    private HashMap<Integer, Boolean> listIdAnnonceCheck = new HashMap<Integer, Boolean>();

    public RecycleAdapterMesAlertes(ArrayList<Alerte> listAlertes, Context ct, int ressource, OnAlerteListenner onAlerteListenner){
        this.listAlertes = listAlertes;
        this.context = ct;
        this.ressource = ressource;
        this.inflater = LayoutInflater.from(context);
        this.onAlerteListenner = onAlerteListenner;
    }

    @NonNull
    @Override
    public RecycleAdapterMesAlertes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recycleViewAnnonces = inflater.inflate(ressource, null, false);
        return new MyViewHolder(recycleViewAnnonces, onAlerteListenner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapterMesAlertes.MyViewHolder holder, int position) {
        int idAlerte = listAlertes.get(position).getIdAlerte();
        int nivDanger = listAlertes.get(position).getNivDanger();
        String titre = listAlertes.get(position).getLibelle();
        String adresse = listAlertes.get(position).getAdresse().getLibelle();
        String photo = "@drawable/";
        if(nivDanger > 0 && nivDanger < 35){
            photo += "icons8_faible_risque_80";
        }
        else if(nivDanger >= 35 && nivDanger < 70){
            photo += "icons8_risque_moyen_80";
        }
        else if(nivDanger >= 70 && nivDanger <=100){
            photo += "icons8_risque_eleve_80";
        }


        int imgRessource = context.getResources().getIdentifier(photo, null, context.getPackageName());
        if(imgRessource > 0){
            holder.img.setImageResource(imgRessource);
        }
        else {
            try {
                Bitmap pic = BitmapFactory.decodeFile(photo);
                if(pic != null)
                    holder.img.setImageBitmap(pic);
            }catch (Exception e){
                System.out.println("Erreur");
            }
        }


        listIdAnnonceCheck.put(idAlerte, false);
        holder.txtTitre.setText(titre);
        holder.txtNivDanger.setText(String.valueOf(nivDanger));
        holder.txtAdresse.setText(adresse);
    }


    @Override
    public int getItemCount() {
        return listAlertes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtTitre, txtAdresse, txtNivDanger;
        private ImageView img;
        private OnAlerteListenner onAlerteListenner;
        private CheckBox checkBox;

        public MyViewHolder(final View view, OnAlerteListenner onAnnonceListenner){
            super(view);
            txtTitre = view.findViewById(R.id.titreAlerte);
            txtAdresse = view.findViewById(R.id.adresse);
            txtNivDanger = view.findViewById(R.id.nivDanger);
            checkBox = view.findViewById(R.id.checkBoxDelete);
            img = view.findViewById(R.id.imageView);
            img.setClipToOutline(true);

            this.onAlerteListenner = onAnnonceListenner;

            itemView.setOnClickListener(this);

            if(checkBox != null){
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkBox.isChecked()){
                            listIdAnnonceCheck.put(((Alerte) listAlertes.get(getAdapterPosition())).getIdAlerte(), true);
                        }
                        else {
                            listIdAnnonceCheck.put(((Alerte) listAlertes.get(getAdapterPosition())).getIdAlerte(), false);
                        }
                    }
                });
            }

        }

        @Override
        public void onClick(View view) {
            onAlerteListenner.onAlerteClick(getAdapterPosition());
        }
    }

    public HashMap<Integer, Boolean> getListIdAlertesCheck() {
        return listIdAnnonceCheck;
    }

    public interface OnAlerteListenner{
        void onAlerteClick(int position);
    }
}
