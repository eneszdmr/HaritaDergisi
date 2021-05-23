package com.hgm.haritagenelmudurlugu.Admin;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hgm.haritagenelmudurlugu.Makale.Makaleler;
import com.hgm.haritagenelmudurlugu.R;

import java.util.ArrayList;

public class makaleSorguFragment extends Fragment {
    private RecyclerView rvAdmin;
    private FloatingActionButton fabAdmin;
    private ArrayList<Makaleler> makalelerArrayList;
    private MakaleDuzenleAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_makale_sorgu, container, false);

        rvAdmin = view.findViewById(R.id.rvAdmin);
        fabAdmin = view.findViewById(R.id.fabAdmin);

        database = FirebaseDatabase.getInstance();
        myref = database.getReference("makaleler");


        rvAdmin.setHasFixedSize(true);
        rvAdmin.setLayoutManager(new LinearLayoutManager(getContext()));


        makalelerArrayList = new ArrayList<>();
        adapter = new MakaleDuzenleAdapter(getContext(), makalelerArrayList, myref);
        rvAdmin.setAdapter(adapter);

        TumMakaleler();

        fabAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertGoster();

            }
        });

        return view;


    }

    public void alertGoster() {
        LayoutInflater layout = LayoutInflater.from(getContext());
        View tasarim = layout.inflate(R.layout.alert_makale_ekle, null);

        EditText editTextMakaleAdi = tasarim.findViewById(R.id.etUnvani);
        EditText editTextMakaleyil = tasarim.findViewById(R.id.etYil);
        EditText editTextMakaleSayi = tasarim.findViewById(R.id.etSayi);
        EditText editTextMakaleYazar = tasarim.findViewById(R.id.etYazar);
        EditText editTextMakaleUrl = tasarim.findViewById(R.id.etOzelSayiUrl);


        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Makale Ekle");
        ad.setView(tasarim);
        ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.setView(tasarim);
        AlertDialog dialog = ad.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextMakaleAdi.getText().toString())) {
                    Toast.makeText(getContext(), "Makale Adı eksik", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editTextMakaleyil.getText().toString())) {
                    Toast.makeText(getContext(), "Makale Yıl eksik", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editTextMakaleSayi.getText().toString())) {
                    Toast.makeText(getContext(), "Makale Sayı eksik", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editTextMakaleYazar.getText().toString())) {
                    Toast.makeText(getContext(), "Makale Yazar eksik", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editTextMakaleUrl.getText().toString())) {
                    Toast.makeText(getContext(), "Makale URL eksik", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String makaleAdi = editTextMakaleAdi.getText().toString().trim();
                    String makaleyil = editTextMakaleyil.getText().toString().trim();
                    String makaleSayi = editTextMakaleSayi.getText().toString().trim();
                    String makaleYazar = editTextMakaleYazar.getText().toString().trim();
                    String makaleUrl = editTextMakaleUrl.getText().toString().trim();
                    String key = myref.push().getKey();
                    Makaleler mkl = new Makaleler(key, makaleAdi, makaleyil, makaleSayi, makaleYazar, makaleUrl);
                    myref.push().setValue(mkl);

                    Toast.makeText(getContext(), "makale eklendi", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
            }
        });
    }

    public void TumMakaleler() {
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                makalelerArrayList.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    Makaleler makale = d.getValue(Makaleler.class);
                    makale.setMakale_id(d.getKey());
                    makalelerArrayList.add(makale);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}