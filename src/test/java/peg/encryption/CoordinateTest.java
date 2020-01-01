package peg.encryption;

import org.junit.Test;
import org.junit.Assert;

import java.math.BigDecimal;

public class CoordinateTest {
    @Test
    public void printCoordTest(){
        Coordinate coord = new Coordinate(3, 4);
        Assert.assertEquals("(3.0,4.0)", coord.toString());
        Coordinate coord2 = new Coordinate("(5.0,7454.0)");
        Assert.assertEquals("(5.0,7454.0)", coord2.toString());
        Coordinate coord3 = new Coordinate(BigDecimal.valueOf(8.0), BigDecimal.valueOf(64.4));
        Assert.assertEquals("(8.0,64.4)", coord3.toString());
        Coordinate coord4 = new Coordinate(4, 433);
        Assert.assertEquals("(4.0,433.0)", coord4.toString());
        Coordinate coord5 = new Coordinate(7, 456);
        Assert.assertEquals("(7.0,456.0)", coord5.toString());
    }
}
