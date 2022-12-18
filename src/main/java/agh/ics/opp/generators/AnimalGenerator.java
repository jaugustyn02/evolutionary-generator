package agh.ics.opp.generators;

import agh.ics.opp.SimulationSetup;
import agh.ics.opp.Vector2d;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.maps.IAnimalPositionCorrector;
import agh.ics.opp.behaviours.ChaoticSelector;
import agh.ics.opp.behaviours.IGeneSelector;
import agh.ics.opp.behaviours.OrderlySelector;
import agh.ics.opp.mutations.BoundedMutator;
import agh.ics.opp.mutations.IGenomeMutator;
import agh.ics.opp.mutations.UnboundedMutator;

public class AnimalGenerator {
    private final IGenomeMutator genomeMutator;
    private final GenomeGenerator genomeGenerator;
    private final IGeneSelector geneSelector;
    private final IAnimalPositionCorrector animalPositionCorrector;
    private final SimulationSetup setup;
    private final Vector2d mapLowerLeft, mapUpperRight;

    public AnimalGenerator(Vector2d mapLowerLeft, Vector2d mapUpperRight, IAnimalPositionCorrector corrector, SimulationSetup setup){
        this.animalPositionCorrector = corrector;
        this.mapLowerLeft = mapLowerLeft;
        this.mapUpperRight = mapUpperRight;
        this.genomeGenerator = new GenomeGenerator(setup.genomeLength());
        this.setup = setup;
        this.geneSelector = (setup.animalBehaviorsVariant() ?
                new ChaoticSelector(setup.genomeLength()):  // true
                new OrderlySelector(setup.genomeLength())); // false
        this.genomeMutator = (setup.mutationsVariant() ?
                new BoundedMutator(setup.minNumOfMutations(), setup.maxNumOfMutations()):       // true
                new UnboundedMutator(setup.minNumOfMutations(), setup.maxNumOfMutations()));    // false
    }

    public Animal generateAdultAnimal(){
        return generateAnimal(Vector2d.getRandom(mapLowerLeft, mapUpperRight), setup.initialAnimalEnergy(), genomeGenerator.getRandomGenome());
    }

    public Animal generateDescendantAnimal(Animal parent1, Animal parent2){
        return generateAnimal(parent1.getPosition(), parent1.getEnergyConsumption() + parent2.getEnergyConsumption(),
                genomeMutator.getMutatedGenome(genomeGenerator.getDescendantGenome(parent1, parent2)));
    }

    private Animal generateAnimal(Vector2d position, int initEnergy, int[] genome){
        return new Animal(position, initEnergy, setup.fullAnimalEnergy(),
                setup.animalEnergyConsumption(), genome, animalPositionCorrector, geneSelector);
    }
}
