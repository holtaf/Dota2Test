package com.indicatorstudios.dota2test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.indicatorstudios.dota2test.data.Dota2Data;
import com.indicatorstudios.dota2test.data.hero.Hero;
import com.indicatorstudios.dota2test.data.item.Item;
import com.indicatorstudios.dota2test.fragment.HeroesListFragment;
import com.indicatorstudios.dota2test.fragment.ItemListFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ViewPager viewPager;

    private Dota2Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new Dota2Data(getResources());

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewpager);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        adapter.addTab(new HeroesListFragment(), "Heroes", 0, true);
        adapter.addTab(new ItemListFragment(), "Items", 1, false);

        setContentView(viewPager);
    }

    public List<Hero> getHeroList() {
        return data.getHeroList();
    }

    public List<Item> getItemList() {
        return data.getItemList();
    }

    private class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener {

        private List<Fragment> fragments = new ArrayList<Fragment>();

        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addTab(Fragment fragment, String title, int position, boolean setSelected) {
            fragments.add(fragment);

            ActionBar actionBar = getSupportActionBar();

            actionBar.addTab(actionBar.newTab().setText(title).setTabListener(this), position, setSelected);
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }
    }
}
