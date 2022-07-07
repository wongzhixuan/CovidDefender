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

public class ThingsToDoAdapter extends FirestoreRecyclerAdapter<Announcement, ThingsToDoAdapter.ThingsToDoViewHolder> {

    FirebaseStorage storage;
    StorageReference storageReference;
    public ThingsToDoAdapter(@NonNull FirestoreRecyclerOptions<Announcement> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ThingsToDoAdapter.ThingsToDoViewHolder holder, int position, @NonNull Announcement model) {
        holder.tv_things_to_do_1.setText(model.getDescription());

        // set up image view
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference.child(model.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.iv_things_to_do_1);
            }
        });

        holder.card_things_to_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open browser with link
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl()));
                view.getContext().startActivity(Intent.createChooser(intent, "Browse with"));
            }
        });
    }

    @NonNull
    @Override
    public ThingsToDoAdapter.ThingsToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.things_to_do_item, parent, false);
        return new ThingsToDoAdapter.ThingsToDoViewHolder(view);
    }

    public class ThingsToDoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_things_to_do_1;
        private final TextView tv_things_to_do_1;
        private final MaterialCardView card_things_to_do;
        public ThingsToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_things_to_do_1 = itemView.findViewById(R.id.card_things_to_do_1);
            tv_things_to_do_1 = itemView.findViewById(R.id.tv_things_to_do_1);
            card_things_to_do = itemView.findViewById(R.id.card_things);
        }
    }
}
