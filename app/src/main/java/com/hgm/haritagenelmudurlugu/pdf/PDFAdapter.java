package com.hgm.haritagenelmudurlugu.pdf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hgm.haritagenelmudurlugu.R;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.Holder> {

    private List<PDFModel> list;
    private Context context;
    ItemClickListener itemClickListener;

    public PDFAdapter(List<PDFModel> list, Context context, ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.pdf_item, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.pdfName.setText(list.get(position).getPdfName());
        holder.pdfTarih.setText(list.get(position).getPdfTarih());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView pdfName;
        private TextView pdfTarih;
        private ImageView imageView;
        private CardView cardViewArsiv;

        public Holder(View itemView) {
            super(itemView);
            pdfName=itemView.findViewById(R.id.TV);
            pdfTarih=itemView.findViewById(R.id.TVtarih);
            imageView=itemView.findViewById(R.id.IV);
            cardViewArsiv=itemView.findViewById(R.id.cardViewArsiv);

            imageView.setOnClickListener(this);
            pdfName.setOnClickListener(this);
            pdfTarih.setOnClickListener(this);
            cardViewArsiv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);

        }
    }
}
