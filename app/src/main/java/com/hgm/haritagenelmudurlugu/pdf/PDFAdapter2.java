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

public class PDFAdapter2 extends RecyclerView.Adapter<PDFAdapter2.Holder> {

    private List<PDFModel2> list;
    private Context context;
    ItemClickListener itemClickListener;

    public PDFAdapter2(List<PDFModel2> list, Context context, ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.pdf_item_ozel, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.pdfName.setText(list.get(position).getPdfName());
        holder.pdfDesc.setText(list.get(position).getPdfDesc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView pdfName;
        private TextView pdfDesc;
        private ImageView imageView;
        private CardView cardView;

        public Holder(View itemView) {
            super(itemView);
            pdfName=itemView.findViewById(R.id.TV);
            pdfDesc=itemView.findViewById(R.id.TVDesc);
            imageView=itemView.findViewById(R.id.IV);
            cardView=itemView.findViewById(R.id.cardView);

            imageView.setOnClickListener(this);
            pdfDesc.setOnClickListener(this);
            pdfName.setOnClickListener(this);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);

        }
    }
}
