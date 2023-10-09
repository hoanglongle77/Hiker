package com.example.hikermanagementapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hikermanagementapp.Fragments.AddFragment;
import com.example.hikermanagementapp.Fragments.HomeFragment;
import com.example.hikermanagementapp.Fragments.SearchFragment;

public class VPAdapter extends FragmentStateAdapter {
    public VPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 1:
                return new HomeFragment();
            case 2:
                return new SearchFragment();
            case 0:
            default:
                return new AddFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
