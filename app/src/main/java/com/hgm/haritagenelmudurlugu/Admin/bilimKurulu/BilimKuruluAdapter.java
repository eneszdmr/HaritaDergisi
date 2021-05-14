package com.hgm.haritagenelmudurlugu.Admin.bilimKurulu;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.hgm.haritagenelmudurlugu.R;
import com.hgm.haritagenelmudurlugu.SiteActivities.Utility.BilimModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BilimKuruluAdapter extends RecyclerView.Adapter<BilimKuruluAdapter.CardTasarimTut> {
    private Context mContext;
    private List<BilimModel> bilimModelList;
    private DatabaseReference myref;

    public BilimKuruluAdapter(Context mContext, List<BilimModel> bilimModelList, DatabaseReference myref) {
        this.mContext = mContext;
        this.bilimModelList = bilimModelList;
        this.myref = myref;
    }

    @NonNull
    @Override
    public BilimKuruluAdapter.CardTasarimTut onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bilimkurulu_cardtasarim, parent, false);
        return new BilimKuruluAdapter.CardTasarimTut(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BilimKuruluAdapter.CardTasarimTut holder, int position) {
        final BilimModel bilimModel = bilimModelList.get(position);
        holder.tvbilimkurulAdi.setText(bilimModel.getAdiSoyadi());
        holder.tvbilimkurulunvani.setText(bilimModel.getUnvan());


        holder.imageViewMoreArsiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.imageViewMoreArsiv);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.makaleSil:
                                Snackbar.make(holder.imageViewMoreArsiv, "silmek ister misiniz ?", Snackbar.LENGTH_SHORT)
                                        .setAction("Evet", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                myref.child(bilimModel.getId()).removeValue();
                                            }
                                        }).show();
                                return true;
                            case R.id.makaleGuncelle:
                                alertGoster(bilimModel);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bilimModelList.size();
    }

    public class CardTasarimTut extends RecyclerView.ViewHolder {

        private TextView tvbilimkurulAdi;
        private TextView tvbilimkurulunvani;

        private ImageView imageViewMoreArsiv;

        public CardTasarimTut(@NonNull View itemView) {
            super(itemView);

            tvbilimkurulAdi = itemView.findViewById(R.id.tvbilimkurulAdi);
            tvbilimkurulunvani = itemView.findViewById(R.id.tvbilimkurulunvani);
            imageViewMoreArsiv = itemView.findViewById(R.id.imageViewMoreArsiv);

        }
    }

    public void alertGoster(BilimModel bilimModel) {
        LayoutInflater layout = LayoutInflater.from(mContext);
        View tasarim = layout.inflate(R.layout.alert_bilimkurul_ekle, null);

        EditText etUnvani = tasarim.findViewById(R.id.etUnvani);
        EditText etAdiSoyadi = tasarim.findViewById(R.id.etAdiSoyadi);
        EditText etKurumu = tasarim.findViewById(R.id.etKurumu);
        EditText etBransi = tasarim.findViewById(R.id.etBransi);


        etUnvani.setText(bilimModel.getUnvan());
        etAdiSoyadi.setText(bilimModel.getAdiSoyadi());
        etKurumu.setText(bilimModel.getKurumu());
        etBransi.setText(bilimModel.getBransi());


        AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
        ad.setTitle("Güncelle");
        ad.setView(tasarim);
        ad.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String unvan = etUnvani.getText().toString().trim();
                String adiSoyadi = etAdiSoyadi.getText().toString().trim();
                String kurumu = etKurumu.getText().toString().trim();
                String bransi = etBransi.getText().toString().trim();


                Map<String, Object> bilgiler = new HashMap<>();
                bilgiler.put("unvan", unvan);
                bilgiler.put("adiSoyadi", adiSoyadi);
                bilgiler.put("kurumu", kurumu);
                bilgiler.put("bransi", bransi);

                myref.child(bilimModel.getId()).updateChildren(bilgiler);

                Toast.makeText(mContext, "Güncellendi", Toast.LENGTH_SHORT).show();

            }
        });
        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.create().show();
    }
}
