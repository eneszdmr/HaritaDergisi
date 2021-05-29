package com.hgm.haritagenelmudurlugu.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hgm.haritagenelmudurlugu.Admin.SendNotif.APIService;
import com.hgm.haritagenelmudurlugu.Admin.SendNotif.Client;
import com.hgm.haritagenelmudurlugu.Admin.SendNotif.Data;
import com.hgm.haritagenelmudurlugu.Admin.SendNotif.MyResponse;
import com.hgm.haritagenelmudurlugu.Admin.SendNotif.NotificationSender;
import com.hgm.haritagenelmudurlugu.Admin.SendNotif.Token;
import com.hgm.haritagenelmudurlugu.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class sonSayiFragment extends Fragment implements ValueEventListener {

    EditText etSonSayi, bildirimBaslik, bildirimIcerik,userTB;
    Button btnGuncelle, btnBildirim;
    private APIService apiService;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mHeadingReference = mRootReference.child("dergisonsayi");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_son_sayi, container, false);

        etSonSayi = v.findViewById(R.id.etSonSayi);
        bildirimBaslik = v.findViewById(R.id.bildirimBaslik);
        userTB = v.findViewById(R.id.userTB);
        bildirimIcerik = v.findViewById(R.id.bildirimIcerik);
        btnGuncelle = v.findViewById(R.id.btnGuncelle);
        btnBildirim = v.findViewById(R.id.btnBildirim);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);



        btnBildirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialogum = new AlertDialog.Builder(getContext())
                        .setTitle("Kullanıcılara Bildirim Gönderilecektir, Emin misiniz ?")
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                FirebaseDatabase.getInstance().getReference().child("Tokens").child(userTB.getText().toString().trim()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String userToken=snapshot.getValue(String.class);
                                        sendNotifications(userToken,bildirimBaslik.getText().toString().trim(),bildirimIcerik.getText().toString().trim());

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getContext(), "Hata alındı !", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                Toast.makeText(getContext(), "Bildirim Gönderilmiştir.", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Hayır", null).show();





            }
        });

        UpdateToken();


        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Derginin Son Sayısı Güncellenecektir, Emin misiniz ?")
                        .setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String dergisonsayi = etSonSayi.getText().toString();

                                if (TextUtils.isEmpty(dergisonsayi)) {
                                    etSonSayi.setError("Eksik bilgi");
                                    etSonSayi.requestFocus();
                                } else {

                                    mHeadingReference.setValue(dergisonsayi);
                                    etSonSayi.setText(dergisonsayi);

                                    Toast.makeText(getContext(), "URL Güncellenmiştir.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        })
                        .setNegativeButton("iptal", null).show();


            }
        });


        return v;
    }


    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    public void sendNotifications(String userToken, String title, String message) {
        Data data=new Data(title,message);
        NotificationSender notificationSender=new NotificationSender(data,userToken);
        apiService.sendNotification(notificationSender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code()==200){
                    if (response.body().success !=1){
                        Toast.makeText(getContext(),"Failed",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mHeadingReference.addValueEventListener(this);
    }
//son sayi güncelleme (update)
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.getValue(String.class) != null) {
            String key = snapshot.getKey();
            if (key.equals("dergisonsayi")) {
                String dergisonsayi = snapshot.getValue(String.class);
                etSonSayi.setText(dergisonsayi);
            }

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }


}
