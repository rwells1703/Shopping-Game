package check.out.game.maingame.nonfermions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.IntIntMap;

import java.io.BufferedReader;

public class Cargo {
    private int maxMass;
    public int mass;
    public IntIntMap quantity = new IntIntMap();//Hmm, should this be more private?
    private IntIntMap massMap;

    public Cargo(int maxMass) {
        this.maxMass = maxMass;
        mass = 0;
        massMap = getMassesFromFile();
    }

    public void addOneOf(int type) throws IllegalArgumentException {
        if (mass + massMap.get(type, 0) > maxMass) {
            throw new IllegalArgumentException();
        }
        System.out.println(quantity);
        quantity.put(type, quantity.get(type, 0) + 1);
        mass += massMap.get(type, 0);
    }

    public void removeOneOf(int type) throws IllegalArgumentException {
        if (quantity.get(type, 0) <= 0) {
            throw new IllegalArgumentException();
        }
        System.out.println(quantity);
        quantity.put(type, quantity.get(type, 1) - 1);
        mass -= massMap.get(type, 0);
    }

    public int getMass() {
        return mass;
    }

    private static IntIntMap getMassesFromFile() {
        IntIntMap massMap = new IntIntMap();

        try {
            FileHandle file = Gdx.files.internal("data/item_data.csv");
            BufferedReader reader = file.reader(100);
            //this line is not needed
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                massMap.put(Integer.parseInt(data[0]), Integer.parseInt(data[2]));
            }
            reader.close();
        } catch (java.io.IOException e) {
        }

        return massMap;
    }
}
