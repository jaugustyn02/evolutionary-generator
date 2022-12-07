package agh.ics.opp;


public record SimulationSetup(
        // map settings
        boolean mapVariant,  // False - GlobeMap, True - HellPortalMap
        int mapHeight,
        int mapWidth,

        // plants settings
        boolean plantPreferencesVariant,  // False - wariant z równikiem, True - wariant z toksycznymi trupami
        int initialNumOfPlants,
        int numOfPlantsPerDay,
        int plantEnergy,

        // animals settings
        boolean animalBehaviorsVariant,  // False - ruch zgodnie z genomem, True - trochę szaleństwa
        int initialNumOfAnimals,
        int initialAnimalEnergy,
        int fullAnimalEnergy,  // minimalna wartość energii by zwierzę było najedzone
        int animalEnergyConsumption,
        int genomeLength,

        // mutation settings
        boolean mutationsVariant, // False - wariant w pełni losowych mutacji, True - mutacja ograniczona +-1
        int minNumOfMutations,
        int maxNumOfMutations
        ) {
}
