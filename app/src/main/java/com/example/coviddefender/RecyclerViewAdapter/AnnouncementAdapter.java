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
import com.example.coviddefender.entity.Announcement;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AnnouncementAdapter extends FirestoreRecyclerAdapter<Announcement, AnnouncementAdapter.AnnouncementViewHolder> {
    FirebaseStorage storage;
    StorageReference storageReference;

    public AnnouncementAdapter(@NonNull FirestoreRecyclerOptions<Announcement> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AnnouncementAdapter.AnnouncementViewHolder holder, int position, @NonNull Announcement model) {
        holder.tv_description.setText(model.getDescription());
        Uri url = Uri.parse(model.getUrl());
        holder.announcement_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open browser with link
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                view.getContext().startActivity(Intent.createChooser(intent, "Browse with"));
            }
        });

        // set up image view
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference.child(model.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.announcement_image);
            }
        });
    }

    @NonNull
    @Override
    public AnnouncementAdapter.AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.latest_announcement_item, parent, false);
        return new AnnouncementAdapter.AnnouncementViewHolder(view);
    }


    public class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_description;
        private final MaterialCardView announcement_card;
        private final ImageView announcement_image;

        public AnnouncementViewHolder(View view) {
            super(view);
            tv_description = view.findViewById(R.id.announcement_text);
            announcement_card = view.findViewById(R.id.announcement_card);
            announcement_image = view.findViewById(R.id.announcement_image);
        }
    }
}
