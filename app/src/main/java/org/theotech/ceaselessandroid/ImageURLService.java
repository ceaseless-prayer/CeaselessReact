package org.theotech.ceaselessandroid;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ben Johnson on 10/3/15.
 */
public class ImageURLService implements ImageURLServiceInterface {

    private static final String TAG = "ScriptureService";

    private static final String HTTP_API_IMAGE = "http://api.ceaselessprayer.com/v1/getAScriptureImage";

    @Override
    public String getImageURL() {

        InputStream in = null;

        // HTTP Get
        try {
            URL url = new URL(HTTP_API_IMAGE);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e) {
            Log.e(TAG, "ERROR", e);
            Log.e(TAG, e.getStackTrace().toString());
            return null;
        }

        String json = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {sb.append(line);}
            json = sb.toString();
        } catch (IOException e) {
            Log.e(TAG, "ERROR", e);
            Log.e(TAG, e.getStackTrace().toString());
            return null;
        }
        String imageUrl = null;

        // parse JSON response
        try {
            JSONObject obj = new JSONObject(json);
            imageUrl = obj.getString("imageUrl");
        } catch (JSONException e) {
            Log.e(TAG, "ERROR", e);
            Log.e(TAG, e.getStackTrace().toString());
            return null;
        }

        return imageUrl;
    }

}
