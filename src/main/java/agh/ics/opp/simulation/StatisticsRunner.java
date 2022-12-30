
package agh.ics.opp.simulation;

import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.types.SimulationStatistics;
import agh.ics.opp.simulation.types.Vector2d;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatisticsRunner {
    final IWorldMap map;
    final static String savesDirectoryName = "src/main/statistics/";
    final File csvFile;
    final boolean saveToFile;

    int numOfAnimals = 0;
    int numOfPlants = 0;
    int numOfEmptySquares = 0;
    int[] mostPopularGenome = null;
    double avgAnimalEnergy;
    double avgAnimalLifeSpan;

    int numOfDeadAnimals = 0;
    int sumOfDeadAnimalsLifeSpan = 0;

    public StatisticsRunner(IWorldMap map, String csvFileName){
        this.map = map;
        saveToFile = csvFileName != null;
        if(saveToFile) {
            csvFile = new File(savesDirectoryName+csvFileName);
            try (PrintWriter pw = new PrintWriter(csvFile)) {
                pw.println(convertToCSV(Arrays.stream(("Day: \n"+getStatistics()
                        .toString())
                        .split("\n"))
                        .map(s -> s.split(": ")[0])
                        .toList())
                );
            } catch (FileNotFoundException e){
                System.out.println("Exception: " + e.getMessage());
            }
        }else csvFile = null;
    }

    public SimulationStatistics getStatistics(){
        return new SimulationStatistics(
                numOfAnimals,
                numOfPlants,
                numOfEmptySquares,
                mostPopularGenome,
                avgAnimalEnergy,
                avgAnimalLifeSpan
        );
    }

    public void updateStatistics(int dayNum){
        Set<Animal> animals = map.getAnimalsSet();
        numOfAnimals = animals.size();
        numOfPlants = map.getPlantsPositions().size();
        numOfEmptySquares = (int)IntStream.range(0, map.getWidth())
                .boxed()
                .flatMap(x -> IntStream.range(0, map.getHeight())
                        .mapToObj(y -> new Vector2d(x, y))
                ).filter(v -> !map.isOccupied(v)).count();
        mostPopularGenome = animals.stream().collect(Collectors.groupingBy(Animal::getGenome, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
        avgAnimalEnergy = animals.stream().mapToInt(Animal::getEnergy)
                .summaryStatistics().getAverage();
        numOfDeadAnimals += animals.stream().filter(animal -> animal.getEnergy() == 0).count();
        sumOfDeadAnimalsLifeSpan += animals.stream().filter(animal -> animal.getEnergy() == 0).mapToInt(Animal::getAge).sum();
        avgAnimalLifeSpan = (double) sumOfDeadAnimalsLifeSpan / numOfDeadAnimals;
        //System.out.println(animals.stream().collect(Collectors.groupingBy(Animal::getGenome, Collectors.counting()))
        //        toString());

        if(saveToFile) saveToCSVFile(dayNum);
    }
    public int getNumOfAnimals(){
        return numOfAnimals;
    }
    public int getNumOfPlants(){
        return numOfPlants;
    }

    private void saveToCSVFile(int dayNum){
        try (FileWriter fw = new FileWriter(csvFile, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(convertToCSV(Arrays.stream(("Day: " + dayNum + "\n" + getStatistics()
                            .toString())
                            .split("\n"))
                    .map(s -> s.split(": ")[1])
                    .toList())
            );
            bw.append('\n');
            bw.close();
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public String convertToCSV(List<String> data) {
        return data.stream().map(s -> (s.contains(",") ? '"'+s+'"' : s)).collect(Collectors.joining(","));
//        return String.join(",", data);
    }
}

