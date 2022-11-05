package com.example.whatsappclone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        androidx.appcompat.widget.Toolbar mToolbar;

    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_layout);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        mToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_veritical_menu);
        mToolbar.setOverflowIcon(drawable);
        ViewPagerMessengerAdapter viewPagerMessengerAdapter=new ViewPagerMessengerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerMessengerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.camera_icon);
        tabLayout.getTabAt(1).getOrCreateBadge().setBackgroundColor(R.color.white);
        tabLayout.getTabAt(1).getOrCreateBadge().setColorFilter(R.color.dark_green, PorterDuff.Mode.ADD);
//        tabLayout.getTabAt(1).getBadge().setBackgroundColor(R.color.white);
//        tabLayout.getTabAt(1).getBadge().setTint(R.color.white);

        tabLayout.getTabAt(1).getBadge().setNumber(8);

        viewPager.setCurrentItem(1,false);
        //To reduce the size of camera tab
        LinearLayout layout = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.weight = 0.5f;
        layout.setLayoutParams(layoutParams);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                Intent intent=new Intent(Tabbed_layout.this,UserProfile.class);
                startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}