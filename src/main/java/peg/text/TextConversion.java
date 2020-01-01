package peg.text;


import peg.encryption.Coordinate;
import peg.encryption.Key;

import java.math.RoundingMode;
import java.util.Arrays;

public class TextConversion {

    public static Coordinate[] textIntoCoords(String text){
        String[] hexValues = new String[text.length()];
        for (int i = 0; i < text.length(); i++) {
            hexValues[i] = (Integer.toString((int) text.charAt(i), 16));
        }
        Coordinate[] coords = new Coordinate[hexValues.length];
        for (int i = 0; i < hexValues.length; i++) {
            double x = Integer.valueOf(String.valueOf(hexValues[i].charAt(0)), 16).doubleValue();
            double y = Integer.valueOf(String.valueOf(hexValues[i].charAt(1)), 16).doubleValue();
            coords[i] = new Coordinate(x, y);
        }
        return coords;
    }

    public static String coordsIntoText(Coordinate[] coords){
        char[] chars = new char[coords.length];
        for (int i = 0; i < coords.length; i++) {
            String val = "";
            String val1 = Integer.toString(coords[i].getX().setScale(0, RoundingMode.HALF_UP).intValue(), 16);
            String val2 = Integer.toString(coords[i].getY().setScale(0, RoundingMode.HALF_UP).intValue(), 16);
            val = val + val1 + val2;
            chars[i] = (char) Integer.valueOf(val, 16).intValue();
        }
        return new String(chars);
    }

}
