package agh.ics.opp.variants.preferences;

import agh.ics.opp.Vector2d;
import agh.ics.opp.variants.maps.IWorldMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EquatorialLocator implements IPlantPlacementLocator {
    private final Vector2d equatorLowerLeft, equatorUpperRight;
    final private IWorldMap map;
    //liczba wszystkich pól na równiku i poza
    private final int maxPlantsEquator;
    private final int maxPlantsAround;
    //liczba roślin na powyższych polach

    private int numPlantsEquator = 0;
    private int numPlantsAround = 0;

    public EquatorialLocator(IWorldMap map) {
        this.map = map;
        // lowerLeft.y - 40% height
        this.equatorLowerLeft = new Vector2d(map.getLowerLeft().x, (int)((map.getUpperRight().y-map.getLowerLeft().y)*0.4));
        //upperRight.y - 60% height
        this.equatorUpperRight = new Vector2d(map.getUpperRight().x, (int)((map.getUpperRight().y-map.getLowerLeft().y)*0.6));

        this.maxPlantsEquator = (equatorUpperRight.x - equatorLowerLeft.x)*(equatorUpperRight.x - equatorLowerLeft.x);
        this.maxPlantsAround = map.getWidth()*map.getHeight() - this.maxPlantsEquator;
        System.out.println("equator: "+equatorLowerLeft+", "+equatorUpperRight);
    }

    // zmieniłbym podejście losowania, ponieważ przy małej dostępności wolnych miejsc pętla do while może się wykonywać nieskończenie długo
    // może wartoby przechowywać gdzieś informacje o tym, które pola są dostępne, np liste pozycji i losować indeks
    private Vector2d getRandomPositionFromArea(Vector2d lowerLeft, Vector2d upperRight){
        Vector2d position;
        do{
            int randomX = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x);
            int randomY = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y);
            position = new Vector2d(randomX, randomY);
        } while (map.isPlantAt(position));
        return position;
    }
    // czy mapa jest cała zapełniona
    private boolean mapIsFull(){
        return numPlantsEquator + numPlantsAround == maxPlantsEquator + maxPlantsAround;
    }
    // czy równik jest cały zapełniony
    private boolean equatorIsFull(){
        return numPlantsEquator == maxPlantsEquator;
        // Trzeba wiedzieć ile jest niezajętych pól przez rośliny na równiku
    }




    // metoda musi sprawdzać czy w ogóle jest miejsce na umieszczenie kolejnej rośliny
    // jeżeli nie ma w ogóle na mapie, to możemy ustalić że zwracana jest wartość null
    public Vector2d getNewPlantPosition(){
        if (mapIsFull()) return null;
        // 20% chance to get position from hole map or if equator is full
        if (ThreadLocalRandom.current().nextInt(0, 5) == 0 || equatorIsFull()){
            numPlantsAround++;
            map.addCurrentPlantsNumber();
            return new Random().nextBoolean() ?
                    getRandomPositionFromArea(map.getLowerLeft(), new Vector2d(map.getUpperRight().x, equatorLowerLeft.y-1)) :
                    getRandomPositionFromArea(new Vector2d(map.getLowerLeft().x, equatorUpperRight.y+1), map.getUpperRight());
        }
        // 80% chance to get position from equator
        else{
            numPlantsEquator++;
            map.addCurrentPlantsNumber();
            return getRandomPositionFromArea(equatorLowerLeft, equatorUpperRight);
        }
    }

   
}

