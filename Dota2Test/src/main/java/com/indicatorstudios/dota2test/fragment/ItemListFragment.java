package com.indicatorstudios.dota2test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.indicatorstudios.dota2test.R;
import com.indicatorstudios.dota2test.data.hero.Hero;
import com.indicatorstudios.dota2test.util.FileUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemListFragment extends Fragment {
    private GridAdapter adapter;

    public HeroesListFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            JSONObject itemData = new JSONObject(FileUtils.getTextFromAssetFile(getActivity().getAssets(), "itemdata.json"));
            List<Hero> itemList = new ArrayList<>();

            Iterator<String> iterator = itemData.keys();

            while (iterator.hasNext()) {
                String key = iterator.next();
                JSONObject heroJSON = heroData.getJSONObject(key);

                heroList.add(Hero.createFromJSON(key, heroJSON));
            }

            adapter.setHeroes(heroList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GridView root = (GridView) inflater.inflate(R.layout.item_grid_fragment, container, false);

        adapter = new GridAdapter();

        root.setAdapter(adapter);

        return root;
    }

    private class GridAdapter extends BaseAdapter {
        private List<Hero> heroData = new ArrayList<>();

        public void setHeroes(List<Hero> heroList) {
            heroData.clear();
            heroData.addAll(heroList);

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return heroData.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = getLayoutInflater(null).inflate(R.layout.grid_item, viewGroup, false);

            final String imageUri = "assets://item icons/" + heroData.get(i).name + "_hphover.png";
            ImageLoader.getInstance().displayImage(imageUri, (ImageView) view.findViewById(R.id.icon));

            ((TextView) view.findViewById(R.id.label)).setText(heroData.get(i).displayName);

            return view;
        }
    }

}
