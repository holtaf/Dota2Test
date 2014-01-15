package com.indicatorstudios.dota2test.data.item;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_ATTRIBUTES;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_COOLDOWN;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_COST;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_CREATED;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_DESCRIPTION;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_DISPLAY_NAME;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_ID;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_IMAGE;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_LORE;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_MANA_COST;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_NOTES;
import static com.indicatorstudios.dota2test.data.JSONConstants.ITEM_QUALIFIER;

public class Item {
    public final String name;
    public final int id;
    public final String imageResource;
    public final String displayName;
    public final String qualifier;
    public final int cost;
    public final String description;
    public final String notes;
    public final String attributes;
    public final int manacost;
    public final int cooldown;
    public final String lore;
    public final List<Item> components;
    public final boolean hasComponents;

    private Item(String name, int id, String imageResource, String displayName, String qualifier, int cost, String description, String notes, String attributes, int manacost, int cooldown, String lore, List<Item> components, boolean hasComponents) {
        this.name = name;
        this.id = id;
        this.imageResource = imageResource;
        this.displayName = displayName;
        this.qualifier = qualifier;
        this.cost = cost;
        this.description = description;
        this.notes = notes;
        this.attributes = attributes;
        this.manacost = manacost;
        this.cooldown = cooldown;
        this.lore = lore;
        this.components = components;
        this.hasComponents = hasComponents;
    }

    public static Item createSimpleItemFromJSON(JSONObject itemJson, String name) throws JSONException {
        if (itemJson.getBoolean(ITEM_CREATED)) {
            throw new IllegalArgumentException("Provided item has components, should call createItemWithComponentsFromJSON method instead");
        }

        int cooldown = -1;
        int manacost = -1; // manacost and cooldown can be number or false so we'll get that with try - catch
        try {
            manacost = itemJson.getInt(ITEM_MANA_COST);
        } catch (JSONException e) {
        }

        try {
            cooldown = itemJson.getInt(ITEM_COOLDOWN);
        } catch (JSONException e) {
        }

        return new Item(name, itemJson.getInt(ITEM_ID), itemJson.getString(ITEM_IMAGE),
                itemJson.getString(ITEM_DISPLAY_NAME), itemJson.getString(ITEM_QUALIFIER), itemJson.getInt(ITEM_COST),
                itemJson.getString(ITEM_DESCRIPTION), itemJson.getString(ITEM_NOTES), itemJson.getString(ITEM_ATTRIBUTES),
                manacost, cooldown, itemJson.getString(ITEM_LORE), null, false);
    }

    public static Item createItemWithComponentsFromJSON(JSONObject itemJson, String name, List<Item> components) throws JSONException {

        int cooldown = -1;
        int manacost = -1; // manacost and cooldown can be number or false so we'll get that with try - catch
        try {
            manacost = itemJson.getInt(ITEM_MANA_COST);
        } catch (JSONException e) {
        }

        try {
            cooldown = itemJson.getInt(ITEM_COOLDOWN);
        } catch (JSONException e) {
        }

        return new Item(name, itemJson.getInt(ITEM_ID), itemJson.getString(ITEM_IMAGE),
                itemJson.getString(ITEM_DISPLAY_NAME), itemJson.getString(ITEM_QUALIFIER), itemJson.getInt(ITEM_COST),
                itemJson.getString(ITEM_DESCRIPTION), itemJson.getString(ITEM_NOTES), itemJson.getString(ITEM_ATTRIBUTES),
                manacost, cooldown, itemJson.getString(ITEM_LORE), components, true);
    }
}
