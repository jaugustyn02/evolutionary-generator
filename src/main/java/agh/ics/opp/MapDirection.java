package agh.ics.opp;

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

    public MapDirection next(){
        return values[(this.ordinal()+1) % values.length];
    }

    public MapDirection previous(){
        return values[(this.ordinal()+values.length-1) % values.length];
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