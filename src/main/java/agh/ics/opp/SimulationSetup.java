package agh.ics.opp;


public record SimulationSetup(
        // map settings
        MapVariant mapVariant,
        int mapHeight,
        int mapWidth,

        // plants settings
        GrowerVariant growerVariant,
        int initialNumOfPlants,
        int numOfPlantsPerDay,
        int plantEnergy,
//        IPlantGrower plantGrower,

        // animals settings
        MoverVariant moverVariant,
        int initialNumOfAnimals,
        int initialAnimalEnergy,
        int fullAnimalEnergy,  // minimalna energia by zwierzę było najedzone
        int animalEnergyConsumption,
        int genomeLength,
//        IAnimalMover animalMover,

        // mutation settings
        MutatorVariant mutatorVariant,
        int minNumOfMutations,
        int maxNumOfMutations
//        IGenomeMutator genomeMutator
        ) {
}
