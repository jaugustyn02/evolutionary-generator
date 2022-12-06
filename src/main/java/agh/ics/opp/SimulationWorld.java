package agh.ics.opp;

public class SimulationWorld {
    private final IWorldMap map;
    private final MapUpdater mapUpdater;
    private final SimulationSetup setup;

    public SimulationWorld(SimulationSetup setup){
        Vector2d upperRight = new Vector2d(setup.mapWidth(), setup.mapHeight());
        this.map = switch (setup.mapVariant()){
            case GlobeMap -> new GlobeMap(upperRight, setup);
            case HellPortalMap -> new HellPortalMap(upperRight, setup);
        };
        this.mapUpdater = new MapUpdater(map, setup);
        this.setup = setup;
        mapUpdater.initMap();
    }

    public void startNextDay(){
        mapUpdater.nextDay();
    }

    public void showMap(){
        System.out.println(map);
    }
}
