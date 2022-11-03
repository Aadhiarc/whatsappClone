package com.example.whatsappclone1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.whatsappclone1.Adapters.ViewPagerMessengerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Tabbed_layout extends AppCompatActivity {
        TabLayout tabLayout;
        ViewPager viewPager;
        SearchView searchView;
        ImageButton menu;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_layout);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        searchView=findViewById(R.id.search_view);
        menu=findViewById(R.id.vertical_menu);
        ViewPagerMessengerAdapter viewPagerMessengerAdapter=new ViewPagerMessengerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerMessengerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.camera_icon);
        //To reduce the size of camera tab
        LinearLayout layout = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.weight = 0.5f;
        layout.setLayoutParams(layoutParams);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Tabbed_layout.this, "menu", Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Tabbed_layout.this, "search", Toast.LENGTH_SHORT).show();
            }
        });
    }




}