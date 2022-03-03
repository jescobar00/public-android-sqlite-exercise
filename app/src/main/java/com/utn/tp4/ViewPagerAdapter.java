package com.utn.tp4;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final int cantTabs = 3;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FragmentAlta();
                break;
            case 1:
                fragment = new FragmentModif();
                break;
            case 2:
                fragment = new FragmentListado();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return cantTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return FragmentAlta.TITULO;
                //break;
            case 1:
                return FragmentModif.TITULO;
                //break;
            case 2:
                return FragmentListado.TITULO;
                //break;
        }

        return super.getPageTitle(position);
    }
}
