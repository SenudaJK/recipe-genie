package com.example.getting_started;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

// IM/2021/009 
// This VPAdapter is used so that then user can easily swipe between different fragments
public class VPAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragmentList;
    private final List<String> fragmentTitleList = new ArrayList<>();

    public VPAdapter(FragmentActivity fa) {
        super(fa);
        fragmentList = new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}




