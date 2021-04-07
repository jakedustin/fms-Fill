package Models;

import java.util.Random;

public class LocationData {
    private Location[] data;

    public Location[] getData() {
        return data;
    }

    public Location getRandomLocation() {
        Random rand = new Random(System.currentTimeMillis());
        int index = rand.nextInt(data.length);
        return data[index];
    }

    public void setData(Location[] data) {
        this.data = data;
    }
}
