package com.example.coviddefender.RecyclerViewAdapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coviddefender.R;
import com.example.coviddefender.entity.Vaccine_Info;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class VaccineInfoAdapter extends FirestoreRecyclerAdapter<Vaccine_Info,VaccineInfoAdapter.VaccineInfoViewHolder > {
    int lastPos = -1;
    FirebaseStorage storage;
    StorageReference storageReference;

    public VaccineInfoAdapter(@NonNull FirestoreRecyclerOptions<Vaccine_Info> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull VaccineInfoAdapter.VaccineInfoViewHolder holder, int position, @NonNull Vaccine_Info model) {

        holder.tv_description.setText(model.getDescription());

        Uri url = Uri.parse(model.getUrl());
        holder.card_vaccine_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                view.getContext().startActivity(Intent.createChooser(intent, "Browse with"));

            }
        });

    }

    @NonNull
    @Override
    public VaccineInfoAdapter.VaccineInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vaccine_info_items, parent, false);
        return new VaccineInfoViewHolder(view);
    }

    public class VaccineInfoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView tv_description;
        private final MaterialCardView card_vaccine_info;
        public VaccineInfoViewHolder(View view) {
            super(view);
            // initialize variables
            image = view.findViewById(R.id.image_vaccine_info);
            tv_description = view.findViewById(R.id.tv_vaccine_info_description);
            card_vaccine_info = view.findViewById(R.id.card_vaccine_info);
        }
    }
}
