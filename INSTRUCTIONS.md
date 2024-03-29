## SIMULATION SETUP FILE INSTRUCTIONS:
### In *src/main/configurations* directory:
1. Create new **.txt** file.
2. Copy the following lines to the file in the given order:
   >mapVariant=\
   mapHeight=\
   mapWidth=\
   plantPreferencesVariant=\
   initialNumOfPlants=\
   numOfPlantsPerDay=\
   plantEnergy=\
   animalBehaviorsVariant=\
   initialNumOfAnimals=\
   initialAnimalEnergy=\
   fullAnimalEnergy=\
   animalEnergyConsumption=\
   genomeLength=\
   mutationsVariant=\
   minNumOfMutations=\
   maxNumOfMutations=
3. In each line, enter the correct value for a given parameter:
   1. Every variant parameter is **boolean** type.
   2. #### Variants table: ####
      | Variant       | false     | true        |
      |---------------|-----------|-------------|
      | Map           | Globe     | Hell Portal |
      | Plant prefs   | Equator   | Anti Toxic  |
      | Animal behavs | Orderly   | Chaotic     |
      | Mutations     | Unbounded | Bounded     |
         
### In Program menu:
1. Select the saved configuration file.
2. Press **Start** button.

### Setup Validation:
If the created configuration is incorrect, appropriate messages in the console will inform the user which parts of the file are not valid.
