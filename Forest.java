import greenfoot.*;  // (Actor, World, Greenfoot, GreenfootImage)
import java.util.List;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.File;

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
    private final String ABREVIATION = "modified";
    
    // Creates fields for writing data
    private FileWriter myWriter;
    private int step;
    private Animal animal;
    private Plant plant;
    
    private static int iteration = 0;
    private static int MAX_ITERATIONS = 5;
    
    int plants[]= new int[MAX_STEPS+5];
    int herbivores[]= new int[MAX_STEPS+5];
    int carnivores[] = new int[MAX_STEPS+5];
    
    
    
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
            int i = 0;
            boolean exists = true;
            while(exists) {
                File tempFile = new File(ABREVIATION+i+".txt");
                exists=tempFile.exists();
                i++;
            }
            
            
            myWriter = new FileWriter(ABREVIATION+(i-1)+".txt");
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
        if(iteration==0) {

            plants[step] = PlantObjects.size();
            herbivores[step] = HerbivoreObjects.size();
            carnivores[step] = CarnivoreObjects.size();
        } else {
            plants[step]  += PlantObjects.size();
            herbivores[step] += HerbivoreObjects.size();
            carnivores[step] += CarnivoreObjects.size();
        }
        if(step % STEP_REPORTING == 0)
        {
            
            String stepLine = "Step: " + step;
            String plantLine = "Plants: " + PlantObjects.size();
            String herbivoreLine = "Herbivore: " + HerbivoreObjects.size();
            String carnivoreLine = "Carnivore: " + CarnivoreObjects.size();
            System.out.format("%15s%13s%16s%18s%14s\n", "Iteration: " +iteration, stepLine, plantLine, herbivoreLine, carnivoreLine);
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
        if(iteration >= MAX_ITERATIONS) {
            try
            {
                FileWriter averageFile = new FileWriter(ABREVIATION+"-average.txt");
                for(int i =1; i < MAX_STEPS+1; i++) {
                    if(i%STEP_REPORTING==0) {
                        System.out.print("Step: " + i+ "\t" + "Plants: " + plants[i]+ "\t" + "Herbivore: " + herbivores[i]+ "\t" + "Carnivore: " + carnivores[i] +"\n");
    
                        averageFile.append("Step: " + i+ "\t" + "Plants: " + (plants[i]/MAX_ITERATIONS)+ "\t" + "Herbivore: " + (herbivores[i]/MAX_ITERATIONS)+ "\t" + "Carnivore: " + (carnivores[i]/MAX_ITERATIONS) +"\n");
                    }
                }
                
                
                averageFile.close();
                System.out.println("End of simulation");
                Greenfoot.stop();
            } catch (IOException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        if(step > MAX_STEPS)
        {    
            iteration++;
            
            
            try
            {
                myWriter.close();
            } catch (IOException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            if(!(iteration >= MAX_ITERATIONS)) {
                Greenfoot.setWorld(new Forest());
            }

        }
        

    }
}
