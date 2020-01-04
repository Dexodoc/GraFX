package peg.encryption;

import org.junit.Assert;
import org.junit.Test;

public class TransformationsTest {

    @Test
    public void transformationsTest(){

        for (int i = 0; i < 16; i++) {
            for (int j = 1; j < 16; j++) {
                Coordinate orig = new Coordinate(5, 4);
                String origCoords = orig.toRoundedString();

                Coordinate trans = Transformations.runTranslation(i, j, orig);
                String newCoords = trans.toRoundedString();

                Coordinate back = Transformations.runOppositeTranslation(i, j, trans);
                String origCoords2 = back.toRoundedString();

                Assert.assertTrue(origCoords.equals(origCoords2));
                Assert.assertFalse(origCoords.equals(newCoords));

                System.out.println(origCoords + newCoords);
            }
        }


    }

    @Test
    public void test(){
        Coordinate orig = new Coordinate(5,4);
        String origS = orig.toRoundedString();
        Coordinate orig2 = new Coordinate(5, 4);
        String orig2s = orig2.toRoundedString();
        Assert.assertTrue(origS.equals(orig2s));

        System.out.println(origS + orig2s);
    }
}
