package Models;

import java.util.Random;

public class NameData {
    private String[] data;

    public String[] getData() {
        return data;
    }

    public int getLength() {
        return data.length;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getRandomName() {
        Random rand = new Random(System.currentTimeMillis());
        int index = rand.nextInt(data.length);
        return data[index];
    }
}
