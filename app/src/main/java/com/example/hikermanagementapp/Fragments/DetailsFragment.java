package com.example.hikermanagementapp.Fragments;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hikermanagementapp.Activities.EditActivity;
import com.example.hikermanagementapp.Adapters.HikeCardAdapter;
import com.example.hikermanagementapp.Database.HikerAppDatabase;
import com.example.hikermanagementapp.Models.Hike;
import com.example.hikermanagementapp.R;

import java.util.List;

public class DetailsFragment extends Fragment implements HikeCardAdapter.ILongClickItem, HikeCardAdapter.IClickItem {

//    private static final int EDIT_REQUEST = 8;
    final Handler handler = new Handler();
    final int AUTO_REFRESH_DELAY = 1000;
    List<Hike> hikes;
    RecyclerView recyclerView;
    HikeCardAdapter adapter;
    final Runnable autoRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            reloadDatabase();
            handler.postDelayed(this, AUTO_REFRESH_DELAY);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);


        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        hikes = HikerAppDatabase.getInstance(requireContext()).hikeDao().getAllHikes();

        adapter = new HikeCardAdapter(hikes, this, this);
        recyclerView.setAdapter(adapter);
        handler.postDelayed(autoRefreshRunnable, AUTO_REFRESH_DELAY);

        return view;
    }


    @SuppressLint("NotifyDataSetChanged")
    private void reloadDatabase() {
        List<Hike> updatedPersons = HikerAppDatabase.getInstance(requireContext()).hikeDao().getAllHikes();
        adapter.setData(updatedPersons);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoRefreshRunnable);
    }

    @Override
    public void deleteHike(Hike hike) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Hike")
                .setMessage("Are you sure you want to delete this hike?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    HikerAppDatabase.getInstance(requireContext()).hikeDao().deleteHike(hike);
                    hikes.remove(hike);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

//    @SuppressWarnings("deprecation")
    @Override
    public void updateHike(Hike hike) {
        Intent intent = new Intent(requireActivity(), EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_hike", hike);
        intent.putExtras(bundle);
        editActivityLauncher.launch(intent);
    }

    // Declare the launcher at the class level
    private final ActivityResultLauncher<Intent> editActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    reloadDatabase();
                }
            }
    );
}