package com.example.hikermanagementapp.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hikermanagementapp.Models.Hike;
import com.example.hikermanagementapp.R;

import java.util.List;

public class HikeCardAdapter extends RecyclerView.Adapter<HikeCardAdapter.HikeCardViewHolder> {
    private final List<Hike> hikes;

    public HikeCardAdapter(List<Hike> hikes) {
        this.hikes = hikes;
    }

    @NonNull
    @Override
    public HikeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_layout,parent,false);
        return new HikeCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeCardViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.tripName.setText(hike.name);
        holder.tripLocation.setText(hike.location);
    }

    @Override
    public int getItemCount() {
        return hikes.size();
    }

    public static class HikeCardViewHolder extends RecyclerView.ViewHolder {
        TextView tripName, tripLocation;

        public HikeCardViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.name);
            tripLocation = itemView.findViewById(R.id.location);
        }
    }
}
