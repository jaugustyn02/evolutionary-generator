package agh.ics.opp;


public class Animal extends AbstractMapElement{
    private Vector2d position;
    private final int[] genome;
    private Integer energy;
//    private int direction;
    private MapDirection direction;
    private int nextGeneIndex;

    public Animal(Vector2d position, Integer initialEnergy, int[] genome){
        this.position = position;
        this.energy = initialEnergy;
        this.genome = genome;
        this.nextGeneIndex = 0; // to powinno być chyba randomowe
        this.direction = MapDirection.NORTH;
    }

    public void turnBy(int rotation){
        this.direction = direction.rotate(rotation);
//        this.direction = (this.direction.ordinal() + rotation) % 8;
    }

    public void turnBack(){ this.turnBy(4); }

    public void moveToPosition(Vector2d position){
        this.position = position;
    }

//    public void moveInDirection(){
//        this.position = this.position.add(this.direction.toUnitVector());
//    }

    public Vector2d getPosition() { return this.position; }

    public int[] getGenome(){ return this.genome; }

    public MapDirection getDirection(){ return this.direction; }

    public int getNextGeneIndex(){ return this.nextGeneIndex; }

    public void setNextGeneIndex(int index) {
        if(0 <= index && index < getGenomeLength())
            this.nextGeneIndex = index;
        else System.out.println(index+" - to niepoprawny indeks genu");
    }

    public void incrementGeneIndex(){
        this.setNextGeneIndex((this.nextGeneIndex+1)%getGenomeLength());
    }

    public int getEnergy(){ return this.energy; }

    public void decrementEnergy(){ this.energy--; }

    public void decreaseEnergy(Integer energy){ this.energy -= energy; }

    public void increaseEnergy(Integer energy){ this.energy += energy; }

    public int getGenomeLength(){
        return this.genome.length;
    }

    public String getImagePath() {
        return null;
    }

    public String getLabelName() {
        return null;
    }

    public String toString(){
        return energy.toString();
    }
}
