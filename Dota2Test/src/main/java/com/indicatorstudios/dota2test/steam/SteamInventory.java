package com.indicatorstudios.dota2test.steam;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import uk.co.thomasc.steamkit.types.steamid.SteamID;

public class SteamInventory {
    public int status;
    public int num_backpack_slots;
    public List<SteamInventoryItem> items;

    public static class SteamInventoryAttribute {
        public int defindex;
        public long long_value;
        public float float_value;
        public String string_value;
        public SteamInventoryAccountInfo account_info = null;
    }

    public static class SteamInventoryAccountInfo {
        public long steamid;
        public String personaname;
    }

    public static class SteamInventoryItem {
        public long id;
        public long original_id;
        public int defindex;
        public int level;
        public int quantity;
        public boolean flag_cannot_trade = false;
        public boolean flag_cannot_craft = false;
        public Schema.SchemaQuality quality;
        public String custom_name = null;
        public String custom_desc = null;
        public SteamInventoryItem contained_item = null;
        public SteamInventoryAttribute[] attributes = null;

        // *in* is most certainly closed
        @SuppressWarnings("resource")
        public static SteamInventory fetchInventory(SteamID id, String apikey, boolean cache, Context context) {
            /*try {
                //InputStream in = new BufferedInputStream(conn.getInputStream());
                File cachedInv = null;
                InputStream in = null;
                if (cache) {
                    File invCacheDir = new File(context.getCacheDir(), "inv/");
                    invCacheDir.mkdirs();
                    cachedInv = new File(context.getCacheDir(), "inv/" + id.convertToLong() + ".cache");
                    long cacheTime = 3 * 60 * 1000;// 3 minutes;
                    if (cachedInv.exists() && System.currentTimeMillis() - cachedInv.lastModified() < cacheTime)
                        in = new BufferedInputStream(new FileInputStream(cachedInv));
                }
                Log.d("cache", "Inventory cache is: " + (in == null ? " null" : " not null"));
                if (in == null) {
                    URL url = new URL("http://api.steampowered.com/IEconItems_440/GetPlayerItems/v0001/?key=" + apikey + "&SteamID=" + id.convertToLong() + "&format=json");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    in = conn.getInputStream();
                    // cache didn't work, read http
                    if (cache) {
                        // read http, and write to the cache...then return an input stream to the cache
                        // I don't know why
                        cachedInv.delete();
                        cachedInv.createNewFile();
                        OutputStream out = new BufferedOutputStream(new FileOutputStream(cachedInv));
                        int bufferSize = 2048;
                        byte[] buffer = new byte[bufferSize];
                        int len = 0;
                        while ((len = in.read(buffer)) != -1)
                            out.write(buffer, 0, len);
                        if (out != null)
                            out.close();
                        in.close();
                        in = new BufferedInputStream(new FileInputStream(cachedInv));
                    }
                }

                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(SchemaQuality.class, new JsonDeserializer<SchemaQuality>() {
                    @Override
                    public SchemaQuality deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
                        return SchemaQuality.values[element.getAsInt()];
                    }
                });
                builder.registerTypeAdapter(SteamInventoryAttribute.class, new JsonDeserializer<SteamInventoryAttribute>() {
                    @Override
                    public SteamInventoryAttribute deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
                        SteamInventoryAttribute attribute = new SteamInventoryAttribute();
                        JsonObject obj = element.getAsJsonObject();
                        attribute.defindex = obj.get("defindex").getAsInt();
                        if (obj.has("float_value"))
                            attribute.float_value = obj.get("float_value").getAsFloat();
                        if (obj.has("value")) {
                            if (obj.get("value").getAsJsonPrimitive().isNumber())
                                attribute.long_value = obj.get("value").getAsLong();
                            else
                                attribute.string_value = obj.get("value").getAsString();
                        }
                        attribute.account_info = context.deserialize(obj.get("account_info"), SteamInventoryAccountInfo.class);
                        return attribute;
                    }
                });
                Gson gson = builder.create();

                JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
                reader.beginObject(); // root
                String result = reader.nextName();
                if (!result.equals("result")) {
                    reader.close();
                    return null;
                }
                SteamInventory inventory = gson.fromJson(reader, SteamInventory.class);
                reader.endObject(); // end root
                reader.close();
                in.close();

                return inventory;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }*/

            return null;
        }

        public Schema.SchemaItem def() {
            return SteamService.singleton.schema.items.get(defindex);
        }

        public String fullname() {
            SteamInventoryAttribute seriesAttribute = getAttribute(187);
            String series_str = seriesAttribute == null ? "" : " Series #" + ((int) seriesAttribute.float_value);//187
            String proper_str = (def().proper_name ? "The " : "");
            String quality_str = (quality.prefix ? quality.name + " " : "");
            String name_str = (custom_name != null ? "\"" + custom_name + "\"" : def().item_name);
            return proper_str + quality_str + name_str + series_str;
        }

        public SteamInventoryAttribute getAttribute(int defindex) {
            if (attributes == null)
                return null;
            for (SteamInventoryAttribute attribute : attributes)
                if (attribute.defindex == defindex)
                    return attribute;
            return null;
        }

        public SteamInventoryItem getItem(long id) {
            /* if (items == null)
                return null;
            for (final SteamInventoryItem item : items)
                if (item != null && item.id == id)
                    return item;
            return null;

            */

            return null;
        }

        public void filterTradable() {
            /*for (int i = 0; i < items.size(); i++) {
                if (items.get(i).flag_cannot_trade) {
                    items.remove(i);
                    i--;
                }
            }
            */
        }
    }

}