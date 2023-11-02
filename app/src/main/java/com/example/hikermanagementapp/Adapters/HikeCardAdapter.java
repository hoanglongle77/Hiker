package com.example.hikermanagementapp.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hikermanagementapp.Models.Hike;
import com.example.hikermanagementapp.R;

import java.util.List;

public class HikeCardAdapter extends RecyclerView.Adapter<HikeCardAdapter.HikeCardViewHolder> {
    private List<Hike> hikes;
    private final ILongClickItem iLongClickItem;
    private final IClickItem iClickItem;

    public interface ILongClickItem{
        void deleteHike(Hike hike);
    }

    public interface IClickItem{
        void updateHike(Hike hike);
    }
    public HikeCardAdapter(List<Hike> hikes, ILongClickItem iLongClickItem, IClickItem iClickItem) {
        this.hikes = hikes;
        this.iLongClickItem = iLongClickItem;
        this.iClickItem = iClickItem;
    }

    @NonNull
    @Override
    public HikeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_layout, parent, false);
        return new HikeCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeCardViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.tripName.setText(hike.getName());
        holder.tripLocation.setText(String.format("Location: %s", hike.getLocation()));
        holder.parking.setText(String.format("Parking: %s", hike.getParking()));
        holder.length.setText(String.format("Length: %s", hike.getLength()));
        holder.level.setText(String.format("Level: %s", hike.getLevel()));

        holder.itemView.setOnLongClickListener(v -> {
            if (iLongClickItem != null) {
                iLongClickItem.deleteHike(hike);
            }
            return true;
        });

       holder.btnEdit.setOnClickListener(v -> iClickItem.updateHike(hike));
    }

    @Override
    public int getItemCount() {
        return hikes.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Hike> listHike) {
        this.hikes = listHike;
        notifyDataSetChanged();
    }


    public static class HikeCardViewHolder extends RecyclerView.ViewHolder {
        TextView tripName, tripLocation, parking, length, level;
        Button btnEdit;
        public HikeCardViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.name);
            tripLocation = itemView.findViewById(R.id.location);
            parking = itemView.findViewById(R.id.parking);
            length = itemView.findViewById(R.id.length);
            level = itemView.findViewById(R.id.level);
            btnEdit = itemView.findViewById(R.id.button_edit);
        }
    }
}
