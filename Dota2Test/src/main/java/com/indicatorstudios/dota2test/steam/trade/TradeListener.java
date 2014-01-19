package com.indicatorstudios.dota2test.steam.trade;


import com.indicatorstudios.dota2test.steam.Schema;
import com.indicatorstudios.dota2test.steam.SteamInventory;

public abstract class TradeListener {
    public Trade trade;
    protected int slot;

    public abstract void onError(Error error);

    public abstract void onAfterInit();

    public abstract void onUserAddItem(Schema.SchemaItem schemaItem, SteamInventory.SteamInventoryItem inventoryItem);

    public abstract void onUserRemoveItem(Schema.SchemaItem schemaItem, SteamInventory.SteamInventoryItem inventoryItem);

    public abstract void onMessage(String msg);

    public abstract void onUserSetReadyState(boolean ready);

    public abstract void onUserAccept();

    public abstract void onNewVersion();

    public abstract void onComplete();

    public abstract void onOfferUpdated();
}
