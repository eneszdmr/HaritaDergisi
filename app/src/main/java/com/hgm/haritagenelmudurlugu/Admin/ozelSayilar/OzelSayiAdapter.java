package com.hgm.haritagenelmudurlugu.Admin.ozelSayilar;

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
import com.hgm.haritagenelmudurlugu.pdf.PDFModel2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OzelSayiAdapter extends RecyclerView.Adapter<OzelSayiAdapter.CardTasarimTut> {
    private Context mContext;
    private List<PDFModel2> pdfModelList;
    private DatabaseReference myref;

    public OzelSayiAdapter(Context mContext, List<PDFModel2> pdfModelList, DatabaseReference myref) {
        this.mContext = mContext;
        this.pdfModelList = pdfModelList;
        this.myref = myref;
    }

    @NonNull
    @Override
    public OzelSayiAdapter.CardTasarimTut onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.arsiv_cardtasarim, parent, false);
        return new OzelSayiAdapter.CardTasarimTut(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OzelSayiAdapter.CardTasarimTut holder, int position) {
        final PDFModel2 pdfModel = pdfModelList.get(position);
        holder.tvArsivAdi.setText(pdfModel.getPdfName());
        holder.tvArsivTarih.setText(pdfModel.getPdfDesc());


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
                                                myref.child(pdfModel.getPdfid()).removeValue();
                                            }
                                        }).show();
                                return true;
                            case R.id.makaleGuncelle:
                                alertGoster(pdfModel);
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
        return pdfModelList.size();
    }

    public class CardTasarimTut extends RecyclerView.ViewHolder {

        private TextView tvArsivAdi;
        private TextView tvArsivTarih;

        private ImageView imageViewMoreArsiv;

        public CardTasarimTut(@NonNull View itemView) {
            super(itemView);

            tvArsivAdi = itemView.findViewById(R.id.tvbilimkurulAdi);
            tvArsivTarih = itemView.findViewById(R.id.tvbilimkurulunvani);
            imageViewMoreArsiv = itemView.findViewById(R.id.imageViewMoreArsiv);

        }
    }

    public void alertGoster(PDFModel2 pdfModel) {
        LayoutInflater layout = LayoutInflater.from(mContext);
        View tasarim = layout.inflate(R.layout.alert_ozelsayi_ekle, null);

        EditText etOzelSayiAdi = tasarim.findViewById(R.id.etUnvani);
        EditText etOzelSayiDesc = tasarim.findViewById(R.id.etAdiSoyadi);
        EditText etOzelSayiUrl = tasarim.findViewById(R.id.etOzelSayiUrl);


        etOzelSayiAdi.setText(pdfModel.getPdfName());
        etOzelSayiDesc.setText(pdfModel.getPdfDesc());
        etOzelSayiUrl.setText(pdfModel.getPdfUrl());


        AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
        ad.setTitle("Özel Sayı Güncelle");
        ad.setView(tasarim);
        ad.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ozelSayiAdi = etOzelSayiAdi.getText().toString().trim();
                String ozelSayiDesc = etOzelSayiDesc.getText().toString().trim();
                String ozelSayiUrl = etOzelSayiUrl.getText().toString().trim();


                Map<String, Object> bilgiler = new HashMap<>();
                bilgiler.put("pdfName", ozelSayiAdi);
                bilgiler.put("pdfDesc", ozelSayiDesc);
                bilgiler.put("pdfUrl", ozelSayiUrl);

                myref.child(pdfModel.getPdfid()).updateChildren(bilgiler);

                Toast.makeText(mContext, "Özel Sayı Güncellendi", Toast.LENGTH_SHORT).show();

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
