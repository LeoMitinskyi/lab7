package LeoMitinskyi.types;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private float x;
    private float y;
    public Coordinates(float x, float y){
        this.x = x;
        this.y = y;
    }
    Coordinates(){}
    public float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}
