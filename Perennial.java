import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Herbs are what herbivores eat.
 * 
 * @author Bruce Gustin
 * @version ©2021.1.13
 */
public class Perennial extends Plant
{
    // Herb Constants
    private final int SEEDS = 20;
    private final int SURVIVAL = 3;  // percentage
    private final int DENSITY = 30;
    private final int SEED_CYCLE = 700;
    private final int LONGEVITY = 3600;
    
    // Herb Variables
    private boolean hasPropagatedSeeds; 
    private int seedCycle;
    private int longevity;
    
    //General
    private int age;
    
    public Perennial()
    {
        longevity = LONGEVITY + (Greenfoot.getRandomNumber((int) (LONGEVITY / 5)));
    }
    
    /**
     * Act - do whatever the Herbs wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        age++;
        propagate();
        die();
    }    

    
    protected void propagate()
    {
        if(age % (SEED_CYCLE) == 0)
        {
            for(int i = 0; i < SEEDS; i++)
            {
                if(Greenfoot.getRandomNumber(100) < SURVIVAL && getNeighbours(60, true, Plant.class).size() < DENSITY)
                {
                    int x = getX() + Greenfoot.getRandomNumber(120) - 60;
                    int y = getY() + Greenfoot.getRandomNumber(120) - 60;
                    getWorld().addObject(new Perennial(), x, y);
                }
            }
        }

    }
    
    protected void die()
    {
        if(isTouching(Herbivore.class) || age > longevity)
        {
            getWorld().removeObject(this);
        }
    }
}
