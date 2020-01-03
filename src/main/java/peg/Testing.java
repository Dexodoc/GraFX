package peg;

import peg.encryption.Coordinate;
import peg.encryption.Encrypt;
import peg.encryption.Key;
import peg.encryption.Transformations;
import peg.text.TextConversion;

public class Testing {
    public static void main(String[] args) {
        Coordinate coords = new Coordinate(3, 4);
        Coordinate newCoord = Transformations.runTranslation(6, 2, coords);
        Coordinate orig = Transformations.runOppositeTranslation(6, 2, newCoord);
        System.out.println(newCoord.toString());
        System.out.println(orig.toString());
    }


}
