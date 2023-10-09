package com.example.hikermanagementapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.hikermanagementapp.Adapters.VPAdapter;
import com.example.hikermanagementapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView myBottomNavigationView;
    private ViewPager2 myViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myViewPager2 = findViewById(R.id.viewPager2);
        myBottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        VPAdapter viewPagerAdapter = new VPAdapter(this);
        myViewPager2.setAdapter(viewPagerAdapter);

        myViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        myBottomNavigationView.getMenu().findItem(R.id.menu_add).setChecked(true);
                        break;
                    case 1:
                        myBottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;
                    case 3:
                        myBottomNavigationView.getMenu().findItem(R.id.menu_search).setChecked(true);
                        break;
                }
            }
        });

        myBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.menu_add){
                    myViewPager2.setCurrentItem(0);
                } else if (id == R.id.menu_home) {
                    myViewPager2.setCurrentItem(1);
                }else if (id == R.id.menu_search){
                    myViewPager2.setCurrentItem(2);
                }
                return true;
            }
        });
    }
}