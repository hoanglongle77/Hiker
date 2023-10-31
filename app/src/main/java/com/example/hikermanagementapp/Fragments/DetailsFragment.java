package com.example.hikermanagementapp.Fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hikermanagementapp.Adapters.HikeCardAdapter;
import com.example.hikermanagementapp.Database.HikerAppDatabase;
import com.example.hikermanagementapp.Models.Hike;
import com.example.hikermanagementapp.R;
import java.util.List;

public class DetailsFragment extends Fragment {
    List<Hike> hikes;
    HikerAppDatabase myAppDatabase;
    RecyclerView recyclerView;
    HikeCardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        // Inflate the layout for this fragment
        myAppDatabase = Room.databaseBuilder(requireContext(), HikerAppDatabase.class, "hike_app_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        hikes = myAppDatabase.hikeDao().getAllHikes();

        adapter = new HikeCardAdapter(hikes);
        recyclerView.setAdapter(adapter);
        return view;
    }
}