package agh.ics.opp.simulation.types;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public class Vector2d implements Comparable<Vector2d> {
    public final int x, y;
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static Vector2d getRandom(Vector2d lowerLeft, Vector2d upperRight){
        return new Vector2d(
            ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x+1),
            ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y+1)
        );
    }

    public String toString(){
        return String.format("(%d,%d)", this.x, this.y);
    }
    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }
    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }
    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }
    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Integer.max(this.x, other.x), Integer.max(this.y, other.y));
    }
    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Integer.min(this.x, other.x), Integer.min(this.y, other.y));
    }
    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof Vector2d that)) return false;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Vector2d copy(){
        return new Vector2d(this.x, this.y);
    }

    @Override
    public int compareTo(Vector2d other) {
        if (this == other) return 0;
        if (this.x != other.x) return Integer.compare(this.x, other.x);
        return Integer.compare(this.y, other.y);
    }
}