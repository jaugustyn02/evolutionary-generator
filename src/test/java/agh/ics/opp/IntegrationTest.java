package agh.ics.opp;

import agh.ics.opp.maps.GlobeMap;
import agh.ics.opp.maps.HellPortalMap;
import agh.ics.opp.maps.IWorldMap;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class IntegrationTest {
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
        System.out.println("Wariant: "+ Arrays.toString(variants));
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

        for (int i=0; i < 100; i++){
            updater.nextDay();
        }
        System.out.println("After 100 days:");
        System.out.println(map);
    }
}
