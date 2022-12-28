package agh.ics.opp.simulation.types;

import java.util.concurrent.ThreadLocalRandom;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;
    private static final MapDirection[] values = values();

    public static MapDirection getRandom(){
        return values[ThreadLocalRandom.current().nextInt(0, values().length)];
    }

    public MapDirection rotate(int rotation){
        return values[(this.ordinal()+rotation) % values.length];
    }

    public String toString(){
        return switch (this){
            case NORTH -> "N";
            case NORTH_EAST -> "NE";
            case SOUTH -> "S";
            case SOUTH_EAST -> "SE";
            case WEST -> "W";
            case SOUTH_WEST -> "SW";
            case EAST -> "E";
            case NORTH_WEST -> "NW";
        };
    }

    public Vector2d toUnitVector(){
        return switch (this){
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);
        };
    }
}