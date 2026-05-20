import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Dog here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dog extends Actor
{
    /**
     * Act - do whatever the Dog wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private int vSpeed = 5;
    private int acceleration = 2;
    
    public void act()
    {
        // Add your action code here.
        
        if(Greenfoot.isKeyDown("space"))
        {
            jump();
            fall();
        }
        checkFall();
    }
    
    public void checkFall()
    {
        if (onGround()) 
        {
            vSpeed = 0;
        }
        else
        {
            fall();
        }
    }
    
    public boolean onGround()
    {
        Actor under = getOneObjectAtOffset(0, getImage().getHeight()/ 2, Obstacle.class);
        return under != null;
    }
    
    public void fall()
    {
        setLocation(getX(), getY() + vSpeed);
        vSpeed = vSpeed + acceleration;
    }
    
    public void jump()
    {
        vSpeed = -9;
        fall();
    }
}
