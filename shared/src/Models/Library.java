package Models;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Library {
    private static Gson gson = new Gson();

    public static LocationData getLocations() {
        LocationData locationData = new LocationData();
        try {
            Reader locationReader = new FileReader("json/locations.json");
            locationData = gson.fromJson(locationReader, LocationData.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return locationData;
    }

    public static NameData getFNameData() {
        NameData fNameData = new NameData();
        try {
            Reader fNameReader = new FileReader("json/fnames.json");
            fNameData = gson.fromJson(fNameReader, NameData.class);

            return fNameData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fNameData;
    }

    public static NameData getMNameData() {
        try {
            NameData mNameData;
            Reader mNameReader = new FileReader("json/mnames.json");
            mNameData = gson.fromJson(mNameReader, NameData.class);

            return mNameData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NameData getSNameData() {
        try {
            NameData sNameData;
            Reader sNameReader = new FileReader("json/snames.json");
            sNameData = gson.fromJson(sNameReader, NameData.class);

            return sNameData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
