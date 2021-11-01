import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Herbs are what herbivores eat.
 * 
 * @author Bruce Gustin
 * @version ©2021.1.13
 */
public class Annual extends Plant
{
    // Annual Constants
    private final int SEEDS = 80;
    private final int SURVIVAL = 3;  // percentage
    private final int DENSITY = 30;
    private final int LONGEVITY = 700;
    
    // Annual Variables
    private boolean hasPropagatedSeeds; 
    private int longevity;
    
    //General
    private int age;
    
    public Annual()
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
        if(age % longevity == 0)
        {
            hasPropagatedSeeds = true;
            for(int i = 0; i < SEEDS; i++)
            {
                if(Greenfoot.getRandomNumber(100) < SURVIVAL && getNeighbours(60, true, Plant.class).size() < DENSITY)
                {
                    int x = getX() + Greenfoot.getRandomNumber(120) - 60;
                    int y = getY() + Greenfoot.getRandomNumber(120) - 60;
                    getWorld().addObject(new Annual(), x, y);
                }
            }
        }

    }
    
    protected void die()
    {
        if(hasPropagatedSeeds || isTouching(Herbivore.class))
        {
            getWorld().removeObject(this);
        }
    }
}
