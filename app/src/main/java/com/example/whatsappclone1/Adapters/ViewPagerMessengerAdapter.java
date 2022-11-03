package com.example.whatsappclone1.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsappclone1.Fragments.CallsFragment;
import com.example.whatsappclone1.Fragments.CameraFragment;
import com.example.whatsappclone1.Fragments.ChatsFragment;
import com.example.whatsappclone1.StatusFragment;

public class ViewPagerMessengerAdapter extends FragmentPagerAdapter {
    private String[] tabTitles = new String[]{"", "CHATS", "STATUS","CALLS"};
    public ViewPagerMessengerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new CameraFragment();
        }else if(position==1){
            return new ChatsFragment();
        }else if(position==2){
            return new StatusFragment();
        }else {
            return new CallsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }


}
