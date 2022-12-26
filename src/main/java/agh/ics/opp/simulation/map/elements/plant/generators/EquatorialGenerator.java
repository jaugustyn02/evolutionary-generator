package agh.ics.opp.simulation.map.elements.plant.generators;

import agh.ics.opp.simulation.types.Vector2d;
import agh.ics.opp.simulation.map.elements.plant.Plant;
import agh.ics.opp.simulation.map.IWorldMap;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class EquatorialGenerator extends AbstractPlantGenerator {
    private final Set<Vector2d> emptySquaresAround;
    private final Set<Vector2d> emptySquaresEquator;

    private final Vector2d equatorLowerLeft, equatorUpperRight;
    private final int plantEnergy;

    public EquatorialGenerator(IWorldMap map, int plantEnergy) {
        this.plantEnergy = plantEnergy;
        // lowerLeft.y - 40% height
        this.equatorLowerLeft = new Vector2d(map.getLowerLeft().x, (int) ((map.getUpperRight().y - map.getLowerLeft().y) * 0.4));
        //upperRight.y - 60% height
        this.equatorUpperRight = new Vector2d(map.getUpperRight().x, (int) ((map.getUpperRight().y - map.getLowerLeft().y) * 0.6));

        emptySquaresAround = new HashSet<>();
        emptySquaresEquator = new HashSet<>();
        for(int x=0; x < map.getWidth(); x++){
            for(int y=0; y < equatorLowerLeft.y; y++)
                emptySquaresAround.add(new Vector2d(x, y));
            for(int y=equatorLowerLeft.y; y <= equatorUpperRight.y; y++)
                emptySquaresEquator.add(new Vector2d(x, y));
            for(int y=equatorUpperRight.y+1; y < map.getHeight(); y++)
                emptySquaresAround.add(new Vector2d(x, y));
        }
    }

    @Override
    public Plant generatePlant() {
        if(mapIsFull()) return null;
        Vector2d position;
        if(emptySquaresEquator.isEmpty() || ThreadLocalRandom.current().nextInt(0, 5) == 0) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, emptySquaresAround.size());
            position = getPositionFromSet(emptySquaresAround, randomIndex);
        }
        else {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, emptySquaresEquator.size());
            position = getPositionFromSet(emptySquaresEquator, randomIndex);
        }
        return new Plant(position, plantEnergy);
    }

    private boolean mapIsFull() {
        return emptySquaresAround.isEmpty() && emptySquaresEquator.isEmpty();
    }

    @Override
    public void plantRemoved(Plant plant) {
        Vector2d position = plant.getPosition();
        if(position.follows(equatorLowerLeft) && position.precedes(equatorUpperRight)) {
            emptySquaresEquator.add(position);
        }
        else {
            emptySquaresAround.add(position);
        }
    }

    @Override
    public void plantPlaced(Plant plant){
        Vector2d position = plant.getPosition();
        if(position.follows(equatorLowerLeft) && position.precedes(equatorUpperRight)) {
            emptySquaresEquator.remove(position);
        }
        else {
            emptySquaresAround.remove(position);
        }
    }

    public Set<Vector2d> getEmptySquaresEquator() {
        return emptySquaresEquator;
    }

    public Set<Vector2d> getEmptySquaresAround() {
        return emptySquaresAround;
    }


//    public EquatorialGenerator(IWorldMap map) {
//        this.map = map;
//        // lowerLeft.y - 40% height
//        this.equatorLowerLeft = new Vector2d(map.getLowerLeft().x, (int)((map.getUpperRight().y-map.getLowerLeft().y)*0.4));
//        //upperRight.y - 60% height
//        this.equatorUpperRight = new Vector2d(map.getUpperRight().x, (int)((map.getUpperRight().y-map.getLowerLeft().y)*0.6));

//        this.maxPlantsEquator = map.getWidth() * (equatorUpperRight.y - equatorLowerLeft.y + 1);
//        this.maxPlantsAround = map.getWidth() * map.getHeight() - this.maxPlantsEquator;

//        this.heightAboveEquator = map.getUpperRight().y - equatorUpperRight.y;
//        this.heightUnderEquator = equatorLowerLeft.y - map.getLowerLeft().y;
//        this.maxPlantsAboveEquator = map.getWidth() * heightAboveEquator;
//        this.maxPlantsUnderEquator = map.getWidth() * heightUnderEquator;
//
//        System.out.println(maxPlantsEquator+" - "+maxPlantsAround);
    }
//
//    private Vector2d getRandomPositionFrom(Vector2d lowerLeft, Vector2d upperRight){
//        Vector2d position;
//        do{
//            position = Vector2d.getRandom(lowerLeft, upperRight);
//        } while (map.isPlantAt(position));
//        return position;
//    }
//
//    private boolean mapIsFull(){
//        return ((IMapStatisticRunner)map).getNumOfPlants() == maxPlantsEquator + maxPlantsAround;
//    }
//    private boolean equatorIsFull(){
//        return getNumOfPlantsOnEquator() == maxPlantsEquator;
//    }
//    private boolean aboveEquatorIsFull(){
//        return getNumOfPlantsAboveEquator() == maxPlantsAboveEquator;
//    }
//    private boolean underEquatorIsFull(){
//        return getNumOfPlantsUnderEquator() == maxPlantsUnderEquator;
//    }
//
//    private int getNumOfPlantsOnEquator(){
//        return (int) map.getPlantsPositions().stream().filter(p -> p.follows(equatorLowerLeft) && p.precedes(equatorUpperRight)).count();
//    }
//    private int getNumOfPlantsAboveEquator(){
//        return (int) map.getPlantsPositions().stream().filter(p -> p.follows(new Vector2d(equatorLowerLeft.x, equatorUpperRight.y+1))).count();
//    }
//    private int getNumOfPlantsUnderEquator(){
//        return (int) map.getPlantsPositions().stream().filter(p -> p.precedes(new Vector2d(equatorUpperRight.x, equatorLowerLeft.y-1))).count();
//    }
//
//    // DO ZMIANY - losowanie po za równikiem powinno odbywać się na prostokącie będącym połączeniem części nad równikiem i pod nim,
//    // a następnie pozycja powinna być odpowiednio przesunięta o wektor (tak żeby pozycja była z jednej z dwóch części)
//    // Wyjaśnienie problemu: w tym momencie szansa na to że pozycja będzie z dołu lub z góry jest określona poprzez stosunek wielkości tych części,
//    // natomiast potem losowana jest pozycja z tej części do skutku, co raczej psuję prawdopodobieństwo trafienia pozycji z danej części
//    public Vector2d generatePlants(){
//        if (mapIsFull()) return null;
//        // 20% chance to get position from hole map or if equator is full
//        if (!(underEquatorIsFull() && aboveEquatorIsFull()) && ThreadLocalRandom.current().nextInt(0, 5) == 0 || equatorIsFull()){
//            int randInt = ThreadLocalRandom.current().nextInt(0, heightAboveEquator + heightUnderEquator);
//            if (!underEquatorIsFull() && randInt < heightUnderEquator || aboveEquatorIsFull())
//                return getRandomPositionFrom(map.getLowerLeft(), new Vector2d(map.getUpperRight().x, equatorLowerLeft.y-1));
//            else
//                return getRandomPositionFrom(new Vector2d(map.getLowerLeft().x, equatorUpperRight.y+1), map.getUpperRight());
//        }
//        // 80% chance to get position from equator
//        else{
//            return getRandomPositionFrom(equatorLowerLeft, equatorUpperRight);
//        }

//    }

//}

