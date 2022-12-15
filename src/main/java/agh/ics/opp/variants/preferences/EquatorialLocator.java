package agh.ics.opp.variants.preferences;

import agh.ics.opp.Vector2d;
import agh.ics.opp.variants.maps.IMapStatisticRunner;
import agh.ics.opp.variants.maps.IWorldMap;

import java.util.concurrent.ThreadLocalRandom;

public class EquatorialLocator implements IPlantPlacementLocator {
    private final int maxPlantsEquator, maxPlantsAround, maxPlantsAboveEquator, maxPlantsUnderEquator;
    private final int heightAboveEquator, heightUnderEquator;
    private final Vector2d equatorLowerLeft, equatorUpperRight;
    final private IWorldMap map;

    public EquatorialLocator(IWorldMap map) {
        this.map = map;
        // lowerLeft.y - 40% height
        this.equatorLowerLeft = new Vector2d(map.getLowerLeft().x, (int)((map.getUpperRight().y-map.getLowerLeft().y)*0.4));
        //upperRight.y - 60% height
        this.equatorUpperRight = new Vector2d(map.getUpperRight().x, (int)((map.getUpperRight().y-map.getLowerLeft().y)*0.6));

        this.maxPlantsEquator = map.getWidth() * (equatorUpperRight.y - equatorLowerLeft.y + 1);
        this.maxPlantsAround = map.getWidth() * map.getHeight() - this.maxPlantsEquator;

        this.heightAboveEquator = map.getUpperRight().y - equatorUpperRight.y;
        this.heightUnderEquator = equatorLowerLeft.y - map.getLowerLeft().y;
        this.maxPlantsAboveEquator = map.getWidth() * heightAboveEquator;
        this.maxPlantsUnderEquator = map.getWidth() * heightUnderEquator;

        System.out.println(maxPlantsEquator+" - "+maxPlantsAround);
    }

    private Vector2d getRandomPositionFrom(Vector2d lowerLeft, Vector2d upperRight){
        Vector2d position;
        do{
            position = Vector2d.getRandom(lowerLeft, upperRight);
        } while (map.isPlantAt(position));
        return position;
    }

    private boolean mapIsFull(){
        return ((IMapStatisticRunner)map).getNumOfPlants() == maxPlantsEquator + maxPlantsAround;
    }
    private boolean equatorIsFull(){
        return getNumOfPlantsOnEquator() == maxPlantsEquator;
    }
    private boolean aboveEquatorIsFull(){
        return getNumOfPlantsAboveEquator() == maxPlantsAboveEquator;
    }
    private boolean underEquatorIsFull(){
        return getNumOfPlantsUnderEquator() == maxPlantsUnderEquator;
    }

    private int getNumOfPlantsOnEquator(){
        return (int) map.getPlantsPositions().stream().filter(p -> p.follows(equatorLowerLeft) && p.precedes(equatorUpperRight)).count();
    }
    private int getNumOfPlantsAboveEquator(){
        return (int) map.getPlantsPositions().stream().filter(p -> p.follows(new Vector2d(equatorLowerLeft.x, equatorUpperRight.y+1))).count();
    }
    private int getNumOfPlantsUnderEquator(){
        return (int) map.getPlantsPositions().stream().filter(p -> p.precedes(new Vector2d(equatorUpperRight.x, equatorLowerLeft.y-1))).count();
    }

    // DO ZMIANY - losowanie po za równikiem powinno odbywać się na prostokącie będącym połączeniem części nad równikiem i pod nim,
    // a następnie pozycja powinna być odpowiednio przesunięta o wektor (tak żeby pozycja była z jednej z dwóch części)
    // Wyjaśnienie problemu: w tym momencie szansa na to że pozycja będzie z dołu lub z góry jest określona poprzez stosunek wielkości tych części,
    // natomiast potem losowana jest pozycja z tej części do skutku, co raczej psuję prawdopodobieństwo trafienia pozycji z danej części
    public Vector2d getNewPlantPosition(){
        if (mapIsFull()) return null;
        // 20% chance to get position from hole map or if equator is full
        if (!(underEquatorIsFull() && aboveEquatorIsFull()) && ThreadLocalRandom.current().nextInt(0, 5) == 0 || equatorIsFull()){
            int randInt = ThreadLocalRandom.current().nextInt(0, heightAboveEquator + heightUnderEquator);
            if (!underEquatorIsFull() && randInt < heightUnderEquator || aboveEquatorIsFull())
                return getRandomPositionFrom(map.getLowerLeft(), new Vector2d(map.getUpperRight().x, equatorLowerLeft.y-1));
            else
                return getRandomPositionFrom(new Vector2d(map.getLowerLeft().x, equatorUpperRight.y+1), map.getUpperRight());
        }
        // 80% chance to get position from equator
        else{
            return getRandomPositionFrom(equatorLowerLeft, equatorUpperRight);
        }
    }

   
}

