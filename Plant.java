import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An abstract class to establish all method for all plants
 * 
 * @author Bruce Gustin
 * @version ©2021.1.13
 */
public abstract class Plant extends Actor
{
    /*
     * Plants are both eaten and propagate.  Any Plant object must therefore have 
     * a method to control these.
    */
   
    protected abstract void propagate();
    protected abstract void die();
}
