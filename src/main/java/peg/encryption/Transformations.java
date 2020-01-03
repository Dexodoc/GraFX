package peg.encryption;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Transformations {

    private static Transformation[] transformationsList = {new TranslateLeft(), new TranslateRight(), new TranslateDown(), new TranslateUp(), new ScaleHorizontal(), new ScaleVertical(), new ScaleObliquePositive(), new ScaleObliqueNegative(), new ReflectX(), new ReflectY(), new ReflectObliquePositive(), new ReflectObliqueNegative(), new RotateClockwise(), new RotateAnticlockwise(), new ShiftObliquePositive(), new ShiftObliqueNegative()};

    public static Coordinate runTranslation(int transformation, int amount, Coordinate initial){
        return transformationsList[transformation].apply(initial, amount, false);
    }

    public static Coordinate runOppositeTranslation(int transformation, int amount, Coordinate initial){
        return transformationsList[transformation].apply(initial, amount, true);
    }

}

interface Transformation{
    public Coordinate apply(Coordinate initial, int amount, boolean opposite);
}

class TranslateLeft implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        BigDecimal amt = BigDecimal.valueOf(amount);
        if (opposite) return new TranslateRight().apply(initial, amount, false);
        return new Coordinate(initial.getX().subtract(amt), initial.getY());
    }
}

class TranslateRight implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        BigDecimal amt = BigDecimal.valueOf(amount);
        if (opposite) return new TranslateLeft().apply(initial, amount, false);
        return new Coordinate(initial.getX().add(amt), initial.getY());
    }
}

class TranslateUp implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        BigDecimal amt = BigDecimal.valueOf(amount);
        if (opposite) return new TranslateDown().apply(initial, amount, false);
        return new Coordinate(initial.getX(), initial.getY().add(amt));
    }
}

class TranslateDown implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        BigDecimal amt = BigDecimal.valueOf(amount);
        if (opposite) return new TranslateUp().apply(initial, amount, false);
        return new Coordinate(initial.getX(), initial.getY().subtract(amt));
    }
}

class ScaleHorizontal implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        BigDecimal amt1 = BigDecimal.valueOf(amount + 1);
        if (opposite){
            return new Coordinate(initial.getX().multiply(BigDecimal.ONE.divide(amt1, 12, RoundingMode.HALF_UP)), initial.getY());
        } else {
            return new Coordinate(initial.getX().multiply(amt1), initial.getY());
        }
    }
}

class ScaleVertical implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        BigDecimal amt1 = BigDecimal.valueOf(amount + 1);
        if (opposite) {
            return new Coordinate(initial.getX(), initial.getY().multiply(BigDecimal.ONE.divide(amt1, 12, RoundingMode.HALF_UP)));
        } else {
            return new Coordinate(initial.getX(), initial.getY().multiply(amt1));
        }
    }
}

class ScaleObliquePositive implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        double scaleFactor = amount;
        if (opposite) {
            scaleFactor = (-1 * (amount / (amount + 1.0)));
        }
        BigDecimal x = initial.getX();
        BigDecimal y = initial.getY();
        double distanceToLine = ((x.subtract(y)).abs()).divide(BigDecimal.valueOf(2), 3, RoundingMode.HALF_UP).doubleValue();
        if (x.compareTo(y) == 1){
            return new Coordinate(x.add(BigDecimal.valueOf(scaleFactor * distanceToLine)), y.subtract(BigDecimal.valueOf(scaleFactor * distanceToLine)));
        } else {
            return new Coordinate(x.subtract(BigDecimal.valueOf(scaleFactor * distanceToLine)), y.add(BigDecimal.valueOf(scaleFactor * distanceToLine)));
        }
    }
}

class ScaleObliqueNegative implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        double scaleFactor = amount;
        if (opposite) {
            scaleFactor = (-1 * (amount / (amount + 1.0)));
        }
        BigDecimal x = initial.getX();
        BigDecimal y = initial.getY();
        double distanceToLine = ((x.add(y)).abs()).divide(BigDecimal.valueOf(2), 3, RoundingMode.HALF_UP).doubleValue();
        if (x.compareTo(y.multiply(BigDecimal.valueOf(-1))) == 1){
            return new Coordinate(x.add(BigDecimal.valueOf(scaleFactor * distanceToLine)), y.add(BigDecimal.valueOf(scaleFactor * distanceToLine)));
        } else {
            return new Coordinate(x.subtract(BigDecimal.valueOf(scaleFactor * distanceToLine)), y.subtract(BigDecimal.valueOf(scaleFactor * distanceToLine)));
        }
    }
}

class ReflectX implements Transformation {
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        return new Coordinate(initial.getX(), initial.getY().multiply(BigDecimal.valueOf(-1)));
    }
}

class ReflectY implements Transformation {
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite){
        return new Coordinate(initial.getX().multiply(BigDecimal.valueOf(-1)), initial.getY());
    }
}

class ReflectObliquePositive implements Transformation {
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite){
        return new Coordinate(initial.getY(), initial.getX());
    }
}

class ReflectObliqueNegative implements Transformation {
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite){
        return new Coordinate(initial.getY().multiply(BigDecimal.valueOf(-1)), initial.getX().multiply(BigDecimal.valueOf(-1)));
    }
}

class ShiftObliquePositive implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        if (opposite) amount = -amount;
        return new Coordinate(initial.getX().add(BigDecimal.valueOf(amount)), initial.getY().add(BigDecimal.valueOf(amount)));
    }
}

class ShiftObliqueNegative implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        if (opposite) amount = -amount;
        return new Coordinate(initial.getX().subtract(BigDecimal.valueOf(amount)), initial.getY().subtract(BigDecimal.valueOf(amount)));
    }
}

class Rotate{
    public static Coordinate rotate(Coordinate initial, int amount){
        double angle = (Math.PI / 15) * amount;
        BigDecimal newX = (initial.getX().multiply(BigDecimal.valueOf(Math.cos(angle)))).subtract(initial.getY().multiply(BigDecimal.valueOf(Math.sin(angle))));
        BigDecimal newY = (initial.getY().multiply(BigDecimal.valueOf(Math.cos(angle)))).add(initial.getX().multiply(BigDecimal.valueOf(Math.sin(angle))));
        return new Coordinate(newX, newY);
    }
}

class RotateClockwise implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        if (opposite) return new RotateAnticlockwise().apply(initial, amount, false);
        return Rotate.rotate(initial, -amount);
    }
}

class RotateAnticlockwise implements Transformation{
    @Override
    public Coordinate apply(Coordinate initial, int amount, boolean opposite) {
        if (opposite) return new RotateClockwise().apply(initial, amount, false);
        return Rotate.rotate(initial, amount);
    }
}
