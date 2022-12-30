package agh.ics.opp.simulation.types;


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
)
{
        public boolean isValid(){
                boolean valid = true;
                int mapSize = mapHeight * mapWidth;
                if(mapHeight < 2 || mapHeight > 25){
                        valid = false;
                        System.out.println("mapHeight must be from range [2, 25]");
                }
                if(mapWidth < 2 || mapWidth > 25){
                        valid = false;
                        System.out.println("mapWidth must be from range [2, 25]");
                }
                if(initialNumOfPlants < 0 || initialNumOfPlants > mapSize){
                        valid = false;
                        System.out.println("initialNumOfPlant must be from range [0, mapSize="+mapSize+"]");
                }
                if(numOfPlantsPerDay < 0 || numOfPlantsPerDay > mapSize){
                        valid = false;
                        System.out.println("numOfPlantsPerDay must be from range [0, mapSize="+mapSize+"]");
                }
                if(plantEnergy < 0){
                        valid = false;
                        System.out.println("plantEnergy must be greater or equal 0");
                }
                if(initialNumOfAnimals < 2 || initialNumOfAnimals > mapSize){
                        valid = false;
                        System.out.println("initialNumOfAnimals must be from range [2, 2*mapSize="+2*mapSize+"]");
                }
                if(initialAnimalEnergy < 1){
                        valid = false;
                        System.out.println("initialAnimalEnergy must be greater than 0");
                }
                if(fullAnimalEnergy < 1){
                        valid = false;
                        System.out.println("fullAnimalEnergy must be greater than 0");
                }
                if(animalEnergyConsumption < 1 || animalEnergyConsumption > fullAnimalEnergy){
                        valid = false;
                        System.out.println("animalEnergyConsumption must be from range [1, fullAnimalEnergy="+fullAnimalEnergy+"]");
                }
                if(genomeLength < 1 || genomeLength > 12){
                        valid = false;
                        System.out.println("genomeLength must be from range [1, 12]");
                }
                if(minNumOfMutations < 0 || minNumOfMutations > genomeLength){
                        valid = false;
                        System.out.println("minNumOfMutations must be from range [0, genomeLength=" +genomeLength +"]");
                }
                if(maxNumOfMutations < minNumOfMutations || maxNumOfMutations > genomeLength){
                        valid = false;
                        System.out.println("maxNumOfMutations must be fro range [minNumOfMutations="+minNumOfMutations+", genomeLength="+genomeLength+"]");
                }
                return valid;
        }
}
