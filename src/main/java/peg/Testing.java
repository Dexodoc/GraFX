package peg;

import peg.encryption.Coordinate;
import peg.encryption.Encrypt;
import peg.encryption.Key;
import peg.text.TextConversion;

public class Testing {
    public static void main(String[] args) {
        String test = "17547162576153761253761523786125378615273651278635.3876283462873469238";
        double ba = Double.parseDouble(test);
        System.out.println(ba);
        Coordinate coords = new Coordinate(3, 4);
        System.out.println(coords.toString());
    }


}
