

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author Kyle Balao
 * @version 04/29/21
 */
public abstract class Animal
{
    // The fox's age.
    private int age;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // A shared random number generator to control breeding.
    public static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        age = 0;
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
        
    /**
     * returns the maximum age of an animal type can live
     * @return agemaximum age of an animal type can live
     */
    protected abstract int getMaxAge();
    
    /**
     * Retrieve the location of animal
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
                
    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed, false otherwise.
     */
    public boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }
    
    /**
     * Returns an animals current age
     * @return age int the current age of an animal
     */
	public int getAge() 
	{
		return age;
	}
	
    /**
     * set an animals current age
     * @param age int set the current age
     */
	public void setAge(int age) 
	{
		this.age = age;
	}


    /**
     * Retrieves MAX_LITTER_SIZE
     * @return MAX_LITTER_SIZE maximum litter
     */
    public abstract int getMaxLitterSize();
    
    /**
     * Retieves BREEDING_PROBABILITY
     * @return BREEDING_PROBABILITY breeding kansen
     */
    public abstract double getBreedingProbability();
    
     /**
     * Returns the breeding age of the animal
     * 
     * @return the breeding age
     */
    public abstract int getBreedingAge();
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */    
    public int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
}
