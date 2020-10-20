package com.example.myapp2.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapp2.R;
import com.example.myapp2.model.Podcast;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaylistRecyclerAdapter extends FirestoreRecyclerAdapter<Podcast, PlaylistRecyclerAdapter.PlaylistViewHolder> {

    private static final String TAG = "PlaylistRecyclerAdapter";

    public PlaylistRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Podcast> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position, @NonNull Podcast model) {
        holder.med_title.setText(model.getTitle());

        Log.d(TAG, "Category fragment on create " + model.getTitle());
        holder.med_locationId.setText(model.getLocation_id());
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_playlist_list_item, parent, false);
        return new PlaylistViewHolder(v);
    }

    class PlaylistViewHolder extends RecyclerView.ViewHolder{
        TextView med_title;
        TextView med_locationId;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            med_title = itemView.findViewById(R.id.media_title);
            med_locationId = (TextView) itemView.findViewById(R.id.media_location);
        }
    }

}
