package com.hgm.haritagenelmudurlugu.SiteActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hgm.haritagenelmudurlugu.Makale.MakaleAdapter;
import com.hgm.haritagenelmudurlugu.Makale.Makaleler;
import com.hgm.haritagenelmudurlugu.R;

import java.util.ArrayList;

public class MakaleSorgulaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    Toolbar toolbarMakale;
    RecyclerView rv;
    private ArrayList<Makaleler> makalelerArrayList;
    private MakaleAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makale_sorgula);
        toolbarMakale=findViewById(R.id.toolbarMakale);
        rv=findViewById(R.id.rvAdmin);
        toolbarMakale.setTitle("Makale Sorgulama");
        setSupportActionBar(toolbarMakale);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("makaleler");

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        makalelerArrayList=new ArrayList<>();
        adapter=new MakaleAdapter(this,makalelerArrayList);
        rv.setAdapter(adapter);

        tumMakaleler();



    }

    public void tumMakaleler(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                makalelerArrayList.clear();
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    Makaleler makaleler=d.getValue(Makaleler.class);
                    makaleler.setMakale_id(d.getKey());
                    makalelerArrayList.add(makaleler);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void makaleArama(String aramaKelimesi){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                makalelerArrayList.clear();
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    Makaleler makaleler=d.getValue(Makaleler.class);

                    if (makaleler.getMakale_adi().contains(aramaKelimesi)
                            || makaleler.getMakale_sayi().contains(aramaKelimesi)
                            || makaleler.getMakale_yazar().contains(aramaKelimesi)
                    || makaleler.getMakale_yil().contains(aramaKelimesi)){

                        makaleler.setMakale_id(d.getKey());
                        makalelerArrayList.add(makaleler);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        MenuItem menuItem=menu.findItem(R.id.action_ara);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        makaleArama(newText);
        return false;
    }
}