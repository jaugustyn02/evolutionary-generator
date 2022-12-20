package agh.ics.opp;

import agh.ics.opp.simulation.maps.IMapUpdater;
import agh.ics.opp.simulation.maps.MapUpdater;
import agh.ics.opp.simulation.maps.GlobeMap;
import agh.ics.opp.simulation.maps.HellPortalMap;
import agh.ics.opp.simulation.maps.IWorldMap;
import agh.ics.opp.simulation.types.SimulationSetup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MapIntegrationTest {
    @Test
    public void AllVariantsIntegrationTest(){
        boolean[] variants = new boolean[4];
        for(int i=0; i<16; i++){
            int j = i;
            variants[0] = (j % 2 == 1);
            j /= 2;
            variants[1] = (j % 2 == 1);
            j /= 2;
            variants[2] = (j % 2 == 1);
            j /= 2;
            variants[3] = (j % 2 == 1);

            VariantsIntegrationTest(variants);
        }
    }

    private void VariantsIntegrationTest(boolean[] variants){
        if(variants.length != 4) return;
        System.out.println("Variants: "+ Arrays.toString(variants));
        SimulationSetup setup = new SimulationSetup(
                variants[0], 10, 10,
                variants[1], 10, 10, 5,
                variants[2], 15, 15, 10, 5, 10,
                variants[3], 0, 10
        );
        IWorldMap map = (setup.mapVariant() ?
                new HellPortalMap(setup) :  // true
                new GlobeMap(setup));       // false
        IMapUpdater updater = new MapUpdater(map, setup);

        for (int i=0; i < 1000; i++){
            updater.nextDay();
        }
        System.out.println("After 1000 days:");
        System.out.println(map);
    }
}
