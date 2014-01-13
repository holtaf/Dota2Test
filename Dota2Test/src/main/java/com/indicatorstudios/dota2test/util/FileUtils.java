package com.indicatorstudios.dota2test.util;

import android.content.res.AssetManager;
import android.content.res.Resources;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
    public static String getTextFromAssetFile(AssetManager assetManager, String fileName) {
        InputStream stream = null;

        try {
            stream = assetManager.open(fileName, AssetManager.ACCESS_STREAMING);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
