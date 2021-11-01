import greenfoot.*;

/**
 * Herbivores eat herbs are are eaten by Carnivores
 * @author Bruce Gustin
 * @version ©2021.1.13
 */
public class Herbivore extends Animal
{
    // Herbivore Constants
    private final int SPEED = 1;
    private final int MAX_TURN_RADIUS = 12;
    private final int TURN_AT_EDGE = 90;
    private final int FOOD_TO_BREED = 10;
    private final int BREED_AGE = 220;
    private final int LONGEVITY = 1000;
    private final int BROOD = 6;
    
    // Herbivore Variables
    private GreenfootImage image;
    private String imageFileName;
    private int longevity; 
    private int fed;
    
    //general
    private int age;   
    
    public Herbivore()
    {
        turn(Greenfoot.getRandomNumber(360));
        longevity = (int) (LONGEVITY * (Greenfoot.getRandomNumber(100) / 80.0));
        fed = 0;
    }
 
    public void act()
    {
        seekFood();
        fed = eat(fed, Plant.class);
        parturition();
        die(age, longevity);
        age++;
    }
    
    protected void seekFood()
    {
        move(SPEED);
        
        if(isAtEdge())
        {
            turn(TURN_AT_EDGE);
        }
        
        if(age % 5 == 0)
        {
            int turn = 2 * Greenfoot.getRandomNumber(MAX_TURN_RADIUS) - MAX_TURN_RADIUS;
            turn(turn);
        }
    }
    
    protected void parturition()
    {
        if(fed >= FOOD_TO_BREED && age > BREED_AGE)
        {
            fed = 0;
            for(int i = 0; i < BROOD; i++)
            {
                getWorld().addObject(new Herbivore(), getX() + 10, getY() + 10);
            }
        }
    }

}