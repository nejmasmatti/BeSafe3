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
        int idAlerte = ((Alerte) listAlertes.get(position)).getIdAlerte();
        int nivDanger = ((Alerte) listAlertes.get(position)).getNivDanger();
        String titre = "Alerte pour un vol n° " + ((Alerte) listAlertes.get(position)).getIdAlerte();
        String photo = "@drawable/";
        if(nivDanger > 0 && nivDanger < 35){
            photo += "icons8_faible_risque_80.png";
        }
        else if(nivDanger >= 35 && nivDanger < 70){
            photo += "icons8_risque_moyen_80.png";
        }
        else if(nivDanger >= 70 && nivDanger <=100){
            photo += "icons8_risque_eleve_80.png";
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
    }


    @Override
    public int getItemCount() {
        return listAlertes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtTitre, txtAdresse;
        private ImageView img;
        private OnAlerteListenner onAlerteListenner;
        private CheckBox checkBox;

        public MyViewHolder(final View view, OnAlerteListenner onAnnonceListenner){
            super(view);
            txtTitre = view.findViewById(R.id.txtTitreAlerte);
            txtAdresse = view.findViewById(R.id.txtAdresse);
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
