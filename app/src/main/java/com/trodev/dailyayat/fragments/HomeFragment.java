package com.trodev.dailyayat.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trodev.dailyayat.R;
import com.trodev.dailyayat.models.UploadData;
import com.trodev.dailyayat.adapters.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recRV;
    private ImageAdapter imageAdapter;
    private DatabaseReference databaseReference;
    private List<UploadData> uploadData;

    LottieAnimationView animationView;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        animationView = view.findViewById(R.id.animationView);
        animationView.loop(true);

        recRV = view.findViewById(R.id.recRV);
        recRV.setHasFixedSize(true);
        recRV.setLayoutManager(new LinearLayoutManager(getContext()));

        uploadData = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadData uploadData1 = postSnapshot.getValue(UploadData.class);
                    uploadData.add(0, uploadData1);
                }

                imageAdapter = new ImageAdapter(getContext(), uploadData);
                recRV.setAdapter(imageAdapter);
                animationView.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                animationView.setVisibility(View.VISIBLE);

            }
        });

        return  view;
    }

}