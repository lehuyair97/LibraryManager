package com.example.duanmau.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.duanmau.fragment.BookFragment;
import com.example.duanmau.fragment.CallCardFragment;
import com.example.duanmau.fragment.CategoryFragment;
import com.example.duanmau.fragment.MemberFragment;
import com.example.duanmau.fragment.Revenue_Fragment;
import com.example.duanmau.fragment.Top10_BooksBestSellers_Fragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CallCardFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new BookFragment();
            case 3:
                return new MemberFragment();
            case 4:
                return new Top10_BooksBestSellers_Fragment();
            case 5:
                return new Revenue_Fragment();

            default:
                return new CallCardFragment();
        }

    }

    @Override
    public int getCount() {
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position){
            case  0:
                title = "Call Card";
                break;
            case  1:
                title = "Category";
                break;

            case  2:
                title = "Book";
                break;

            case  3:
                title = "Member";
                break;

        }
        return title;
    }


}
