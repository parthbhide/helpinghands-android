package com.example.helpinghands.Fragments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.helpinghands.R;

public class MyAdapter extends FragmentStateAdapter {

    int[] titles = new int[]{R.drawable.ic_one,R.drawable.ic_two,R.drawable.ic_three};

    public MyAdapter(@NonNull RegisterFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @Nullable
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                First ff = new First();
                return ff;
            case 1:
                Second sf = new Second();
                return sf;
            case 2:
                Third tf = new Third();
                return tf;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

}
