package peg.encryption;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Encrypt {
    public static Coordinate[] encrypt(Coordinate[] coords, int[][] keys){
        int numberOfPoints = coords.length;
        for (int i = 0; i < keys.length; i++) {
            int[] key = keys[i];
            Coordinate initial = coords[i % numberOfPoints];
            Coordinate transformed = Transformations.runTranslation(key[0], key[1], initial);
            coords[i % numberOfPoints] = transformed;
        }
        return coords;
    }

    public static Coordinate[] decrypt(Coordinate[] coords, int[][] keys){
        int numberOfPoints = coords.length;
        for (int i = keys.length - 1; i > -1 ; i--) {
            int[] key = keys[i];
            Coordinate initial = coords[i % numberOfPoints];
            Coordinate transformed = Transformations.runOppositeTranslation(key[0], key[1], initial);
            coords[i % numberOfPoints] = transformed;
        }
        return coords;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
