package com.hgm.haritagenelmudurlugu.Admin;

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
import com.hgm.haritagenelmudurlugu.Makale.Makaleler;
import com.hgm.haritagenelmudurlugu.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakaleDuzenleAdapter extends RecyclerView.Adapter<MakaleDuzenleAdapter.CardTasarimTut> {
    private Context mContext;
    private List<Makaleler> makalelersListesi;
    private DatabaseReference myref;

    public MakaleDuzenleAdapter(Context mContext, List<Makaleler> makalelersListesi, DatabaseReference myref) {
        this.mContext = mContext;
        this.makalelersListesi = makalelersListesi;
        this.myref = myref;
    }

    @NonNull
    @Override
    public CardTasarimTut onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.makale_ekle_card_tasarim, parent, false);
        return new CardTasarimTut(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTasarimTut holder, int position) {
       final Makaleler makale = makalelersListesi.get(position);
        holder.tvMakaleAdi.setText(makale.getMakale_adi());
        holder.tvSayi.setText(makale.getMakale_sayi());
        holder.tvYil.setText(makale.getMakale_yil());
        holder.tvYazar.setText(makale.getMakale_yazar());

        holder.imageViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.imageViewMore);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.makaleSil:
                                Snackbar.make(holder.imageViewMore, "silmek ister misiniz ?", Snackbar.LENGTH_SHORT)
                                        .setAction("Evet", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                myref.child(makale.getMakale_id()).removeValue();
                                            }
                                        }).show();
                                return true;
                            case R.id.makaleGuncelle:
                                alertGoster(makale);
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
        return makalelersListesi.size();
    }

    public class CardTasarimTut extends RecyclerView.ViewHolder {

        private TextView tvMakaleAdi;
        private TextView tvYil;
        private TextView tvSayi;
        private TextView tvYazar;
        private ImageView imageViewMore;

        public CardTasarimTut(@NonNull View itemView) {
            super(itemView);

            tvMakaleAdi = itemView.findViewById(R.id.tvMakaleAdi);
            tvYil = itemView.findViewById(R.id.tvbilimkurulAdi);
            tvSayi = itemView.findViewById(R.id.tvArsivUrl);
            tvSayi = itemView.findViewById(R.id.tvArsivUrl);
            tvYazar = itemView.findViewById(R.id.tvYazar);
            imageViewMore = itemView.findViewById(R.id.imageViewMore);
        }
    }

    public void alertGoster(Makaleler makale) {
        LayoutInflater layout = LayoutInflater.from(mContext);
        View tasarim = layout.inflate(R.layout.alert_makale_ekle, null);

        EditText editTextMakaleAdi = tasarim.findViewById(R.id.etUnvani);
        EditText editTextMakaleyil = tasarim.findViewById(R.id.etYil);
        EditText editTextMakaleSayi = tasarim.findViewById(R.id.etSayi);
        EditText editTextMakaleYazar = tasarim.findViewById(R.id.etYazar);
        EditText editTextMakaleUrl = tasarim.findViewById(R.id.etOzelSayiUrl);

        editTextMakaleAdi.setText(makale.getMakale_adi());
        editTextMakaleSayi.setText(makale.getMakale_sayi());
        editTextMakaleUrl.setText(makale.getMakale_url());
        editTextMakaleYazar.setText(makale.getMakale_yazar());
        editTextMakaleyil.setText(makale.getMakale_yil());

        AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
        ad.setTitle("Makale Güncelle");
        ad.setView(tasarim);
        ad.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String makaleAdi = editTextMakaleAdi.getText().toString().trim();
                String makaleyil = editTextMakaleyil.getText().toString().trim();
                String makaleSayi = editTextMakaleSayi.getText().toString().trim();
                String makaleYazar = editTextMakaleYazar.getText().toString().trim();
                String makaleUrl = editTextMakaleUrl.getText().toString().trim();


                Map<String,Object> bilgiler =new HashMap<>();
                bilgiler.put("makale_adi",makaleAdi);
                bilgiler.put("makale_yil",makaleyil);
                bilgiler.put("makale_sayi",makaleSayi);
                bilgiler.put("makale_yazar",makaleYazar);
                bilgiler.put("makale_url",makaleUrl);

                myref.child(makale.getMakale_id()).updateChildren(bilgiler);

                Toast.makeText(mContext, "Makale Güncellendi", Toast.LENGTH_SHORT).show();

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
