import greenfoot.*;  // (Actor, World, Greenfoot, GreenfootImage)
import java.util.List;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

/**
 * Creates the world for predation events to occur and
 * to display population results
 * 
 * @author Bruce Gustin
 * @version ©2021.1.13
 */

public class Forest extends World
{
    // Forest Constants
    private final int START_PLANTS = 800;
    private final int START_HERBIVORE = 48;
    private final int START_CARNIVORE = 3;
    private final int STEP_REPORTING = 50;
    private final int MAX_STEPS = 5000;
    
    // Creates fields for writing data
    private FileWriter myWriter;
    private int step;
    private Animal animal;
    private Plant plant;
    
    // Forest constructor
    public Forest() 
    {
        super(1200, 800, 1);
        prepare();
    }

    // Repeats
    public void act()
    {
        tableGraph();
        endSimulation();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {     
        for(int i = 0; i < START_PLANTS; i++)
        {
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight());
            plant = new Perennial();
            if(Math.random() < 0.5)
            {
                plant = new Annual();
            }
            addObject(plant, x, y);
        }
        
        for(int i = 0; i < START_HERBIVORE; i++)
        {
            animal = new Herbivore();
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight());
            addObject(animal, x, y);
        }
        
        for(int i = 0; i < START_CARNIVORE; i++)
        {
            animal = new Carnivore();
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight());
            addObject(animal, x, y);
        }
        
        try
        {
            myWriter = new FileWriter("data.txt");
        } catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    private void tableGraph()
    {
        step++;
        List PlantObjects = getObjects(Plant.class);
        List HerbivoreObjects = getObjects(Herbivore.class);        
        List CarnivoreObjects = getObjects(Carnivore.class);
        if(step % STEP_REPORTING == 0)
        {
            String stepLine = "Step: " + step;
            String plantLine = "Plants: " + PlantObjects.size();
            String herbivoreLine = "Herbivore: " + HerbivoreObjects.size();
            String carnivoreLine = "Carnivore: " + CarnivoreObjects.size();
            System.out.format("%15s%13s%16s%18s\n", stepLine, plantLine, herbivoreLine, carnivoreLine);
            try
            {
                myWriter.append(stepLine + "\t" + plantLine + "\t" + herbivoreLine + "\t" + carnivoreLine + "\n");
            } catch (IOException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }  
    
    private void endSimulation() 
    {
        if(step > MAX_STEPS)
        {       
            try
            {
                myWriter.close();
                System.out.println("End of simulation");
                Greenfoot.stop();
            } catch (IOException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }
}
