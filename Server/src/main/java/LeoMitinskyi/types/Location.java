package LeoMitinskyi.types;

import java.io.Serializable;

public class Location implements Serializable {
    private Long X;
    private double Y;
    private Float Z;
    Location(){}
    public Location(Long X, double Y, Float Z){
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public Long getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public Float getZ() {
        return Z;
    }

    @Override
    public String toString() {
        return "{" +
                "X=" + X +
                ", Y=" + Y +
                ", Z=" + Z +
                '}';
    }
}
