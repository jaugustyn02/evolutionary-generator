package agh.ics.opp.simulation.map.animal.behaviours;

import agh.ics.opp.simulation.map.elements.animal.behaviours.OrderlySelector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderlySelectorTest {
    @Test
    public void orderlyBehaviour() {
        OrderlySelector orderlySelector = new OrderlySelector(10);
        for (int i = 0; i < 10; i++) {
            int nextGeneIndex = orderlySelector.getNextGeneIndex(i);
            assertEquals((i + 1) % 10, nextGeneIndex);
        }
    }
    @Test
    public void withinGenomeBounds() {
        OrderlySelector orderlySelector = new OrderlySelector(10);
        for (int i = 0; i < 10; i++) {
            int nextGeneIndex = orderlySelector.getNextGeneIndex(i);
            assertTrue(nextGeneIndex >= 0 && nextGeneIndex < 10);
        }
    }
    @Test
    public void firstGeneAfterEnd() {
        OrderlySelector orderlySelector = new OrderlySelector(10);
        int nextGeneIndex = orderlySelector.getNextGeneIndex(9);
        assertEquals(0, nextGeneIndex);
    }

}
