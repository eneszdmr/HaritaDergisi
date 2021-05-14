package com.hgm.haritagenelmudurlugu.SiteActivities.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hgm.haritagenelmudurlugu.R;
import com.hgm.haritagenelmudurlugu.pdf.ItemClickListener;

import java.util.List;

public class BilimAdapter extends RecyclerView.Adapter<BilimAdapter.ViewHolder> {
    Context context;
    List<BilimModel> bilimModelList;



    public BilimAdapter(Context context, List<BilimModel> bilimModelList) {
        this.context = context;
        this.bilimModelList = bilimModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.bilim_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (bilimModelList !=null && bilimModelList.size() >0)
        {
            BilimModel model=bilimModelList.get(position);
            holder.tvunvan.setText(model.getUnvan());
            holder.tvadisoyadi.setText(model.getAdiSoyadi());
            holder.tvkurumu.setText(model.getKurumu());
            holder.tvbransi.setText(model.getBransi());

            holder.tableLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getRootView().getContext());
                    View dialogView=LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.bottom_sheet_bilim,null);
                    TextView textviewunvan,textviewadisoyadi,textviewkurumu,textviewbransi;
                    textviewunvan=dialogView.findViewById(R.id.textviewunvan);
                    textviewadisoyadi=dialogView.findViewById(R.id.textviewadisoyadi);
                    textviewkurumu=dialogView.findViewById(R.id.textviewkurumu);
                    textviewbransi=dialogView.findViewById(R.id.textviewbransi);

                    textviewunvan.setText(model.getUnvan());
                    textviewadisoyadi.setText(model.getAdiSoyadi());
                    textviewkurumu.setText(model.getKurumu());
                    textviewbransi.setText(model.getBransi());

                    builder.setView(dialogView);
                    builder.setCancelable(true);
                    builder.show();
                }
            });


        }else{
            return;
        }

    }

    @Override
    public int getItemCount() {
        return bilimModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvunvan,tvadisoyadi,tvkurumu,tvbransi;
        TableLayout tableLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvunvan=itemView.findViewById(R.id.tvunvan);
            tvadisoyadi=itemView.findViewById(R.id.tvadisoyadi);
            tvkurumu=itemView.findViewById(R.id.tvkurumu);
            tvbransi=itemView.findViewById(R.id.tvbransi);
            tableLayout=itemView.findViewById(R.id.tableLayout);

            tableLayout.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {


        }
    }

}
