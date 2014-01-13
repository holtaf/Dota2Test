package com.indicatorstudios.dota2test.data;

import android.content.res.Resources;

import com.indicatorstudios.dota2test.data.hero.Hero;
import com.indicatorstudios.dota2test.data.item.Item;
import com.indicatorstudios.dota2test.util.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Dota2Data {
    private static final String HERO_DATA_JSON_FILENAME = "herodata.json";
    private static final String ITEM_DATA_JSON_FILENAME = "itemdata.json";

    private final List<Hero> heroList = new ArrayList<>();
    private final List<Item> itemList = new ArrayList<>();

    public Dota2Data(Resources res) {
        loadHeroData(res);
        loadItemData(res);
    }

    private void loadHeroData(Resources res) {
        try {
            JSONObject heroData = new JSONObject(FileUtils.getTextFromAssetFile(res.getAssets(), HERO_DATA_JSON_FILENAME));
            List<Hero> heroList = new ArrayList<>();

            Iterator<String> iterator = heroData.keys();

            while (iterator.hasNext()) {
                String key = iterator.next();
                JSONObject heroJSON = heroData.getJSONObject(key);

                heroList.add(Hero.createFromJSON(key, heroJSON));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadItemData(Resources res) {
        Map<String, Item> loadedItemDatas = new HashMap<>();

        try {
            JSONObject itemData = new JSONObject(FileUtils.getTextFromAssetFile(res.getAssets(), ITEM_DATA_JSON_FILENAME));

            Iterator<String> iterator = itemData.keys();

            while (iterator.hasNext()) {
                String key = iterator.next();
                JSONObject itemJSON = itemData.getJSONObject(key);

                if (!itemJSON.getBoolean(JSONConstants.ITEM_CREATED)) {
                    loadedItemDatas.put(key, Item.createSimpleItemFromJSON(itemJSON, key));
                } else {
                    JSONArray components = itemJSON.getJSONArray(JSONConstants.ITEM_COMPONENTS);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Item loadItem(JSONObject globalItemJSON, Map<String, Item> loadedItemDatas, String key, JSONObject itemJSON) throws JSONException {
        if (!itemJSON.getBoolean(JSONConstants.ITEM_CREATED)) {
            return Item.createSimpleItemFromJSON(itemJSON, key);
        } else {
            JSONArray components = itemJSON.getJSONArray(JSONConstants.ITEM_COMPONENTS);

            List<Item> componentList = new ArrayList<>();

            for (int i = 0, len = components.length(); i < len; ++i) {
                String component = components.getString(i);
                if (loadedItemDatas.containsKey(component)) {
                    componentList.add(loadedItemDatas.get(component));
                } else {
                    loadedItemDatas.put(component, loadItem(globalItemJSON, loadedItemDatas, component, globalItemJSON.getJSONObject(component)));
                }
            }
        }
    }
}
