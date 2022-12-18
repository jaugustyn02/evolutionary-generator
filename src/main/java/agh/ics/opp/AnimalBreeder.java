package agh.ics.opp;

import agh.ics.opp.elements.Animal;
import agh.ics.opp.generators.AnimalGenerator;

public class AnimalBreeder {
    private final AnimalGenerator animalGenerator;

    public AnimalBreeder(AnimalGenerator animalGenerator) {
        this.animalGenerator = animalGenerator;
    }

    public Animal breed2Animals(Animal parent1, Animal parent2){
        parent1.hasBred();
        parent2.hasBred();
        return animalGenerator.generateDescendantAnimal(parent1, parent2);
    }
}
