package peg.encryption;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Coordinate {
    private BigDecimal x;
    private BigDecimal y;

    public Coordinate(double x, double y) {
        this.x = BigDecimal.valueOf(x);
        this.y = BigDecimal.valueOf(y);
    }

    public Coordinate(BigDecimal x, BigDecimal y){
        this.x = x;
        this.y = y;
    }

    public Coordinate(String coordinates){
        String p = coordinates.substring(1, coordinates.length() - 1);
        int x = p.indexOf(',');
        String num1 = p.substring(0, x);
        String num2 = p.substring(x+1, p.length());
        this.x = new BigDecimal(num1);
        this.y = new BigDecimal(num2);
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    @Override
    public String toString(){
        String xValue = x.toPlainString();
        String yValue = y.toPlainString();
        return "(" + xValue + "," + yValue + ")";
    }

    public String toRoundedString(){
        String xValue = x.setScale(0, RoundingMode.HALF_UP).toPlainString();
        String yValue = y.setScale(0, RoundingMode.HALF_UP).toPlainString();
        return "(" + xValue + "," + yValue + ")";
    }
}
