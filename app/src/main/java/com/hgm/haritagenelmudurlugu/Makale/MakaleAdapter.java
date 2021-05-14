package com.hgm.haritagenelmudurlugu.Makale;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hgm.haritagenelmudurlugu.R;
import com.hgm.haritagenelmudurlugu.SiteActivities.ViewActivity;

import java.util.List;

public class
MakaleAdapter extends RecyclerView.Adapter<MakaleAdapter.CardTasarimTutucu> {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;
    private Context mContext;
    private List<Makaleler> makalelerList;

    public MakaleAdapter(Context mContext, List<Makaleler> makalelerList) {
        this.mContext = mContext;
        this.makalelerList = makalelerList;
    }

    @NonNull
    @Override
    public CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        CardTasarimTutucu cardTasarimTutucu;

        if (viewType == TYPE_HEAD) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_layout, parent, false);
            cardTasarimTutucu = new CardTasarimTutucu(view, viewType);
            return cardTasarimTutucu;
        }

        if (viewType == TYPE_LIST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.makale_sorgulama_card_tasarim, parent, false);
            cardTasarimTutucu = new CardTasarimTutucu(view, viewType);
            return cardTasarimTutucu;
        }
        return null;


    }

    @Override
    public void onBindViewHolder(@NonNull CardTasarimTutucu holder, int position) {
        if (holder.view_type == TYPE_LIST) {
            Makaleler makaleler = makalelerList.get(position - 1);
            holder.textViewMakale.setText(makaleler.getMakale_adi());
            holder.textViewSayi.setText(makaleler.getMakale_sayi());
            holder.textViewYazar.setText(makaleler.getMakale_yazar());
            holder.textViewYil.setText(makaleler.getMakale_yil());

            holder.imageViewPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Snackbar.make(holder.imageViewPdf, "buradan Makale Açılacaktır", Snackbar.LENGTH_LONG).show();

                    Intent intent = new Intent(mContext, ViewActivity.class);
                    intent.putExtra("key",makaleler.getMakale_url());
                    Context context = holder.imageViewPdf.getContext();
                    context.startActivity(intent);

                }
            });

        } else if (holder.view_type == TYPE_HEAD) {


        }


    }

    @Override
    public int getItemCount() {
        return makalelerList.size() + 1;
    }

    TextView headMakaleAdi, headMakaleSayi, headMakaleYazar, headMakalePdf, headMakaleYil;

    public class CardTasarimTutucu extends RecyclerView.ViewHolder {
        int view_type;
        private TextView textViewMakale;
        private TextView textViewYil;
        private TextView textViewSayi;
        private TextView textViewYazar;
        private ImageView imageViewPdf;

        public CardTasarimTutucu(@NonNull View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_LIST) {
                textViewMakale = itemView.findViewById(R.id.textViewMakale);
                textViewYil = itemView.findViewById(R.id.textViewArsivAdi);
                textViewSayi = itemView.findViewById(R.id.textViewArsivLink);
                textViewYazar = itemView.findViewById(R.id.textViewYazar);
                imageViewPdf = itemView.findViewById(R.id.imageViewPdf);
                view_type = 1;
            } else if (viewType == TYPE_HEAD) {
                headMakaleAdi = (TextView) itemView.findViewById(R.id.headMakaleAdi);
                headMakaleSayi = (TextView) itemView.findViewById(R.id.headMakaleSayi);
                headMakaleYazar = (TextView) itemView.findViewById(R.id.headMakaleYazar);
                headMakalePdf = (TextView) itemView.findViewById(R.id.headMakalePdf);
                headMakaleYil = (TextView) itemView.findViewById(R.id.headMakaleYil);
                view_type = 0;
            }


        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEAD;
        return TYPE_LIST;
    }
}
