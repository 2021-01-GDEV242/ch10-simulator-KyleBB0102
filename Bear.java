import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A class representing a bear as they are on the top of the food chain, no
 * one can eat them. They feed on both foxes and rabbits
 * They also live longer than foxes and rabbits and they can only die by old 
 * age
 * 
 * @author Kyle Balao
 * @version 04/29/21
 */
public class Bear extends Animal
{
    // Characteristics shared by all bears (class variables).
    
    // The age at which a bear can start to breed.
    private static int BREEDING_AGE = 30;
    // The age to which a bear can live.
    private static int MAX_AGE = 190;
    // The likelihood of a bear breeding.
    private static double BREEDING_PROBABILITY = 0.02;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE = 1;
    // The food value of a single rabbit and a single fox. In effect, this is the
    // number of steps a bear can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;
    private static final int FOX_FOOD_VALUE = 18;
    
    // Individual characteristics (instance fields).
    // The bear's age.
    private int age;
    
    // The bears's food level, which is increased by eating rabbits or foxes.
    private int foodLevel;
    /**
     * Create a bear. A bear can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the bear will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Bear(boolean randomAge, Field field, Location location)
    {
        super(field, location);
    }
    
    /**
     * This is what the bear does most of the time: it hunts for
     * foxes and rabbits. In the process, it might breed or die of old age
     * @param field The field currently occupied.
     * @param newbears A list to return newly born bears.
     */    
    public void act(List<Animal> newBears)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newBears);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
     
      /**
     * Check whether or not this bear is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newbearrs A list to return newly born bears.
     */
    private void giveBirth(List<Animal> newBears)
    {
        // New bears are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Bear young = new Bear(false, field, loc);
            newBears.add(young);
        }
    }
    
    /**
     * Look for foxes and rabbits adjacent to the current location.
     * Only the first live animal that is seen will be eaten.
     * @return Where food was found, or null if it wasn't.
     */
     private Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Animal animal = (Animal) field.getObjectAt(where);
            if (animal != null) {
                if (animal.isAlive()) {
                    animal.setDead();
                    return where;
                }
            }
        }
        return null;
    }
    
     /**
     * returns the maximum age of a bear can live
     * @return int maximum age of a bear can live
     */
    protected int getMaxAge()
    {
    	return MAX_AGE;
    }
    
    /**
     * Increase the age. This could result in the bear's death.
     */
    private void incrementAge() {
        int age = getAge();
        setAge(++age);
        if (age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Returns the breeding age of the animal
     * @return BREEDING_AGE 
     */
    public int getBreedingAge()
    {
    	return BREEDING_AGE;
    }
    
    /**
     * Retrieve MAX_LITTER_SIZE
     * @return MAX_LITTER_SIZE 
     */
    public int getMaxLitterSize()
    {
    	return MAX_LITTER_SIZE;
    }
    
    /**
     * RETRIEVE BREEDING_PROBABILITY
     * @return BREEDING_PROBABILITY 
     */
    public double getBreedingProbability()
    {
    	return BREEDING_PROBABILITY;
    }
}
