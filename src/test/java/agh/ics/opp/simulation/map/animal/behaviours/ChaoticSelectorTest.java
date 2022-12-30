package agh.ics.opp.simulation.map.animal.behaviours;

import agh.ics.opp.simulation.map.elements.animal.behaviours.ChaoticSelector;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChaoticSelectorTest {
    @Test
    public void withinGenomeBounds() {
        ChaoticSelector chaoticSelector = new ChaoticSelector(10);
        for (int i = 0; i < 100; i++) {
            int currentGeneIndex = ThreadLocalRandom.current().nextInt(0, 10);
            int nextGeneIndex = chaoticSelector.getNextGeneIndex(currentGeneIndex);
            assertTrue(nextGeneIndex >= 0 && nextGeneIndex < 10);
        }
    }
}
